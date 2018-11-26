package com.cp.chengradle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.StringTokenizer;

/**
 * Created by PengChen on 2018/7/10.
 */

public class ChenStartActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chen_start);
//        new ReadFile().readFile(this);
//        String str = "chen{}return abc;";
//        Log.i("ChenSdk", "str = ");
        String name = "com.chen.name.change.kl.dom.cm";
        String result = zzb(name, ".", 3);
        Log.i("ChenSdk", "str = " + result);
    }

    String zzb(String var1, String var2, int var3) {
        StringTokenizer var4 = new StringTokenizer(var1, var2);
        StringBuilder var5 = new StringBuilder();
        if(var3-- > 0 && var4.hasMoreElements()) {
            var5.append(var4.nextToken());

            while(var3-- > 0 && var4.hasMoreElements()) {
                var5.append(".").append(var4.nextToken());
            }

            return var5.toString();
        } else {
            return var1;
        }
    }
}
