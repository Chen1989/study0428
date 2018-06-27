package com.chen.security.json;

/**
 * Created by admin on 2018/6/24.
 */

public abstract class State {
    protected IContext mContext;
    public final static int State_Start=0;
    public final static int Start_Number=1;
    public final static int Start_Float=1;
    public final static int Start_String=1;
    public final static int Start_Error=1;

    public State(IContext context) {
        this.mContext=context;
    }

    public abstract void handle(String jsonString);
}
