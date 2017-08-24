package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.zantong.mobilecttx.widght.refresh.PullRefreshable;

public class PullRefreshableScrollView extends ScrollView implements PullRefreshable {

    public PullRefreshableScrollView(Context context) {
        this(context, null);
    }

    public PullRefreshableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }

    float mLastMotionY = 0;
    float mLastMotionX = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(x - mLastMotionX);
                float deltaY = Math.abs(y - mLastMotionY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

}
