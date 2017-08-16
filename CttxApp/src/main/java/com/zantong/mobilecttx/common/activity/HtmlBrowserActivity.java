package com.zantong.mobilecttx.common.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.contract.InterfaceForJS;
import com.zantong.mobilecttx.widght.ProgressWebView;

import butterknife.Bind;
import cn.qqtheme.framework.global.GlobalConstant;

/**
 * 公用浏览器 html页面显示
 */
public class HtmlBrowserActivity extends BaseJxActivity {

    protected String strTitle;
    protected String strUrl;

    @Bind(R.id.webView)
    ProgressWebView mWebView;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            strTitle = intent.getStringExtra(GlobalConstant.putExtra.browser_title_extra);
            strUrl = intent.getStringExtra(GlobalConstant.putExtra.browser_url_extra);
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
        initTitleContent(strTitle);
    }

    protected void initViewStatus() {
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
        mWebView.loadUrl(strUrl);

        WebSettings settings = mWebView.getSettings();
        //设置支持Javascript
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //触摸焦点起作用.
        //如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
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

    /**
     * 右侧点击
     */
    protected void rightClickListener() {
    }

    private class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        mWebView.destroyWebView();
    }

}
