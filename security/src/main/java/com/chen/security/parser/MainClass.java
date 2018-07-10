package com.chen.security.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PengChen on 2018/7/10.
 */

public class MainClass {
    public static void main(String[] args) {
        //java -jar bin classes.jar encKey $(src) $(tar)
        String str = "$(src) $(tar)-$(src)";
        IChenContext context = new ChenContextImp();
        context.registerChenState();
        Map<String, String> reMap = new HashMap();
        reMap.put("src", "chen/dao");
        reMap.put("tar", "chen/chu");
        context.setReplaceMap(reMap);
        context.parser(FinalValue.StateStart, str.toCharArray(), 0);
    }
}
