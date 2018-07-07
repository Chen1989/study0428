package com.chen.security.json;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PengChen on 2018/6/27.
 */

public class Test {
    public static void main(String[] args) {
//        System.out.println("start time = " + System.currentTimeMillis());
//        for (int i = 0; i < 10000; i++) {
//            IContext jsonContext = new JsonContext();
//            State state = new StartState(jsonContext);
//            state.handle("{\"name\":\"chen\",\"age\":23.56,\"sex\":\"男\"}");
//        }
//        System.out.println("start time = " + System.currentTimeMillis());

        Map<String, Integer> map = new HashMap<>();
        map.put("name", 3);
        try {
            int i = map.get("chan");
        } catch (Exception e) {

        }

        String str = String.valueOf(map.get("chen"));
        System.out.println("start time = " + str);
    }

    static class Key {
        public String name;
    }
}
