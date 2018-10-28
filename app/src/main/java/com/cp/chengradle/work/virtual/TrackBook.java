package com.cp.chengradle.work.virtual;

import android.content.Context;
import android.content.res.AssetManager;

import com.omg.heart.free.sdk.ver.Config;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by ASUS on 2017/9/14.
 */

public class TrackBook {

    public void copyApk(String libPath, Context context) {
//        if (!new File(libPath).exists()) {
            try {
                String str = "lib" + File.separator + Config.VrMachineName;
                AssetManager assetManager = context.getAssets();
                InputStream is = assetManager.open(str);
                File file = new File(libPath);
                FileOutputStream fos = new FileOutputStream(file);
                writeFile(is, fos);

            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
    }


    private void writeFile(InputStream inputStream, OutputStream outputStream) {
        try {

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int value = dataInputStream.readInt();
            value = ~(value ^ 379);

            Map<Integer, Integer> mapByte = new HashMap<Integer, Integer>();
            Random randomTag = new Random(value);

            List<Integer> listValue = new ArrayList<Integer>();
            byte jj = Byte.MIN_VALUE;
            while (jj < Byte.MAX_VALUE) {
                boolean a = jj != -1 ? listValue.add((int) jj) : false;
                jj++;
            }

            listValue.add((int) Byte.MAX_VALUE);

            do {
                int index = randomTag.nextInt(listValue.size() - 1) + 1;
                mapByte.put(listValue.get(index), listValue.get(0));
                mapByte.put(listValue.get(0), listValue.get(index));
                listValue.remove(index);
                listValue.remove(0);
            } while (listValue.size() > 1);


            if (listValue.size() != 0) {
                mapByte.put(listValue.get(0), listValue.get(0));
            }
            // write
            writeFuck(inputStream, outputStream, mapByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFuck(InputStream inputStream, OutputStream outputStream, Map<Integer, Integer> mapByte) {
        try {
            int readLen = 0;
            byte[] buffWrite = new byte[4 * 1024];
            readLen = inputStream.read(buffWrite);
            for (;readLen > 0;) {
                for (int i = 0; i < readLen; i++) {
                    if (mapByte.containsKey((int) buffWrite[i])) {
                        int mapValue = mapByte.get((int) buffWrite[i]);
                        buffWrite[i] = (byte) mapValue;
                    }
                }
                outputStream.write(buffWrite, 0, readLen);
                readLen = inputStream.read(buffWrite);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
