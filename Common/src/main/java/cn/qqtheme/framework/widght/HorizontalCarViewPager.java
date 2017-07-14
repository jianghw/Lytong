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

    int lastX = -1;
    int lastY = -1;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
