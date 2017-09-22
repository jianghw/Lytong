package com.zantong.mobile.widght;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 自定义viewpager限定滑动规则
 * 作者：王海洋
 * 时间：2016/7/26 17:06
 */
public class MainViewPager extends ViewPager{

    private boolean scrollble = true;

    public MainViewPager(Context context) {
        super(context);
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (!scrollble) {
//            return true;
//        }
//        return super.onTouchEvent(ev);
//    }


}
