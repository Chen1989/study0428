package com.cp.chengradle.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.cp.chengradle.R;

/**
 * Created by PengChen on 2018/5/9.
 */

public class SlideChenView extends RelativeLayout {
    public SlideChenView(Context context) {
        this(context, null);
    }

    public SlideChenView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideChenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.slide_chen_view, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    //    public SlideChenView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
}
