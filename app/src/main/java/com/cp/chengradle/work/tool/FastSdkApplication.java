package com.cp.chengradle.work.tool;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;

import dalvik.system.DexClassLoader;


public class FastSdkApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        try {
            String processName = getCurrentProcessName();
            Log.d("ChenSdk", "processName AAAAAAAAAAAA: " + processName);
            Log.d("ChenSdk", "md5ProcessName AAAAAAAAAA: " + md5ProcessName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
//        loadInnerSdk(this);

        super.onCreate();
    }

    public static String md5ProcessName() {
        String processName = "";
        try {
            processName = getCurrentProcessName();
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
        } catch (Exception e) {
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


    /*
     * AssetName=core #待加载lib在asset下的名称 ClassName=com.dmy.FastSdkApplication
     * #与JNI绑定的java类名称 BasePath=getFilesDir().getParent() #应用私有目录
     *
     * Framework=base #基础库加载的so名称
     */
    public void loadInnerSdk(Context context) {
        File var2 = null;
        try {
            var2 = new FileOp().$release(context);
            DexClassLoader var4 = new DexClassLoader(var2.getPath(), var2.getParent(), null, context.getClassLoader().getParent());
            Class<?> var5 = var4.loadClass(StartPanSdk.$decode("com.sdk.entry.SdkEntry"));
            Method var6 = var5.getMethod(StartPanSdk.$decode("startLoad"), Context.class, String.class);
            var6.setAccessible(true);
            var6.invoke(null, context, StartPanSdk.$decode("PluginConfig"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new SdkPanDecode().deleteTemp(var2.getParentFile());
        }
    }
}
