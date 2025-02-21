package com.ben.codoj.model.dto.problem;

import lombok.Data;

/**
 * 运行用例
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;
    /**
     * 输出用例
     */
    private String output;
}
