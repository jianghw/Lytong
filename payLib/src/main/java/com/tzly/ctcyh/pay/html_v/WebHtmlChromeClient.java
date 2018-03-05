package com.tzly.ctcyh.pay.html_v;

import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 页面加载回调顺序
 * shouldOverrideUrlLoading
 * onProgressChanged[10]
 * shouldInterceptRequest
 * onProgressChanged[...]
 * onPageStarted
 * onProgressChanged[...]
 * onLoadResource
 * onProgressChanged[...]
 * onReceivedTitle/onPageCommitVisible
 * onProgressChanged[100]
 * onPageFinished
 * onReceivedIcon
 * 资源加载回调
 * shouldInterceptRequest() -> onLoadResource()
 * 发生重定向时回调
 * onPageStarted() -> shouldOverrideUrlLoading()
 */

public class WebHtmlChromeClient extends WebChromeClient {

    private final ChromeClientable chromeClientable;

    public WebHtmlChromeClient(ChromeClientable chromeClientable) {
        this.chromeClientable = chromeClientable;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(chromeClientable!=null) chromeClientable.onProgressChanged(view, newProgress);
    }

    /**
     * 接收文档标题
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    /**
     * 接收图标(favicon)
     */
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }
}
