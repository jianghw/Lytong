package com.tzly.ctcyh.router.custom.scroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.util.LogUtils;

/**
 * 上拉处广告
 */

public class ScrollBottomLayout extends LinearLayout implements GestureDetector.OnGestureListener {
    /**
     * 底部可见高度
     */
    private float visibilityHeight;
    private Scroller mScroller;
    private View childView;
    /**
     * 底部控件到头部的距离
     */
    private int movedMaxDis;
    private GestureDetector mGestureDetector;
    /**
     * It means the {@link #childView} is arriving the top of parent or else.
     */
    private boolean arriveTop = false;

    private ScrollBottomListener slideListener;
    /**
     * 嵌套子布局
     */
    private RecyclerView recyclerView;

    public ScrollBottomLayout(Context context) {
        this(context, null);
    }

    public ScrollBottomLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBottomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initScroll(context);
        initGesture(context);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollBottomLayout);
        visibilityHeight = typedArray.getDimension(R.styleable.ScrollBottomLayout_handlerHeight, 0);
        typedArray.recycle();
    }

    private void initScroll(Context context) {
        if (mScroller == null) {
            mScroller = new Scroller(context);
        }
    }

    private void initGesture(Context context) {
        mGestureDetector = new GestureDetector(context, this);
        mGestureDetector.setIsLongpressEnabled(true);
    }

    /**
     * onDraw --> computeScroll
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        initScroll(getContext());
        //判断滑动过程是否完成
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void customChildView(View childView) {
        if (getChildCount() > 0 && getChildAt(0) != null) {
            this.childView = getChildAt(0);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() > 0 && getChildAt(0) != null) {
            childView = getChildAt(0);
        } else if (getChildCount() == 0) {
            //
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        visibilityHeight = childView.getMeasuredHeight() / 4;
        movedMaxDis = childView.getMeasuredHeight() - (int) visibilityHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //绘制子控件的位置
        childView.layout(0, movedMaxDis, childView.getMeasuredWidth(),
                childView.getMeasuredHeight() + movedMaxDis);
    }

    private int downDY;
    /**
     * 移动距离
     */
    private int movedDis;

    /**
     * 事件拦截
     * <p>
     * RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
     * RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean state = this.recyclerView.canScrollVertically(-1);
        switch (event.getAction()) {
            //直接自己消费
            case MotionEvent.ACTION_DOWN:
                downDY = (int) event.getY();
                //未出现状态时都自己处理
                LogUtils.e("ACTION_DOWN-->" + state);
                if (!arriveTop) return true;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE-->" + state);
                int moveDY = (int) event.getY();
                int dy = downDY - moveDY;
                LogUtils.e("--dy-->" + dy);
                boolean ischild = !(arriveTop && Math.abs(dy) < 1 || arriveTop && dy > 0 || arriveTop && state);
                LogUtils.e("--ischild-->" + ischild);
                return ischild;
            case MotionEvent.ACTION_UP:
                LogUtils.e(arriveTop + "--ACTION_UP-->" + state);
                if (!arriveTop) return true;
        }
        LogUtils.e("state-->" + state);
        return super.onInterceptTouchEvent(event);
    }

    /**
     * getLeft-getTop-getRight -->View 原始的 上边
     * getX-getY -->View 左上角的坐标
     * getTranslationX-getTranslationY -->View 左上角的偏移量
     * getRawX-getRawY -->点击的手相对于屏幕边距
     * getLeft() + getTranslationX() = getX()
     * <p>
     * 在哪个View的onTouchEvent 返回true-->ACTION_MOVE和ACTION_UP的事件从上往下传到这个View后就不再往下传递
     * <p>
     * 你在执行ACTION_DOWN的时候返回了false，后面一系列其它的action就不会再得到执行了
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean state = this.recyclerView.canScrollVertically(-1);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("movedMaxDis-->" + movedMaxDis);
                LogUtils.e("downDY-->" + downDY);
                LogUtils.e("arriveTop-->" + arriveTop);

                if (movedMaxDis <= downDY && !arriveTop) return true;
                else if (movedMaxDis > downDY && arriveTop) return true;
            case MotionEvent.ACTION_MOVE:
                int moveDY = (int) event.getY();
                int dy = downDY - moveDY;
                movedDis += dy;//移动距离
                if (dy > 0) {//up
                    if (movedDis >= movedMaxDis) movedDis = movedMaxDis;
                    if (movedDis < movedMaxDis) {
                        scrollBy(0, dy);
                    }
                } else {//down
                    if (movedDis <= 0) movedDis = 0;
                    if (movedDis > 0) {
                        scrollBy(0, dy);
                    }
                }
                downDY = moveDY;
                LogUtils.e("_MOVE-->" + movedDis);
                return true;
            case MotionEvent.ACTION_UP:
                LogUtils.e(arriveTop+"_UP-->" + state);

                if (!state) {
                    if (movedDis >= movedMaxDis * 0.25f) {
                        switchVisible();
                    } else {
                        hideIt();
                    }
                }
                return true;
        }
        //true 消费
        //        boolean consume = mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private boolean switchVisible() {
        if (arriveTop) hideIt();
        else showIt();
        return arriveTop;
    }

    /**
     * startScroll 方法用来记录了起始位置、终止位置、位移、以及移动的开始时间和移动的时长 duration
     * <p>
     * getScrolly-- The top edge of the displayed part of your view, in pixels.
     * <p>
     * Return the scrolled top position of this view. This is the top edge of
     * the displayed part of your view. You do not need to draw any pixels above
     * it, since those are outside of the frame of your view on screen.
     */
    private void showIt() {
        mScroller.startScroll(0, getScrollY(), 0, (movedMaxDis - getScrollY()), 1000);
        invalidate();
        movedDis = movedMaxDis;
        arriveTop = true;
    }

    private void hideIt() {
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 1000);
        postInvalidate();
        movedDis = 0;
        arriveTop = false;
    }

    public void setShortSlideListener(ScrollBottomListener listener) {
        this.slideListener = listener;
    }

    /**
     * 按下
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        downDY = (int) motionEvent.getY();
        return (movedMaxDis) <= downDY || arriveTop;
    }

    /**
     * 按住不松手
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    /**
     * 单击
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * 滑动
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * 快速滑动
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    public void setRecycler(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
