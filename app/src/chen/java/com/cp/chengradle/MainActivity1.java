package com.cp.chengradle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String api = BuildConfig.Chen_Api;
        Log.i("ChenSdk", "api = " + api);
    }
}
