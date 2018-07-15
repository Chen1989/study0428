package com.chen.security.pool;

/**
 * Created by PengChen on 2018/7/15.
 */

public class Test {
    public static void main(String[] arg) {
        //创建对象池工厂
        ObjectPoolFactory poolFactory = ObjectPoolFactory.getInstance();
        //定义所创建对象池的属性
        ParameterObject paraObj = new ParameterObject(2,1);
        //利用对象池工厂,创建一个存放StringBuffer类型对象的对象池
        ObjectPool pool = poolFactory.createPool(paraObj,StringBuffer.class);
        //从池中取出一个StringBuffer对象
        StringBuffer buffer = (StringBuffer)pool.getObject();
        //使用从池中取出的StringBuffer对象
        buffer.append("hello");
        System.out.println(buffer.toString());
        buffer = (StringBuffer)pool.getObject();
        //使用从池中取出的StringBuffer对象
        buffer.append("hello1");
        System.out.println(buffer.toString());
        buffer = (StringBuffer)pool.getObject();
        //使用从池中取出的StringBuffer对象
        buffer.append("hello2");
        System.out.println(buffer.toString());

        buffer = (StringBuffer)pool.getObject();
        //使用从池中取出的StringBuffer对象
        buffer.append("hello3");
        System.out.println(buffer.toString());
    }
}
