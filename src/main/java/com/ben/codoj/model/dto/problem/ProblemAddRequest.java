package com.ben.codoj.model.dto.problem;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 */
@Data
public class ProblemAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判断用例（json 数组）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判断配置（json 数组）
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}