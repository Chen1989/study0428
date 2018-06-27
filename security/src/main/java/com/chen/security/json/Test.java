package com.chen.security.json;

/**
 * Created by PengChen on 2018/6/27.
 */

public class Test {
    public static void main(String[] args) {
        System.out.println("start time = " + System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            IContext jsonContext = new JsonContext();
            State state = new StartState(jsonContext);
            state.handle("{\"name\":\"chen\",\"age\":23.56,\"sex\":\"男\"}");
        }
        System.out.println("start time = " + System.currentTimeMillis());
    }
}
