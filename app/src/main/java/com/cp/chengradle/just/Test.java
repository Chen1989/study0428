package com.cp.chengradle.just;

import java.io.File;
import java.io.IOException;

/**
 * Created by PengChen on 2018/11/13.
 */

public class Test {
    private void test() {

        try {
            File file = new File("");
            createFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFile(File p0) throws IOException {
        if (!p0.exists()) {
            String path = p0.getAbsolutePath();
            String parent = path.substring(0, path.lastIndexOf(File.separator));
            File parentFile = new File(parent);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            p0.createNewFile();
        }
    }


}
