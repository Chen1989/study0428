package com.chen.security.pool;

/**
 * Created by PengChen on 2018/7/17.
 */

public class ChenObject {
    public ChenObject() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < 20000; i++) {
            stringBuilder.append("chen");
        }
    }
}
