package com.cp.chengradle.json;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PengChen on 2018/6/25.
 */

public class NumberState extends State {
    private String patternSign = "[{|}|:|,]";
    private String patternNumber = "[0-9]";
    private String patternPoint = ".";

    private String patternFindNumber = "(\\d+)";

    public NumberState(IContext context) {
        super(context);
    }

    @Override
    public void handle(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            mContext.toString();
            return;
        }
        String number = findNumber(jsonString);
        mContext.addToken(new JsonToken(number, 1));
        jsonString = jsonString.substring(number.length());
        if (patternPoint.equals(jsonString.substring(0, 1))) {
            JsonToken token = new JsonToken(patternPoint, 3);
            mContext.addToken(token);
            State state = new FloatState(mContext);
            mContext.setState(state);
            state.handle(jsonString.substring(1));
        } if (patternSign(jsonString)) {
            JsonToken token = new JsonToken(jsonString.substring(0, 1), 0);
            mContext.addToken(token);
            State state = new StartState(mContext);
            mContext.setState(state);
            state.handle(jsonString.substring(1));
        }
    }

    private boolean patternSign(String jsonString) {
        Pattern pa = Pattern.compile(patternSign);
        Matcher ma = pa.matcher(jsonString);
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
