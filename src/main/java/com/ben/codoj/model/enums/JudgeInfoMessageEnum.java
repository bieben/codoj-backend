package com.ben.codoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息消息枚举
 */
public enum JudgeInfoMessageEnum {

    ACCEPTED("Accepted", "Accepted"),
    WRONG_ANSWER("Wrong Answer", "Wrong Answer"),
    COMPILE_ERROR("Compile Error", "Compile Error"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded", "Memory Limit Exceeded"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", "Time Limit Exceeded"),
    PRESENTATION_ERROR("Presentation Error", "Presentation Error"),
    WAITING("Waiting", "Waiting"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", "Output Limit Exceeded"),
    DANGEROUS_OPERATION("Dangerous Operation", "Dangerous Operation"),
    RUNTIME_ERROR("Runtime Error", "Runtime Error"),
    SYSTEM_ERROR("System Error", "System Error");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
