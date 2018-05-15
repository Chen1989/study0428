package com.cp.chengradle.messagequeue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by PengChen on 2018/5/14.
 */

public class ChenLooper {
    Queue queue = new ConcurrentLinkedDeque();
    ChenHandler handler;

    public ChenLooper(ChenHandler handler) {
        this.handler = handler;
    }
    public void addToQueue(ChenMessage message) {
        queue.offer(message);
    }

    public void loop() {
        while (true) {
            if (!queue.isEmpty()) {
                for (Object message : queue) {
                    ChenMessage msg = (ChenMessage) message;
                    handler.handleMessage(msg);
                    queue.remove(message);
                }
            }
        }
    }
}
