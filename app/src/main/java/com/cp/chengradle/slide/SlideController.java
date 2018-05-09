package com.cp.chengradle.slide;

import android.view.MotionEvent;

/**
 * Created by PengChen on 2018/5/9.
 */

public class SlideController {
    private float beginX;
    private float beginY;
    private float endX;
    private float endY;
    private float currentX;
    private float currentY;
    private boolean isDraging;
    private DragCallBack _callBack;

    public SlideController(DragCallBack callBack) {
        _callBack = callBack;
    }

    public void onDragBegin(MotionEvent event) {
        beginX = event.getRawX();
        beginY = event.getRawY();
        currentX = beginX;
        currentY = beginY;
        isDraging = true;
        _callBack.callBackDragBagin();
    }

    public void onDraging(MotionEvent event) {
        float detalX = event.getRawX() - currentX;
        float detalY = event.getRawY() - currentY;
        float allDetalX = event.getRawX() - beginX;
        float allDetalY = event.getRawY() - beginY;
        currentX = event.getRawX();
        currentY = event.getRawY();
        _callBack.callBackDrag(detalX, detalY, allDetalX, allDetalY);
    }

    public void onDragEnd(MotionEvent event) {
        endX = event.getRawX();
        endY = event.getY();
        isDraging = false;
        _callBack.callBackDragEnd();
    }

    public interface DragCallBack {
        void callBackDragBagin();
        void callBackDrag(float detaX, float detaY, float allDetaX, float allDetaY);
        void callBackDragEnd();
    }
}
