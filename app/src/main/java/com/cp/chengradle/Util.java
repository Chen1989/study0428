package com.cp.chengradle;

import android.util.Log;

import com.cp.chengradle.messagequeue.ChenHandler;
import com.cp.chengradle.messagequeue.ChenManager;
import com.cp.chengradle.messagequeue.ChenMessage;

/**
 * Created by admin on 2018/5/1.
 */

public class Util {
    public static void testMessage() {
        final ChenManager manager = new ChenManager(new ChenHandler() {
            @Override
            public void handleMessage(ChenMessage message) {
                Log.i("ChenSdk", "message = " + message.getValue());
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while(i < 100) {
                    manager.send(new ChenMessage(String.valueOf(i++)));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 100;
                while(i < 200) {
                    manager.send(new ChenMessage(String.valueOf(i++)));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 200;
                while(i < 300) {
                    manager.send(new ChenMessage(String.valueOf(i++)));
                }
            }
        }).start();
        manager.run();
    }
}
