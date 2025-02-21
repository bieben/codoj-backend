package com.ben.codoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ben.codoj.model.dto.problem.ProblemQueryRequest;
import com.ben.codoj.model.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ben.codoj.model.vo.ProblemVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author yitezheng
* @description 针对表【problem(题目)】的数据库操作Service
* @createDate 2025-02-18 00:14:37
*/
public interface ProblemService extends IService<Problem> {

    /**
     * 校验
     *
     * @param problem
     * @param add
     */
    void validProblem(Problem problem, boolean add);

    /**
     * 获取查询条件
     *
     * @param problemQueryRequest
     * @return
     */
    QueryWrapper<Problem> getQueryWrapper(ProblemQueryRequest problemQueryRequest);
    
    /**
     * 获取题目封装
     *
     * @param problem
     * @param request
     * @return
     */
    ProblemVO getProblemVO(Problem problem, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param problemPage
     * @param request
     * @return
     */
    Page<ProblemVO> getProblemVOPage(Page<Problem> problemPage, HttpServletRequest request);

}
