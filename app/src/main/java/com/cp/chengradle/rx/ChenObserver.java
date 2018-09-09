package com.cp.chengradle.rx;

/**
 * Created by PengChen on 2018/9/5.
 */

public interface ChenObserver<T> {
    void onCompleted();
    void onError(Throwable t);
    void onNext(T var1);
}
