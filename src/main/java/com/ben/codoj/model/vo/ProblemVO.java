package com.ben.codoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.ben.codoj.model.dto.problem.JudgeConfig;
import com.ben.codoj.model.entity.Problem;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目封装类
 * @TableName problem
 */
@Data
public class ProblemVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判断配置（json 数组）
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交用户的信息
     */
    private UserVO userVO;

    /**
     * 提交题目信息
     */
    private ProblemVO problemVO;


    /**
     * 包装类转对象
     *
     * @param problemVO
     * @return
     */
    public static Problem voToObj(ProblemVO problemVO) {
        if (problemVO == null) {
            return null;
        }
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemVO, problem);
        List<String> tagList = problemVO.getTags();
        if (tagList != null) {
            problem.setTags(JSONUtil.toJsonStr(tagList));
        }

        JudgeConfig voJudgeConfig = problemVO.getJudgeConfig();
       if (voJudgeConfig != null) {
           problem.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
       }
        return problem;
    }

    /**
     * 对象转包装类
     *
     * @param problem
     * @return
     */
    public static ProblemVO objToVo(Problem problem) {
        if (problem == null) {
            return null;
        }
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        List<String> tagList = JSONUtil.toList(problem.getTags(), String.class);
        problemVO.setTags(tagList);
        String judgeConfigStr = problem.getJudgeConfig();
        problemVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return problemVO;
    }

    private static final long serialVersionUID = 1L;
}