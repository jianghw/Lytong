package com.zantong.mobilecttx.common.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.contract.InterfaceForJS;
import com.zantong.mobilecttx.utils.UiHelpers;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.log.LogUtils;

/**
 * 支付浏览器
 * Created by zhengyingbing on 16/9/8.
 */
public class BrowserForPayActivity extends BaseActivity {

    public boolean mStatusBarHeight = true;
    protected String strTitle;
    protected String strUrl;

    @Bind(R.id.activity_browser_pay_webview)
    WebView mWebView;
    @Bind(R.id.activity_browser_pay_title)
    TextView mTitle;
    @Bind(R.id.activity_browser_pay_back)
    TextView mBack;


    protected int getLayoutResId() {
        return R.layout.activity_browser_pay;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarSpace();
    }

    private void setStatusBarSpace() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusBar = findViewById(R.id.activity_browser_title);
            if (mStatusBarHeight) {
                statusBar.setPadding(0, tintManager.getConfig()
                        .getStatusBarHeight(), 0, 0);
            }
        }
    }

    @Override
    public void initView() {
        // 初始化返回按钮图片大小
        UiHelpers.setTextViewIcon(this, mBack, R.mipmap.back_btn_image,
                R.dimen.ds_24,
                R.dimen.ds_51, UiHelpers.DRAWABLE_LEFT);

        strTitle = PublicData.getInstance().webviewTitle;
        strUrl = PublicData.getInstance().webviewUrl;

        if (!TextUtils.isEmpty(strTitle)) {
            mTitle.setText(strTitle);
        }
        String url = "<%@ page language=\"java\" contentType=\"text/html; charset=GBK\" pageEncoding=\"GBK\"%>" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\" http://www.w3.org/TR/html4/loose.dtd\">" +
                "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">" +
                "<title>表单提交</title></head><body>" + strUrl + "</body></html>";
        LogUtils.i(url);

        mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        mWebView.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.activity_browser_pay_back})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_browser_pay_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();// 返回前一个页面
                } else {
                    finish();
                }
                break;
        }
    }

    class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            mWebView.loadUrl(url);
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();//返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
