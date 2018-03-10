package com.zantong.mobilecttx.widght.refresh;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.R;


/**
 * 自定义的布局，用来管理三个子控件，其中一个是下拉头，一个是包含内容的pullableView（可以是实现Pullable接口的的任何View），
 * 还有一个上拉头，更多详解见博客http://blog.csdn.net/zhongkejingwang/article/details/38868463
 *
 * @author 陈靖
 */
public class PullToRefreshLayout extends RelativeLayout {

    public static final String TAG = "PullToRefreshLayout";

    // 初始状态
    public static final int INIT = 0;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;
    // 释放加载
    public static final int RELEASE_TO_LOAD = 3;
    // 正在加载
    public static final int LOADING = 4;
    // 操作完毕
    public static final int DONE = 5;
    // 当前状态
    public int mViewState = INIT;

    // 刷新成功
    public static final int SUCCEED = 0;
    // 刷新失败
    public static final int FAIL = 1;

    // 回滚速度
    public float mMoveSpeed = 18;

    // 在刷新过程中滑动操作
    public boolean isTouch = false;

    // 下拉箭头的转180°动画
    private RotateAnimation reverseUpAnimation;
    private RotateAnimation reverseDownAnimation;
    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;

    // 实现了Pullable接口的View
    private View mPullRefreshable;

    // 刷新回调接口
    public OnPullListener mOnPullListener;
    // 下拉刷新过程监听器
    public OnPullProcessListener mOnRefreshProcessListener;
    // 上拉加载更多过程监听器
    public OnPullProcessListener mOnLoadmoreProcessListener;
    // 这两个变量用来控制pull的方向，如果不加控制，当情况满足可上拉又可下拉时没法下拉
    private boolean mCanPullDown = true;

    private boolean mCanPullUp = true;
    //子类布局用户可自定义
    private boolean mPullDownEnable;
    private boolean mPullUpEnable;

    // 释放刷新的距离 即.getMeasuredHeight()
    public float mRefreshDist = 200;
    // 释放加载的距离
    public float mUploadMoreDist
            = 200;
    // 下拉的距离。注意：pullDownY和pullUpY不可能同时不为0
    public float pullDownY = 0f;
    // 上拉的距离
    public float pullUpY = 0f;
    // 按下Y坐标，上一个事件点Y坐标
    private float mDownY, mLastY;
    // 过滤多点触碰
    private int mEvents;
    // 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
    private float mRadio = 2;

    // 执行自动回滚的handler
    private Handler mUpdateHandler;
    public MyTimer mMyTimer;

    // 第一次执行布局
    private boolean isFirstLayout;

    // 默认下拉头
    private View defaultRefreshView;
    // 默认上拉头
    private View defaultUploadMoreView;
    // 下拉头 用来测量及操作的代理view
    public View mRefreshView;
    // 上拉头
    private View mUploadMoreView;
    // 自定义下拉头
    public View customRefreshView;
    // 自定义上拉头
    public View customUploadMoreView;

    // 下拉的箭头
    public View pullDownArrows;
    // 正在刷新的图标
    private View refreshingView;
    // 刷新结果图标
    private View refreshStateImageView;
    // 刷新结果：成功或失败
    private TextView refreshStateTextView;
    // 上拉的箭头
    public View pullUpArrows;
    // 正在加载的图标
    private View loadingView;
    // 加载结果：成功或失败
    private TextView loadStateTextView;
    // 加载结果图标
    private View loadStateImageView;

    // 是否已经准备下拉
    private boolean mPreparedPullDown;
    // 是否已经准备上拉
    private boolean mPreparedPullUp;

    public PullToRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initLayoutView(context, attrs, defStyle);
    }

    private void initLayoutView(Context context, AttributeSet attrs, int defStyle) {

        mUpdateHandler = new UpdateHandler(this);
        mMyTimer = new MyTimer(mUpdateHandler);

        reverseUpAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.anim_rotate_up);
        reverseDownAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.anim_rotate_down);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.anim_rotating);

        // 添加匀速转动动画
        LinearInterpolator interpolator = new LinearInterpolator();
        reverseUpAnimation.setInterpolator(interpolator);
        refreshingAnimation.setInterpolator(interpolator);

        // 添加上拉头和下拉头
        LayoutInflater inflater = LayoutInflater.from(context);
        defaultRefreshView = inflater.inflate(R.layout.refresh_head, this, false);
        mRefreshView = defaultRefreshView;

        defaultUploadMoreView = inflater.inflate(R.layout.load_more, this, false);
        mUploadMoreView = defaultUploadMoreView;

        addView(defaultRefreshView);
        addView(defaultUploadMoreView);
    }

    /**
     * 设置是否可下拉刷新
     */
    public void setPullDownEnable(boolean pullDownEnable) {
        mPullDownEnable = pullDownEnable;
    }

    /**
     * 设置是否可上拉刷新
     */
    public void setPullUpEnable(boolean pullUpEnable) {
        mPullUpEnable = pullUpEnable;
    }

    /**
     * 设置自定义下拉头
     */
    public void setCustomRefreshView(View v) {
        customRefreshView = v;
        removeView(defaultRefreshView);
        addView(customRefreshView);
        mRefreshView = customRefreshView;
    }

    /**
     * 设置自定义上拉头
     */
    public void setCustomUploadMoreView(View v) {
        customUploadMoreView = v;
        removeView(defaultUploadMoreView);
        addView(customUploadMoreView);
        mUploadMoreView = customUploadMoreView;
    }

    /**
     * @author chenjing 自动模拟手指滑动的task
     */
    public void setOnPullListener(OnPullListener pullListener) {
        mOnPullListener = pullListener;
    }

    /**
     * 修改布局的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isFirstLayout) {
            // 这里是第一次进来的时候做一些初始化
            mPullRefreshable = getPullRefreshableView();
            initHeadAndBottomView();
            //下拉头测量
            mRefreshView.measure(0, 0);
            mRefreshDist = mRefreshView.getMeasuredHeight();
            //上拉头测量
            mUploadMoreView.measure(0, 0);
            mUploadMoreDist = mUploadMoreView.getMeasuredHeight();
            isFirstLayout = true;
        }
        // 改变子控件的布局，这里直接用(pullDownY + pullUpY)作为偏移量，这样就可以不对当前状态作区分
        mRefreshView.layout(0,
                (int) (pullDownY + pullUpY) - mRefreshView.getMeasuredHeight(),
                mRefreshView.getMeasuredWidth(),
                (int) (pullDownY + pullUpY));

        mPullRefreshable.layout(0,
                (int) (pullDownY + pullUpY),
                mPullRefreshable.getMeasuredWidth(),
                (int) (pullDownY + pullUpY) + mPullRefreshable.getMeasuredHeight());

        mUploadMoreView.layout(0,
                (int) (pullDownY + pullUpY) + mPullRefreshable.getMeasuredHeight(),
                mUploadMoreView.getMeasuredWidth(),
                (int) (pullDownY + pullUpY) + mPullRefreshable.getMeasuredHeight() + mUploadMoreView.getMeasuredHeight());
    }

    /**
     * 初始化默认的头部，底部view
     */
    private void initHeadAndBottomView() {
        // 初始化下拉布局
        if (null == customRefreshView) {
            pullDownArrows = defaultRefreshView.findViewById(R.id.pull_icon);
            refreshStateTextView = (TextView) defaultRefreshView.findViewById(R.id.state_tv);
            refreshingView = defaultRefreshView.findViewById(R.id.refreshing_icon);
            refreshStateImageView = defaultRefreshView.findViewById(R.id.state_iv);
        }
        // 初始化上拉布局
        if (null == customUploadMoreView) {
            pullUpArrows = defaultUploadMoreView.findViewById(R.id.pullup_icon);
            loadStateTextView = (TextView) defaultUploadMoreView.findViewById(R.id.loadstate_tv);
            loadingView = defaultUploadMoreView.findViewById(R.id.loading_icon);
            loadStateImageView = defaultUploadMoreView.findViewById(R.id.loadstate_iv);
        }

        changeState(INIT);
    }

    /**
     * 获取可拉取的视图
     */
    public View getPullRefreshableView() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof PullRefreshable) {
                return childAt;
            }
        }
        return mPullRefreshable;
    }

    public void hide() {
        mMyTimer.schedule(5);
    }

    /**
     * 事件处理模块
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * TODO 事件拦截
     */
    float mLastMotionY = 0;

    float mLastMotionX = 0;

    /**
     * true-->onTouchEvent
     * false,super--> 下层处理
     * 下滑为正slideY 上滑为负
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;

                mDownY = event.getY();
                mLastY = mDownY;
                mMyTimer.cancel();
                mEvents = 0;
                releasePull();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(x - mLastMotionX);
                float slideY = y - mLastMotionY;
                float deltaY = Math.abs(slideY);

                if (mPullDownEnable && !((PullRefreshable) mPullRefreshable).canPullDown())
                    return false;
                if (mPullUpEnable && !((PullRefreshable) mPullRefreshable).canPullUp())
                    return false;

                // 当下拉时或上拉时，否去处理子类业务逻辑
                if (deltaX < deltaY - 6 && deltaY >= 6) {

                    return mPullDownEnable && slideY > 0 || mPullUpEnable && slideY < 0;
                }
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN://此方法不会被调用??
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 过滤多点触碰
                mEvents = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                //git 下拉 先不用
                if (null != mOnRefreshProcessListener && pullDownY > 0) {
                    if (!mPreparedPullDown) {
                        mPreparedPullDown = true;
                        mOnRefreshProcessListener.onPrepare(mRefreshView, OnPullProcessListener.REFRESH);
                    }
                    mOnRefreshProcessListener.onPull(mRefreshView, pullDownY, OnPullProcessListener.REFRESH);
                }
                //git 上拉 先不用
                if (null != mOnLoadmoreProcessListener && pullUpY < 0) {
                    if (!mPreparedPullUp) {
                        mPreparedPullUp = true;
                        mOnLoadmoreProcessListener.onPrepare(mUploadMoreView, OnPullProcessListener.LOADMORE);
                    }
                    mOnLoadmoreProcessListener.onPull(mUploadMoreView, pullUpY, OnPullProcessListener.LOADMORE);
                }

                if (mEvents == 0) {//单点触摸
                    slidingDistanceOperation(event);
                } else {
                    mEvents = 0;
                }
                mLastY = event.getY();

                // 根据下拉距离改变比例
                mRadio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));

                if (pullDownY > 0 || pullUpY < 0) requestLayout();

                if (pullDownY > 0) {
                    if (pullDownY <= mRefreshDist && (mViewState == RELEASE_TO_REFRESH || mViewState == DONE)) {
                        // 如果下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新
                        changeState(INIT);
                    }
                    if (pullDownY >= mRefreshDist && mViewState == INIT) {
                        // 如果下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新
                        changeState(RELEASE_TO_REFRESH);
                    }
                } else if (pullUpY < 0) {
                    // 下面是判断上拉加载的，同上，注意pullUpY是负值
                    if (-pullUpY <= mUploadMoreDist && (mViewState == RELEASE_TO_LOAD || mViewState == DONE)) {
                        changeState(INIT);
                    }
                    // 上拉操作
                    if (-pullUpY >= mUploadMoreDist && mViewState == INIT) {
                        changeState(RELEASE_TO_LOAD);
                    }
                }
                // 因为刷新和加载操作不能同时进行，所以pullDownY和pullUpY不会同时不为0，
                // 因此这里用(pullDownY +Math.abs(pullUpY))就可以不对当前状态作区分了
                if ((pullDownY + Math.abs(pullUpY)) > 8) {
                    // 防止下拉过程中误触发长按事件和点击事件
                    event.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 正在刷新时往下拉（正在加载时往上拉），释放后下拉头（上拉头）不隐藏
                if (pullDownY > mRefreshDist || -pullUpY > mUploadMoreDist) {
                    isTouch = false;
                }
                if (mViewState == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);
                    // 刷新操作
                    if (mOnPullListener != null) mOnPullListener.onRefresh(this);
                } else if (mViewState == RELEASE_TO_LOAD) {
                    changeState(LOADING);
                    // 加载操作
                    if (mOnPullListener != null) mOnPullListener.onLoadMore(this);
                }
                hide();
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 滑动距离处理
     */
    protected void slidingDistanceOperation(MotionEvent event) {
        LogUtils.i("onTouchEvent=1=" + "pullDownY==" + pullDownY + "pullUpY==" + pullUpY);

        if (pullDownY > 0 ||
                (((PullRefreshable) mPullRefreshable).canPullDown()
                        && mCanPullDown && mPullDownEnable && mViewState != LOADING)) {
            // 可以下拉，正在加载时不能下拉 对实际滑动距离做缩小，造成用力拉的感觉
            pullDownY = pullDownY + (event.getY() - mLastY) / mRadio;
            LogUtils.i("onTouchEvent=2=" + "pullDownY==" + pullDownY + "pullUpY==" + pullUpY);
            if (pullDownY < 0) {
                pullDownY = 0;
                mCanPullDown = false;
                mCanPullUp = true;
            }

            if (pullDownY > getMeasuredHeight()) pullDownY = getMeasuredHeight();

            if (mViewState == REFRESHING) {// 正在刷新的时候触摸移动
                isTouch = true;
            }
        } else if (pullUpY < 0 || (((PullRefreshable) mPullRefreshable).canPullUp()
                && mCanPullUp && mPullUpEnable && mViewState != REFRESHING)) {
            // 可以上拉，正在刷新时不能上拉
            pullUpY = pullUpY + (event.getY() - mLastY) / mRadio;

            if (pullUpY > 0) {
                pullUpY = 0;
                mCanPullDown = true;
                mCanPullUp = false;
            }
            if (pullUpY < -getMeasuredHeight()) pullUpY = -getMeasuredHeight();
            if (mViewState == LOADING) {// 正在加载的时候触摸移动
                isTouch = true;
            }
        } else {
            releasePull();
        }
    }

    /**
     * 不限制上拉或下拉
     */
    private void releasePull() {
        mCanPullDown = true;
        mCanPullUp = true;
    }


    /**
     * // 初始状态
     * public static final int INIT = 0;
     * // 释放刷新
     * public static final int RELEASE_TO_REFRESH = 1;
     * // 正在刷新
     * public static final int REFRESHING = 2;
     * // 释放加载
     * public static final int RELEASE_TO_LOAD = 3;
     * // 正在加载
     * public static final int LOADING = 4;
     * // 操作完毕
     * public static final int DONE = 5;
     */
    public void changeState(int states) {
        mViewState = states;
        switch (mViewState) {
            case INIT://初始状态
                mPreparedPullDown = false;
                mPreparedPullUp = false;

                // 下拉布局初始状态
                if (null == customRefreshView) {
                    if (pullDownArrows == null) return;
                    pullDownArrows.clearAnimation();
                    pullDownArrows.setVisibility(View.INVISIBLE);

                    refreshingView.clearAnimation();
                    refreshingView.setVisibility(View.INVISIBLE);

                    refreshStateImageView.setVisibility(View.GONE);
                    refreshStateTextView.setText(R.string.refreshing_nomal);
                }

                // 上拉布局初始状态
                if (null == customUploadMoreView) {
                    if (pullUpArrows == null) return;
                    pullUpArrows.clearAnimation();
                    pullUpArrows.setVisibility(View.INVISIBLE);

                    loadingView.clearAnimation();
                    refreshingView.setVisibility(View.INVISIBLE);

                    loadStateImageView.setVisibility(View.GONE);
                    loadStateTextView.setText(R.string.pullup_to_load);
                }
                break;
            case RELEASE_TO_REFRESH://释放刷新
                if (null != mOnRefreshProcessListener) {
                    mOnRefreshProcessListener.onStart(mRefreshView, OnPullProcessListener.REFRESH);
                }

                if (null == customRefreshView) {
                    if (pullDownArrows == null) return;
                    pullDownArrows.setVisibility(View.VISIBLE);
                    pullDownArrows.startAnimation(reverseDownAnimation);

                    refreshingView.setVisibility(View.VISIBLE);
                    refreshingView.startAnimation(refreshingAnimation);

                    refreshStateTextView.setText(R.string.refreshing_nomal);
                }
                break;
            case REFRESHING://正在刷新
                if (null != mOnRefreshProcessListener) {
                    mOnRefreshProcessListener.onHandling(mRefreshView, OnPullProcessListener.REFRESH);
                }

                if (null == customRefreshView) {
                    if (pullDownArrows == null) return;
                    pullDownArrows.clearAnimation();
                    pullDownArrows.setVisibility(View.INVISIBLE);

                    refreshingView.setVisibility(View.VISIBLE);
                    refreshingView.startAnimation(refreshingAnimation);

                    refreshStateTextView.setText(R.string.refreshing_nomal);
                }
                break;
            case RELEASE_TO_LOAD://释放加载
                if (null != mOnLoadmoreProcessListener) {
                    mOnLoadmoreProcessListener.onStart(mUploadMoreView, OnPullProcessListener.LOADMORE);
                }
                if (null == customUploadMoreView) {
                    if (pullUpArrows == null) return;
                    pullUpArrows.setVisibility(View.VISIBLE);
                    pullUpArrows.startAnimation(reverseUpAnimation);

                    loadingView.clearAnimation();
                    loadingView.setVisibility(View.INVISIBLE);

                    loadStateTextView.setText(R.string.release_to_load);
                }
                break;
            case LOADING:// 正在加载状态
                if (null != mOnLoadmoreProcessListener) {
                    mOnLoadmoreProcessListener.onHandling(mUploadMoreView, OnPullProcessListener.LOADMORE);
                }
                if (null == customUploadMoreView) {
                    if (pullUpArrows == null) return;
                    pullUpArrows.clearAnimation();
                    pullUpArrows.setVisibility(View.INVISIBLE);

                    loadingView.setVisibility(View.VISIBLE);
                    loadingView.startAnimation(refreshingAnimation);

                    loadStateTextView.setText(R.string.loading);
                }
                break;
            case DONE://操作完毕
                mRefreshView.setVisibility(View.VISIBLE);
                mUploadMoreView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 完成刷新操作，显示刷新结果。注意：刷新完成后一定要调用这个方法
     * PullToRefreshLayout.SUCCEED代表成功
     * PullToRefreshLayout.FAIL代表失败
     */
    public void refreshFinish(final int refreshResult) {
        if (null == refreshingView) return;

        if (null != mOnRefreshProcessListener) {
            mOnRefreshProcessListener.onFinish(mRefreshView, OnPullProcessListener.REFRESH);
        }
        if (null == customRefreshView) {
            refreshingView.clearAnimation();
            refreshingView.setVisibility(View.INVISIBLE);
        }

        switch (refreshResult) {
            case SUCCEED:// 刷新成功
                if (null == customRefreshView) {
                    if (pullDownArrows == null) return;
                    pullDownArrows.clearAnimation();
                    pullDownArrows.setVisibility(View.INVISIBLE);

                    refreshStateTextView.setText(R.string.load_succeed);
                    refreshStateImageView.setVisibility(View.VISIBLE);
                    if (refreshStateImageView instanceof ImageView)
                        ((ImageView) refreshStateImageView).setImageResource(R.mipmap.load_succeed);
                }
                break;
            case FAIL:
            default:// 刷新失败
                if (null == customRefreshView) {
                    if (pullDownArrows == null) return;
                    pullDownArrows.clearAnimation();
                    pullDownArrows.setVisibility(View.INVISIBLE);

                    refreshStateTextView.setText(R.string.refresh_fail);
                    refreshStateImageView.setVisibility(View.VISIBLE);
                    if (refreshStateImageView instanceof ImageView)
                        ((ImageView) refreshStateImageView).setImageResource(R.mipmap.refresh_failed);
                }
                break;
        }

        if (pullDownY > 0) {// 刷新结果停留1秒
            new RemainHandler(PullToRefreshLayout.this).sendEmptyMessageDelayed(0, 1000);
        } else {
            changeState(DONE);
            hide();
        }
    }

    /**
     * 加载完毕，显示加载结果。注意：加载完成后一定要调用这个方法
     * PullToRefreshLayout.SUCCEED代表成功
     * PullToRefreshLayout.FAIL代表失败
     */
    public void uploadMoreFinish(int refreshResult) {
        if (null != mOnLoadmoreProcessListener) {
            mOnLoadmoreProcessListener.onFinish(mUploadMoreView, OnPullProcessListener.LOADMORE);
        }
        if (null == customUploadMoreView) {
            loadingView.clearAnimation();
            loadingView.setVisibility(View.INVISIBLE);
        }

        switch (refreshResult) {
            case SUCCEED:// 加载成功
                if (null == customUploadMoreView) {
                    if (pullUpArrows == null) return;
                    pullUpArrows.clearAnimation();
                    pullUpArrows.setVisibility(View.INVISIBLE);

                    loadStateTextView.setText(R.string.load_succeed);

                    loadStateImageView.setVisibility(View.VISIBLE);
                    if (loadStateImageView instanceof ImageView)
                        ((ImageView) loadStateImageView).setImageResource(R.mipmap.load_succeed);
                }
                break;
            case FAIL:
            default:
                // 加载失败
                if (null == customUploadMoreView) {
                    if (pullUpArrows == null) return;
                    pullUpArrows.clearAnimation();
                    pullUpArrows.setVisibility(View.INVISIBLE);

                    loadStateTextView.setText(R.string.load_fail);

                    loadStateImageView.setVisibility(View.VISIBLE);
                    if (loadStateImageView instanceof ImageView)
                        ((ImageView) loadStateImageView).setImageResource(R.mipmap.load_failed);
                }
                break;
        }
        if (pullUpY < 0) {
            // 刷新结果停留1秒
            new RemainHandler(this).sendEmptyMessageDelayed(0, 1000);
        } else {
            changeState(DONE);
            hide();
        }
    }

    /**
     * 是否应该到了父View,即PullToRefreshView滑动
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY) {
        if (mViewState == REFRESHING) {
            return false;
        }
        // 对于ScrollView
        if (mPullRefreshable != null) {
            // 子scroll view滑动到最顶端
            if (deltaY > 0 && mPullRefreshable.getScrollY() == 0) {
                return true;
            } else if (deltaY < 0 && mPullRefreshable.getMeasuredHeight() <= getHeight()
                    + mPullRefreshable.getScrollY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        mUploadMoreView.setVisibility(View.GONE);
        AutoRefreshAndLoadTask task = new AutoRefreshAndLoadTask(this);
        task.execute(2);
    }

    /**
     * 自动加载
     */
    public void autoLoad() {
        mRefreshView.setVisibility(View.GONE);
        pullUpY = -mUploadMoreDist;
        requestLayout();
        changeState(LOADING);
        // 加载操作
        if (mOnPullListener != null)
            mOnPullListener.onLoadMore(this);
    }

    /**
     * 设置下拉刷新gif动画头
     */
//    public void setGifRefreshView(GifDrawable headGifDrawable) {
//        // 设置下拉头
//        GifHeadView headView = new GifHeadView(getContext());
//        headView.setGifAnim(headGifDrawable);
//        setCustomRefreshView(headView);
//        setOnRefreshProcessListener(new GifOnPullProcessListener(headView.getDrawable()));
//    }
//
//    /**
//     * 设置上拉加载更多gif动画头
//     */
//    public void setGifLoadmoreView(GifDrawable footGifDrawable) {
//        // 设置上拉头
//        GifHeadView footView = new GifHeadView(getContext());
//        footView.setGifAnim(footGifDrawable);
//        setCustomUploadMoreView(footView);
//        setOnLoadmoreProcessListener(new GifOnPullProcessListener(footView.getDrawable()));
//    }

    /**
     * 设置下拉刷新过程监听器
     */
    public void setOnRefreshProcessListener(OnPullProcessListener onPullProcessListener) {
        mOnRefreshProcessListener = onPullProcessListener;
    }

    /**
     * 设置上拉加载更多过程监听器
     */
    public void setOnLoadmoreProcessListener(OnPullProcessListener onPullProcessListener) {
        mOnLoadmoreProcessListener = onPullProcessListener;
    }

}
