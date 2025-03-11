package com.ben.codoj.judge.codesandbox.impl;

import com.ben.codoj.judge.codesandbox.CodeSandbox;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ben.codoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThridPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("ThirdParty CodeSandbox");
        return null;
    }
}
