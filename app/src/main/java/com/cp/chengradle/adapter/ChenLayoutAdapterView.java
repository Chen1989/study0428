package com.cp.chengradle.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by PengChen on 2018/10/31.
 */

public class ChenLayoutAdapterView extends AdapterView<ListAdapter> {

    ListAdapter _adapter;

    public ChenLayoutAdapterView(Context context) {
        super(context);
    }

    public ChenLayoutAdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChenLayoutAdapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ListAdapter getAdapter() {
        return _adapter;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        _adapter = adapter;
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int ss = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);

        int count = _adapter.getCount();
        int index = 0;
        int height = 0;
        int width = 0;
        int parentHeight = getMeasuredHeight();
        int parentWidth = getMeasuredWidth();
        while (height < parentHeight) {
            View childView = _adapter.getView(index, null, null);
            childView.measure(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.AT_MOST));
            int acWidth = childView.getMeasuredWidth();
            int acHeight = childView.getMeasuredHeight();
            if (width + acWidth < parentWidth) {
                addViewInLayout(childView, index, new LayoutParams(acWidth, acHeight));
                childView.layout(width, height, width + acWidth, height + acHeight);
                index++;
                width = width + acWidth;
            } else {
                height = height + acHeight;
                width = 0;
                addViewInLayout(childView, index, new LayoutParams(acWidth, acHeight));
                childView.layout(width, height, width + acWidth, height + acHeight);
                width = acWidth;
                index++;
            }
            Log.i("ChenSdk", "index = " + index);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
    }
}
