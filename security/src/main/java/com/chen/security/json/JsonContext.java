package com.chen.security.json;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengChen on 2018/6/25.
 * 符号：0
 * 数字：1
 * String：2
 * 点(.)：3
 */

public class JsonContext implements IContext{
    private List<JsonToken> tokenList = new ArrayList<>();
    private State startState;
    private State stringState;
    private State numberState;
    private State floatState;
    private State errorState;
    private State finishState;
    private State mState;

    JsonContext() {
        stringState = new StringState(this);
        startState = new StartState(this);
        numberState = new NumberState(this);
        floatState = new FloatState(this);
        errorState = new ErrorState(this);
        mState = startState;
//        startState = new StartState(this);
//        startState = new StartState(this);
    }

    @Override
    public void setState(State state) {
        mState = state;
    }

    @Override
    public void addToken(JsonToken token) {
        tokenList.add(token);
    }

    @Override
    public void clearToken() {
        tokenList.clear();
    }

    @Override
    public String toString() {
        for (int i = 0; i < tokenList.size(); i++) {
            Log.i("SdkJson", "value = " + tokenList.get(i).value + ", key = " + tokenList.get(i).key);
        }
        return super.toString();
    }

}
