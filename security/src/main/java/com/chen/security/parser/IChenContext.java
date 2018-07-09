package com.chen.security.parser;

/**
 * Created by PengChen on 2018/7/9.
 */

public interface IChenContext {
    void registerChenState();
    void AddKey(String key);
    void parser(String str);
}
