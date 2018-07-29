package com.cp.chengradle;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.cp.chengradle.adapter.DragGridAdapter;
import com.cp.chengradle.adapter.DragGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/7/29.
 * https://www.jianshu.com/p/559b631ec330
 */

public class GridViewActivity extends Activity {
    private DragGridView otherGridView;
    private DragGridView mGridView;

    private List<String> list = new ArrayList();

    private List<String> list2 = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_grid_view);
        mGridView = (DragGridView) findViewById(R.id.mGridView);
        for (int i = 0; i < 30; i++) {
            list.add("chen_" + i);
        }

        MAdapter mAdapter = new MAdapter(list);
        mGridView.setAdapter(mAdapter);

    }


    class MAdapter extends DragGridAdapter<String> {

        public MAdapter(List list) {
            super(list);
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent) {
            String text = getList().get(position);
            convertView = LayoutInflater.from(GridViewActivity.this).inflate(R.layout.item, null);
            TextView tv_text = (TextView) convertView.findViewById(R.id.tv_text);
            tv_text.setText(text);
            return convertView;
        }
    }
}
