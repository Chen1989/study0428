package com.cp.chengradle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.cp.chengradle.adapter.DragGridAdapter;
import com.cp.chengradle.adapter.DragGridView;

import java.lang.ref.WeakReference;
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
    private Handler handler = new MyHandler(this);

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

        Message msg = Message.obtain();
        msg.what = 1;
        handler.sendMessage(msg);
        Intent intent = new Intent();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void doSomething() {
        mGridView.setAdapter(null);
        Log.i("ChenSdk", "doSomething");
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

    public static class MyHandler extends Handler {
        private WeakReference<Activity> reference;

        public MyHandler(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = reference.get();
            if (activity != null && msg.what == 1) {
                Log.i("ChenSdk", "what = 1");
            }
            super.handleMessage(msg);
        }
    }
}
