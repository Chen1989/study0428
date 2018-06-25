package com.cp.chengradle.json;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PengChen on 2018/6/25.
 * "chen":"name"
 */

public class StringState extends State {
    private String patternString = "\"";
    private String patternUnString = "[^\"]";

    private String findString = "(\\S*?)\"";
    public StringState(IContext context) {
        super(context);
    }

    @Override
    public void handle(String jsonString) {
        jsonString = jsonString.trim();
        if (TextUtils.isEmpty(jsonString)) {
            mContext.toString();
            return;
        }
        if (patternString(jsonString)) {
            jsonString = jsonString.substring(1);
            String str = findString(jsonString);
            mContext.addToken(new JsonToken(str, 2));
            State state = new StringState(mContext);
            mContext.setState(state);
            state.handle(jsonString.substring(str.length() + 1));
        } else if (patternUnString(jsonString)){
            State state = new StartState(mContext);
            mContext.setState(state);
            state.handle(jsonString);
        }
    }

    private boolean patternString(String jsonString) {
        Pattern pa = Pattern.compile(patternString);
        Matcher ma = pa.matcher(jsonString.substring(0,1));
        if (ma.find()) {
            return true;
        }
        return false;
    }

    private boolean patternUnString(String jsonString) {
        Pattern pa = Pattern.compile(patternUnString);
        Matcher ma = pa.matcher(jsonString.substring(0,1));
        if (ma.find()) {
            return true;
        }
        return false;
    }

    private String findString(String jsonString) {
        Pattern pa = Pattern.compile(findString);
        Matcher ma = pa.matcher(jsonString);
        if (ma.find()) {
            return ma.group(1);
        }
        return "";
    }
}
