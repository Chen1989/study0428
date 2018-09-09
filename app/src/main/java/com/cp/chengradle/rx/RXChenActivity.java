package com.cp.chengradle.rx;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cp.chengradle.R;

/**
 * Created by PengChen on 2018/9/5.
 */

public class RXChenActivity extends Activity {
    private final String TAG = "ChenSdk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_view);
        test();
    }

    private void test() {
//        ChenObservable.create(new ChenObservable.OnChenSubscribe<Integer>() {
//            @Override
//            public void call(ChenSubscriber<? super Integer> subscriber) {
//                for (int i = 0; i < 10; i++) {
//                    subscriber.onNext(i);
//                }
//                subscriber.onCompleted();
//            }
//        })
//        .map(new ChenObservable.Transformer<Integer, String>() {
//            @Override
//            public String call(Integer from) {
//                return "trans i = " + from;
//            }
//        })
//        .subscribe(new ChenSubscriber<String>() {
//            @Override
//            public void onStart() {
//                Log.i(TAG, "var1 = onStart AAAAA" );
//                super.onStart();
//                Log.i(TAG, "var1 = onStart" );
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.i(TAG, "var1 = onCompleted" );
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onNext(String var1) {
//                Log.i(TAG, "var1 = " + var1);
//            }
//        });

        Log.i(TAG, "out = currentThread " + Thread.currentThread().getName());
        ChenObservable.create(new ChenObservable.OnChenSubscribe<Integer>() {
            @Override
            public void call(ChenSubscriber<? super Integer> chenSubscriber) {
                Log.i(TAG, "OnChenSubscribe = currentThread " + Thread.currentThread().getName());
                for (int i = 20; i < 25; i++) {
                    chenSubscriber.onNext(i);
                }
                chenSubscriber.onCompleted();
            }
        })
        .map2(new ChenObservable.Transformer<Integer, String>() {
            @Override
            public String call(Integer from) {
                return "from " + from;
            }
        })
        .subscribeOn(ChenSchedulerFactory.io())
        .observeOn(ChenSchedulerFactory.io())
        .subscribe(new ChenSubscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "var1 = onCompleted map2 " );
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onNext(String var1) {
                Log.i(TAG, "var1 = onNext map2 " + var1);
                Log.i(TAG, "var1 = onNext map2 currentThread " + Thread.currentThread().getName());
            }
        });
    }
}
