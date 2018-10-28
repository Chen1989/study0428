package com.cp.chengradle.work.tool;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

import static android.content.res.AssetManager.ACCESS_STREAMING;

/**
 * Created by PengChen on 2018/9/17.
 */

public class FileOp {

    public static synchronized File $release(Context context) {
        //TODO
        Random var1 = new Random();
        char[] var2 = new char[6];
        StringBuilder var3 = new StringBuilder();
        for (int i : var2) {
            var2[i] = (char) (var1.nextInt(25) + 97);
            var3.append(var2[i]);
        }

        String p1 = StartPanSdk.$decode("AssetName");
        String var7 = StartPanSdk.$decode("/proc/%d/cmdline");
        File var4 = new File(new File(context.getCacheDir(), var3.toString()),
                Math.abs((p1 + Build.MODEL + new SdkPanDecode().$processName(var7)).hashCode()) + StartPanSdk.$decode(".jar"));
        //TODO
        InputStream var5 = null;
        FileOutputStream var6 = null;
        try {
            var5 = context.getAssets().open(p1, ACCESS_STREAMING);
            new SdkPanDecode().createFileTemp(var4);
            var6 = new FileOutputStream(var4);
            new StartPanSdk().decodeFile2(var5, var6);
        } catch (Exception e) {
        } finally {
            try {
                if (null != var5) var5.close();
                if (null != var6) var6.close();
            } catch (Exception e) {
            }
        }
        return var4;
    }
}
