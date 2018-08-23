package com.cp.chengradle;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends Activity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String api = BuildConfig.Chen_Api;
        Log.i("ChenSdk", "api = " + api);
        Log.i("ChenSdk", "BuildConfig.DEBUG = " + UUID.randomUUID().toString());
        byte[] resu = Base64.encode("你好".getBytes(), Base64.DEFAULT);
        byte[] test = new byte[]{'c','h','e','n',',','h','e','l','l','o'};
        for (int i = 0; i < test.length; i++) {
            if (i > 1) {
                test[i] = (byte) (test[i] ^ test[i - 1]);

            }
        }
        int v = (int)System.nanoTime();

//        ChenTestClass.testSingleton();


        AtomicInteger a = new AtomicInteger(0);
        int result = a.incrementAndGet();
        Log.i("ChenSdk", "result = CCCCCCCCCCC" + result);
        if (a.compareAndSet(1, 3)) {
            Log.i("ChenSdk", "result = AAAAAAAAAA");
        } else {
            Log.i("ChenSdk", "result = BBBBBBBB");
        }
//        try {
//            ApplicationInfo info = getPackageManager().getApplicationInfo("com.blue.chen", 0);
//            if (info == null) {
//                Log.i("ChenSdk", "info is null. ");
//            }
//            String md5Apk = md5Apk(new File(info.sourceDir));
//            Log.i("ChenSdk", "md5Apk = " + md5Apk);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

//        getWindow().getAttributes().flags |=
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().getAttributes().alpha = (float) 0;
//        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
//        setTheme(android.R.style.Theme_Translucent_NoTitleBar);

//        PendingIntent var1 = PendingIntent.getActivity(this, 0, new Intent(), 0);
//        String testResult =  Build.VERSION.SDK_INT >= 17?var1.getCreatorPackage():var1.getTargetPackage();
//        System.getenv("PATH");
//        IContext jsonContext = new JsonContext();
//        State state = new StartState(jsonContext);
//        state.handle("{\"name\":\"chen\",\"age\":23.56.89,\"sex\":\"男\"}");
//        hookStartActivity();
//        hookActivityStartActivity(this);
//        Intent intent = new Intent(this, StartActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        File apkFile = new File("/sdcard/test/com.iqoption.apk");
//        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//        startActivity(intent);
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(5)         // (Optional) How many method line to show. Default 5
////                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
////                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag("Chen_Sdk")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
//            @Override
//            public boolean isLoggable(int priority, @Nullable String tag) {
//                return super.isLoggable(priority, tag);
//            }
//        });
//        Logger.i("你好");
//        Logger.i("ChenSdk_%s", "cacacacacacacac");
//        Logger.i("ChenSdk_%s", "这是什么情况");
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);

        List<Integer> l = new ArrayList();
        l.stream().filter(i -> i % 2 == 0);

        List<Integer> list =  Arrays.asList(3,4,5,6,7).stream()
                .map(na -> na.intValue())
                .filter(integer -> integer % 2 == 0)
                .collect(Collectors.toList());
        Log.i("ChenSdk", "list = " + list.toString());
    }

    private void hookActivityStartActivity(Activity activity) {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.Activity");

            // 拿到原始的 mInstrumentation字段
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(activity);

            // 创建代理对象
            Instrumentation chenInstrumentation = new ChenInstrumentation(mInstrumentation, activity);

            // 偷梁换柱
            mInstrumentationField.set(activity, chenInstrumentation);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    private void hookStartActivity() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 拿到原始的 mInstrumentation字段
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

            // 创建代理对象
            Instrumentation chenInstrumentation = new ChenInstrumentation(mInstrumentation);

            // 偷梁换柱
            mInstrumentationField.set(currentActivityThread, chenInstrumentation);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("ChenSdk", "result = BBBBBBBB");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("ChenSdk", "result = AAAAAAAA");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        finish();
    }

    private static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,//"com.taxslayerRFC",
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static final String md5Apk(File var0) {
        try {
            MessageDigest var1 = MessageDigest.getInstance("MD5");
            byte[] var2 = new byte[1024];
            FileInputStream var4 = new FileInputStream(var0);

            int var3;
            do {
                var3 = var4.read(var2);
                if(var3 > 0) {
                    var1.update(var2, 0, var3);
                }
            } while(var3 != -1);

            var4.close();
            return a(var1.digest());
        } catch (Exception var5) {
            return null;
        }
    }

    private static final String a(byte[] var0) {
        StringBuilder var1 = new StringBuilder();
        byte[] var2 = var0;
        int var3 = var0.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte var5 = var2[var4];
            var1.append(Integer.toString((var5 & 255) + 256, 16).substring(1));
        }

        return var1.toString();
    }
}
