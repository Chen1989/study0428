package com.cp.chengradle.json;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2018/6/24.
 *
 * String pageUUIDRex = "id=\"pageUUID\"(.*)value=\'(.*)\'\\s*?/>";
 Pattern pa = Pattern.compile(pageUUIDRex);
 Matcher matcher = pa.matcher(htmlString);
 if (matcher.find()) {
 result.put("pageUUID", matcher.group(2));
 }
 *
 *
 */

public class StartState extends State {
    private String patternSign = "[{|}|:|,]";
    private String patternNumber = "[0-9]";
    private String patternString = "\"";
    public StartState(IContext context) {
        super(context);
    }

    @Override
    public void handle(String jsonString) {
        jsonString = jsonString.trim();
        if (TextUtils.isEmpty(jsonString)) {
            mContext.toString();
            return;
        }
        if (patternSign(jsonString)) {
            mContext.addToken(new JsonToken(jsonString.substring(0, 1), 0));
            jsonString = jsonString.substring(1);
            State state = new StartState(mContext);
            mContext.setState(state);
            state.handle(jsonString);
        } else if (patternNumber(jsonString)) {
            State state = new NumberState(mContext);
            mContext.setState(state);
            state.handle(jsonString);
        } else if (patternString(jsonString)) {
            State state = new StringState(mContext);
            mContext.setState(state);
            state.handle(jsonString);
        } else {

        }
    }

    public boolean patternMatcher(String jsonString) {
        return false;
    }

    private boolean patternSign(String jsonString) {
        Pattern pa = Pattern.compile(patternSign);
        Matcher ma = pa.matcher(jsonString.substring(0,1));
        if (ma.find()) {
            return true;
        }
        return false;
    }

    private boolean patternNumber(String jsonString) {
        Pattern pa = Pattern.compile(patternNumber);
        Matcher ma = pa.matcher(jsonString.substring(0,1));
        if (ma.find()) {
            return true;
        }
        return false;
    }

    private boolean patternString(String jsonString) {
        Pattern pa = Pattern.compile(patternString);
        Matcher ma = pa.matcher(jsonString.substring(0,1));
        if (ma.find()) {
            return true;
        }
        return false;
    }
}
