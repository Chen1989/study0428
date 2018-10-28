package com.cp.chengradle.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PengChen on 2018/10/25.
 */

public class SensorUtil {

    private static SensorManager a = null;
    private static Sensor b = null;
    private static Sensor c = null;
    private static volatile float[] d;
    private static volatile float[] e;
    private static Map<String, String> f = new ConcurrentHashMap();
    private static String[] g = new String[]{"x", "y", "z"};
    private static SensorEventListener h;
    private static SensorEventListener i;

    public static synchronized void a(Context var0) {
        b(var0);
        c(var0);
        d(var0);
        if(a == null) {
            a = (SensorManager)var0.getSystemService("sensor");
            if(a == null) {
                return;
            }
        }

        if(b == null) {
            b = a.getDefaultSensor(1);
        }

        if(c == null) {
            c = a.getDefaultSensor(4);
        }

        if(h == null) {
            h = new SensorUtil.a();
            if(b != null) {
                a.registerListener(h, b, 3);
            }
        }

        if(i == null) {
            i = new SensorUtil.b();
            if(c != null) {
                a.registerListener(i, c, 3);
            }
        }

    }

    private static synchronized void d() {
        if(a != null) {
            a.unregisterListener(h);
        }

        h = null;
    }

    private static synchronized void e() {
        if(a != null) {
            a.unregisterListener(i);
        }

        i = null;
    }

    public static Map<String, String> getSensorInfo() {
        HashMap var0 = new HashMap();
        var0.putAll(f);
        a((Map)var0);
        return var0;
    }

    private static void b(Context var0) {
        ActivityManager.MemoryInfo var1 = new ActivityManager.MemoryInfo();
        ActivityManager var2 = (ActivityManager)var0.getSystemService("activity");
        var2.getMemoryInfo(var1);
        f.put("available_memory", String.valueOf(var1.availMem));
    }

    private static void c(Context var0) {
        File var1 = Environment.getDataDirectory();
        StatFs var2 = new StatFs(var1.getPath());
        long var3 = (long)var2.getBlockSize();
        long var5 = (long)var2.getAvailableBlocks();
        f.put("free_space", String.valueOf(var5 * var3));
    }

    private static void d(Context var0) {
        Intent var1 = var0.registerReceiver((BroadcastReceiver)null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if(var1 != null) {
            int var2 = var1.getIntExtra("level", -1);
            int var3 = var1.getIntExtra("scale", -1);
            int var4 = var1.getIntExtra("status", -1);
            boolean var5 = var4 == 2 || var4 == 5;
            float var6 = 0.0F;
            if(var3 > 0) {
                var6 = (float)var2 / (float)var3 * 100.0F;
            }

            f.put("battery", String.valueOf(var6));
            f.put("charging", var5?"1":"0");
        }
    }

    private static void a(Map<String, String> var0) {
        float[] var1 = d;
        float[] var2 = e;
        int var3;
        int var4;
        if(var1 != null) {
            var3 = Math.min(g.length, var1.length);

            for(var4 = 0; var4 < var3; ++var4) {
                var0.put("accelerometer_" + g[var4], String.valueOf(var1[var4]));
            }
        }

        if(var2 != null) {
            var3 = Math.min(g.length, var2.length);

            for(var4 = 0; var4 < var3; ++var4) {
                var0.put("rotation_" + g[var4], String.valueOf(var2[var4]));
            }
        }

    }

    private static class b implements SensorEventListener {
        private b() {
        }

        public void onSensorChanged(SensorEvent var1) {
            SensorUtil.e = var1.values;
            SensorUtil.e();
        }

        public void onAccuracyChanged(Sensor var1, int var2) {
        }
    }

    private static class a implements SensorEventListener {
        private a() {
        }

        public void onSensorChanged(SensorEvent var1) {
            SensorUtil.d = var1.values;
            SensorUtil.d();
        }

        public void onAccuracyChanged(Sensor var1, int var2) {
        }
    }
}
