package com.chen.security.parser;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by PengChen on 2018/7/10.
 *
 * "k$(src) $(tar)s"
 *
 */

public class ChenStateFinish extends ChenState {
    public ChenStateFinish(IChenContext chenContext) {
        super(chenContext);
    }

    @Override
    public void handleString(char[] str, int index) {
        String result = new String(str);
        Map keyMap = mChenContext.getKeyMap();
        Map replaceMap = mChenContext.getReplaceMap();
        Set<Integer> set=keyMap.keySet();
        Iterator<Integer> iterator=set.iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer) iterator.next();
            System.out.println(key+" >> "+keyMap.get(key));
            String re = "$(" + keyMap.get(key) + ")";
            result = result.replace(re, (CharSequence) replaceMap.get(keyMap.get(key)));
        }
        System.out.println("result = " + result);
    }
}
