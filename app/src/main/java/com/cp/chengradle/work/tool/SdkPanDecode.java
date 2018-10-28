package com.cp.chengradle.work.tool;

import android.os.Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by PengChen on 2018/7/19.
 */

public class SdkPanDecode {

    public boolean deleteTemp(File p0) {
        if (p0.isDirectory()) {
            String[] var1 = p0.list();
            for (int i = 0; i < var1.length; i++) {
                boolean success = deleteTemp(new File(p0, var1[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return p0.delete();
    }

    public void createFileTemp(File p0) throws IOException {
        if (p0.exists())
            return;
        if (null != p0.getParentFile() && !p0.getParentFile().exists())
            p0.getParentFile().mkdirs();
        p0.createNewFile();
    }

    public String $processName(String p0) {
        try {
            int v1 = Process.myPid();
            String v2 = String.format(p0, v1);
            File var1 = new File(v2);
            InputStreamReader v3 = new InputStreamReader(new FileInputStream(var1));
            return new BufferedReader(v3).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
