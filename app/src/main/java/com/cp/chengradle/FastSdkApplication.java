package com.cp.chengradle;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.channels.FileLock;
import java.util.Random;

import dalvik.system.DexClassLoader;

import static android.content.res.AssetManager.ACCESS_STREAMING;


public class FastSdkApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        loadInnerSdk(this);
        super.onCreate();
    }

    private String $decode(String v, String ok) {//改
        byte[] r = Base64.decode(v, Base64.DEFAULT);
        char[] b = new char[r.length];
        for (int i = 0; i < b.length; i++)
            b[i] = (char) (r[i] ^ (byte) ok.hashCode());

        return new String(b);
    }

    private String $processName(String o) {
        try {
            File p = new File(String.format($decode("/proc/%d/cmdline", o), Process.myPid()));
            return new BufferedReader(new InputStreamReader(new FileInputStream(p))).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private synchronized File $release(Context context, String name, String o) {
        //TODO
        Random random = new Random();
        char[] tmp = new char[6];
        StringBuilder builder = new StringBuilder();
        for (int i : tmp) {
            tmp[i] = (char) (random.nextInt(25) + 97);
            builder.append(tmp[i]);
        }

        File file = new File(new File(context.getCacheDir(), builder.toString()), Math.abs((name + Build.MODEL + $processName(o)).hashCode()) + ".jar");
        //TODO
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = context.getAssets().open(name, ACCESS_STREAMING);
            createFile(file);
            out = new FileOutputStream(file);
            decodeFile3(in, out);
        } catch (Exception e) {
        } finally {
            try {
                if (null != in) in.close();
                if (null != out) out.close();
            } catch (Exception e) {
            }
        }
        return file;
    }

    private void decodeFile3(InputStream in, FileOutputStream out) throws IOException {
        FileLock lock = out.getChannel().lock();
        if (lock.isValid()) {
            byte[] tempArr = new byte[2];
            in.read(tempArr, 0, 2);
            byte _temp1 = tempArr[0];
            byte _temp2 = tempArr[1];

            int _index = 0;
            byte[] buff2 = new byte[4 * 1024];
            int len;

            while ((len = in.read(buff2)) > 0) {
                for (int i = 0; i < len; i++) {
                    buff2[i] = (byte) (((buff2[i] & 0xff) >>> 4) | ((buff2[i] & 0xff) << 4));
                    if (_index % 2 == 0) {
                        buff2[i] = (byte) ~(buff2[i] ^ _temp1);
                    } else {
                        buff2[i] = (byte) (buff2[i] ^ ~_temp2);
                    }
                    _index++;
                }
                out.write(buff2, 0, len);
            }
        }
    }

    /*
     * AssetName=core #待加载lib在asset下的名称 ClassName=com.dmy.FastSdkApplication
     * #与JNI绑定的java类名称 BasePath=getFilesDir().getParent() #应用私有目录
     *
     * Framework=base #基础库加载的so名称
     */
    public void loadInnerSdk(Context context) {
        String ok = "OSB";
        File file = null;
        try {
            String name = $decode("AssetName", ok);
            file = $release(context, name, ok);
            DexClassLoader dex = new DexClassLoader(file.getPath(), file.getParent(), null, context.getClassLoader().getParent());
            Class<?> c = dex.loadClass($decode("com.sdk.entry.SdkEntry", ok));
            Method me = c.getMethod($decode("startLoad", ok), Context.class, String.class);
            me.setAccessible(true);
            me.invoke(null, context, $decode("PluginConfig", ok));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            delete(file.getParentFile());
        }
    }

    //TODO
    private boolean delete(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delete(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return file.delete();
    }

    private void createFile(File file) throws IOException {
        if (file.exists())
            return;
        if (null != file.getParentFile() && !file.getParentFile().exists())
            file.getParentFile().mkdirs();
        file.createNewFile();
    }
}
