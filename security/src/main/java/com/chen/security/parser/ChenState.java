package com.chen.security.parser;

/**
 * Created by PengChen on 2018/7/9.
 *
 * java -jar bin\\classes.jar encKey $(src) $(tar)
 */

public abstract class ChenState {
    protected IChenContext mChenContext;

    public ChenState(IChenContext chenContext) {
        mChenContext = chenContext;
    }
    public abstract void handleString(char[] str, int index);
}
