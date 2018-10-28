package com.cp.chengradle.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;


/**
 * Created by PengChen on 2018/10/24.
 */

public class ChenService extends Service {
    String TAG = "ChenSdk_Sdk";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String getBattery(Context var0) {
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

            return "battery : " + String.valueOf(var6) + ", charging : " + var5;
//            f.put("battery", String.valueOf(var6));
//            f.put("charging", var5?"1":"0");
        }
        return "";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocationUtils.getInstance(this).showLocation();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);//监听解锁屏
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//监听蓝牙打开关闭
        filter.addAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        Log.i(TAG, "battery = " + getBattery(this));

        SharedPreferences preferences = getSharedPreferences("person", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("age", 28);
        editor.putString("name", "Chen_jj");
        editor.putBoolean("sex", true);
        editor.putFloat("height", 171.5F);
        editor.commit();

        HashMap<String, Object> map = (HashMap<String, Object>) preferences.getAll();
        SensorUtil.a(this);
        String a = "chen".substring(0, 4);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "map = " + SensorUtil.getSensorInfo().toString());
            }
        }).start();


        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "intent.getAction() = " + intent.getAction());

                SharedPreferences preferences = getSharedPreferences("collectionInfo", 0);
                int value = preferences.getInt(intent.getAction(), 0);
                preferences.edit().putInt(intent.getAction(), value + 1);

                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    Log.i(TAG, "Chen_chen android.net.conn.CONNECTIVITY_CHANGE");
                }
                if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    //BluetoothAdapter.STATE_OFF;关闭，正在关闭，打开，正在打开
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    Log.i(TAG, "state = " + state);

                }
            }
        }, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
