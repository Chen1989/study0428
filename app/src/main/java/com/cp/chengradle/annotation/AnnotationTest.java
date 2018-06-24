package com.cp.chengradle.annotation;

import java.lang.reflect.Method;

/**
 * Created by PengChen on 2018/5/23.
 *
 * 'count_down_text': '还剩 %1$d 秒','count_down_text_for_reward': '{num_seconds,plural, \\x3d1{1 秒后奖励}other{%1$d 秒后奖励}}','count_down_seconds_text_for_reward': '{NUM_SECONDS,plural, \\x3d1{1 秒后奖励}other{# 秒后奖励}}','close_confirmation_title':
 *
 *
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
