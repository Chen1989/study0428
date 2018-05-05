package com.cp.chengradle.instance;

import java.io.Serializable;

/**
 * Created by PengChen on 2018/5/5.
 */

public class LazyInnerSingleton implements Serializable{
    //改变量用于放置反射创建不同的单例对象
    private static boolean initialized = false;
    private LazyInnerSingleton() {
        synchronized (LazyInnerSingleton.class) {
            if (initialized == false) {
                initialized = !initialized;
            } else {
                throw new RuntimeException("单例已被破坏");
            }
        }
    }
    static class SingletonHolder {
        private static final LazyInnerSingleton instance = new LazyInnerSingleton();
    }
    public static LazyInnerSingleton getInstance() {
        return SingletonHolder.instance;
    }

    //readResolve(）代替了从流中读取对象。这就确保了在序列化和反序列化的过程中没人可以创建新的实例。
    private Object readResolve() {
        return getInstance();
    }

}
