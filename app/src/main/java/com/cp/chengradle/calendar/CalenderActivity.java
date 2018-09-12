package com.cp.chengradle.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.cp.chengradle.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by PengChen on 2018/9/7.
 */

public class CalenderActivity extends Activity {

    private ViewPager viewPager;
    private TextView monthView;
    private TextView detailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);
        viewPager = findViewById(R.id.calendar_viewpager);
        monthView = findViewById(R.id.calendar_title);
        detailTextView = findViewById(R.id.calendar_detail);
        Animation animation = new ScaleAnimation(0, 0, 0, 200);
        detailTextView.startAnimation(animation);


        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int count = year * 12 + month;
        monthView.setText(year + "年" + (month + 1) + "月");
        CalendarPagerAdapter adapter = new CalendarPagerAdapter(this, count, new CalendarPagerAdapter.ClickDelegate() {
            @Override
            public void click(View view, int index) {
                detailTextView.setText("click view index is " + index);
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(count - 1);
        viewPager.setPageMargin(15);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("ChenSdk", "onPageScrolled position = " + position);
            }

            @Override
            public void onPageSelected(int position) {
                int year = (position + 1) / 12;
                int month = (position + 1) % 12 + 1;
                monthView.setText(year + "年" + month + "月");
//                Log.i("ChenSdk", "onPageSelected position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("ChenSdk", "onPageScrollStateChanged state = " + state);
            }
        });
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        Log.i("ChenSdk", "Calendar.YEAR = " + Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().get(Calendar.MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, -1);
        Log.i("ChenSdk", "year = " + calendar.get(Calendar.YEAR) + ", month = " + calendar.get(Calendar.MONTH));
//        DAY_OF_WEEK:日一二三四五六：1,2,3,4,5,6,7
//        DownLoaderManger.getInstance(this).start("appleid",
//                "http://192.168.1.157:7898/group1/M00/00/02/wKgBnVuYzyWAQYMeAHzHWDqki5k942.txt",
//                33);
    }
}
