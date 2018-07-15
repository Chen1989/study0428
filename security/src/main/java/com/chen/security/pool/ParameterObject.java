package com.chen.security.pool;

/**
 * Created by PengChen on 2018/7/15.
 */

public class ParameterObject {
    private int max;
    private int min;

    public ParameterObject(int max, int min) {
        this.max = max;
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
