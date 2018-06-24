package com.cp.chengradle.Web;

/**
 * Created by longyu on 2017/4/15.
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　＞　　　＜　┃
 * ┃　　　　　　　┃
 * ┃...　⌒　...　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * ┃　　　┃
 * ┃　　　┃
 * ┃　　　┃
 * ┃　　　┃  神兽保佑
 * ┃　　　┃  代码无bug
 * ┃　　　┃
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 */

public interface CMD {
    void executeRoll(int remainLength, boolean scrollDown, String methodName);

    void roll(int i, int i1, int i2, int i3, int rate, int span);

    void setScrollY(int y);

    int getScrollY();

    int getScrollX();

    void click(int x, int y);

    /**
     * 按返回键处理
     */
    void back();

    /**
     * 模拟人的行为点击左上角的关闭按钮
     */
    void close();

    void executeRoll(int remainLength, boolean scrollDown);

    /**
     * 销毁搭载ad的activity
     */
    void destory();

    void home();
}
