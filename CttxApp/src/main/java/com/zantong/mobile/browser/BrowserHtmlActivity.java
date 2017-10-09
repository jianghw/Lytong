package com.zantong.mobile.browser;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.primission.PermissionFail;
import com.tzly.annual.base.util.primission.PermissionGen;
import com.tzly.annual.base.util.primission.PermissionSuccess;
import com.zantong.mobile.R;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.common.activity.OcrCameraActivity;
import com.zantong.mobile.contract.InterfaceForJS;
import com.zantong.mobile.contract.browser.IHtmlBrowserContract;
import com.zantong.mobile.daijia.bean.DrivingOcrBean;
import com.zantong.mobile.daijia.bean.DrivingOcrResult;
import com.zantong.mobile.eventbus.DriveLicensePhotoEvent;
import com.zantong.mobile.eventbus.PayMotoOrderEvent;
import com.zantong.mobile.presenter.browser.HtmlBrowserPresenter;
import com.zantong.mobile.utils.DialogMgr;
import com.zantong.mobile.utils.DialogUtils;
import com.zantong.mobile.weizhang.bean.PayOrderResult;
import com.zantong.mobile.wxapi.WXEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

import static com.tzly.annual.base.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 公用浏览器 html页面显示
 */
public class BrowserHtmlActivity extends BaseJxActivity implements IHtmlBrowserContract.IHtmlBrowserView {

    @Bind(R.id.webView)
    ProgressWebView mWebView;

    protected String mStrTitle;
    protected String mStrUrl;
    private IHtmlBrowserContract.IHtmlBrowserPresenter mPresenter;

    //浏览器右上角菜单的状态 0：活动说明  1：活动规则
    private int mRightBtnStatus = -1;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mStrTitle = intent.getStringExtra(JxGlobal.putExtra.browser_title_extra);
            mStrUrl = intent.getStringExtra(JxGlobal.putExtra.browser_url_extra);
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
        EventBus.getDefault().register(this);
        initTitleContent(mStrTitle);
        setTvCloseVisible();
        setTvRightVisible("分享");

        HtmlBrowserPresenter presenter = new HtmlBrowserPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initViewStatus() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");
        mWebView.loadUrl(mStrUrl);

        WebSettings settings = mWebView.getSettings();
        //设置支持Javascript
        settings.setDefaultTextEncodingName("utf-8");

        //触摸焦点起作用.
        //如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        mWebView.requestFocus();
    }

    /**
     * 回退建
     */
    protected void backClickListener() {
        if (mWebView.canGoBack()) mWebView.goBack();
        else finish();
    }

    @Override
    public void setPresenter(IHtmlBrowserContract.IHtmlBrowserPresenter presenter) {
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
    public void uploadDrivingImgError(String message) {
        errorToast(message);
    }

    /**
     * 55.行驶证扫描接口
     */
    @Override
    public void uploadDrivingImgSucceed(DrivingOcrResult result) {
        DrivingOcrBean bean = result.getContent();
        if (bean != null) {
            mWebView.loadUrl("javascript:callbackCamera(" + new Gson().toJson(bean) + ");");
        } else {
            ToastUtils.toastShort("行驶证图片解析失败(55)，请重试");
        }
    }

    /**
     * 支付回调
     */
    @Override
    public void getBankPayHtmlError(String message) {
        errorToast(message);
    }

    protected void errorToast(String message) {
        ToastUtils.toastShort(message);
        dismissLoadingDialog();
    }

    @Override
    public void getBankPayHtmlSucceed(PayOrderResult result, String orderId) {
        Intent intent = new Intent(this, PayBrowserActivity.class);
        intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付");
        intent.putExtra(JxGlobal.putExtra.web_url_extra, result.getData());
        intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
        startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
    }

    /**
     * 右侧点击
     */
    protected void rightClickListener() {
        switch (mRightBtnStatus) {
            case 0:
                DialogUtils.updateDialog(this,
                        "扩展功能", "此功能为中国工商银行产品(畅通车友会App)专属,请下载体验",
                        "不用 谢谢", "去应用市场下载",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareAppShop(ContextUtils.getContext().getPackageName());
                            }
                        });
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

    /**
     * 根据应用包名，跳转到应用市场
     *
     * @param packageName 所需下载（评论）的应用包名
     */
    public void shareAppShop(String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + "com.zantong.mobilecttx");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.toastShort("您没有安装应用市场,请点击立即更新");
        }
    }

    private class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent();
            if (url.startsWith("tel:")) {
                intent.setAction(Intent.ACTION_DIAL);
                Uri data = Uri.parse(url);
                intent.setData(data);
                startActivity(intent);
            } else if (url.startsWith("alipays:")) {//阿里支付
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.toastShort("请确认手机安装支付宝app");
                }
            } else {
                view.loadUrl(url);
            }
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
                initTitleContent(mStrTitle);
                setTvRightVisible("分享");
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        EventBus.getDefault().unregister(this);
        mWebView.destroyWebView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    /**
     * 当返回true时，表示已经完整地处理了这个事件，并不希望其他的回调方法再次进行处理，
     * 当返回false时，表示并没有完全处理完该事件，更希望其他回调方法继续对其进行处理，例如Activity中的回调方法。
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();//返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(DriveLicensePhotoEvent event) {
        takePhoto();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(PayMotoOrderEvent event) {
        String orderId = event.getOrderId();
        float orderPrice = Float.valueOf(event.getAmount());
        int price = (int) (orderPrice * 100);
        mPresenter.getBankPayHtml(orderId, String.valueOf(price));
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
        } else {
            goToCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 行驶证 拍照前权限调用
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        goToCamera();
    }

    protected void goToCamera() {
        Intent intentOcr = new Intent(this, OcrCameraActivity.class);
        intentOcr.putExtra(JxGlobal.putExtra.ocr_camera_extra, 0);
        startActivityForResult(intentOcr, JxGlobal.requestCode.violation_query_camera);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("您已关闭摄像头权限,请设置中打开");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照回调
        if (requestCode == JxGlobal.requestCode.violation_query_camera
                && resultCode == JxGlobal.resultCode.ocr_camera_license) {
            if (OcrCameraActivity.file == null)
                ToastUtils.toastShort("照片获取失败");
            else if (mPresenter != null)
                mPresenter.uploadDrivingImg();
        }
        //TODO
        if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
                && resultCode == JxGlobal.resultCode.web_order_id_succeed) {

        } else if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
                && resultCode == JxGlobal.resultCode.web_order_id_error && data != null) {
        }
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
        if (MemoryData.getInstance().loginFlag) {
            webpage.webpageUrl = mStrUrl;
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = mStrTitle;
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
