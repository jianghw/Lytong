package com.zantong.mobilecttx.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;


import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.utils.StateBarSetting;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.concurrent.ExecutorService;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/1.
 */
public class Demo extends AppCompatActivity implements CordovaInterface {

    @Bind(R.id.cordovaWebView)
    SystemWebView cordovaWebView;
    public static String START_URL = "file:///android_asset/www/discount_index.html";
    private CordovaWebView cordovaWebViewReal;
    protected CordovaInterfaceImpl cordovaInterface;
    private SystemWebViewEngine mSystemWebViewEngine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
//        ButterKnife.bind(this);
        cordovaWebView = (SystemWebView) findViewById(R.id.cordovaWebView);
        StateBarSetting.settingBar(this);

//
        START_URL = (String) LoginData.getInstance().mHashMap.get("htmlUrl");
        cordovaInterface = new CordovaInterfaceImpl(Demo.this);
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(this);//这里会解析res/xml/config.xml配置文件
        mSystemWebViewEngine = new SystemWebViewEngine(cordovaWebView);
        cordovaWebViewReal = new CordovaWebViewImpl(mSystemWebViewEngine);//创建一个cordovawebview
        cordovaWebViewReal.init(cordovaInterface, parser.getPluginEntries(), parser.getPreferences());//初始化
//        cordovaWebViewSys.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        cordovaWebView.getSettings().setJavaScriptEnabled(true);
        cordovaWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        cordovaWebView.loadUrl(START_URL);

        //Set up the webview
//        ConfigXmlParser parser = new ConfigXmlParser();
//        parser.parse(this);
////        cordovaWebView.setScrollbarFadingEnabled(false);
////        cordovaWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
//        cordovaWebViewReal = new CordovaWebViewImpl(new SystemWebViewEngine(cordovaWebView));
////        cordovaWebViewReal.init(new MyCordovaImpl(this,webView), parser.getPluginEntries(),
//        cordovaWebViewReal.init(cordovaInterface, parser.getPluginEntries(), parser.getPreferences());
//        cordovaWebViewReal.loadUrl(START_URL);
//        mUserPresenterImp = new UserPresenterImp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cordovaWebView != null){
            cordovaWebViewReal.handleDestroy();
        }
    }

    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {

    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Object onMessage(String id, Object data) {
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        return null;
    }

    @Override
    public void requestPermission(CordovaPlugin plugin, int requestCode, String permission) {

    }

    @Override
    public void requestPermissions(CordovaPlugin plugin, int requestCode, String[] permissions) {

    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }
}
