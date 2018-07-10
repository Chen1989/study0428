package com.chen.security.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PengChen on 2018/7/9.
 *
 * java -jar bin\\classes.jar encKey $(src) $(tar)
 */

public class ChenContextImp implements IChenContext{

    private Map<String, ChenState> chenStateMap = new HashMap<>();
    private Map<Integer, String> keyMap = new HashMap<>();
    private Map<String, String> replaceMap = new HashMap<>();

    @Override
    public void registerChenState() {
        chenStateMap.put(FinalValue.StateStart, new ChenStateStart(this));
        chenStateMap.put(FinalValue.StateParser, new ChenStateParser(this));
        chenStateMap.put(FinalValue.StateError, new ChenStateError(this));
        chenStateMap.put(FinalValue.StateFinish, new ChenStateFinish(this));
    }

    @Override
    public void AddKey(int index, String key) {
        keyMap.put(index, key);
    }

    @Override
    public void parser(String state, char[] str, int index) {
        chenStateMap.get(state).handleString(str, index);
    }

    @Override
    public Map getKeyMap() {
        return keyMap;
    }

    @Override
    public void setReplaceMap(Map map) {
        replaceMap = map;
    }

    @Override
    public Map getReplaceMap() {
        return replaceMap;
    }
}
