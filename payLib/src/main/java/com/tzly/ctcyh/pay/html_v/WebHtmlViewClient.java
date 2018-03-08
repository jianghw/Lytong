package com.tzly.ctcyh.pay.html_v;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tzly.ctcyh.router.util.LogUtils;

/**
 * 加载url
 */

public class WebHtmlViewClient extends WebViewClient {
    private final ViewClientable viewClientable;

    public WebHtmlViewClient(ViewClientable viewClientable) {
        this.viewClientable = viewClientable;
    }

    /**
     * 返回false由当前WebView处理
     * 此方法在API24被废弃，不处理POST请求
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //        return super.shouldOverrideUrlLoading(view, url);
        if (viewClientable != null) viewClientable.shouldOverrideUrlLoading(view, url);
        return true;
    }

    /**
     * 不处理POST请求，可拦截处理子frame的非http请求
     */
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        //        return super.shouldOverrideUrlLoading(view, request);
        if (viewClientable != null)
            viewClientable.shouldOverrideUrlLoading(view, request.getUrl().toString());
        return true;
    }

    /**
     * 直接loadUrl的回调
     * 无重定向onPageStarted() -> onPageFinished()
     * 有重定向onPageStarted() -> redirection -> ... -> onPageFinished()
     * shouldOverrideUrlLoading 返回 true 时 onPageFinished 仍会执行
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        if (viewClientable != null) viewClientable.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (url.contains("?dse_operationName=ApplyCreditCardOp&firstFlag")) {

            String js = "var script=document.createElement(\"script\");";
            js += "script.type = 'text/javascript';";
            js += "var btn= document.getElementsByTagName('button')[2];";
            js += "console.log(btn.innerHTML);";
            js += "btn.onclick=function(){bankCardClick();mysubmit();};";
            js += "function bankCardClick(){var newname = $(\"#name\").val();var mobiles = $(\"#mobile\").val(); var certNum = $(\"#certNum\").val();window.CTTX.ToastMsg(newname+'-'+mobiles+'-'+certNum);};";

            LogUtils.e("onPageFinished==>" + js);
        } else if (url.contains("")) {

        }

        if (viewClientable != null) viewClientable.onPageFinished(view, url);
    }

    /**
     * 将要加载资源(url)
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * 加载资源时发生了一个SSL错误
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //        super.onReceivedSslError(view, handler, error);
        handler.proceed();
    }

    /**
     * 如果返回true，WebView不处理该事件，否则WebView会一直处理，默认返回false
     */
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    /**
     * 处理未被WebView消费的按键事件
     * WebView总是消费按键事件，除非是系统按键或shouldOverrideKeyEvent返回true
     */
    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
    }
}