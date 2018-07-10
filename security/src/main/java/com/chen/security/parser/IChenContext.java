package com.chen.security.parser;

import java.util.Map;

/**
 * Created by PengChen on 2018/7/9.
 */

public interface IChenContext {
    void registerChenState();
    void AddKey(int index, String key);
    void parser(String state, char[] str, int index);
    Map getKeyMap();
    void setReplaceMap(Map map);
    Map getReplaceMap();
}
