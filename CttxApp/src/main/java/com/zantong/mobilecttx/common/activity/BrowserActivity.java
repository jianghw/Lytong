package com.zantong.mobilecttx.common.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.contract.InterfaceForJS;
import com.zantong.mobilecttx.huodong.activity.HundredAgreementActivity;
import com.zantong.mobilecttx.huodong.activity.HundredRuleActivity;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.widght.ProgressWebView;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;

import butterknife.Bind;
import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 公用浏览器 第三方广告
 */
public class BrowserActivity extends BaseJxActivity {

    protected String strTitle;
    protected String strUrl;

    @Bind(R.id.webView)
    ProgressWebView mWebView;

    //浏览器右上角菜单的状态 0：活动说明  1：活动规则
    private int mRightBtnStatus = -1;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        strTitle = PublicData.getInstance().webviewTitle;
        strUrl = PublicData.getInstance().webviewUrl;
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
        setTvRightVisible("分享");
    }

    protected void initViewStatus() {
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
        mWebView.loadUrl(strUrl);

        GlobalConfig.getInstance().customUrlUMeng(strUrl);
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
        switch (mRightBtnStatus) {
            case 0:
                Act.getInstance().gotoIntent(this, HundredAgreementActivity.class);
                break;
            case 1:
                Act.getInstance().gotoIntent(this, HundredRuleActivity.class);
                break;
            case -1:
                new DialogMgr(this,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wechatShare(0);
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wechatShare(1);
                            }
                        });
                break;
            default:
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
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
                initTitleContent("积分明细");
                setTvRightVisible("积分规则");
            } else if (url.contains("index")) {
                mRightBtnStatus = 0;
                initTitleContent("百日无违章");
                setTvRightVisible("活动说明");
            } else {
                mRightBtnStatus = -1;
                initTitleContent(strTitle);
                setTvRightVisible("分享");
            }
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mWebView != null) mWebView.destroyWebView();
    }

    /**
     * 微信分享
     *
     * @param flag (0 分享到微信好友 1 分享到微信朋友圈)
     */
    private void wechatShare(int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(ContextUtils.getContext(), WXEntryActivity.APP_ID, true);
        api.registerApp(WXEntryActivity.APP_ID);

        if (!api.isWXAppInstalled()) {
            ToastUtils.toastShort("您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if (PublicData.getInstance().loginFlag) {
            webpage.webpageUrl = strUrl;
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = strTitle;
        msg.description = "优惠活动推荐";
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sharelogo);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
