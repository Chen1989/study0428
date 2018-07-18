package com.chen.security.pool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by PengChen on 2018/7/15.
 */

public class Test {
    public static void main(String[] arg) {
        System.out.println("+++++++++++++++     "+System.currentTimeMillis());
        //创建对象池工厂
        ObjectPoolFactory poolFactory = ObjectPoolFactory.getInstance();
        //定义所创建对象池的属性
        ParameterObject paraObj = new ParameterObject(2,2);
        //利用对象池工厂,创建一个存放ChenObject类型对象的对象池
        ObjectPool pool = poolFactory.createPool(paraObj,ChenObject.class);
        for (int i = 0; i < 20000; i++) {
            //从池中取出一个ChenObject对象
            ChenObject chenObject = (ChenObject)pool.getObject();
            pool.returnObject(chenObject);
        }
        System.out.println("+++++++++++++++  size   "+pool.size());
        System.out.println("+++++++++++++++     "+System.currentTimeMillis());
        for (int i = 0; i < 20000; i++) {
            new ChenObject();
        }
        System.out.println("+++++++++++++++     "+System.currentTimeMillis());
        AtomicInteger  ato = new AtomicInteger();
    }
}
