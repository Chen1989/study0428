package com.cp.chengradle.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PengChen on 2017/12/12.
 */

public class DownLoadTask extends Thread {
    private FileInfo info;
    private SQLiteDatabase db;
    private DBHelper helper;//数据库帮助类
    private long finished = 0;//当前已下载完成的进度
    private int retryCount = 0;
    private DownLoadTaskListener taskListener;
    private Context mContext;

    public DownLoadTask(Context context, FileInfo info, DBHelper helper, DownLoadTaskListener taskListener) {
        mContext = context;
        this.info = info;
        this.helper = helper;
        this.db = helper.getWritableDatabase();
        this.taskListener = taskListener;
    }

    public FileInfo getFileInfo() {
        return info;
    }

    private void downloadFile() {
        getLength();
        HttpURLConnection connection = null;
        RandomAccessFile rwd = null;
        try {
            URL url = new URL(info.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            //从上次下载完成的地方下载
            long start = info.getFinished();
            //设置下载位置(从服务器上取要下载文件的某一段)
            Log.i("ChenSdk","start = " + start);
            connection.setRequestProperty("Range", "bytes=" + start + "-" + (info.getLength()-1));//设置下载范围
            //设置文件写入位置
            File file = new File(DownLoaderManger.getInstance(mContext).FILE_PATH, info.getFileName());
            rwd = new RandomAccessFile(file, "rw");
            //从文件的某一位置开始写入
            rwd.seek(start);
            finished = info.getFinished();
            int responseCode = connection.getResponseCode();
            Log.i("ChenSdk","connection.getResponseCode() start= " + responseCode);
            if (responseCode == 206 || responseCode == 200) {//文件部分下载，返回码为206
                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    //写入文件
                    rwd.write(buffer, 0, len);
                    finished += len;
                    info.setFinished(finished);
                    helper.updateFinishSize(db, info);
                    //更新界面显示
                    Log.i("ChenSdk","finished = = " + finished);
                }
                //下载完成
                Log.i("ChenSdk","finished = " + finished + ", 下载完成");
                taskListener.downloadSuccess(this);
                connection.disconnect();
                rwd.close();
            }
        } catch (Exception e) {
            Log.i("ChenSdk","下载失败 = " + retryCount + info.getFileName());
            e.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (rwd != null) {
                    rwd.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (retryCount < 3) {
                Log.i("ChenSdk","retryCount = " + retryCount);
                retryCount++;
                downloadFile();
            }else {
                taskListener.downloadFailed(this);
            }
        }
    }

    @Override
    public void run() {
        //如果文件存在删除
        File file = new File(DownLoaderManger.getInstance(mContext).FILE_PATH, info.getFileName());
        if (file.exists()) {
            file.delete();
        }
        downloadFile();
    }

    /**
     * 首先开启一个线程去获取要下载文件的大小（长度）
     */
    private void getLength() {
        HttpURLConnection connection = null;
        try {
            //连接网络
            URL url = new URL(info.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(5000);
            int length = -1;
            if (connection.getResponseCode() == 200) {//网络连接成功
                //获得文件长度
                length = connection.getContentLength();
            }
            if (length <= 0) {
                //连接失败
                return;
            }
            //创建文件保存路径
            File dir = new File(DownLoaderManger.getInstance(mContext).FILE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.i("ChenSdk","length = " + length);
            info.setLength(length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface DownLoadTaskListener {
        void downloadSuccess(DownLoadTask task);
        void downloadFailed(DownLoadTask task);
    }
}
