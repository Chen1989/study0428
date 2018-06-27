package com.chen.security.json;

/**
 * Created by PengChen on 2018/6/25.
 */

public class JsonToken {
    public String value;
    public int key;
    JsonToken(String value, int key) {
        this.value = value;
        this.key = key;
    }
}
