package com.tzly.ctcyh.pay.html_v;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tzly.ctcyh.pay.bean.response.OrderDetailBean;
import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.html_p.HtmlPayPresenter;
import com.tzly.ctcyh.pay.html_p.IHtmlPayContract;
import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.base.JxBaseActivity;

import static com.tzly.ctcyh.router.util.ToastUtils.toastShort;

/**
 * Created by jianghw on 2017/10/23.
 * Description:
 * Update by:
 * Update day:
 */

public class Html5Activity extends JxBaseActivity implements IHtmlPayContract.IHtmlPayView {
    private LinearLayout mLayout;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    private String mTitle;
    private String mUrl;
    private String mOrderId;
    private int mPayType;
    private IHtmlPayContract.IHtmlPayPresenter mPresenter;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(PayGlobal.putExtra.web_title_extra))
                mTitle = bundle.getString(PayGlobal.putExtra.web_title_extra);
            if (intent.hasExtra(PayGlobal.putExtra.web_url_extra))
                mUrl = bundle.getString(PayGlobal.putExtra.web_url_extra);
            if (intent.hasExtra(PayGlobal.putExtra.web_orderId_extra))
                mOrderId = bundle.getString(PayGlobal.putExtra.web_orderId_extra);
            if (intent.hasExtra(PayGlobal.putExtra.web_pay_type_extra))
                mPayType = bundle.getInt(PayGlobal.putExtra.web_pay_type_extra);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_html_5;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void bindContentView(View childView) {
        mProgressBar = (ProgressBar) childView.findViewById(R.id.pb_html5);
        mWebView = (WebView) childView.findViewById(R.id.wv_html5);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);//支持缩放，默认为true
        //mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件
        mWebSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        mWebSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebSettings.setLoadsImagesAutomatically(true);//支持自动加载图片

        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(null, "CTTX");

        saveData(mWebSettings);

        newWin(mWebSettings);
        // 5.0上 Webview 默认不允许加载 Http 与 Https 混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        if (mPayType == 1) {
            mUrl = "<%@ page language=\"java\" contentType=\"text/html; charset=GBK\" pageEncoding=\"GBK\"%>" +
                    "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\" http://www.w3.org/TR/html4/loose.dtd\">" +
                    "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">" +
                    "<title>表单提交</title></head><body>" + mUrl + "</body></html>";

            mWebView.loadDataWithBaseURL(null, mUrl, "text/html", "utf-8", null);
        } else {
            mWebView.loadUrl(mUrl);
        }

        //支持获取手势焦点，输入用户名、密码或其他
        mWebView.requestFocusFromTouch();
    }

    @Override
    protected void initContentData() {
        titleContent(mTitle);
        titleClose();

        HtmlPayPresenter presenter = new HtmlPayPresenter(
                InjectionRepository.provideRepository(getApplicationContext()), this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers(); //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

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

    WebViewClient webViewClient = new WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent();
            if (url.startsWith("tel:")) {//电话
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
                   toastShort("请确认手机安装支付宝app");
                }
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        /**
         * 重写此方法才能够处理在浏览器中的按键事件
         */
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }

        /**
         * 这个事件就是开始载入页面调用的
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        /**
         * 在页面加载结束时调用
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        /**
         * 拦截替换网络请求数据,API 21引入
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        /**
         * 报告错误信息
         */
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        /**
         * 重写此方法可以让webview处理https请求
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        /**
         * 应用程序重新请求网页数据
         */
        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            super.onFormResubmission(view, dontResend, resend);
        }

        /**
         * 获取返回信息授权请求
         */
        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        /**
         * WebView发生改变时调用
         */
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }

        /**
         * Key事件未被加载时调用
         */
        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
            super.onUnhandledKeyEvent(view, event);
        }

        /**
         * 更新历史记录
         */
        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    };

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
     * 长按事件
     */
    public void onLongClickListener() {
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //判断点击的类型
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (null == result) return false;
                int type = result.getType();
                if (type == WebView.HitTestResult.UNKNOWN_TYPE) return false;

                // 这里可以拦截很多类型，我们只处理图片类型就可以了
                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // 地图类型
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        break;
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        // 获取图片的路径
                        String saveImgUrl = result.getExtra();

                        // 跳转到图片详情页，显示图片
                        //                        Intent i = new Intent(MainActivity.this, ImageActivity.class);
                        //                        i.putExtra("imgUrl", saveImgUrl);
                        //                        startActivity(i);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 回退键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        if (!TextUtils.isEmpty(mOrderId)) orderDetail();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearFormData();
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();

        System.exit(0);
    }

    /**
     * 回退
     */
    @Override
    protected void backClickListener() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
        } else {
            if (!TextUtils.isEmpty(mOrderId)) orderDetail();
        }
    }

    /**
     * 关闭
     */
    @Override
    protected void closeClickListener() {
        if (!TextUtils.isEmpty(mOrderId)) orderDetail();
    }

    protected void orderDetail() {
        if (mPresenter != null) mPresenter.orderDetail();
    }

    @Override
    public void setPresenter(IHtmlPayContract.IHtmlPayPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void orderDetailCompleted() {
        //失败页面
        errorStatus();
    }

    @Override
    public void intervalError(String message) {
        toastShort(message);
    }

    @Override
    public String getOrderId() {
        return mOrderId;
    }

    /**
     * 获取订单详情页面
     */
    @Override
    public void getOrderDetailSucceed(OrderDetailResponse response) {
        OrderDetailBean orderDetailBean = response.getData();
        if (orderDetailBean != null) {
            int orderStatus = orderDetailBean.getOrderStatus();
            //成功页面
            if (orderStatus == 1) succeedStatus(orderDetailBean);
        }
    }

    protected void succeedStatus(OrderDetailBean orderDetailBean) {
        setResult(PayGlobal.resultCode.web_pay_succeed, null);
        finish();
    }

    protected void errorStatus() {
        setResult(PayGlobal.resultCode.web_pay_error, null);
        finish();
    }

    @Override
    public void getOrderDetailError(String s) {
        toastShort(s);
        errorStatus();
    }
}
