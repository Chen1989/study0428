package com.cp.chengradle.annotation;

import java.lang.reflect.Method;

/**
 * Created by PengChen on 2018/5/23.
 */

@HelloWorld(type = 1, name = "hello")

public class AnnotationTest {
    @HelloWorld(type = 2, name = "good")

    public static void test() {

        //扫描类上的注解。

        if (AnnotationTest.class.isAnnotationPresent(HelloWorld.class)) {

            HelloWorld helloWorld = (HelloWorld) AnnotationTest.class.getAnnotation(HelloWorld.class);

            System.out.println("type:" + helloWorld.type() + " , name:" + helloWorld.name());

        }


        //扫描方法上的注解。

        try {

            Method method = AnnotationTest.class.getMethod("test");

            if (method.isAnnotationPresent(HelloWorld.class)) {

                HelloWorld helloWorld = (HelloWorld) method.getAnnotation(HelloWorld.class);

                System.out.println("type:" + helloWorld.type() + " , name:" + helloWorld.name());

            }

        } catch (NoSuchMethodException | SecurityException e) {

            e.printStackTrace();

        }
    }
}
