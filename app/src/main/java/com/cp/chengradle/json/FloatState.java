package com.cp.chengradle.json;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PengChen on 2018/6/25.
 */

public class FloatState extends State {
    private String patternSign = "[{|}|:|,]";
    private String patternNumber = "[0-9]";

    private String patternFindNumber = "(\\d+)";

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
            String nextNumber = findNumber(jsonString);
            mContext.addToken(new JsonToken(nextNumber, 1));
            jsonString = jsonString.substring(nextNumber.length());
            State state = new FloatState(mContext);
            mContext.setState(state);
            state.handle(jsonString);
        } else if (patternSign(jsonString)) {
            JsonToken token = new JsonToken(jsonString.substring(0, 1), 0);
            mContext.addToken(token);
            State state = new StartState(mContext);
            mContext.setState(state);
            state.handle(jsonString.substring(1));
        } else {
            State state = new ErrorState(mContext);
            mContext.setState(state);
            state.handle(jsonString);
        }
    }

    private boolean patternNumber(String jsonString) {
        Pattern pa = Pattern.compile(patternNumber);
        Matcher ma = pa.matcher(jsonString.substring(0, 1));
        if (ma.find()) {
            return true;
        }
        return false;
    }

    private boolean patternSign(String jsonString) {
        Pattern pa = Pattern.compile(patternSign);
        Matcher ma = pa.matcher(jsonString.substring(0, 1));
        if (ma.find()) {
            return true;
        }
        return false;
    }

    private String findNumber(String jsonString) {
        Pattern pa = Pattern.compile(patternFindNumber);
        Matcher ma = pa.matcher(jsonString);
        if (ma.find()) {
            return ma.group(1);
        }
        return "";
    }
}
