package com.ben.codoj.judge.codesandbox.impl;

import com.ben.codoj.judge.codesandbox.CodeSandbox;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ben.codoj.judge.codesandbox.model.JudgeInfo;
import com.ben.codoj.model.enums.JudgeInfoMessageEnum;
import com.ben.codoj.model.enums.ProblemSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱，仅为跑通流程
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("Test Success");
        executeCodeResponse.setStatus(ProblemSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
