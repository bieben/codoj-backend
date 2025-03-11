package com.ben.codoj.judge;

import com.ben.codoj.model.entity.ProblemSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param problemSubmitId
     * @return
     */
    ProblemSubmit doJudge(long problemSubmitId);
}
