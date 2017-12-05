package com.zantong.mobilecttx.home.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.tzly.ctcyh.router.global.JxGlobal;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.SystemBarTintManager;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

/**
 * 保险模块浏览器
 * Created by zhoujie on 2016/10/13.
 */

public class CustomCordovaActivity extends CordovaActivity {
    RelativeLayout mLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_open_card_web_activity);

        setStatusBarColor();
        setStatusBarSpace();

        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra(JxGlobal.putExtra.common_extra);
            loadUrl(url);
        }
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

}
