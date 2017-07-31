package com.zantong.mobilecttx.common.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.huodong.activity.HundredAgreementActivity;
import com.zantong.mobilecttx.huodong.activity.HundredRuleActivity;
import com.zantong.mobilecttx.contract.InterfaceForJS;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.utils.UiHelpers;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.widght.ProgressWebView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 公用浏览器
 * Created by zhengyingbing on 16/9/8.
 */
public class BrowserActivity extends BaseActivity {

    public boolean mStatusBarHeight = true;
    protected String strTitle;
    protected String strUrl;

    @Bind(R.id.common_browser_webview)
    ProgressWebView mWebView;
    @Bind(R.id.common_browser_title)
    TextView mTitle;
    @Bind(R.id.common_browser_back)
    TextView mBack;
    @Bind(R.id.common_finish)
    TextView mFinish;
    @Bind(R.id.common_browser_right)
    TextView mRightBtn;

    //浏览器右上角菜单的状态 0：活动说明  1：活动规则
    private int mRightBtnStatus = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarSpace();

        mFinish.setVisibility(View.GONE);
    }

    protected int getLayoutResId() {
        return R.layout.activity_browser;
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

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
    }

    private void setStatusBarSpace() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusBar = findViewById(R.id.activity_browser_title);
            if (mStatusBarHeight) {
                statusBar.setPadding(0, tintManager.getConfig().getStatusBarHeight(), 0, 0);
            }
        }
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(strUrl)) {
            mWebView.loadUrl(strUrl);
        }
    }

    @OnClick({R.id.common_browser_back, R.id.common_finish, R.id.common_browser_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_browser_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();// 返回前一个页面
                    mFinish.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }
                break;
            case R.id.common_finish:
                finish();
                break;
            case R.id.common_browser_right:
                switch (mRightBtnStatus) {
                    case 0:
                        Act.getInstance().gotoIntent(this, HundredAgreementActivity.class);
                        break;
                    case 1:
                        Act.getInstance().gotoIntent(this, HundredRuleActivity.class);
                        break;
                }
                break;
            default:
                break;
        }
    }

    class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("tel:")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } else if (url.startsWith("alipays:")) {//阿里支付
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.toastShort("请确认手机安装支付宝app");
                }
            } else {
                if (PublicData.getInstance().isCheckLogin && !PublicData.getInstance().loginFlag) {
                    startActivity(new Intent(BrowserActivity.this, LoginActivity.class));
                } else {
                    view.loadUrl(url);
                }
            }
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if (url.contains("detail")) {
                mRightBtnStatus = 1;
                mRightBtn.setVisibility(View.VISIBLE);
                mRightBtn.setText("积分规则");
                mTitle.setText("积分明细");
            } else if (url.contains("index")) {
                mRightBtnStatus = 0;
                mRightBtn.setVisibility(View.VISIBLE);
                mRightBtn.setText("活动说明");
                mTitle.setText("百日无违章");
            } else {
                mRightBtn.setVisibility(View.GONE);
                mTitle.setText(strTitle);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroyWebview();
    }
}
