package com.cp.chengradle.rx;

/**
 * Created by PengChen on 2018/9/6.
 */

public class ChenMapSubscriber<T, R> extends ChenSubscriber<R> {

    final ChenSubscriber<? super T> actual;
    final ChenObservable.Transformer<? super R, ? extends T> transformer;
    public ChenMapSubscriber(ChenSubscriber<? super T> actual, ChenObservable.Transformer<? super R, ? extends T> transformer) {
        this.actual = actual;
        this.transformer = transformer;
    }

    @Override
    public void onCompleted() {
        actual.onCompleted();
    }

    @Override
    public void onError(Throwable t) {
        actual.onError(t);
    }

    @Override
    public void onNext(R var1) {
        actual.onNext(transformer.call(var1));
    }
}
