package com.tzly.ctcyh.router.custom.scroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.tzly.ctcyh.router.R;

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

    private float hideWeight = 0.25f;
    private ScrollBottomListener slideListener;

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

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        childView = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        movedMaxDis = (int) (childView.getMeasuredHeight() - visibilityHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        childView.layout(0, movedMaxDis, childView.getMeasuredWidth(), childView.getMeasuredHeight() + movedMaxDis);
    }

    private int downDY;
    /**
     * 移动距离
     */
    private int movedDis;

    /**
     * getLeft-getTop-getRight -->View 原始的 上边
     * getX-getY -->View 左上角的坐标
     * getTranslationX-getTranslationY -->View 左上角的偏移量
     * getRawX-getRawY -->点击的手相对于屏幕边距
     * <p>
     * getLeft() + getTranslationX() = getX()
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downDY = (int) event.getY();
                if (movedMaxDis > downDY && !arriveTop) return true;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveDY = (int) event.getY();
                int dy = downDY - moveDY;
                movedDis += dy;//移动距离

                if (dy > 0) {//up
                    if (movedDis >= movedMaxDis) movedDis = movedMaxDis;
                    if (movedDis < movedMaxDis) {
                        scrollBy(0, dy);
                        downDY = moveDY;
                        return true;
                    }
                } else {//down
                    if (movedDis <= 0) movedDis = 0;
                    if (movedDis > 0) {
                        scrollBy(0, dy);
                    }
                    downDY = moveDY;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //当移动距离超过最大能移动的1/4时
                if (movedDis > movedMaxDis * hideWeight) {
                    switchVisible();
                } else {
                    if (this.slideListener != null) slideListener.listener(downDY);
                    else hideIt();
                }
                return true;
            default:
                break;
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
     *
     * Return the scrolled top position of this view. This is the top edge of
     * the displayed part of your view. You do not need to draw any pixels above
     * it, since those are outside of the frame of your view on screen.
     */
    private void showIt() {
        mScroller.startScroll(0, getScrollY(), 0, (movedMaxDis - getScrollY()));
        invalidate();
        movedDis = movedMaxDis;
        arriveTop = true;
    }

    private void hideIt() {
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
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
}
