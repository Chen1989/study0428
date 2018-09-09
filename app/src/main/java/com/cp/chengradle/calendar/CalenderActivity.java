package com.cp.chengradle.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);
        viewPager = findViewById(R.id.calendar_viewpager);
        monthView = findViewById(R.id.calendar_title);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            list.add("你好" + i);
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int count = year * 12 + month;
        monthView.setText(year + "年" + (month + 1) + "月");
        CalendarPagerAdapter adapter = new CalendarPagerAdapter(this, count);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(count - 1);
        viewPager.setPageMargin(15);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("ChenSdk", "onPageScrolled position = " + position);
            }

            @Override
            public void onPageSelected(int position) {
                int year = (position + 1) / 12;
                int month = (position + 1) % 12 + 1;
                monthView.setText(year + "年" + month + "月");
                Log.i("ChenSdk", "onPageSelected position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("ChenSdk", "onPageScrollStateChanged state = " + state);
            }
        });
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Log.i("ChenSdk", "DAY_OF_WEEK = " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        Log.i("ChenSdk", "Calendar.YEAR = " + Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().get(Calendar.MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH));
        Log.i("ChenSdk", "Calendar.MONTH = " + Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
//        DAY_OF_WEEK:日一二三四五六：1,2,3,4,5,6,7
    }
}
