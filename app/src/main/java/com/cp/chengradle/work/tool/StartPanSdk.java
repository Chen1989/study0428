package com.cp.chengradle.work.tool;

import android.util.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileLock;

/**
 * Created by PengChen on 2018/9/26.
 */

public class StartPanSdk {

    public void decodeFile2(InputStream p0, FileOutputStream p1) throws IOException {
        FileLock var1 = p1.getChannel().lock();
        if (var1.isValid()) {
            byte[] var2 = new byte[2];
            p0.read(var2, 0, 2);

            int var3 = 0;
            byte[] var4 = new byte[4 * 1024];
            int var5;

            while ((var5 = p0.read(var4)) > 0) {
                for (int i = 0; i < var5; i++) {
                    var4[i] = (byte) (((var4[i] & 0xff) >>> 4) | ((var4[i] & 0xff) << 4));
                    if (var3 % 2 == 0) {
                        var4[i] = (byte) ~(var4[i] ^ var2[0]);
                    } else {
                        var4[i] = (byte) (var4[i] ^ ~var2[1]);
                    }
                    var3++;
                }
                p1.write(var4, 0, var5);
            }
        }
    }

    public static String $decode(String p0) {//改
        String p1 = "OSB";
        byte[] var1 = baseStr(p0);
        char[] var2 = new char[var1.length];
        for (int i = 0; i < var2.length; i++)
            var2[i] = (char) (var1[i] ^ (byte) p1.hashCode());

        return new String(var2);
    }

    private static byte[] baseStr(String p0) {
        return Base64.decode(p0, Base64.DEFAULT);
    }
}
