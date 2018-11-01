package com.cp.chengradle.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.cp.chengradle.R;

/**
 * Created by PengChen on 2018/10/31.
 */

public class ChenListViewActivity extends Activity {

    ChenLayoutAdapterView adapterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chen_list_view);
        adapterView = findViewById(R.id.chen_adapter);
        adapterView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 500;
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
                Button button = new Button(getApplicationContext());
                button.setText("你好" + position);
                return button;
            }
        });
    }


}
