package com.cp.chengradle.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cp.chengradle.R;

import java.util.Calendar;

/**
 * Created by PengChen on 2018/9/9.
 */

public class ChenGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private Calendar mCalendar;
    private int count;

    public ChenGridViewAdapter(Context context, Calendar calendar) {
        mContext = context;
        mCalendar = calendar;
        count = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) * 7;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChenHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.calender_pager_content, null);
            holder = new ChenHolder();
            holder.tx = convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (ChenHolder) convertView.getTag();
        }
        int currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDay = mCalendar.get(Calendar.DAY_OF_WEEK);
        mCalendar.set(Calendar.DAY_OF_MONTH, currentDay);
        if (position + 1 < firstDay) {
            int currentMonth = mCalendar.get(Calendar.MONTH);
            mCalendar.set(Calendar.MONTH, currentMonth - 1);
            int text = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) + position + 2 - firstDay;
            mCalendar.set(Calendar.MONTH, currentMonth);
            holder.tx.setText("" + text);
            convertView.setBackgroundColor(Color.parseColor("#FA5555"));
        } else if (position + 1 - firstDay >= mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            holder.tx.setText("" + (position + 2 - firstDay - mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
            convertView.setBackgroundColor(Color.parseColor("#FA5555"));
        } else {
            holder.tx.setText("" + (position + 2 - firstDay));
            convertView.setBackgroundColor(Color.parseColor("#FAE8DA"));
        }

        return convertView;
    }

    private class ChenHolder {
        public TextView tx;
    }
}
