package com.cp.chengradle;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String api = BuildConfig.Chen_Api;
        Log.i("ChenSdk", "api = " + api);
        Log.i("ChenSdk", "BuildConfig.DEBUG = " + UUID.randomUUID().toString());

//        ChenTestClass.testSingleton();

        try {
            ApplicationInfo var2 = getPackageManager().getApplicationInfo(getPackageName(), 0);
            Log.i("ChenSdk", "var2.sourceDir = " + var2.sourceDir);
        } catch (Exception var3) {

        }

        AtomicInteger a = new AtomicInteger(0);
        int result = a.incrementAndGet();
        Log.i("ChenSdk", "result = CCCCCCCCCCC" + result);
        if (a.compareAndSet(1, 3)) {
            Log.i("ChenSdk", "result = AAAAAAAAAA");
        } else {
            Log.i("ChenSdk", "result = BBBBBBBB");
        }

        String md5Apk = md5Apk(new File("/data/app/fr.calendrierlunaire.android-1.apk"));
        Log.i("ChenSdk", "md5Apk = " + md5Apk);

//        printKeyHash(this);
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
