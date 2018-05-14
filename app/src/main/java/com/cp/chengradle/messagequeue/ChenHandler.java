package com.cp.chengradle.messagequeue;

/**
 * Created by PengChen on 2018/5/14.
 */

public interface ChenHandler {
    void handleMessage(ChenMessage message);
}
