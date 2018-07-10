package com.chen.security.parser;

/**
 * Created by PengChen on 2018/7/10.
 */

public class ChenStateParser extends ChenState {
    StringBuilder builder = new StringBuilder();
    public ChenStateParser(IChenContext chenContext) {
        super(chenContext);
    }

    @Override
    public void handleString(char[] str, int index) {
        if (index >= str.length) {
            mChenContext.parser(FinalValue.StateFinish, str, index);
            return;
        }
        if ((str[index] <= 'z' && str[index] >= 'a') || (str[index] <= 'Z' && str[index] >= 'A')) {
            builder.append(str[index]);
            index++;
            mChenContext.parser(FinalValue.StateParser, str, index);
        } else if (str[index] == ')' && builder.length() > 0) {
            mChenContext.AddKey(index, builder.toString());
            builder = new StringBuilder();
            mChenContext.parser(FinalValue.StateStart, str, index + 1);
        } else {
            mChenContext.parser(FinalValue.StateError, str, index);
        }
    }
}
