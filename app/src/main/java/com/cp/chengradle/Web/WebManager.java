package com.cp.chengradle.Web;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class WebManager {
    public static final int Close = 1;
    public static final int Completed = 2;
    public static final int Limit = 3;
    private static final String ListName = "webadlist";

    private static WebManager _manager;
    private static boolean _clearFlag = false;

    private static Handler _handler = new Handler(Looper.getMainLooper());


    public class Controller extends Web {
        //浏览器返回
        @JavascriptInterface
        public void goBack() {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    _webView.goBack();
                }
            });
        }

        //获取浏览器当前Url
        @JavascriptInterface
        public String getCurrentUrl() {
            return _webView.getUrl();
        }

        //结束流程
        @JavascriptInterface
        public void close() {
            WebManager.this.close(Close);
        }

        @JavascriptInterface
        public void switchShowImage(final boolean flag) {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    _webView.getSettings().setLoadsImagesAutomatically(flag);
                }
            });
        }
    }

    public class Storage extends Web {
        private Map<String, String> _data = new HashMap<String, String>();
        private String _uuid;

        public Storage() {
            _uuid = UUID.randomUUID().toString();
        }

        public String getUuid() {
            return _uuid;
        }

        public void setData(Map<String, String> _data) {
            this._data = _data;
        }

        //存储指定 key-value 到map
        @JavascriptInterface
        public void put(String key, String value) {
            _data.put(key, value);
        }

        //按key从map获取value
        @JavascriptInterface
        public String get(String key, String def) {
            String v = _data.get(key);

            if (v == null) {
                return def;
            } else {
                return v;
            }
        }

        @JavascriptInterface
        public void add(String name) {
        }

        @JavascriptInterface
        public int count(String name, int def) {

            return def;
        }

        public void clear() {
            if (_data.size() > 0)
                _data.clear();
        }
    }

    public class Sdk extends Web {
        private float getDensity() {
            return _context.getResources().getDisplayMetrics().density;
        }

        private int getDensity(int value) {
            return (int) (value * getDensity());
        }

        @JavascriptInterface
        public float density() {
            return _context.getResources().getDisplayMetrics().density;
        }

        @JavascriptInterface
        public void reportError(int type, String content, String msg) {
//            EventUtil.sendArgs(_context, "", "request_web_error")
//                    .arg(2, type + "").arg(3, content).arg(4, msg).send();
        }

        @JavascriptInterface
        public void dataEvent(String type, String ext1, String ext2, String ext3, String ext4) {
//            EventUtil.sendArgs(_context, type, "request_web_error").arg(1, ext1).arg(2, ext2).arg(3, ext3).arg(4, ext4)
//                    .send();
        }

        @JavascriptInterface
        public String getInfo() {
//            try {
//                JSONObject json = new JSONObject();
//                TerminalInfo ter = TerminalInfo.instance(_context);
//                json.put("imei", TerminalInfo.instance(_context).getImei());
//                json.put("imsi", ter.getImsi());
//                json.put("aid", ter.getAppId());
//                json.put("cid", ter.getChannelId());
//                json.put("hstype", ter.getHstype());
//                json.put("hsman", ter.getHsman());
//                json.put("osVer", ter.getOsVer());
//                json.put("netType", ter.getNetworkType());
//
//                return json.toString();
//            } catch (Exception e) {
//                ExceptionUtils.handle(e);
//            }

            return "{}";
        }

        @JavascriptInterface
        public void rollMob(final int remainLength, final boolean scrollState, final String methodName) {
//            Sync.run(new Runnable() {
//                @Override
//                public void run() {
//                    if (_webView != null) {
//                        Logger.e("Roll[Len:" + remainLength + "," + "Begin:" + _webView.getScaleY() + "," + (scrollState ? "Down" : "Up") + ",CallBack:" + methodName + "]");
//                        WebManager.this.executeRoll(remainLength, scrollState);
//                        if (_webView != null) {
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

        @JavascriptInterface
        public void click(int x, int y) {
            float dp = 1;
//            Logger.d("click x:" + x * dp + " y:" + y * dp);
//            InputSimulator.clickRandom(_webView, (int) (x * dp), (int) (y * dp));
        }

        @JavascriptInterface
        public void clickNoDelay(int x, int y) {
            float dp = 1;
//            Logger.d("click x:" + x * dp + " y:" + y * dp);
//            InputSimulator.clickNoDelay(_webView, (int) (x * dp), (int) (y * dp));
        }

        @JavascriptInterface
        public void roll(final int fx, final int fy, final int tx, final int ty, int rate, int span) {
            float dp = 1;
//            Logger.d("roll fx:" + fx * dp + " fy:" + fy * dp + " tx:" + tx * dp + " ty:" + ty * dp + " rate:" + rate + " span:" + span);
//            InputSimulator.roll(_webView, (int) (fx * dp), (int) (fy * dp), (int) (tx * dp), (int) (ty * dp), rate, span);
        }

        @JavascriptInterface
        public void show(String msg) {
//            Logger.showOnUI(_context, msg);
        }

        @JavascriptInterface
        public void print(String msg) {
//            Logger.showOnUI(_context, msg);
            log(msg);
        }

        @JavascriptInterface
        public void log(String msg) {
            WebManager.this.log(msg);
        }

        @JavascriptInterface
        public int getScrollX() {
            return _webView.getScrollX();
        }

        @JavascriptInterface
        public int getScrollY() {
            return _webView.getScrollY();
        }

        @JavascriptInterface
        public void setScrollY(final int y) {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= 14)
                        _webView.setScrollY(y);
                }
            });
        }

        @JavascriptInterface
        public String getKeyword(int type) {


            return "";
        }

        @JavascriptInterface
        public String getLastUseKeyword(int type) {


            return "";
        }

        @JavascriptInterface
        public void executeCompleted() {

        }
    }

    public class StateManager
    {
        private WebStateManager _manager;

        public StateManager(WebStateManager manager)
        {
            _manager = manager;
        }

        @JavascriptInterface
        public void notifyState(int span)
        {
//            Logger.showOnUI(_context, "notify " + span);
//            Logger.i("notify " + span);
            WebStateManager.Input input = new WebStateManager.Input();
            input.setType(StateParams.Notify);
            input.setArg(span);
            _manager.input(input);
        }

        @JavascriptInterface
        public void closeState()
        {
            close(Completed);
            _manager.input(StateParams.Close);
        }
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    public WebManager(final Context context, WebView webView) {
        _context = context;
        if (webView != null) {
            _webView = webView;
        } else
            _webView = new WebView(_context);

        float a = 1.0f;

        _webView.getSettings().setJavaScriptEnabled(true);
        _webView.getSettings().setAllowFileAccess(true);
        _webView.getSettings().setLoadsImagesAutomatically(true);
        _webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        _webView.getSettings().setDomStorageEnabled(true);

        _webView.getSettings().setAppCacheEnabled(true);
        _webView.getSettings().setAppCachePath(_context.getDir("localdata", Context.MODE_PRIVATE).getPath());

        _webView.setBackgroundColor(Color.BLACK);
        _webView.setAlpha(a);
        _webView.addJavascriptInterface(new Sdk(), "WebSdk");
        _webView.addJavascriptInterface(new Storage(), "WebStorage");
        _webView.addJavascriptInterface(new Controller(), "WebController");

        if (Build.VERSION.SDK_INT >= 21)
            _webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        _webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//                if (!NetUtil.hasWifiConn(context))
//                    return;
                if (url != null && (url.startsWith("http") || url.startsWith("ftp"))) {
                    try {
                        DownloadManager manager = (DownloadManager) _context.getSystemService(Context.DOWNLOAD_SERVICE);

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                        request.setMimeType(mimetype);
                        request.setDescription("");
                        request.setVisibleInDownloadsUi(false);
                        manager.enqueue(request);
                    } catch (Exception e) {
//                        sdk.basic.tool.builds.ExceptionUtils.handle(e);
                    }
                }
            }
        });
    }

    public void setup(String method, final String url) {
//        Logger.i("web:" + url);
        final WebStateManager state = initManager("", method);
        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                state.input(0);
                _webView.loadUrl(url);
            }
        }, 3000);

        _webView.addJavascriptInterface(new StateManager(state), "StateController");
        _webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage != null && consoleMessage.message() != null) {

                }
//                    Logger.i(consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });

        _webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                state.input(StateParams.PageFinished);
            }
        });
    }

    private WebView _webView;
    private Context _context;

    private WebStateManager initManager(final String jsUrl, final String method) {
        final long timeout = 7000;
        WebStateManager webStateManager = new WebStateManager();

        // Start -> WaitLong
        webStateManager.define(StateParams.Start, new WebStateManager.WebStateBase() {
            @Override
            protected String judge(WebStateManager.Input input) {
                return StateParams.WaitLong;
            }
        });

        // WaitLong -> WaitShort PageFinished
        // WaitLong -> Hook TimeoutLong
        webStateManager.define(StateParams.WaitLong, new WebStateManager.WebStateBase() {
            private TimeoutHandler _handler;

            @Override
            protected String judge(WebStateManager.Input input) {
                switch (input.getType()) {
                    case StateParams.TimeoutLong:
                        return StateParams.Hook;
                    case StateParams.PageFinished:
                        return StateParams.WaitShort;
                }

                return null;
            }

            @Override
            protected void init(final WebStateManager.IWebStateOwner owner, WebStateManager.Input preInput) {
                int span = 20 * 1000;
                if (preInput.getArg() != null) {
                    span = (Integer) preInput.getArg();
                }

                _handler = new TimeoutHandler(new Runnable() {
                    @Override
                    public void run() {
                        owner.manager().input(StateParams.TimeoutLong);
                    }
                }, span);

                _handler.start();
            }

            @Override
            protected void release(WebStateManager.IWebStateOwner owner) {
                if (_handler != null)
                    _handler.cancel();
            }
        });

        // WaitShort -> WaitShort PageFinished
        // WaitShort -> Hook TimeoutShort
        webStateManager.define(StateParams.WaitShort, new WebStateManager.WebStateBase() {
            private TimeoutHandler _handler;

            @Override
            protected String judge(WebStateManager.Input input) {
                switch (input.getType()) {
                    case StateParams.TimeoutShort:
                        return StateParams.Hook;
                    case StateParams.PageFinished:
                        return StateParams.WaitShort;
                }
                return null;
            }

            @Override
            protected void init(final WebStateManager.IWebStateOwner owner, WebStateManager.Input preInput) {
                _handler = new TimeoutHandler(new Runnable() {
                    @Override
                    public void run() {
                        owner.manager().input(StateParams.TimeoutShort);
                    }
                }, timeout);

                _handler.start();
            }

            @Override
            protected void release(WebStateManager.IWebStateOwner owner) {
                if (_handler != null)
                    _handler.cancel();
            }
        });

        // Hook -> WaitLong Notify
        // Hook -> End Close
        webStateManager.define(StateParams.Hook, new WebStateManager.WebStateBase() {
            @Override
            protected String judge(WebStateManager.Input input) {
                switch (input.getType()) {
                    case StateParams.Notify:
                        return StateParams.WaitLong;
                    case StateParams.Close:
                        return StateParams.End;
                }

                return null;
            }

            @Override
            protected void init(WebStateManager.IWebStateOwner owner, WebStateManager.Input preInput) {
                inject(method, jsUrl);
            }
        });

        webStateManager.define(StateParams.End, new WebStateManager.WebStateBase() {
            @Override
            protected String judge(WebStateManager.Input input) {
                return null;
            }
        });

        webStateManager.setInitState(StateParams.Start);

        return webStateManager;
    }

    private void executeRoll(int remainLength, boolean scrollDown) {
        int startY = _webView.getScrollY();

        int rm = innerRoll(startY, remainLength, scrollDown);
        if (rm > 200)
            innerRoll(_webView.getScrollY(), rm, scrollDown);
    }

    private int innerRoll(int startY, int remainLength, boolean scrollDown) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
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
//                InputSimulator.roll(_webView, fx, fy, tx, ty, 100, (r * 1000 + n * 100));
            } else {
                //起始坐标 > 目标坐标 往上滑动
//                InputSimulator.roll(_webView, tx, ty, fx, fy, 100, (r * 1000 + n * 100));
            }

            try {
                Thread.sleep((6 + random.nextInt(5)) * 100);
            } catch (Exception e) {

            }

            int tmpLength;
            tmpLength = totalLength - Math.abs(_webView.getScrollY() - startY);

            if (tmpLength == remainLength || remainLength <= (int) (20 * _context.getResources().getDisplayMetrics().density)) {
//                Logger.e("tmpLength:" + tmpLength + ",remainLength:" + remainLength);
                break;
            }
            remainLength = tmpLength;
        }

        if ((totalLength - Math.abs(_webView.getScrollY() - startY) > 300)) {
//            Logger.showOnUI(_context, "滑动相差过大");
//            Logger.e("滑动相差过大, " + ((totalLength - Math.abs(_webView.getScrollY() - startY))));
        }

        return remainLength;
    }

    private synchronized void close(int type) {

//        if (type == Close)
//            Logger.showOnUI(_context, "close");
//        else if (type == Completed)
//            Logger.showOnUI(_context, "wap completed");
//        else if (type == Limit)
//            Logger.showOnUI(_context, "wap limit");
    }

    public void destroy() {

        _handler.post(new Runnable() {
            @Override
            public void run() {
                if (_webView != null)
                    _webView.destroy();
            }
        });
    }

    private void log(String msg) {
//        Logger.i("[Web: " + msg + " ]");
    }

    private void inject(String invoke, final String jsUrl) {
        String js = "var newscript = document.createElement(\"script\");";
        js += "newscript.src=\"" + jsUrl + "\";";
        js += String.format("newscript.onload=function(){%s;};", invoke); // xxx()代表js中某方法
        js += "document.body.appendChild(newscript);";
        _webView.loadUrl("javascript:" + js);
    }
}
