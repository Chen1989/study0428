package com.cp.chengradle.annotation;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cp.chengradle.R;

import java.util.Random;

/**
 * Created by PengChen on 2018/9/3.
 */

public class ChenSecondActivity extends Activity {

    @ChenField(name = R.id.btn_field_chen, value = "chen")
    private Button btn;
    private String btnValue;

    @ChenField(name = R.id.btn_click_chen, value = "chenA")
    private Button btn_click;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_view);
        initBind();
        if (btn == null) {
            Log.i("ChenSdk", " btn is null ");
        }
        try {
            int v =getPackageManager().getPackageInfo("com.flextrick.fringerprintscannertools", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initBind() {
        ChenBind.bindField(this);
        ChenBind.bindClickAction(this, null);
    }

    private void changeValue(Button button, String value) {
        button.setText(value);

    }

    @ChenClickAction(name = {R.id.btn_field_chen, R.id.btn_click_chen})
    private void btnClickAction(View view) {
        if (view.getId() == R.id.btn_field_chen) {
            Log.i("ChenSdk", " btn was clicked ");
        } if (view.getId() == R.id.btn_click_chen) {
            btnValue = "Chen_Click" + new Random().nextInt(30);
            changeValue(btn_click, btnValue);
        }
    }

//    private static String getImsi(Context context) {
//        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
//        String result = telephonyManager.getSubscriberId();
//        if (TextUtils.isEmpty(result)) {
//            result = "";
//        }
//        return result;
//    }
}
