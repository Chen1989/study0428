package com.cp.chengradle.slide;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import com.cp.chengradle.R;

/**
 * Created by PengChen on 2018/5/9.
 */

public class SlideMainActivity extends Activity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_view);
        btn = findViewById(R.id.btn_chen);

        SlideController controller = new SlideController(new SlideController.DragCallBack() {
            @Override
            public void callBackDragBagin() {

            }

            @Override
            public void callBackDrag(float detaX, float detaY, float allDetaX, float allDetaY) {
                btn.scrollBy((int)detaX, (int)detaY);
            }

            @Override
            public void callBackDragEnd() {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
