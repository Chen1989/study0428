package com.cp.chengradle.annotation;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by PengChen on 2018/9/3.
 */

public class ChenProxyHandler implements InvocationHandler {

    private WeakReference<Activity> mHandlerRef;
    private HashMap<String, Method> mMethodHashMap = new HashMap<>();

    ChenProxyHandler(Activity activity) {
        mHandlerRef = new WeakReference<Activity>(activity);
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
