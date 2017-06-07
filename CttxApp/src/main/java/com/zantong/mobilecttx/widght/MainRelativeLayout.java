package com.zantong.mobilecttx.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import org.apache.cordova.engine.SystemWebView;

/**
 * 作者：王海洋
 * 时间：2016/7/28 11:57
 */
public class MainRelativeLayout extends RelativeLayout {
    private SystemWebView mDispatchWebView;

    public MainRelativeLayout(Context context) {
        super(context);
    }

    public MainRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MainRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void preventParentTouchEvent (SystemWebView view) {
        mDispatchWebView = (SystemWebView)view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE && mDispatchWebView != null) {
            mDispatchWebView.ignoreTouchCancel(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mDispatchWebView != null){
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    mDispatchWebView.onTouchEvent(ev);
                    break;
                default:
                    mDispatchWebView.ignoreTouchCancel(false);
                    mDispatchWebView.onTouchEvent(ev);
                    mDispatchWebView = null;
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
