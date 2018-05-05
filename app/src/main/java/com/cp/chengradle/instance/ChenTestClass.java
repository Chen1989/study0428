package com.cp.chengradle.instance;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * Created by PengChen on 2018/5/5.
 */

public class ChenTestClass {
    public static void testSingleton() {

        //通过反射创建第一个实例
        LazyInnerSingleton instance2 = null;
        try {
            Class<LazyInnerSingleton> clazz = LazyInnerSingleton.class;
            Constructor<LazyInnerSingleton> cons = clazz.getDeclaredConstructor();
            cons.setAccessible(true);
            instance2 = cons.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建第二个实例
        LazyInnerSingleton instance1 = LazyInnerSingleton.getInstance();
        //检查两个实例的hash值
        Log.i("ChenSdk","Instance 1 hash:" + instance1.hashCode());
        Log.i("ChenSdk","Instance 2 hash:" + instance2.hashCode());


        //序列化测试
        try {
            LazyInnerSingleton instance3 = LazyInnerSingleton.getInstance();
            ObjectOutput out = null;
            out = new ObjectOutputStream(new FileOutputStream("sdcard/filename.ser"));
            out.writeObject(instance3);
            out.close();
            //deserialize from file to object
            ObjectInput in = new ObjectInputStream(new FileInputStream("sdcard/filename.ser"));
            LazyInnerSingleton instance4 = (LazyInnerSingleton) in.readObject();
            in.close();
            Log.i("ChenSdk","instance3 hashCode=" + instance3.hashCode());
            Log.i("ChenSdk","instance4 hashCode=" + instance4.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
