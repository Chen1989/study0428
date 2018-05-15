package com.cp.chengradle.messagequeue;

/**
 * Created by PengChen on 2018/5/14.
 * producer;customer
 */

public class ChenManager {
    private ChenLooper looper;

    public ChenManager(ChenHandler handler) {
        looper = new ChenLooper(handler);
    }

    public void send(ChenMessage message) {
        looper.addToQueue(message);
    }

    public void run() {
        looper.loop();
    }
}
