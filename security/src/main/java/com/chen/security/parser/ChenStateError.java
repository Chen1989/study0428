package com.chen.security.parser;

/**
 * Created by PengChen on 2018/7/10.
 */

public class ChenStateError extends ChenState {

    public ChenStateError(IChenContext chenContext) {
        super(chenContext);
    }

    @Override
    public void handleString(char[] str, int index) {
        System.out.println("错误位置：" + index);
    }
}
