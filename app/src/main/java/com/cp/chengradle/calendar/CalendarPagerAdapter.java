package com.cp.chengradle.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
    private ClickDelegate mDelegate;

    CalendarPagerAdapter(Context context, int count, ClickDelegate delegate) {
        mContext = context;
        List<String> list = new ArrayList<>();
        mCount = count;
        mDelegate = delegate;
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
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if(((ChenGridViewAdapter.ChenHolder)parent.getChildAt(i).getTag()).index == 0) {
                        parent.getChildAt(i).setBackgroundColor(Color.parseColor("#FA5555"));
                    } else {
                        parent.getChildAt(i).setBackgroundColor(Color.parseColor("#FAE8DA"));
                    }
                }
                view.setBackgroundColor(Color.parseColor("#0000FF"));
                mDelegate.click(view, position);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    public interface ClickDelegate {
        void click(View view, int index);
    }
}
