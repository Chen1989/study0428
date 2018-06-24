package com.cp.chengradle.Web;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.util.Random;

/**
 * Created by longyu on 2017/4/14.
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

public class WebSdk extends Web implements CMD {
    private CMD cmd;

    public WebSdk(CMD cmd) {
        this.cmd = cmd;
    }

    @JavascriptInterface
    public void click(int x, int y) {
        cmd.click(x, y);
    }

    @JavascriptInterface
    public int getScrollX() {
        return cmd.getScrollX();
    }

    @JavascriptInterface
    public int getScrollY() {
        return cmd.getScrollY();
    }

    @JavascriptInterface
    public void setScrollY(final int y) {
        cmd.setScrollY(y);
    }


    @Override
    @JavascriptInterface
    public void executeRoll(int remainLength, boolean scrollDown, String methodName) {
        cmd.executeRoll(remainLength, scrollDown, methodName);
    }

    @JavascriptInterface
    public void roll(final int fx, final int fy, final int tx, final int ty, int rate, int span) {
        float dp = 1;
        cmd.roll((int) (fx * dp), (int) (fy * dp), (int) (tx * dp), (int) (ty * dp), rate, span);
    }

    @JavascriptInterface
    public void rollMob(final int remainLength, final boolean scrollState, final String methodName) {
        cmd.executeRoll(remainLength, scrollState, methodName);
    }

    @JavascriptInterface
    public void back() {
        cmd.back();
    }

    @JavascriptInterface
    public void close() {
        cmd.close();
    }

    @Override
    @JavascriptInterface
    public void executeRoll(int remainLength, boolean scrollDown) {
        cmd.executeRoll(remainLength, scrollDown);
    }

    @Override
    @JavascriptInterface
    public void destory() {
        cmd.destory();
    }

    @Override
    public void home() {
        cmd.home();
    }

    public static abstract class CMDImpl implements CMD {
        WebView _webView;

        public CMDImpl(WebView _webView) {
            this._webView = _webView;
        }

        public void executeRoll(final int remainLength, final boolean scrollDown, final String methodName) {
//            Sync.run(new Runnable() {
//                @Override
//                public void run() {
//                    if (_webView != null) {
//                        Logger.e("Roll[Len:" + remainLength + "," + "Begin:" + _webView.getScrollY() + "," + (scrollDown ? "Down" : "Up") + ",CallBack:" + methodName + "]");
//                        executeRoll(remainLength, scrollDown);
//                        if (_webView != null && !TextUtils.isEmpty(methodName)) {
//                            _webView.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String str = String.format("javascript:%s()", methodName);
//                                    Logger.e("CallBack:" + str);
//                                    _webView.loadUrl(str);
//                                }
//                            });
//                        }
//                    }
//                }
//            });
        }

        @Override
        public void roll(int i, int i1, int i2, int i3, int rate, int span) {
//            InputSimulator.roll(_webView, i, i1, i2, i3, rate, span);
        }

        @Override
        public void setScrollY(final int y) {
            _webView.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= 14)
                        _webView.setScrollY(y);
                }
            });
        }

        @Override
        public int getScrollY() {
            return _webView.getScrollY();
        }

        @Override
        public int getScrollX() {
            return _webView.getScrollX();
        }

        @Override
        public void click(int x, int y) {
//            InputSimulator.clickRandom(_webView, x, y);
        }

        public void executeRoll(int remainLength, boolean scrollDown) {
            int startY = _webView.getScrollY();

            int rm = innerRoll(startY, remainLength, scrollDown);
            if (rm > 200)
                innerRoll(_webView.getScrollY(), rm, scrollDown);
        }

        private int innerRoll(int startY, int remainLength, boolean scrollDown) {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) _webView.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);
            int clientWidth = metrics.widthPixels;
            int clientHeight = metrics.heightPixels;
            int ty;
            Random random = new Random();
            int totalLength = remainLength;
            while (remainLength > 0) {
                int fx = (int) (Math.floor(clientWidth * (0.4 + Math.random() * 0.1)));
                int fy = (int) Math.floor(clientHeight * (0.8 + Math.random() * 0.1));
                int tx = (int) Math.floor(clientWidth * (0.3 + Math.random() * 0.2));
                int scrollLength = (int) Math.floor(clientHeight * (0.5 + Math.random() * 0.2));

                int length;
                if (remainLength < scrollLength) {
                    length = remainLength;
                } else {
                    length = scrollLength;
                }
                ty = fy - length;
                int r = 1 + random.nextInt(2);
                int n = 2 + random.nextInt(5);
                if (scrollDown) {
                    // fy < ty（起始坐标 < 目标坐标）往下滑动
//                    InputSimulator.roll(_webView, fx, fy, tx, ty, 100, (r * 1000 + n * 100));
                } else {
                    //起始坐标 > 目标坐标 往上滑动
//                    InputSimulator.roll(_webView, tx, ty, fx, fy, 100, (r * 1000 + n * 100));
                }

                try {
                    Thread.sleep((6 + random.nextInt(5)) * 100);
                } catch (Exception e) {

                }

                int tmpLength;
                tmpLength = totalLength - Math.abs(_webView.getScrollY() - startY);

                if (tmpLength == remainLength || remainLength <= (int) (20 * _webView.getContext().getResources().getDisplayMetrics().density)) {
//                    Logger.e("tmpLength:" + tmpLength + ",remainLength:" + remainLength);
                    break;
                }
                remainLength = tmpLength;
            }

            if ((totalLength - Math.abs(_webView.getScrollY() - startY) > 300)) {
//                Logger.showOnUI(_webView.getContext(), "滑动相差过大");
//                Logger.e("滑动相差过大, " + ((totalLength - Math.abs(_webView.getScrollY() - startY))));
            }

            return remainLength;
        }
    }
}
