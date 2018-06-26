package com.cp.chengradle.json;

/**
 * Created by admin on 2018/6/24.
 */

public interface IContext {
    void setState(State state);
    void addToken(JsonToken token);
    void clearToken();
}
