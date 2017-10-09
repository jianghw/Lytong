package com.zantong.mobile.browser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.contract.InterfaceForJS;
import com.zantong.mobile.contract.browser.IPayHtmlContract;
import com.zantong.mobile.presenter.browser.PayHtmlPresenter;

import butterknife.Bind;

/**
 * 加油支付浏览器
 */
public class PayHtmlActivity extends BaseJxActivity implements IPayHtmlContract.IPayHtmlView {

    @Bind(R.id.webView)
    ProgressWebView mWebView;

    protected String mStrTitle;
    protected String mStrUrl;
    private String mViolationNum;

    private IPayHtmlContract.IPayHtmlPresenter mPresenter;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mStrTitle = intent.getStringExtra(JxGlobal.putExtra.browser_title_extra);
            mStrUrl = intent.getStringExtra(JxGlobal.putExtra.browser_url_extra);
            if (intent.hasExtra(JxGlobal.putExtra.violation_num_extra)) {
                mViolationNum = intent.getStringExtra(JxGlobal.putExtra.violation_num_extra);
            }
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

        PayHtmlPresenter presenter = new PayHtmlPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initViewStatus() {
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
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");

        mWebView.setWebViewClient(new MyWebViewClient());
        //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        mWebView.myRequestFocus();
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
        if (mWebView != null) mWebView.destroyWebView();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    @Override
    public void setPresenter(IPayHtmlContract.IPayHtmlPresenter presenter) {
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

    @Override
    public String getViolationNum() {
        return mViolationNum;
    }

    @Override
    public void numberedQueryError(String msg) {
        ToastUtils.toastShort("获取违章数据失败" + msg);
        finish();
    }

    @Override
    public void updateStateError(String msg) {
        ToastUtils.toastShort("更新同步数据失败" + msg);
        finish();
    }

    @Override
    public void updateStateSucceed(BaseResult result) {
        finish();
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
        return false;
    }

    private void updateState() {
        if (mPresenter != null) mPresenter.numberedQuery();
    }

}
