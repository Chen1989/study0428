package com.chen.security.parser;

/**
 * Created by PengChen on 2018/7/9.
 *
 * java -jar bin\\classes.jar encKey $(src) $(tar)
 *
 */

public class ChenStateStart extends ChenState {

    public ChenStateStart(IChenContext chenContext) {
        super(chenContext);
    }

    @Override
    public void handleString(char[] str, int index) {
        if (index + 1 >= str.length) {
            mChenContext.parser(FinalValue.StateFinish, str, index);
            return;
        }
        if (str[index] == '$' && index + 1 < str.length && str[index + 1] == '(') {
            mChenContext.parser(FinalValue.StateParser, str, index + 2);
        } else if (index >= str.length){
            mChenContext.parser(FinalValue.StateError, str, index);
        } else {
            mChenContext.parser(FinalValue.StateStart, str, index + 1);
        }
    }
}
