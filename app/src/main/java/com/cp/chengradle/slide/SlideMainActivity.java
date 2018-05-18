package com.cp.chengradle.slide;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.cp.chengradle.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by PengChen on 2018/5/9.
 */

public class SlideMainActivity extends Activity {

    Button btn;
    SlideController controller;
    Button openBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_view);
        btn = findViewById(R.id.btn_chen);
        btn.setTranslationX(-600);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ChenSdk", "点击了按钮 1 = " + getWindowManager().getDefaultDisplay().getHeight());
                Log.i("ChenSdk", "点击了按钮 2 = " + getResources().getDisplayMetrics().heightPixels);
            }
        });
        openBtn = findViewById(R.id.btn_chen_open);
        controller = new SlideController(btn, new SlideController.DragCallBack() {
            @Override
            public void callBackDragBegin() {

            }

            @Override
            public void callBackDrag(float detaX, float detaY, float allDetaX, float allDetaY) {
//                btn.scrollBy((int)detaX, (int)detaY);
                btn.setTranslationX(btn.getTranslationX() + detaX);
//                btn.setTranslationY(btn.getTranslationY() + detaY);
//                btn.setTop(btn.getTop() + (int)detaY);
//                btn.setBottom(btn.getBottom() + (int)detaY);
//                btn.setLeft(btn.getLeft() + (int)detaX);
//                btn.setRight(btn.getRight() + (int)detaX);
            }

            @Override
            public void callBackDragEnd(float detaX, float detaY) {
                btn.animate().translationX(detaX).start();
//                btn.setTranslationX(btn.getTranslationX() + detaX);
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Util.testMessage();
//            }
//        }).start();
        Log.i("ChenSdk", "点击了按钮 3 = " + Build.PRODUCT);

        try {
            String[] asserts = getAssets().list("");
            Log.i("ChenSdk", "asserts.length = " + getProcessName());

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private String getProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "Kingplug";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process: manager.getRunningAppProcesses()) {
            if(process.pid == pid)
            {
                processName = process.processName;

            }
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(processName.getBytes());
            return md5C(md.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return processName;
    }

    private final String md5C(byte[] var0) {
        StringBuilder var1 = new StringBuilder();
        byte[] var2 = var0;
        int var3 = var0.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte var5 = var2[var4];
            var1.append(Integer.toString((var5 & 255) + 256, 16).substring(1));
        }

        return var1.toString();
    }

    private static String getProcessName(int pid) {
        File file = new File("/proc/" + pid + "/cmdline");
        String processName = System.getProperty("vrMachineName", "Kingplug");
        if (file.canRead())
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                processName = reader.readLine();
                reader.close();
                if (processName != null && !"".equals(processName.trim()))
                    return processName.trim();

                return processName;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return processName;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            controller.onDragBegin(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            controller.onDraging(event);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            controller.onDragEnd(event);
        }
        return super.onTouchEvent(event);
    }
}
