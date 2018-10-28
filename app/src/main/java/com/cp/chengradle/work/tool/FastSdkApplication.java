package com.cp.chengradle.work.tool;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;


public class FastSdkApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        loadInnerSdk(this);
        super.onCreate();
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
