package com.cp.chengradle.slide;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.cp.chengradle.R;

/**
 * Created by PengChen on 2018/5/9.
 */

public class SlideMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
