package com.cp.chengradle.messagequeue;

/**
 * Created by PengChen on 2018/5/14.
 */

public class ChenMessage {
    private String value;

    public ChenMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
