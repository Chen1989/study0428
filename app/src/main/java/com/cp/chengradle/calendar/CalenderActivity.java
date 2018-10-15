package com.cp.chengradle.calendar;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.cp.chengradle.R;
import com.cp.chengradle.serializable.DataException;
import com.cp.chengradle.serializable.DataMapping;
import com.cp.chengradle.serializable.json.JsonSource;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Random;

import dalvik.system.DexClassLoader;

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
        Log.i("ChenSdk", "MApplication " + getCacheDir());
        Log.i("ChenSdk", "MApplication External " + Environment.getExternalStorageDirectory().getPath());
        new File(getCacheDir() + "/test/test").mkdirs();
        DexClassLoader loader = new DexClassLoader(Environment.getExternalStorageDirectory().getPath() +
                "/test/tes1.apk", getCacheDir() + "/test/test",
                null, getClassLoader());
        DexClassLoader loader2 = new DexClassLoader(Environment.getExternalStorageDirectory().getPath() +
                "/test/test02.apk", getCacheDir() + "/test/test",
                null, getClassLoader());
        Log.i("ChenSdk", "MApplication" + loader);
        File file = new File(getCacheDir() + "/test/test");
        String[] list = file.list();
        try {
            Person p = new Person("computer", 200);
            String str = DataMapping.instance(JsonSource.format()).convert(p).toString();
            Log.i("ChenSdk", "str = " + str);
//            DataMapping.instance(JsonSource.format()).convert(JsonSource.object(""), null);
        } catch (DataException e) {
            e.printStackTrace();
        }
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
