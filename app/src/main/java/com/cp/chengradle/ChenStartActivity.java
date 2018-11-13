package com.cp.chengradle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cp.chengradle.just.ReadFile;

/**
 * Created by PengChen on 2018/7/10.
 */

public class ChenStartActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chen_start);
        new ReadFile().readFile(this);
//        String str = "chen{}return abc;";
//        Log.i("ChenSdk", "str = " + str.substring(str.indexOf("return") + 6, str.lastIndexOf(";")));
    }
}
