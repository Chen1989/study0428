package com.chen.security.serializer;

import java.io.Serializable;

/**
 * Created by PengChen on 2018/9/29.
 */

public class Person implements Serializable{
    private int age;
    private String name;
    private String sex;

    public Person(int age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }
}
