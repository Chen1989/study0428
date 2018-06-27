package com.chen.security.json;

import android.util.Log;

/**
 * Created by PengChen on 2018/6/26.
 */

public class ErrorState extends State {
    public ErrorState(IContext context) {
        super(context);
    }

    @Override
    public void handle(String jsonString) {
        Log.i("JsonSdk", "json格式错误: " + jsonString);
        mContext.toString();
        mContext.clearToken();
    }
}
