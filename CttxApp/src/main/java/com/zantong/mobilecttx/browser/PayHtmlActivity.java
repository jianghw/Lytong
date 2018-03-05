package com.zantong.mobilecttx.browser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.browser.IPayHtmlContract;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.presenter.browser.PayHtmlPresenter;
import com.zantong.mobilecttx.router.MainRouter;

/**
 * 加油 违章 支付浏览器
 */
public class PayHtmlActivity extends AbstractBaseActivity implements IPayHtmlContract.IPayHtmlView {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    protected String mStrTitle;
    protected String mStrUrl;
    private String mViolationNum;
    private String mViolationEngine;

    private IPayHtmlContract.IPayHtmlPresenter mPresenter;

    @Override
    protected int initContentView() {
        return R.layout.activity_browser;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(MainGlobal.putExtra.browser_title_extra))
                    mStrTitle = bundle.getString(MainGlobal.putExtra.browser_title_extra);
                if (intent.hasExtra(MainGlobal.putExtra.browser_url_extra))
                    mStrUrl = bundle.getString(MainGlobal.putExtra.browser_url_extra);
                if (intent.hasExtra(MainGlobal.putExtra.violation_num_extra))
                    mViolationNum = bundle.getString(MainGlobal.putExtra.violation_num_extra);
                if (intent.hasExtra(MainGlobal.putExtra.car_enginenum_extra))
                    mViolationEngine = bundle.getString(MainGlobal.putExtra.car_enginenum_extra);
            }
        }
    }

    @Override
    protected void bindFragment() {
        mProgressBar = (ProgressBar) findViewById(R.id.pb_html5);
        mWebView = (WebView) findViewById(R.id.wv_html5);

        titleContent(mStrTitle);
        titleClose();

        PayHtmlPresenter presenter = new PayHtmlPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void initContentData() {
        initViewStatus();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    protected void initViewStatus() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置支持Javascript
        settings.setDefaultTextEncodingName("utf-8");
        //自己添加
        settings.setSupportZoom(true);//支持缩放，默认为true
        //mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        settings.setLoadsImagesAutomatically(true);//支持自动加载图片
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
        saveData(settings);
        newWin(settings);

        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(webChromeClient);

        //设置支持Javascript
        mWebView.getSettings().setJavaScriptEnabled(true);

        if (!TextUtils.isEmpty(mViolationNum)) {
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.loadUrl(mStrUrl);
            mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        } else {
            String url = "<%@ page language=\"java\" contentType=\"text/html; charset=GBK\" pageEncoding=\"GBK\"%>" +
                    "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\" http://www.w3.org/TR/html4/loose.dtd\">" +
                    "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">" +
                    "<title>表单提交</title></head><body>" + mStrUrl + "</body></html>";
            mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        }

        //支持获取手势焦点，输入用户名、密码或其他
        mWebView.requestFocusFromTouch();
    }

    WebChromeClient webChromeClient = new WebChromeClient() {
        /**
         * 获得网页的加载进度
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        /**
         * 获取Web页中的title用来设置自己界面中的title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================

        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
        //=========多窗口的问题==========================================================
    };

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.setDomStorageEnabled(true); //开启 DOM storage API 功能
        mWebSettings.setDatabaseEnabled(true); //开启database storage API 功能
        mWebSettings.setAppCacheEnabled(true);//开启Application Caches 功能
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false); //多窗口
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) mPresenter.unSubscribe();
        if (mWebView != null) mWebView.destroy();
    }

    WebViewClient webViewClient = new WebViewClient() {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent();
            if (url.startsWith("tel:")) {
                intent.setAction(Intent.ACTION_DIAL);
                Uri data = Uri.parse(url);
                intent.setData(data);
                startActivity(intent);
            } else if (url.startsWith("alipays:")) {//阿里支付
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url.startsWith("weixin://wap/pay?")) {//微信
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    };

    @Override
    public void setPresenter(IPayHtmlContract.IPayHtmlPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getViolationNum() {
        return mViolationNum;
    }

    @Override
    public void numberedQueryError(String msg) {
        shortToast("获取违章数据失败" + msg);
        gotoActive();
    }

    @Override
    public void updateStateError(String msg) {
        shortToast("更新同步数据失败" + msg);
        gotoActive();
    }

    @Override
    public void updateStateSucceed(BaseResponse result) {
        gotoActive();
    }

    private void gotoActive() {
        if (TextUtils.isEmpty(mViolationEngine))
            MainRouter.gotoMainActivity(this, 1);
        else
            MainRouter.gotoActiveActivity(this, 2);
    }

    /**
     * 回退
     */
    @Override
    protected void backClickListener() {
        if (mWebView.canGoBack()) {
            mWebView.goBack(); //返回前一个页面
        } else if (!TextUtils.isEmpty(mViolationNum)) {
            updateState();
        } else {
            finish();
        }
    }

    /**
     * 关闭
     */
    @Override
    protected void closeClickListener() {
        if (!TextUtils.isEmpty(mViolationNum)) {
            updateState();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();//返回webView的上一页面
            return true;
        }
        if (!TextUtils.isEmpty(mViolationNum)) {
            updateState();
        } else {
            finish();
        }
        return true;
    }

    private void updateState() {
        if (mPresenter != null) mPresenter.numberedQuery();
    }

}
