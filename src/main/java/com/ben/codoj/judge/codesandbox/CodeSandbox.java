package com.ben.codoj.judge.codesandbox;

import com.ben.codoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
