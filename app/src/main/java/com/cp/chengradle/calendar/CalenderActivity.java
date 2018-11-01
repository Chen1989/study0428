package com.cp.chengradle.calendar;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.cp.chengradle.R;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

/**
 * Created by PengChen on 2018/9/7.
 */

public class CalenderActivity extends Activity {

    private ViewPager viewPager;
    private TextView monthView;
    private TextView detailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);
        viewPager = findViewById(R.id.calendar_viewpager);
        monthView = findViewById(R.id.calendar_title);
        detailTextView = findViewById(R.id.calendar_detail);
        Animation animation = new ScaleAnimation(0, 0, 0, 200);
        detailTextView.startAnimation(animation);

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int count = year * 12 + month;
        monthView.setText(year + "年" + (month + 1) + "月");
        CalendarPagerAdapter adapter = new CalendarPagerAdapter(this, count, new CalendarPagerAdapter.ClickDelegate() {
            @Override
            public void click(View view, int index) {
                detailTextView.setText("click view index is " + index);
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(count - 1);
        viewPager.setPageMargin(15);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("ChenSdk", "onPageScrolled position = " + position);
            }

            @Override
            public void onPageSelected(int position) {
                int year = (position + 1) / 12;
                int month = (position + 1) % 12 + 1;
                monthView.setText(year + "年" + month + "月");
//                Log.i("ChenSdk", "onPageSelected position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("ChenSdk", "onPageScrollStateChanged state = " + state);
            }
        });
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        Log.i("ChenSdk", "Calendar.YEAR = " + Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().get(Calendar.MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, -1);
        Log.i("ChenSdk", "year = " + calendar.get(Calendar.YEAR) + ", month = " + calendar.get(Calendar.MONTH));
//        DAY_OF_WEEK:日一二三四五六：1,2,3,4,5,6,7
//        DownLoaderManger.getInstance(this).start("appleid",
//                "http://192.168.1.157:7898/group1/M00/00/02/wKgBnVuYzyWAQYMeAHzHWDqki5k942.txt",
//                33);

        int[] src = new int[]{2, 4, 5, 10, 12, 15, 18, 21, 28, 32, 36, 39, 45, 50, 57, 65};
//        int result = DynamicProgramming.dynamiRriverCrossing(src);
        String ti = tttt("com");
        getPermission();
        Log.i("ChenSdk", "MApplication = " + ti + ", result = " + hexStringToByte(ti));
//        DynamicProgramming.Bagger03();
//        DynamicProgramming.fa(1000);
//        DynamicProgramming.maxSubStr();
//        Log.i("ChenSdk", "MApplication " + getCacheDir());
//        Log.i("ChenSdk", "MApplication External " + Environment.getExternalStorageDirectory().getPath());
//        new File(getCacheDir() + "/test/test").mkdirs();
//        DexClassLoader loader = new DexClassLoader(Environment.getExternalStorageDirectory().getPath() +
//                "/test/tes1.apk", getCacheDir() + "/test/test",
//                null, getClassLoader());
//        DexClassLoader loader2 = new DexClassLoader(Environment.getExternalStorageDirectory().getPath() +
//                "/test/test02.apk", getCacheDir() + "/test/test",
//                null, getClassLoader());
//        Log.i("ChenSdk", "MApplication" + loader);
//        File file = new File(getCacheDir() + "/test/test");
//        String[] list = file.list();
//        try {
//            Person p = new Person("computer", 200);
//            String str = DataMapping.instance(JsonSource.format()).convert(p).toString();
//            Log.i("ChenSdk", "str = " + str);
////            DataMapping.instance(JsonSource.format()).convert(JsonSource.object(""), null);
//        } catch (DataException e) {
//            e.printStackTrace();
//        }
        boolean b = notHasLightSensorManager1(getBaseContext());
        Log.i("ChenSdk", "result = " + b);
        notHasBlueTooth1(this);
//        isWifiProxy(this);
//        isVpnUsed();
//        Log.d("ChenSdk", "isVpnUsed() : " + isVpnUsed());
//        try {
//            int versionCode = getPackageManager().getPackageInfo("com.hwgg.pk", 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        startService(new Intent(getApplicationContext(), ChenService.class));
//        try {
//            String processName = getCurrentProcessName();
//            Log.d("ChenSdk", "processName : " + processName);
//            Log.d("ChenSdk", "md5ProcessName : " + md5ProcessName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public Object invokeMethod(Class<?> cl, Object host, String name, Class<?>[] clsArray, Object... pArray) {
        try {
            Method method = cl.getDeclaredMethod(name, clsArray);
            method.setAccessible(true);
            return method.invoke(host, pArray);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean notHasBlueTooth1(Context context) {
        try {
            Class cl = context.getClassLoader().loadClass("android.bluetooth.BluetoothAdapter");
            Object ob = invokeMethod(cl, null, "getDefaultAdapter", null);
            if (ob == null) {
                return true;
            }
            Object result = invokeMethod(cl, ob, "getName", null);
            if (result == null) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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

    private Boolean notHasLightSensorManager1(Context context) {

        try {
            Class cc = context.getClassLoader().loadClass("android.content.Context");
            Object ob = invokeMethod(cc, context, "getSystemService",
                    new Class[]{String.class},
                    "sensor");
            if (ob == null) {
                return true;
            }
            Class cl = context.getClassLoader().loadClass("android.hardware.SensorManager");
            Object re = invokeMethod(cl, ob, "getDefaultSensor", new Class[]{int.class}, 5);
            if (re == null) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     * @return true 为模拟器
     */
    public static Boolean notHasLightSensorManager(Context context) {

        try {
            Log.i("ChenSdk", "class = " + context.getClass());
            Log.i("ChenSdk", "class = " + Context.class);
            Method method = Context.class.getDeclaredMethod("getSystemService", String.class);
            method.setAccessible(true);
            Object ob = method.invoke(context, Context.SENSOR_SERVICE);
            Log.i("ChenSdk", "result ob = " + ob);
            if (ob == null) {
                return true;
            }
            Class sensorClass = context.getClassLoader().loadClass("android.hardware.SensorManager");
            Method sensor = sensorClass.getDeclaredMethod("getDefaultSensor", int.class);
            sensor.setAccessible(true);
            Object re = sensor.invoke(ob, 5);
            Log.i("ChenSdk", "result rev= " + re);
            if (re == null) {
                return true;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
//        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//        Sensor sensor8 = null; //光
//        if (sensorManager != null) {
//            sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        }
//        if (null == sensor8) {
//            return true;
//        } else {
//            return false;
//        }
    }

    //检测是否是虚拟机
    //https://blog.csdn.net/tianshuai4317618/article/details/78834683/
    private static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public boolean notHasBlueTooth() {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            if (TextUtils.isEmpty(name)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /*
* 判断设备 是否使用代理上网
* */
    private boolean isWifiProxy(Context context) {
// 是否大于等于4.0
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

    public boolean isVpnUsed() {
        List<PackageInfo> list = getPackageManager().getInstalledPackages(0);
        Log.d("ChenSdk", "getInstalledPackages : " + list);
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if(niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if(!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    Log.d("ChenSdk", "isVpnUsed() NetworkInterface Name: " + intf.getName());
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())){
                        return true; // The VPN is up
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    class Person implements Serializable{
        int age;
        String name;
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

//        public int getAge() {
//            return age;
//        }
//
//        public void setAge(int age) {
//            this.age = age;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
    }

    void getPermission()
    {
        int permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    124);
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 124) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
            {
                Log.d("heihei","获取到权限了！");
            }  else {
                Log.d("heihei","搞不定啊！");
            }
        }
    }



    public static String getRealName(String enc) {
        byte[] t1 = enc.getBytes(Charset.forName("UTF-8"));
        byte[] re = new byte[t1.length - 1];
        for (int i = 0; i < re.length; i++) {
            re[i] = (byte) (t1[0] ^ t1[i + 1]);
        }

        return new String(re, Charset.forName("UTF-8"));
    }

    private String tttt(String world) {
        byte te = (byte)new Random().nextInt();
        byte[] wor = world.getBytes(Charset.forName("UTF-8"));
        byte[] res = new byte[wor.length + 1];
        res[0] = te;
        for (int i = 0; i < wor.length; i++) {
            res[i + 1] = (byte)(wor[i] ^ te);
        }
        return getRealName2(res);
    }

    private static final String getRealName2(byte[] var0) {
        StringBuilder var1 = new StringBuilder();
        byte[] var2 = var0;
        int var3 = var0.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte var5 = var2[var4];
            var1.append(Integer.toString((var5 & 255) + 256, 16).substring(1));
        }

        return var1.toString();
    }

    public static String hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        byte[] res = new byte[len - 1];
        for (int i = 0; i < len - 1; i++) {
            res[i] = (byte) (result[0] ^ result[i + 1]);
        }

        return new String(res);
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }
}
