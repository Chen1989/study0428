package com.cp.chengradle.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cp.chengradle.R;

/**
 * Created by admin on 2018/7/22.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;

    public ListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view, null);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.chen_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(holder.title.getText())) {
            holder.title.setText("chen_" + position);
        }

        return convertView;
    }


    public final class ViewHolder{
        public TextView title;
        public TextView text;
        public Button bt;
    }
}
