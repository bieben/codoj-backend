package com.ben.codoj.judge;

import com.ben.codoj.judge.strategy.DefaultJudgeStrategy;
import com.ben.codoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.ben.codoj.judge.strategy.JudgeContext;
import com.ben.codoj.judge.codesandbox.model.JudgeInfo;
import com.ben.codoj.judge.strategy.JudgeStrategy;
import com.ben.codoj.model.entity.ProblemSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        ProblemSubmit problemSubmit = judgeContext.getProblemSubmit();
        String language = problemSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
