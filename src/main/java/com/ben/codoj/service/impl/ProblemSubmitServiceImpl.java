package com.ben.codoj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ben.codoj.common.ErrorCode;
import com.ben.codoj.constant.CommonConstant;
import com.ben.codoj.exception.BusinessException;
import com.ben.codoj.judge.JudgeService;
import com.ben.codoj.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.ben.codoj.model.dto.problemSubmit.ProblemSubmitQueryRequest;
import com.ben.codoj.model.entity.Problem;
import com.ben.codoj.model.entity.ProblemSubmit;
import com.ben.codoj.model.entity.User;
import com.ben.codoj.model.enums.ProblemSubmitLanguageEnum;
import com.ben.codoj.model.enums.ProblemSubmitStatusEnum;
import com.ben.codoj.model.vo.ProblemSubmitVO;
import com.ben.codoj.service.ProblemService;
import com.ben.codoj.service.ProblemSubmitService;
import com.ben.codoj.mapper.ProblemSubmitMapper;
import com.ben.codoj.service.UserService;
import com.ben.codoj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author yitezheng
* @description 针对表【problem_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-02-18 00:14:40
*/
@Service
public class ProblemSubmitServiceImpl extends ServiceImpl<ProblemSubmitMapper, ProblemSubmit>
    implements ProblemSubmitService{

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    /**
     * 提交题目
     *
     * @param problemSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doProblemSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, User loginUser) {
        String language = problemSubmitAddRequest.getLanguage();
        ProblemSubmitLanguageEnum languageEnum = ProblemSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Wrong Language");
        }
        Long problemId = problemSubmitAddRequest.getProblemId();
        // 判断实体是否存在，根据类别获取实体
        Problem problem = problemService.getById(problemId);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        ProblemSubmit problemSubmit = new ProblemSubmit();
        problemSubmit.setUserId(userId);
        problemSubmit.setProblemId(problemId);
        problemSubmit.setCode(problemSubmitAddRequest.getCode());
        problemSubmit.setLanguage(problemSubmitAddRequest.getLanguage());
        problemSubmit.setStatus(ProblemSubmitStatusEnum.WAITING.getValue());
        problemSubmit.setJudgeInfo("{}");
        boolean save = this.save(problemSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        // 执行判题服务
        long problemSubmitId = problemSubmit.getId();
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(problemSubmitId);
        });

        return problemSubmitId;
    }

    /**
     * 获取查询包装类（用户根据哪些字段查询，根据前段传来的请求对象，得到 mybatis 框架支持的查询 QueryWrapper 类）
     *
     * @param problemSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<ProblemSubmit> getQueryWrapper(ProblemSubmitQueryRequest problemSubmitQueryRequest) {
        QueryWrapper<ProblemSubmit> queryWrapper = new QueryWrapper<>();
        if (problemSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String language = problemSubmitQueryRequest.getLanguage();
        Integer status = problemSubmitQueryRequest.getStatus();
        Long problemId = problemSubmitQueryRequest.getProblemId();
        Long userId = problemSubmitQueryRequest.getUserId();
        String sortField = problemSubmitQueryRequest.getSortField();
        String sortOrder = problemSubmitQueryRequest.getSortOrder();


        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(problemId), "problemId", problemId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ProblemSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public ProblemSubmitVO getProblemSubmitVO(ProblemSubmit problemSubmit, User loginUser) {
        ProblemSubmitVO problemSubmitVO = ProblemSubmitVO.objToVo(problemSubmit);
        // 脱敏：仅本人和管理员（提交 userId 和登录用户 Id 不同）能看到自己提交题目的答案
        long userId = loginUser.getId();
        // 处理脱敏
        if (userId != problemSubmit.getUserId() && !userService.isAdmin(loginUser)) {
            problemSubmitVO.setCode(null);
        }
        return problemSubmitVO;
    }

    @Override
    public Page<ProblemSubmitVO> getProblemSubmitVOPage(Page<ProblemSubmit> problemSubmitPage, User loginUser) {
        List<ProblemSubmit> problemSubmitList = problemSubmitPage.getRecords();
        Page<ProblemSubmitVO> problemSubmitVOPage = new Page<>(problemSubmitPage.getCurrent(), problemSubmitPage.getSize(), problemSubmitPage.getTotal());
        if (CollUtil.isEmpty(problemSubmitList)) {
            return problemSubmitVOPage;
        }
        List<ProblemSubmitVO> problemSubmitVOList = problemSubmitList.stream().map(problemSubmit -> {
            return getProblemSubmitVO(problemSubmit, loginUser);
        }).collect(Collectors.toList());
        problemSubmitVOPage.setRecords(problemSubmitVOList);
        return problemSubmitVOPage;
    }

}




