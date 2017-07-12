package cn.qqtheme.framework.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

/**
 * Created by jianghw on 2017/7/12.
 * Description:
 * Update by:
 * Update day:
 */

public class HorizontalCarViewPager extends HorizontalInfiniteCycleViewPager {

    public HorizontalCarViewPager(Context context) {
        super(context);
    }

    public HorizontalCarViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int mLastX;
    int mLaseY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int startX = (int) ev.getX();
        int startY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = startX - mLastX;
                int deltaY = startY - mLaseY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastX = startX;
                mLaseY = startY;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
