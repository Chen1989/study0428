package com.cp.chengradle;

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
                System.out.println(message.getValue());
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while(i < 1000) {
                    manager.send(new ChenMessage(++i + ""));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1000;
                while(i < 2001) {
                    manager.send(new ChenMessage(++i + ""));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 2000;
                while(i < 3001) {
                    manager.send(new ChenMessage(++i + ""));
                }
            }
        }).start();
        manager.run();
    }
}
