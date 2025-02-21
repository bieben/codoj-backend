package com.ben.codoj.model.dto.problemSubmit;

import lombok.Data;

import java.io.Serializable;
/**
 * 创建请求
 */
@Data
public class ProblemSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long problemId;

    private static final long serialVersionUID = 1L;
}