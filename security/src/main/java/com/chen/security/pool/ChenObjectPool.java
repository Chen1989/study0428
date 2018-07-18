package com.chen.security.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by PengChen on 2018/7/17.
 */

public class ChenObjectPool<T> {
    private Queue<T> queue = new ConcurrentLinkedQueue<T>();
    private IObject<T> obj;
    public void set(IObject<T> obj){
        this.obj = obj;
    }
    public T obtain() {
        if (queue.size() == 0) {
            return createObject();
        }
        return queue.poll();
    }
    private T createObject() {
        return obj.setObject();
    }
    public void release(T t){
        queue.offer(t);
    }

    interface IObject<T>{
        T setObject();
    }

    public static void main(String[] args) {
        System.out.println("+++++++++++++++     "+System.currentTimeMillis());
        ChenObjectPool<ChenObject> objectPool = new ChenObjectPool<ChenObject>();
        objectPool.set(new IObject<ChenObject>() {
            @Override
            public ChenObject setObject() {
                return new ChenObject();
            }
        });

        for (int i = 0; i < 2000; i++) {
            ChenObject chen = objectPool.obtain();
            objectPool.release(chen);//释放已经使用的对象
        }
        System.out.println("+++++++++++++++     "+System.currentTimeMillis());
        for (int i = 0; i < 2000; i++) {
            new ChenObject();
        }
        System.out.println("+++++++++++++++     "+System.currentTimeMillis());
    }
}
