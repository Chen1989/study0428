package com.cp.chengradle.json;

/**
 * Created by admin on 2018/6/24.
 */

public abstract class State {
    protected IContext mContext;
    public State(IContext context) {
        this.mContext=context;
    }
    public abstract void handle(String action);
}
