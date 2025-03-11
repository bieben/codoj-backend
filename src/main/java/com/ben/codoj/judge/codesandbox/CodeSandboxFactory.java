package com.ben.codoj.judge.codesandbox;

import com.ben.codoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.ben.codoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.ben.codoj.judge.codesandbox.impl.ThridPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）（
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱实例
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
         switch (type) {
             case "example":
                return new ExampleCodeSandbox();
             case "remote":
                 return new RemoteCodeSandbox();
             case "thirdParty":
                 return new ThridPartyCodeSandbox();
             default:
                 return new ExampleCodeSandbox();
         }
    }
}
