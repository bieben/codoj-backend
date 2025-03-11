package com.ben.codoj.judge.codesandbox;

import com.ben.codoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("Code Sandbox Request Info: " + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("Code Sandbox Response Info: " + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
