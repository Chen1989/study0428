package com.cp.chengradle.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by PengChen on 2018/8/21.
 */

public class MPRegister {

    public static void register(Context context) {

        try {
            String url = "http://uaremedia.com/bus-webapi/rest/service/mzc";
            HashMap<String, String> initParams = new HashMap<String, String>();
            initParams.put("pub_key", "Test_adplug_001");
            initParams.put("media_type", "3");
            initParams.put("phone_model", Build.MODEL);
            initParams.put("imei", getImei(context));
            initParams.put("imsi", getImsi(context));
            initParams.put("android_version", Build.VERSION.RELEASE);
            initParams.put("android_id", getAndroidID(context));
            initParams.put("serial", getSerialNumber());
            initParams.put("os", "20");
            HttpURLConnection connection = (HttpURLConnection) sendGetRequest(url, initParams, null);
            int code = connection.getResponseCode();
            Log.i("AppleID", "init code = " + code);
            String result = read2String(connection.getInputStream());
            Log.i("AppleID", "init result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static URLConnection sendGetRequest(String url, Map<String, String> params, Map<String, String> headers)
            throws Exception {
        StringBuilder buf = new StringBuilder(url);
        Set<Map.Entry<String, String>> entrys = null;
        // 如果是GET请求，则请求参数在URL中
        if (params != null && !params.isEmpty()) {
            buf.append("?");
            entrys = params.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                buf.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(buf.toString());
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("content-type","application/json;charset=utf-8");
        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.getResponseCode();
        return conn;
    }

    private static String read2String(InputStream inStream)throws Exception{
        char[] buffer = new char[1024];
        int len = 0;
        StringBuffer sb = new StringBuffer();
        InputStreamReader streamReader = new InputStreamReader(inStream, "UTF-8");
        while( (len = streamReader.read(buffer)) !=-1 ){
            sb.append(new String(buffer, 0, len));
        }
        inStream.close();
        streamReader.close();
        return sb.toString();
    }

    private static String getAndroidID(Context context) {
        String result = Settings.System.getString(context.getContentResolver(), "android_id");
        Log.i("ChenSdk", "android_id = " + result);
        if (TextUtils.isEmpty(result)) {
            result = "";
        }
        return result;
    }

    @SuppressLint("MissingPermission")
    private static String getImsi(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        String result = telephonyManager.getSubscriberId();
        Log.i("ChenSdk", "getImsi = " + result);
        if (TextUtils.isEmpty(result)) {
            result = "";
        }
        return result;
    }

    @SuppressLint("MissingPermission")
    private static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        String result = telephonyManager.getDeviceId();
        Log.i("ChenSdk", "getImei = " + result);
        if (TextUtils.isEmpty(result)) {
            result = "";
        }
        return result;
    }

    private static String getSerialNumber(){

        String serial = null;

        try {

            Class<?> c =Class.forName("android.os.SystemProperties");

            Method get =c.getMethod("get", String.class);

            serial = (String)get.invoke(c, "ro.serialno");

        } catch (Exception e) {

            e.printStackTrace();

        }
        Log.i("ChenSdk", "getSerialNumber = " + serial);
        if (TextUtils.isEmpty(serial)) {
            serial = "";
        }
        return serial;

    }
}
