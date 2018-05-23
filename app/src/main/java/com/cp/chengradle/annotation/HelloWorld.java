package com.cp.chengradle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by PengChen on 2018/5/23.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,ElementType.LOCAL_VARIABLE,ElementType.PACKAGE})
public @interface HelloWorld {
    int      type();
    String   name();
}
