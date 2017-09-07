package com.zantong.mobilecttx.browser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.contract.InterfaceForJS;

import butterknife.Bind;
import cn.qqtheme.framework.global.JxGlobal;

/**
 * 加油支付浏览器
 */
public class BrowserForPayActivity extends BaseJxActivity {

    @Bind(R.id.webView)
    ProgressWebView mWebView;

    protected String mStrTitle;
    protected String mStrUrl;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mStrTitle = intent.getStringExtra(JxGlobal.putExtra.browser_title_extra);
            mStrUrl = intent.getStringExtra(JxGlobal.putExtra.browser_url_extra);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_browser;
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent(mStrTitle);
        setTvCloseVisible();
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initViewStatus() {
        String url = "<%@ page language=\"java\" contentType=\"text/html; charset=GBK\" pageEncoding=\"GBK\"%>" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\" http://www.w3.org/TR/html4/loose.dtd\">" +
                "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">" +
                "<title>表单提交</title></head><body>" + mStrUrl + "</body></html>";

        mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        mWebView.requestFocus();
    }

    /**
     * 回退建
     */
    protected void backClickListener() {
        // 返回前一个页面
        if (mWebView.canGoBack()) mWebView.goBack();
        else finish();
    }


    @Override
    protected void DestroyViewAndThing() {
        if (mWebView != null) mWebView.destroyWebView();
    }

    private class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
