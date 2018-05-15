package com.cp.chengradle.slide;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by PengChen on 2018/5/9.
 *android:id/content
 * http://gitlab.abcdserver.com/
 */

public class SlideController {
    private final String TAG = "ChenSdk";
    private float beginX;
    private float beginY;
    private float endX;
    private float endY;
    private float currentX;
    private float currentY;
    private boolean isDraging;
    private DragCallBack _callBack;
    private View _view;
    private int _heigth;
    private int _width;

    public SlideController(View view, DragCallBack callBack) {
        _callBack = callBack;
        _view = view;
        DisplayMetrics dm = _view.getResources().getDisplayMetrics();
        _heigth = dm.heightPixels - 146;
        _width = dm.widthPixels;
        Log.i(TAG, "height = " + _heigth);
        Log.i(TAG, "width = " + _width);
        Log.i(TAG, "_view.getTop() = " + _view.getTop());
    }

    public void onDragBegin(MotionEvent event) {
        beginX = event.getRawX();
        beginY = event.getRawY();
        currentX = beginX;
        currentY = beginY;
        isDraging = true;
        _callBack.callBackDragBegin();
    }

    public void onDraging(MotionEvent event) {
        float detalX = event.getRawX() - currentX;
        float detalY = event.getRawY() - currentY;
        float allDetalX = event.getRawX() - beginX;
        float allDetalY = event.getRawY() - beginY;
        currentX = event.getRawX();
        currentY = event.getRawY();
        if (_view.getTop() + detalY < 0 || _view.getBottom() + detalY > _heigth ) {
            detalY = 0;
        }
        if (_view.getLeft() + detalX < 0 || _view.getRight() + detalX > _width) {
            detalX = 0;
        }
        _callBack.callBackDrag(detalX, detalY, allDetalX, allDetalY);
    }

    public void onDragEnd(MotionEvent event) {
        endX = event.getRawX();
        endY = event.getY();
        isDraging = false;
        _callBack.callBackDragEnd();
    }

    public interface DragCallBack {
        void callBackDragBegin();
        void callBackDrag(float detaX, float detaY, float allDetaX, float allDetaY);
        void callBackDragEnd();
    }
}
