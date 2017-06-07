/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/

package org.apache.cordova.engine;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;

/**
 * Custom WebView subclass that enables us to capture events needed for Cordova.
 */
public class SystemWebView extends WebView implements CordovaWebViewEngine.EngineView {
    private SystemWebViewClient viewClient;
    SystemWebChromeClient chromeClient;
    private SystemWebViewEngine parentEngine;
    private CordovaInterface cordova;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private boolean isFlpe = false;
    private ViewpagerViewListener mViewpagerViewListener;

    public SystemWebView(Context context) {
        this(context, null);
    }

    public SystemWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Package visibility to enforce that only SystemWebViewEngine should call this method.
    void init(SystemWebViewEngine parentEngine, CordovaInterface cordova) {
        this.cordova = cordova;
        this.parentEngine = parentEngine;
        if (this.viewClient == null) {
            setWebViewClient(new SystemWebViewClient(parentEngine));
        }

        if (this.chromeClient == null) {
            setWebChromeClient(new SystemWebChromeClient(parentEngine));
        }
    }

    @Override
    public CordovaWebView getCordovaWebView() {
        return parentEngine != null ? parentEngine.getCordovaWebView() : null;
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        viewClient = (SystemWebViewClient)client;
        super.setWebViewClient(client);
    }
    public void setViewpagerViewListener(ViewpagerViewListener mViewpagerViewListener){
        this.mViewpagerViewListener = mViewpagerViewListener;
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        chromeClient = (SystemWebChromeClient)client;
        super.setWebChromeClient(client);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Boolean ret = parentEngine.client.onDispatchKeyEvent(event);
        if (ret != null) {
            return ret.booleanValue();
        }
        return super.dispatchKeyEvent(event);
    }
    boolean mIgnoreTouchCancel;

    public void ignoreTouchCancel (boolean val) {
        mIgnoreTouchCancel = val;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return ev.getAction() == MotionEvent.ACTION_CANCEL && mIgnoreTouchCancel || super.onTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if(x2-x1 > 28 && Math.abs(y2 - y1) < (x2 - x1)){
                    if(mViewpagerViewListener != null){
                        mViewpagerViewListener.onViewpagerViewListener(event);
                    }
                    return false;
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    public interface ViewpagerViewListener{
        public void onViewpagerViewListener(MotionEvent event);
    }

}
