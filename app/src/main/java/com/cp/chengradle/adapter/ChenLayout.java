package com.cp.chengradle.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PengChen on 2018/10/24.
 */

public class ChenLayout extends ViewGroup {
    public ChenLayout(Context context) {
        super(context);
    }

    public ChenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
        }
    }
}
