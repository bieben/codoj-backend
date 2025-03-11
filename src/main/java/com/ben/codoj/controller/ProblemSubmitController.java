package com.ben.codoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ben.codoj.common.BaseResponse;
import com.ben.codoj.common.ErrorCode;
import com.ben.codoj.common.ResultUtils;
import com.ben.codoj.exception.BusinessException;
import com.ben.codoj.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.ben.codoj.model.dto.problemSubmit.ProblemSubmitQueryRequest;
import com.ben.codoj.model.entity.ProblemSubmit;
import com.ben.codoj.model.entity.User;
import com.ben.codoj.model.vo.ProblemSubmitVO;
import com.ben.codoj.service.ProblemSubmitService;
import com.ben.codoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 */
@RestController
@RequestMapping("/problem_submit")
@Slf4j
@Deprecated
public class ProblemSubmitController {

    @Resource
    private ProblemSubmitService problemSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param problemSubmitAddRequest
     * @param request
     * @return 提交记录的 Id
     */
    @PostMapping("/")
    public BaseResponse<Long> doProblemSubmit(@RequestBody ProblemSubmitAddRequest problemSubmitAddRequest,
            HttpServletRequest request) {
        if (problemSubmitAddRequest == null || problemSubmitAddRequest.getProblemId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long problemSubmitId = problemSubmitService.doProblemSubmit(problemSubmitAddRequest, loginUser);
        return ResultUtils.success(problemSubmitId);
    }

    /**
     * 分页获取 题目提交列表（普通用户只能看到非答案，提交代码等公开信息）
     *
     * @param problemSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<ProblemSubmitVO>> listProblemSubmitByPage(@RequestBody ProblemSubmitQueryRequest problemSubmitQueryRequest,
                                                                       HttpServletRequest request) {
        long current = problemSubmitQueryRequest.getCurrent();
        long size = problemSubmitQueryRequest.getPageSize();
        final User loginUser = userService.getLoginUser(request);
        // 从数据库中查到了原始的提交题目数据
        Page<ProblemSubmit> problemSubmitPage = problemSubmitService.page(new Page<>(current, size),
                problemSubmitService.getQueryWrapper(problemSubmitQueryRequest));
        return ResultUtils.success(problemSubmitService.getProblemSubmitVOPage(problemSubmitPage, loginUser));
    }

}
