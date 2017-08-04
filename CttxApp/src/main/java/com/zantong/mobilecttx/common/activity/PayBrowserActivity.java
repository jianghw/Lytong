package com.zantong.mobilecttx.common.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.browser.IPayBrowserFtyContract;
import com.zantong.mobilecttx.contract.InterfaceForJS;
import com.zantong.mobilecttx.order.bean.OrderDetailBean;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;
import com.zantong.mobilecttx.presenter.browser.PayBrowserPresenter;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.log.LogUtils;

/**
 * 支付浏览器
 */
public class PayBrowserActivity extends BaseJxActivity
        implements IPayBrowserFtyContract.IPayBrowserFtyView {

    WebView mWebView;
    private IPayBrowserFtyContract.IPayBrowserFtyPresenter mPresenter;

    private String mTitleWeb;
    private String mUrl;
    private String mOrderId;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_fahrschule_pay_browser;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initFragmentView(View view) {
        PayBrowserPresenter presenter = new PayBrowserPresenter(
                Injection.provideRepository(getApplicationContext()), this);

        Intent intent = getIntent();
        if (intent != null) {
            mTitleWeb = intent.getStringExtra(GlobalConstant.putExtra.web_title_extra);
            mUrl = intent.getStringExtra(GlobalConstant.putExtra.web_url_extra);
            mOrderId = intent.getStringExtra(GlobalConstant.putExtra.web_order_id_extra);
        }

        initTitleContent(mTitleWeb);
        mWebView = (WebView) view.findViewById(R.id.webView);

        String url = "<%@ page language=\"java\" contentType=\"text/html; charset=GBK\" pageEncoding=\"GBK\"%>" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\" http://www.w3.org/TR/html4/loose.dtd\">" +
                "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">" +
                "<title>表单提交</title></head><body>" + mUrl + "</body></html>";

        LogUtils.xml(url);

        WebSettings settings = mWebView.getSettings();
        //设置支持Javascript
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebViewChromeClient());
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");

        //触摸焦点起作用.
        //如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        mWebView.requestFocus();
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(IPayBrowserFtyContract.IPayBrowserFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    private class MyWebViewClient extends WebViewClient {

        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            mWebView.loadUrl(url);
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }
    }

    private class MyWebViewChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();//返回webView的上一页面
            return false;
        }
        orderDetail();
        return false;
    }

    protected void orderDetail() {
        if (mPresenter != null) mPresenter.orderDetail();
    }

    @Override
    protected void backClickListener() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
        } else {
            orderDetail();
        }
    }

    /**
     * 只显示失败
     */
    @Override
    public void getOrderDetailError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void getOrderDetailSucceed(OrderDetailResult result) {
        OrderDetailBean resultData = result.getData();
        if (resultData != null) {
            int orderStatus = resultData.getOrderStatus();
            //成功页面
            if (orderStatus == 1) succeedStatus();
        }
    }

    protected void succeedStatus() {
        Intent intent = new Intent();
        intent.putExtra(GlobalConstant.putExtra.web_order_id_extra, mOrderId);
        setResult(GlobalConstant.resultCode.web_order_id_succeed, intent);
        finish();
    }

    protected void errorStatus() {
        Intent intent = new Intent();
        intent.putExtra(GlobalConstant.putExtra.web_order_id_extra, mOrderId);
        setResult(GlobalConstant.resultCode.web_order_id_error, intent);
        finish();
    }

    @Override
    public void intervalOrderDetailError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
        //失败页面
        errorStatus();
    }

    @Override
    public void orderDetailCompleted() {
        //失败页面
        errorStatus();
    }

    @Override
    public String getOrderId() {
        return mOrderId;
    }

}
