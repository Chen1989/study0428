package com.cp.chengradle.json;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PengChen on 2018/6/25.
 */

public class FloatState extends State {

    private String patternNumber = "[0-9]";


    public FloatState(IContext context) {
        super(context);
    }

    @Override
    public void handle(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            mContext.toString();
            return;
        }
        if (patternNumber(jsonString)) {

        }
    }

    private boolean patternNumber(String jsonString) {
        Pattern pa = Pattern.compile(patternNumber);
        Matcher ma = pa.matcher(jsonString);
        if (ma.find()) {
            return true;
        }
        return false;
    }
}
