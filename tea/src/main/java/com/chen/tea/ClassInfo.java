package com.chen.tea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by PengChen on 2018/11/13.
 */

public class ClassInfo {
    String className;
    ArrayList<FunctionInfo> functionInfos;

    public void test() throws IOException {
        String ss = "";
        File file = new File(ss);

        if (!file.exists()) {
            String path = file.getAbsolutePath();
            String parent = path.substring(0, path.lastIndexOf(File.separator));
            File parentFile = new File(parent);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        }

        System.out.println("ChenSdk ----- sdk chen just.");
    }
}
