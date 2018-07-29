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
        otherGridView = (DragGridView) findViewById(R.id.otherGridView);
        list.add("杭州");
        list.add("宁波");
        list.add("上海");
        list.add("北京");
        list.add("南京");
        list.add("西安");
        MAdapter mAdapter = new MAdapter(list);
        mGridView.setAdapter(mAdapter);

        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        list2.add("5");
        list2.add("6");
        MAdapter mAdapter2 = new MAdapter(list2);
        otherGridView.setAdapter(mAdapter2);
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
