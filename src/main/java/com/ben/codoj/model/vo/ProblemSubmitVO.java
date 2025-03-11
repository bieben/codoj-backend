package com.ben.codoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.ben.codoj.judge.codesandbox.model.JudgeInfo;
import com.ben.codoj.model.entity.ProblemSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交封装类
 * @TableName problem
 */
@Data
public class ProblemSubmitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long problemId;

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
     * 包装类转对象
     *
     * @param problemSubmitVO
     * @return
     */
    public static ProblemSubmit voToObj(ProblemSubmitVO problemSubmitVO) {
        if (problemSubmitVO == null) {
            return null;
        }
        ProblemSubmit problemSubmit = new ProblemSubmit();
        BeanUtils.copyProperties(problemSubmitVO, problemSubmit);
        JudgeInfo judgeInfoObj = problemSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            problemSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return problemSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param problemSubmit
     * @return
     */
    public static ProblemSubmitVO objToVo(ProblemSubmit problemSubmit) {
        if (problemSubmit == null) {
            return null;
        }
        ProblemSubmitVO problemSubmitVO = new ProblemSubmitVO();
        BeanUtils.copyProperties(problemSubmit, problemSubmitVO);
        String judgeInfoStr = problemSubmit.getJudgeInfo();
        problemSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));
        return problemSubmitVO;
    }

    private static final long serialVersionUID = 1L;
}