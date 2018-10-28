package com.chen.security.serializer;

import java.io.Serializable;

/**
 * Created by PengChen on 2018/10/18.
 */

public enum ChenEnum implements Serializable {
    INSTANCE1(9),
    INSTANCE2(10);
    int age;
    public void printChen() {

    }

    ChenEnum() {
        this.age = 0;
    }

    ChenEnum(int age) {
        this.age = age;
    }

    public static void chen2() {

    }
}
