package com.cp.chengradle.rx;

/**
 * Created by PengChen on 2018/9/6.
 */

public class ChenMapOnSubscribe<T, R> implements ChenObservable.OnChenSubscribe<R> {

    private ChenObservable<T> source;
    private ChenObservable.Transformer transformer;

    public ChenMapOnSubscribe(ChenObservable<T> source, ChenObservable.Transformer<? super T, ? extends R> transformer) {
        this.source = source;
        this.transformer = transformer;
    }

    @Override
    public void call(ChenSubscriber chenSubscriber) {
        source.subscribe(new ChenMapSubscriber<R, T>(chenSubscriber, transformer));
    }
}
