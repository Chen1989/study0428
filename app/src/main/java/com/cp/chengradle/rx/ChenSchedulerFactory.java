package com.cp.chengradle.rx;

import java.util.concurrent.Executors;

/**
 * Created by PengChen on 2018/9/6.
 */

public class ChenSchedulerFactory {
    private static final ChenScheduler ioScheduler = new ChenScheduler(Executors.newSingleThreadExecutor());
    public static ChenScheduler io() {
        return new ChenScheduler(Executors.newSingleThreadExecutor());
    }

//    public static ChenScheduler mainThread() {
//        return new ChenScheduler(Executors.Looper.getMainLooper().getThread());
//    }
}
