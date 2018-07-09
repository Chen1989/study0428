package com.chen.security.parser;

/**
 * Created by PengChen on 2018/7/9.
 */

public abstract class ChenState {
    private IChenContext chenContext;

    public ChenState(IChenContext chenContext) {

    }
    public abstract void handleString();
}
