package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zantong.mobilecttx.utils.LogUtils;

/**
 * Created by zhengyingbing on 17/4/25.
 */

public class InsideViewPager extends ViewPager {

    int lastX = -1;
    int lastY = -1;

    public InsideViewPager(Context context) {
        super(context);
    }

    public InsideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int)ev.getRawX();
        int y = (int)ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                LogUtils.i("dealtX:=" + dealtX);
                LogUtils.i("dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


}
