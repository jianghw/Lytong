package com.zantong.mobilecttx.browser;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.EncryptUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.contract.InterfaceForJS;
import com.zantong.mobilecttx.contract.browser.IHtmlBrowserContract;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrBean;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.eventbus.DriveLicensePhotoEvent;
import com.zantong.mobilecttx.eventbus.PayMotoOrderEvent;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.huodong.activity.HundredAgreementActivity;
import com.zantong.mobilecttx.huodong.activity.HundredRuleActivity;
import com.zantong.mobilecttx.presenter.browser.HtmlBrowserPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.tzly.ctcyh.router.util.primission.PermissionGen.PER_REQUEST_CODE;


/**
 * 公用浏览器 html页面显示
 */
public class BrowserHtmlActivity extends JxBaseActivity
        implements IHtmlBrowserContract.IHtmlBrowserView {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    protected String mStrTitle;
    protected String mStrUrl;
    private IHtmlBrowserContract.IHtmlBrowserPresenter mPresenter;

    //浏览器右上角菜单的状态 0：活动说明  1：活动规则
    private int mRightBtnStatus = -1;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.putExtra.browser_title_extra))
                mStrTitle = bundle.getString(MainGlobal.putExtra.browser_title_extra);
            if (intent.hasExtra(MainGlobal.putExtra.browser_url_extra))
                mStrUrl = bundle.getString(MainGlobal.putExtra.browser_url_extra);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_browser;
    }

    @Override
    protected void bindContentView(View childView) {
        EventBus.getDefault().register(this);

        mProgressBar = (ProgressBar) childView.findViewById(R.id.pb_html5);
        mWebView = (WebView) childView.findViewById(R.id.wv_html5);

        titleContent(mStrTitle);
        titleClose();
        titleMore("分享");
    }

    @Override
    protected void initContentData() {
        initViewStatus();

        HtmlBrowserPresenter presenter = new HtmlBrowserPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }


    @SuppressLint("SetJavaScriptEnabled")
    protected void initViewStatus() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置支持Javascript
        settings.setDefaultTextEncodingName("utf-8");
        //自己添加
        settings.setSupportZoom(true);//支持缩放，默认为true
        //mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        settings.setLoadsImagesAutomatically(true);//支持自动加载图片
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        mWebView.addJavascriptInterface(new InterfaceForJS(this), "CTTX");

        saveData(settings);
        newWin(settings);

        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(webChromeClient);

        if (mStrUrl.contains("m.wedrive.com.cn") || mStrUrl.contains("tester.wedrive.com.cn")) {
            String cust_id = MainRouter.getUserPhoenum();
            String SEC_KEY = "BE7D6564766740037581842CE0ACA1DD";
            String token = EncryptUtils.encryptMD5ToString(SEC_KEY + cust_id + SEC_KEY);
            mStrUrl = mStrUrl + "?cust_id=" + cust_id + "&token=" + token;
        }
        mWebView.loadUrl(mStrUrl);

        //支持获取手势焦点，输入用户名、密码或其他
        mWebView.requestFocusFromTouch();
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.setDomStorageEnabled(true); //开启 DOM storage API 功能
        mWebSettings.setDatabaseEnabled(true); //开启database storage API 功能
        mWebSettings.setAppCacheEnabled(true);//开启Application Caches 功能
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false); //多窗口
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
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
    }

    @Override
    public void getBankPayHtmlSucceed(PayOrderResponse result, String orderId) {

        MainRouter.gotoHtmlActivity(this, "银行支付", result.getData(), orderId, 1);
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

    WebViewClient webViewClient = new WebViewClient() {
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
                titleContent("积分明细");
                titleMore("积分规则");
            } else if (url.contains("h5/build/index")) {
                mRightBtnStatus = 0;
                titleContent("百日无违章");
                titleMore("活动说明");
            } else {
                mRightBtnStatus = -1;
                titleContent(mStrTitle);
                titleMore("分享");
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
    };

    WebChromeClient webChromeClient = new WebChromeClient() {
        /**
         * 获得网页的加载进度
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        /**
         * 获取Web页中的title用来设置自己界面中的title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================

        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
        //=========多窗口的问题==========================================================
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        if (mPresenter != null) mPresenter.unSubscribe();
        setResult(MainGlobal.resultCode.web_browser_back);

        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearFormData();
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
        //        System.exit(0);
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
        String coupon = event.getCoupon();
        mPresenter.getBankPayHtml(coupon, orderId, String.valueOf(price));
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
        MainRouter.gotoOcrCameraActivity(this);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("您已关闭摄像头权限,请设置中打开");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照回调
        if (requestCode == MainGlobal.requestCode.violation_query_camera
                && resultCode == MainGlobal.resultCode.ocr_camera_license) {
            if (OcrCameraActivity.file == null)
                ToastUtils.toastShort("照片获取失败");
            else if (mPresenter != null)
                mPresenter.uploadDrivingImg();
        }
    }

    /**
     * 微信分享
     *
     * @param flag (0 分享到微信好友 1 分享到微信朋友圈)
     */
    private void wechatShare(int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(Utils.getContext(), WXEntryActivity.APP_ID, true);
        api.registerApp(WXEntryActivity.APP_ID);

        if (!api.isWXAppInstalled()) {
            ToastUtils.toastShort("您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if (MainRouter.isUserLogin()) {
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
