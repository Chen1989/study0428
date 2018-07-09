package com.chen.security.parser;

/**
 * Created by PengChen on 2018/7/9.
 *
 * java -jar bin\\classes.jar encKey $(src) $(tar)
 */

public class ChenContextImp implements IChenContext{
    private ChenState startChenState;

    @Override
    public void registerChenState() {
        startChenState = new StartChenState();
    }

    @Override
    public void AddKey(String key) {

    }

    @Override
    public void parser(String str) {

    }
}
