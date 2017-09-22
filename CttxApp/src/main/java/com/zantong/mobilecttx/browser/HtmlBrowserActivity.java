package com.zantong.mobilecttx.browser;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.contract.InterfaceForJS;
import com.zantong.mobilecttx.contract.browser.IHtmlBrowserContract;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrBean;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.eventbus.DriveLicensePhotoEvent;
import com.zantong.mobilecttx.eventbus.PayMotoOrderEvent;
import com.zantong.mobilecttx.presenter.browser.HtmlBrowserPresenter;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.primission.PermissionFail;
import com.tzly.annual.base.util.primission.PermissionGen;
import com.tzly.annual.base.util.primission.PermissionSuccess;

import static com.tzly.annual.base.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 公用浏览器 html页面显示
 */
public class HtmlBrowserActivity extends BaseJxActivity implements IHtmlBrowserContract.IHtmlBrowserView {

    protected String mStrTitle;
    protected String mStrUrl;

    @Bind(R.id.webView)
    ProgressWebView mWebView;
    private IHtmlBrowserContract.IHtmlBrowserPresenter mPresenter;

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

        HtmlBrowserPresenter presenter = new HtmlBrowserPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    protected void initViewStatus() {
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
        // 返回前一个页面
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

    private class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
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


}
