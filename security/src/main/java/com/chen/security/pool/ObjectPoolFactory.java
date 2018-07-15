package com.chen.security.pool;

/**
 * Created by PengChen on 2018/7/15.
 */

public class ObjectPoolFactory {
    private static ObjectPoolFactory poolFactory;
    public static ObjectPoolFactory getInstance() {
        if (poolFactory == null) {
            poolFactory = new ObjectPoolFactory();
        }
        return poolFactory;
    }

    public ObjectPool createPool(ParameterObject paraObj,Class clsType) {
        return new ObjectPool(paraObj, clsType);
    }


}
