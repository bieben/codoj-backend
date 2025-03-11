package com.ben.codoj.judge;

import cn.hutool.json.JSONUtil;
import com.ben.codoj.common.ErrorCode;
import com.ben.codoj.exception.BusinessException;
import com.ben.codoj.judge.codesandbox.CodeSandbox;
import com.ben.codoj.judge.codesandbox.CodeSandboxFactory;
import com.ben.codoj.judge.codesandbox.CodeSandboxProxy;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ben.codoj.judge.strategy.JudgeContext;
import com.ben.codoj.model.dto.problem.JudgeCase;
import com.ben.codoj.judge.codesandbox.model.JudgeInfo;
import com.ben.codoj.model.entity.Problem;
import com.ben.codoj.model.entity.ProblemSubmit;
import com.ben.codoj.model.enums.ProblemSubmitStatusEnum;
import com.ben.codoj.service.ProblemService;
import com.ben.codoj.service.ProblemSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private ProblemService problemService;

    @Resource
    private ProblemSubmitService problemSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;


    @Override
    public ProblemSubmit doJudge(long problemSubmitId) {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        ProblemSubmit problemSubmit = problemSubmitService.getById(problemSubmitId);
        if (problemSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long problemId = problemSubmit.getProblemId();
        Problem problem = problemService.getById(problemId);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!problemSubmit.getStatus().equals(ProblemSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行
        ProblemSubmit problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setStatus(ProblemSubmitStatusEnum.RUNNING.getValue());
        boolean update = problemSubmitService.updateById(problemSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = problemSubmit.getLanguage();
        String code = problemSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = problem.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setProblem(problem);
        judgeContext.setProblemSubmit(problemSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6）修改数据库中的判题结果
        problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setStatus(ProblemSubmitStatusEnum.SUCCEED.getValue());
        problemSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = problemSubmitService.updateById(problemSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        return problemSubmitService.getById(problemId);
    }
}
