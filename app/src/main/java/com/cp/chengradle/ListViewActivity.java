package com.cp.chengradle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cp.chengradle.adapter.ChenDragListView;
import com.cp.chengradle.adapter.ListViewAdapter;

/**
 * Created by admin on 2018/7/22.
 */

public class ListViewActivity extends Activity {
    private ChenDragListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv = findViewById(R.id.list_view);
        lv.setAdapter(new ListViewAdapter(this));
    }
}
