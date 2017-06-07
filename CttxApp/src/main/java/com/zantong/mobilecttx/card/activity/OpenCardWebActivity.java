package com.zantong.mobilecttx.card.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.SystemBarTintManager;
import com.zantong.mobilecttx.utils.ToastUtils;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;
import org.json.JSONObject;
public class OpenCardWebActivity extends CordovaActivity {
    RelativeLayout mLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_open_card_web_activity);
        setStatusBarColor();
        setStatusBarSpace();
        loadUrl("file:///android_asset/www/card_apply.html");
    }

    public boolean mStatusBarHeight = true;
    protected SystemBarTintManager tintManager;

    private void setStatusBarSpace() {
        mLayout = (RelativeLayout) findViewById(R.id.bind_open_card_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mStatusBarHeight) {
                mLayout.setPadding(0, tintManager.getConfig()
                        .getStatusBarHeight(), 0, 0);
            }
        }
//        if (!SPUtils.getInstance(this).getGuideBanKa()) {
//            PublicData.getInstance().GUIDE_TYPE = 3;
//            Act.getInstance().gotoIntent(this, GuideActivity.class);
//        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView) findViewById(R.id.bind_open_card_web_view);
        return new CordovaWebViewImpl(new SystemWebViewEngine(webView));
    }

    @Override
    protected void createViews() {
        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            appView.getView().setBackgroundColor(backgroundColor);
        }
        appView.getView().requestFocusFromTouch();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        ToastUtils.showShort(getApplicationContext(), "CTTXOCRScan获取信息1");
        if (requestCode == 1204 && resultCode == 1203) {
            ToastUtils.showShort(getApplicationContext(), "CTTXOCRScan获取信息2");
            Adata = intent.getByteArrayExtra("A_data");
            LogUtils.i("Adata:" + Adata.toString());
            new Thread(runnable).start();
        }
    }

    private byte[] Adata;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            OcrEngine ocrJZ = new OcrEngine();
//            jiazInfo = ocrJZ.getJZInfo(getApplicationContext(), Adata);
//            LogUtils.i("CTTXOCRScan-----------------------" + jiazInfo.getIDText());
//            mHandler.sendEmptyMessage(jiazInfo.getYMrecognState());
        }
    };

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (Build.VERSION.SDK_INT < 23) {
                if (msg.what == 1) {
                    LogUtils.i("开发发送数据给h5");
                    Gson gson = new Gson();
                    JSONObject object = null;
//                    try {
//                        object = new JSONObject(gson.toJson(jiazInfo));
//                        CTTXOCRScan.getInstance().sendOcrInfo(object);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                } else if (msg.what == 7) {
                    Toast.makeText(getApplication(), "引擎过期！", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 1000){
                    ToastUtils.showShort(OpenCardWebActivity.this,"当前版本暂不支持OCR扫描功能");
                } else {
                    Toast.makeText(getApplication(), "图像无法识别" + msg.what, Toast.LENGTH_SHORT).show();
                }
            } else {
                mHandler.sendEmptyMessage(1000);
            }
        }
    };

}
