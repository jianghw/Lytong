package com.zantong.mobilecttx.browser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zantong.mobilecttx.R;


public class ProgressWebView extends LinearLayout {
    private ProgressBar mProgressBar;
    private WebView mWebView;
    private onReceivedTitleListener mTitleListener;
    private boolean isLoadCompleted;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_progress_webview, this);

        mProgressBar = (ProgressBar) findViewById(R.id.widget_progress_webview_pb);
        mWebView = (WebView) findViewById(R.id.widget_progress_webview);

        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

        String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);

        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient());
    }

    public WebSettings getSettings() {
        return mWebView.getSettings();
    }

    public void setWebViewClient(WebViewClient client) {
        mWebView.setWebViewClient(client);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        mWebView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        mWebView.setWebChromeClient(webChromeClient);
    }

    private class WebChromeClient extends android.webkit.WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress != 100) mProgressBar.setProgress(newProgress);

            mProgressBar.setVisibility(newProgress < 100 ? View.VISIBLE : View.GONE);
            isLoadCompleted = (newProgress == 100);

            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mTitleListener != null) mTitleListener.onReceivedTitle(view, title);

            super.onReceivedTitle(view, title);
        }
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
        mWebView.goBack();
    }

    public void setOnReceivedTitleListener(onReceivedTitleListener listener) {
        this.mTitleListener = listener;
    }

    public interface onReceivedTitleListener {
        void onReceivedTitle(WebView view, String title);
    }

    @SuppressLint({"AddJavascriptInterface", "JavascriptInterface"})
    public void addJavascriptInterface(Object object, String name) {
        mWebView.addJavascriptInterface(object, name);
    }

    public boolean getIsLoadCompleted() {
        return this.isLoadCompleted;
    }

    public void destroyWebView() {
        mWebView.destroy();
    }
}