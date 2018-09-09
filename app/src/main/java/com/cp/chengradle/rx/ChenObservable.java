package com.cp.chengradle.rx;

import android.util.Log;

/**
 * Created by PengChen on 2018/9/5.
 */

public class ChenObservable<T> {
    final private OnChenSubscribe<T> onChenSubscribe;

    private ChenObservable(OnChenSubscribe<T> onChenSubscribe) {
        this.onChenSubscribe = onChenSubscribe;
    }

    public static <T> ChenObservable<T> create(OnChenSubscribe<T> onChenSubscribe) {
        return new ChenObservable<T>(onChenSubscribe);
    }

    public void subscribe(ChenSubscriber<? super T> subscriber) {
        subscriber.onStart();
        onChenSubscribe.call(subscriber);
    }

    public ChenObservable<T> subscribeOn(ChenScheduler chenScheduler) {
        return create(new OnChenSubscribe<T>() {
            @Override
            public void call(ChenSubscriber<? super T> chenSubscriber) {
                chenSubscriber.onStart();
                chenScheduler.createWorker().schedule(new Runnable() {
                    @Override
                    public void run() {
                        onChenSubscribe.call(chenSubscriber);
                    }
                });
            }
        });
    }

    public ChenObservable<T> observeOn(ChenScheduler chenScheduler) {
        return create(new OnChenSubscribe<T>() {
            @Override
            public void call(ChenSubscriber<? super T> chenSubscriber) {
                chenSubscriber.onStart();
                ChenScheduler.Worker worker = chenScheduler.createWorker();
                subscribe(new ChenSubscriber<T>() {
                    @Override
                    public void onCompleted() {

                        worker.schedule(new Runnable() {
                            @Override
                            public void run() {
                                chenSubscriber.onCompleted();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable t) {
                        worker.schedule(new Runnable() {
                            @Override
                            public void run() {
                                chenSubscriber.onError(t);
                            }
                        });
                    }

                    @Override
                    public void onNext(T var1) {
                        worker.schedule(new Runnable() {
                            @Override
                            public void run() {
                                chenSubscriber.onNext(var1);
                            }
                        });
                    }
                });
            }
        });
    }

    public <R> ChenObservable<R> map(Transformer<? super T, ? extends R> transformer) {

        return create(new OnChenSubscribe<R>() {
            @Override
            public void call(ChenSubscriber<? super R> chenSubscriber) {
                subscribe(new ChenSubscriber<T>() {
                    @Override
                    public void onStart() {
                        Log.i("ChenSdk", "var1 = onStart mapping AAA " );
                        super.onStart();
                        Log.i("ChenSdk", "var1 = onStart mapping " );
                    }

                    @Override
                    public void onCompleted() {
                        chenSubscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable t) {
                        chenSubscriber.onError(t);
                    }

                    @Override
                    public void onNext(T var1) {
                        chenSubscriber.onNext(transformer.call(var1));
                    }
                });
            }
        });

//        return create(new OnChenSubscribe<R>() { // 生成一个桥接的Observable和 OnSubscribe
//            @Override
//            public void call(ChenSubscriber<? super R> subscriber) {
//                ChenObservable.this.subscribe(new ChenSubscriber<T>() { // 订阅上层的Observable
//                    @Override
//                    public void onCompleted() {
//                        subscriber.onCompleted();
//                    }
//                    @Override
//                    public void onError(Throwable t) {
//                        subscriber.onError(t);
//                    }
//
//                    @Override
//                    public void onNext(Object var1) {
//                        subscriber.onNext(transformer.call(var1));
//                    }
//
////                    @Override
////                    public void onNext(T var1) {
////                        // 将上层的onSubscribe发送过来的Event，通过转换和处理，转发给目标的subscriber
////                        subscriber.onNext(transformer.call(var1));
////                    }
//                });
//            }
//        });
    }


    public <R> ChenObservable<R> map2(Transformer<? super T, ? extends R> transformer) {
        return create(new ChenMapOnSubscribe<>(this, transformer));
    }

    public interface OnChenSubscribe<T> {
        void call(ChenSubscriber<? super T> chenSubscriber);
    }

    public interface Transformer<T, R> {
        R call(T from);
    }
}
