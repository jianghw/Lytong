package com.tzly.ctcyh.pay.html_v;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.dialog.DialogUtils;
import com.tzly.ctcyh.router.custom.dialog.WeiXinDialogFragment;
import com.tzly.ctcyh.router.util.EncryptUtils;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * html页面
 */

public class WebHtmlActivity extends AbstractBaseActivity
        implements FmentToAtyable, ViewClientable, ChromeClientable {
    /**
     * 布局
     */
    private WebView mWebView;
    private ProgressBar mProgressBar;
    /**
     * 参数
     */
    protected String mStrTitle;
    protected String mStrUrl;

    private String mOrderId;
    private int mPayType;
    private String mPayChannel;
    private String mPayViolationNum;
    private String mPayEnginenum;
    /**
     * 右控件
     */
    private int mRightBtnStatus = -1;
    /**
     * 客服地址
     */
    private String customerUrl;

    /**
     * 右侧点击
     */
    @Override
    protected void rightClickListener() {
        if (mRightBtnStatus == 1) {//积分规则
            PayRouter.gotoHundredRuleActivity(this);
        } else if (mRightBtnStatus == 2) {//百日
            PayRouter.gotoHundredAgreementActivity(this);
        } else if (mRightBtnStatus == 3) {
            WeiXinDialogFragment fragment = WeiXinDialogFragment.newInstance(mStrUrl);
            DialogUtils.showDialog(this, fragment, "wechat_dialog");
        }
    }

    @Override
    protected void imageClickListener() {
        if (!TextUtils.isEmpty(customerUrl)) {
            PayRouter.gotoWebHtmlActivity(this, "客服系统", customerUrl);
        } else {
            ToastUtils.toastShort("无客服地址" + customerUrl);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.pay_activity_html_web;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(PayGlobal.putExtra.web_title_extra))
                    mStrTitle = bundle.getString(PayGlobal.putExtra.web_title_extra);
                else mStrTitle = "";
                if (intent.hasExtra(PayGlobal.putExtra.web_url_extra))
                    mStrUrl = bundle.getString(PayGlobal.putExtra.web_url_extra);
                else mStrUrl = "";

                if (intent.hasExtra(PayGlobal.putExtra.web_orderId_extra))
                    mOrderId = bundle.getString(PayGlobal.putExtra.web_orderId_extra);
                else mOrderId = "";
                if (intent.hasExtra(PayGlobal.putExtra.web_pay_type_extra))
                    mPayType = bundle.getInt(PayGlobal.putExtra.web_pay_type_extra);
                else mPayType = -1;
                if (intent.hasExtra(PayGlobal.putExtra.web_pay_channel_extra))
                    mPayChannel = bundle.getString(PayGlobal.putExtra.web_pay_channel_extra);
                else mPayChannel = "";

                if (intent.hasExtra(PayGlobal.putExtra.web_violationnum_extra))
                    mPayViolationNum = bundle.getString(PayGlobal.putExtra.web_violationnum_extra);
                else mPayViolationNum = "";
                if (intent.hasExtra(PayGlobal.putExtra.web_enginenum_extra))
                    mPayEnginenum = bundle.getString(PayGlobal.putExtra.web_enginenum_extra);
                else mPayEnginenum = "";
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        initTitle();
        if (!TextUtils.isEmpty(mStrUrl)) webLoadUrl(mStrUrl);
    }

    @Override
    protected void bindFragment() {
        initTitle();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        mProgressBar = (ProgressBar) layout.findViewById(R.id.pb_html);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        layout.addView(mWebView);
    }

    private void initTitle() {
        if (!TextUtils.isEmpty(mStrTitle)) titleContent(mStrTitle);
        titleClose();

        if (TextUtils.isEmpty(mStrUrl)) {
            titleContent("网站空地址");
        } else if (mStrUrl.contains("detail")) {
            mRightBtnStatus = 1;
            titleContent("积分明细");
            titleMore("积分规则");
        } else if (mStrUrl.contains("h5/build/index")) {
            mRightBtnStatus = 2;
            titleContent("百日无违章");
            titleMore("活动说明");
        } else if (!TextUtils.isEmpty(customerUrl)) {
            titleServer();
        }
    }

    @Override
    protected void initContentData() {
        bindWebSettings();
        //支持获取手势焦点，输入用户名、密码或其他
        mWebView.requestFocusFromTouch();

        webLoadUrl(mStrUrl);
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void bindWebSettings() {
        mWebView.setWebViewClient(new WebHtmlViewClient(this));
        mWebView.setWebChromeClient(new WebHtmlChromeClient(this));

        WebSettings settings = mWebView.getSettings();

        FragmentManager manager = getSupportFragmentManager();
        @SuppressLint("CommitTransaction")
        FragmentTransaction transaction = manager.beginTransaction();
        //创建fragment但是不绘制UI
        WebHtmlFragment htmlFragment = WebHtmlFragment.newInstance();
        transaction.add(htmlFragment, "web_html").commit();
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        settings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(htmlFragment, "CTTX");

        //设置是否支持缩放，我这里为false，默认为true。
        settings.setSupportZoom(true);
        //设置是否显示缩放工具，默认为false
        settings.setBuiltInZoomControls(false);
        //设置自适应屏幕将图片调整到适合webview的大小
        settings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        //是否当webview调用requestFocus时为页面的某个元素设置焦点，默认值 true
        settings.setNeedInitialFocus(true);

        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置默认的字体大小，默认为16，有效值区间在1-72之间
        settings.setDefaultFontSize(18);

        //提高渲染等级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 布局算法
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //关闭webview中缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置编码格式
        settings.setDefaultTextEncodingName("utf-8");
        saveData(settings);
        newWin(settings);
        // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //调试调试用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.isDeta);
        }
    }

    private void webLoadUrl(String mStrUrl) {
        //额外的第三方要求
        if (!TextUtils.isEmpty(mPayViolationNum)) {//违章支付
            mWebView.loadUrl(mStrUrl);
        } else if (mPayType == 1) {//银行支付
            String url = "<%@ page language=\"java\" contentType=\"text/html; charset=GBK\" pageEncoding=\"GBK\"%>" +
                    "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\" http://www.w3.org/TR/html4/loose.dtd\">" +
                    "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">" +
                    "<title>表单提交</title></head><body>" + mStrUrl + "</body></html>";
            mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else if (mPayType == 3) {//阿里支付
            mWebView.loadUrl(mStrUrl);
        } else if (mPayType == 4) {//微信支付
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Referer", "http://liyingtong.com");
            mWebView.loadUrl(mStrUrl, extraHeaders);
        } else if (mStrUrl.contains("m.wedrive.com.cn") || mStrUrl.contains("tester.wedrive.com.cn")) {
            String cust_id = PayRouter.getUserPhoenum();
            String SEC_KEY = "BE7D6564766740037581842CE0ACA1DD";
            String token = EncryptUtils.encryptMD5ToString(SEC_KEY + cust_id + SEC_KEY);
            String url = mStrUrl + "?cust_id=" + cust_id + "&token=" + token;
            mWebView.loadUrl(url);
        } else {
            mWebView.loadUrl(mStrUrl);
        }
    }

    /**
     * HTML5数据存储
     */

    private void saveData(WebSettings settings) {
        settings.setDomStorageEnabled(true); //开启 DOM storage API 功能
        settings.setDatabaseEnabled(true); //开启database storage API 功能
        settings.setAppCacheEnabled(true);//开启Application Caches 功能
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);

        settings.setGeolocationEnabled(true);//定位(location)
        settings.setSaveFormData(true);//是否保存表单数据
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings settings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        settings.setSupportMultipleWindows(false); //多窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mWebView != null) {
            mWebView.clearFormData();
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.clearMatches();
            mWebView.clearSslPreferences();

            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.removeJavascriptInterface("CTTX");
            mWebView.destroy();
            mWebView = null;
        }
    }

    /**
     * 当返回true时，表示已经完整地处理了这个事件，并不希望其他的回调方法再次进行处理，
     * 当返回false时，表示并没有完全处理完该事件，更希望其他回调方法继续对其进行处理，例如Activity中的回调方法。
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();//返回webView的上一页面
            return true;
        }
        finishBySelf();
        return true;
    }

    /**
     * 回退
     */
    @Override
    protected void backClickListener() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack(); //返回前一个页面
            return;
        }
        finishBySelf();
    }

    private void finishBySelf() {
        if (!TextUtils.isEmpty(mPayViolationNum)) {//违章支付
            updateState();
        } else if (!TextUtils.isEmpty(mOrderId)) {//支付
            orderDetail();
        } else {
            finish();
        }
    }

    /**
     * 关闭
     */
    @Override
    protected void closeClickListener() {
        finishBySelf();
    }

    private void updateState() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("web_html");
        if (fragment != null && fragment instanceof WebHtmlFragment) {
            ((WebHtmlFragment) fragment).bank_v003(mPayViolationNum);
        }
    }

    private void orderDetail() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("web_html");
        if (fragment != null && fragment instanceof WebHtmlFragment) {
            ((WebHtmlFragment) fragment).orderDetail(mOrderId);
        }
    }

    /**
     * 接口实现
     */
    @Override
    public void shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.e("shouldOverrideUrlLoading-->" + url);
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
        } else if (url.startsWith("com.icbc.androidclient://")) {//工行页面
            //            intent.setAction(Intent.ACTION_VIEW);
            //            intent.setData(Uri.parse(url));
            //            try {
            //                startActivity(intent);
            //            } catch (Exception e) {
            //                e.printStackTrace();
            //            }
        } else {
            view.loadUrl(url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        //首先阻塞图片，让图片不显示
        view.getSettings().setBlockNetworkImage(true);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        String script = "javascript:" + url;
        if (view != null && url != null) view.loadUrl(script);

        LogUtils.e("javascript==>" + script);

        //页面加载好以后，在放开图片
        view.getSettings().setBlockNetworkImage(false);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mProgressBar != null) mProgressBar.setProgress(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        //        if (!TextUtils.isEmpty(title)) titleContent(title);
    }

    /**
     * fragment--activity
     */
    @Override
    public void callbackCamera(String js) {
        if (mWebView != null) mWebView.loadUrl(js);
    }

    @Override
    public String getEnginenum() {
        return mPayEnginenum;
    }

    @Override
    public String getChannel() {
        return mPayChannel;
    }

    @Override
    public void backApp() {
        finishBySelf();
    }

    /**
     * 客服
     */
    @Override
    public void customerService(String url) {
        this.customerUrl = url;
        titleServer();
    }

}
