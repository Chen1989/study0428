package com.cp.chengradle.rx;

import java.util.concurrent.Executor;

/**
 * Created by PengChen on 2018/9/6.
 */

public class ChenScheduler {
    final Executor executor;
    public ChenScheduler(Executor executor) {
        this.executor = executor;
    }
    public Worker createWorker() {
        return new Worker(executor);
    }
    public static class Worker {
        final Executor executor;
        public Worker(Executor executor) {
            this.executor = executor;
        }
        // 这里接受的是Runnable而不是Action0，其实这没什么关系，主要是懒得自定义函数式接口了。
        public void schedule(Runnable runnable) {
            executor.execute(runnable);
        }
    }
}
