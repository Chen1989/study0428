package com.cp.chengradle.annotation;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by PengChen on 2018/9/3.
 */

public class ChenBind {
    public static void bindField(Activity activity) {
        Class<? extends Activity> activityClass = activity.getClass();
        Field[] fields = activityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ChenField.class)) {
                ChenField chenField = field.getAnnotation(ChenField.class);
                int id = chenField.name();
                String initValue = chenField.value();
                try {
                    Object btnObj = activity.findViewById(id);
                    field.setAccessible(true);
                    field.set(activity, btnObj);
                    initValue(btnObj, initValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bindClickAction(Activity activity, InitValue initValue) {
        Class<? extends Activity> activityClass = activity.getClass();
        Method[] methods = activityClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ChenClickAction.class)) {
                ChenClickAction chenField = method.getAnnotation(ChenClickAction.class);
                int[] id = chenField.name();
                for (int i = 0; i < id.length; i++) {
                    activity.findViewById(id[i]).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.setAccessible(true);
                                method.invoke(activity, v);
//                            initValue.setValue(value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        }
    }

    private static void initValue(Object obj, String value) {
        //考虑value为空的情况,TextView是文字控件的父类
        if (obj instanceof TextView) {
            ((Button)obj).setText(value);
        }
    }

    public interface InitValue {
        void setValue(Object obj, String init);
    }
}
