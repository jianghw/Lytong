package com.zantong.mobile.weizhang.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.UserApiClient;
import com.zantong.mobile.car.bean.PayCarResult;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.contract.ModelView;
import com.zantong.mobile.share.activity.ShareParentActivity;
import com.zantong.mobile.user.dto.LogoutDTO;
import com.zantong.mobile.utils.DialogMgr;
import com.zantong.mobile.utils.ScreenManager;
import com.zantong.mobile.utils.StateBarSetting;
import com.zantong.mobile.utils.TitleSetting;
import com.zantong.mobile.utils.rsa.Des3;
import com.zantong.mobile.wxapi.WXEntryActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tzly.annual.base.util.ToastUtils;

public class PayWebActivity extends AppCompatActivity implements ModelView{
    @Bind(R.id.webview_about)
    WebView webview_about;
    @Bind(R.id.linear_title)
    LinearLayout linear_title;
    @Bind(R.id.myProgressBar)
    ProgressBar myProgressBar;
    @Bind(R.id.text_right)
    TextView mRightText;

    private String payUrl;
    private int resultCode = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_web_view);

        ButterKnife.bind(this);

        StateBarSetting.settingBar(this);
        TitleSetting.getInstance().initTitle(this, "支付", R.mipmap.back_btn_image, "返回", null, null);

        payUrl = (String) PublicData.getInstance().mHashMap.get("PayWebActivity");
        mRightText.setText("分享");

        webview_about.getSettings().setJavaScriptEnabled(true);
        webview_about.getSettings().setUseWideViewPort(true);
        webview_about.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview_about.getSettings().setLoadWithOverviewMode(true);
        webview_about.loadUrl(payUrl);
        webview_about.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webview_about.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
            }
        });
        webview_about.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });
        webview_about.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @OnClick({R.id.linear_title,R.id.text_right})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_title:
                closeActivity();
                break;
            case R.id.text_right:
                clickShare();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getBangDingCar();//查询一次绑定车辆，目的是让工行查询一次交管局的绑定信息
    }

    private void closeActivity(){
        Intent mIntent = new Intent();
        mIntent.putExtra("PayStateFlag", "SUCCESS");
        // 设置结果，并进行传送
        this.setResult(resultCode, mIntent);
        ScreenManager.popActivity();
    }

    private void clickShare(){
        new DialogMgr(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(0);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(1);
            }
        });
    }

    /**
     * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    private void wechatShare(int flag){
        IWXAPI api = WXAPIFactory.createWXAPI(this, WXEntryActivity.APP_ID, false);
        api.registerApp(WXEntryActivity.APP_ID);

        if (!api.isWXAppInstalled()) {
            ToastUtils.showShort(this, "您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if(PublicData.getInstance().loginFlag){
            webpage.webpageUrl = ShareParentActivity.getOldShareAppUrl()+"?phoneNum="
                    + Des3.encode(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        }else{
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "掌上违章缴费，销分一步到位，与你只有一个App的距离";
        msg.description = "畅通车友会——有我在手，一路畅通畅通车友会由工银安盛与中国工商银行" +
                "上海分行联手打造，旨在为牡丹畅通卡用户提供便捷的驾乘金融服务体验。功能覆盖了" +
                "交通违章缴费、驾乘人员保险保障、特色增值服务等多项方便快捷的在线服务，" +
                "使车主的驾车生活更便捷、更丰富，更畅通!";
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sharelogo);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
    
    //网页回退
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webview_about.canGoBack()
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            webview_about.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void updateView(Object object, int index) {
    }

    @Override
    public void hideProgress() {
    }

    /**
     * 获取绑定车辆信息
     */
    private void getBangDingCar(){
        LogoutDTO dto = new LogoutDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        UserApiClient.getPayCars(this, dto, new CallBack<PayCarResult>() {
            @Override
            public void onSuccess(PayCarResult result) {
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }

}