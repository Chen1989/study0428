package com.cp.chengradle.slide;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.cp.chengradle.R;
import com.cp.chengradle.Util;

/**
 * Created by PengChen on 2018/5/9.
 */

public class SlideMainActivity extends Activity {

    Button btn;
    SlideController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_view);
        btn = findViewById(R.id.btn_chen);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ChenSdk", "点击了按钮 1 = " + getWindowManager().getDefaultDisplay().getHeight());
                Log.i("ChenSdk", "点击了按钮 2 = " + getResources().getDisplayMetrics().heightPixels);
            }
        });
        controller = new SlideController(btn, new SlideController.DragCallBack() {
            @Override
            public void callBackDragBegin() {

            }

            @Override
            public void callBackDrag(float detaX, float detaY, float allDetaX, float allDetaY) {
//                btn.scrollBy((int)detaX, (int)detaY);
                btn.setTop(btn.getTop() + (int)detaY);
                btn.setBottom(btn.getBottom() + (int)detaY);
                btn.setLeft(btn.getLeft() + (int)detaX);
                btn.setRight(btn.getRight() + (int)detaX);
            }

            @Override
            public void callBackDragEnd() {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.testMessage();
            }
        }).start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            controller.onDragBegin(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            controller.onDraging(event);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            controller.onDragEnd(event);
        }
        return super.onTouchEvent(event);
    }
}
