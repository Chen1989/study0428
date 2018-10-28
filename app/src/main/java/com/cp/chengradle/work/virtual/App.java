package com.cp.chengradle.work.virtual;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.omg.heart.free.sdk.ver.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dalvik.system.DexClassLoader;

/**
 * Created by ASUS on 2017/10/10.
 *
 * 名字的随机使用一个虚拟机名字和另一个字符串表示所有包括反射在内的所有字符串，解析，使用一种加密算法
 */

public class App extends Application {

    private Object instance;
    private Class localClass;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            //调用 onCreate方法
            String create = hexStringToByte(Config.Create);
            Method methodonCreate = localClass.getDeclaredMethod(create, new Class[]{});
            methodonCreate.setAccessible(true);
            methodonCreate.invoke(instance, new Object[]{});
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            System.setProperty("vrMachineName", Config.VrMachineName);
            String name = hexStringToByte(Config.StartName);
            localClass = loaderPath(base).loadClass(name);
            instance = instance(localClass, base);
//            ReflectHelper.InsertParent(getClassLoader(), createWrapper(base, getClassLoader()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object instance(Class localClass, Context base) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor<?> localConstructor = localClass.getConstructor(new Class[]{});
        Object instance = localConstructor.newInstance(new Object[]{});

        Method methodOnCreate = localClass.getDeclaredMethod(hexStringToByte(Config.attCon), Context.class);
        methodOnCreate.setAccessible(true);
        methodOnCreate.invoke(instance, base);
        return instance;
    }

    public DexClassLoader loaderPath(Context context) {
        String processName = md5ProcessName();
        String libPath = context.getCacheDir().getAbsolutePath() + File.separator + processName + ".apk";

        new TrackBook().copyApk(libPath, context);
        try {
            context.getAssets().getClass().getDeclaredMethod(
                    hexStringToByte(Config.addPath), String.class)
                    .invoke(context.getAssets(), libPath);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        DexClassLoader loader = new DexClassLoader(libPath, context.getCacheDir().getAbsolutePath(),
                null, context.getClass().getClassLoader());
        return loader;
    }

    public String md5ProcessName() {
        String processName = getProcessName();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(processName.getBytes());
            StringBuilder var1 = new StringBuilder();
            byte[] var2 = md.digest();
            int var4 = 0;
            for(; var4 < var2.length; ) {
                var1.append(Integer.toString((var2[var4] & 255) + 256, 16).substring(1));
                var4++;
            }

            return var1.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 返回当前的进程名
     */
    public static String getCurrentProcessName()throws Exception {

        //1. 通过ActivityThread中的currentActivityThread()方法得到ActivityThread的实例对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        activityThreadClass.getDeclaredMethods();
        Method method = activityThreadClass.getDeclaredMethod("currentProcessName");
        Object processName = method.invoke(null);

        //2. 通过activityThread的getProcessName() 方法获取进程名
//        Method getProcessNameMethod = activityThreadClass.getDeclaredMethod("getProcessName", activityThreadClass);
//        Object processName = getProcessNameMethod.invoke(activityThread);

        return processName.toString();
    }

    private String getProcessName() {
        File file = new File("/proc/" + Process.myPid() + "/cmdline");
        String processName = Config.VrMachineName;

        try {
            if (file.canRead()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                processName = reader.readLine();
                reader.close();
                return !TextUtils.isEmpty(processName) ? processName.trim() : processName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processName;
    }

    private String hexStringToByte(String hex) {
        int len = (hex.length() / 2);

        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        int i = 0;
        while (i < len) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
            i++;
        }

        byte[] res = new byte[len - 1];
        for (i = 0; i < len - 1; i++) {
            res[i] = (byte) (result[0] ^ result[i + 1]);
        }

        return new String(res);
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }
}
