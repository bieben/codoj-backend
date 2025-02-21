package com.ben.codoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ben.codoj.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.ben.codoj.model.dto.problemSubmit.ProblemSubmitQueryRequest;
import com.ben.codoj.model.entity.ProblemSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ben.codoj.model.entity.User;
import com.ben.codoj.model.vo.ProblemSubmitVO;

/**
* @author yitezheng
* @description 针对表【problem_submit(题目提交)】的数据库操作Service
* @createDate 2025-02-18 00:14:40
*/
public interface ProblemSubmitService extends IService<ProblemSubmit> {

    /**
     * 题目提交
     *
     * @param problemSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doProblemSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param problemSubmitQueryRequest
     * @return
     */
    QueryWrapper<ProblemSubmit> getQueryWrapper(ProblemSubmitQueryRequest problemSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param problemSubmit
     * @param loginUser
     * @return
     */
    ProblemSubmitVO getProblemSubmitVO(ProblemSubmit problemSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param problemSubmitPage
     * @param loginUser
     * @return
     */
    Page<ProblemSubmitVO> getProblemSubmitVOPage(Page<ProblemSubmit> problemSubmitPage, User loginUser);

}
