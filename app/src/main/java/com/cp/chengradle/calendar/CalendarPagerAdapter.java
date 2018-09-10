package com.cp.chengradle.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cp.chengradle.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by PengChen on 2018/9/8.
 */

public class CalendarPagerAdapter extends PagerAdapter {

    private Context mContext;
    private Calendar mCalendar;
    private List<String> mData;
    private int mCount;

    CalendarPagerAdapter(Context context, int count) {
        mContext = context;
        List<String> list = new ArrayList<>();
        mCount = count;
    }

    @Override
    public int getCount() {
        return mCount * 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, (position + 1) / 12 );
        calendar.set(Calendar.MONTH, (position + 1) % 12 );
        View view = View.inflate(mContext, R.layout.calendar_gridview,null);
        GridView gridView = (GridView) view.findViewById(R.id.calendar_grid_view);
        gridView.setAdapter(new ChenGridViewAdapter(mContext, calendar));
        gridView.setNumColumns(7);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ChenSdk", "position = " + position);
            }
        });
        TextView textView = view.findViewById(R.id.calendar_detail);
        textView.startAnimation(null);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }
}
