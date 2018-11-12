package com.chen.security.serializer;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by PengChen on 2018/11/12.
 */

public class FileUtil {
    public static void readFile() {
        //D:\workspace\work\Auto\start

        try {
            String path = "D:\\workspace\\work\\Auto\\start\\1.txt";
            FileInputStream inputStream = new FileInputStream(path);
            int len = 0;
            StringBuffer buffer = new StringBuffer();
            byte[] var = new byte[1024*4];
            while ((len = inputStream.read(var)) > 0) {
                new String(var, 0, len, "UTF-8");
                buffer.append(new String(var, 0, len));
            }
            String result = buffer.toString();
//            JSONObject jsonObject = new JSONObject("{\"import\":\"你好\"}");
//            String importStr = jsonObject.getString("import");
//            System.out.println("importStr = " + importStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
