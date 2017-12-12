package com.jianghw.multi.state.layout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by jianghw on 2017/9/19.
 * Description:
 * Update by:
 * Update day:
 */

public class MultiStateLayout extends FrameLayout {

    /**
     * 布局控件
     */
    private View mContentView;
    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mNetErrorView;
    private int mLoadingResId;

    private int mEmptyResId;
    private int mErrorResId;
    private int mNetErrorResId;
    private int mAnimDuration;

    private LayoutInflater mInflater;
    /**
     * 用来存放自定义的布局 key值对应保存
     */
    private SparseArray<View> mCustomStateViewArray = null;
    /**
     * 动画
     */
    private boolean mAnimEnable;
    private ObjectAnimator mAlphaAnimator;
    private static final int DEFAULT_ANIM_DURATION = 300;
    private TransitionAnimatorLoader mTransitionAnimatorLoader;
    /**
     * 当前状态
     */
    @MultiState
    private int mCurState = MultiState.LOADING;

    /**
     * 自定义当前状态布局
     */
    private int mCurCustomStateKey;
    /**
     * 是否是用原生状态，true原生 false 自定义
     */
    private boolean mIsSystemState;
    private OnStateViewCreatedListener mOnStateViewCreatedListener;
    private static MultiStateConfiguration.Builder mStateConfiguration;
    /**
     * 接口
     */
    private IAddContentView mIAddContentView;

    public MultiStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public MultiStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateLayout(@NonNull Context context,
                            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
    }

    /**
     * int CONTENT = 0;
     * int LOADING = 1;
     * int EMPTY = 2;
     * int ERROR = 3;
     * int NET_ERROR = 4;
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiStateLayout);
        mLoadingResId = typedArray.getResourceId(R.styleable.MultiStateLayout_layoutLoading, getDefaultResIdByState(MultiState.LOADING));
        mEmptyResId = typedArray.getResourceId(R.styleable.MultiStateLayout_layoutEmpty, getDefaultResIdByState(MultiState.EMPTY));
        mErrorResId = typedArray.getResourceId(R.styleable.MultiStateLayout_layoutError, getDefaultResIdByState(MultiState.ERROR));
        mNetErrorResId = typedArray.getResourceId(R.styleable.MultiStateLayout_layoutNetError, getDefaultResIdByState(MultiState.NET_ERROR));

        mAnimEnable = typedArray.getBoolean(R.styleable.MultiStateLayout_animEnable, isDefaultAnimEnable());
        mAnimDuration = typedArray.getInt(R.styleable.MultiStateLayout_animDuration, getDefaultAnimDuration());
        typedArray.recycle();

        mInflater = LayoutInflater.from(context);
        mCustomStateViewArray = new SparseArray<>();
    }

    /**
     * get common layout resource id by state except CONTENT(Content)
     *
     * @param state state
     * @return resource id
     */
    private int getDefaultResIdByState(@MultiState int state) {
        switch (state) {
            case MultiState.LOADING:
                return null == mStateConfiguration ? -1 : mStateConfiguration.getDefaultLoadingLayout();
            case MultiState.EMPTY:
                return null == mStateConfiguration ? -1 : mStateConfiguration.getDefaultEmptyLayout();
            case MultiState.ERROR:
                return null == mStateConfiguration ? -1 : mStateConfiguration.getDefaultErrorLayout();
            case MultiState.NET_ERROR:
                return null == mStateConfiguration ? -1 : mStateConfiguration.getDefaultNetErrorLayout();
            case MultiState.CONTENT:
                return -1;
        }
        return 0;
    }

    /**
     * 回调方法，当应用从XML加载该组件并用它构建界面之后调用的方法
     * 在调用inflater.inflate(R.layout.base_activity_jx_content, null)...
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("onFinishInflate", "=====" + getChildCount());
        if (getChildCount() > 1) {
            throw new IllegalStateException("Expect to have one child.");
        } else if (getChildCount() == 1) {
            mContentView = getChildAt(MultiState.CONTENT);
        } else {
            mContentView = null;
        }
    }

    public void customChildAt() {
        Log.e("customChildAt", "=====" + getChildCount());
        if (getChildCount() > 1) {
            throw new IllegalStateException("Expect to have one child.");
        } else if (getChildCount() == 1) {
            mContentView = getChildAt(MultiState.CONTENT);
        } else {
            mContentView = null;
        }
    }

    /**
     * onDetachedFromWindow() 当把该组件从某个窗口上分离时触发的方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearTargetViewAnimation();
    }

    /*--------------------------------布局控制api------------------------------------------*/

    /**
     * set common mStateConfiguration settings
     *
     * @param builder MultiStateConfiguration.Builder
     */
    @SuppressWarnings("unused")
    public static void setConfiguration(MultiStateConfiguration.Builder builder) {
        mStateConfiguration = builder;
    }

    /**
     * Put customise state view by customise key
     *
     * @param customStateKey key
     * @param stateView      view
     */
    @SuppressWarnings("unused")
    public void putCustomStateView(int customStateKey, View stateView) {
        mCustomStateViewArray.put(customStateKey, stateView);
        addView(stateView, stateView.getLayoutParams());
        stateView.setVisibility(GONE);
    }

    /**
     * @return current state value
     */
    @SuppressWarnings("unused")
    public int getState() {
        return mIsSystemState ? mCurState : mCurCustomStateKey;
    }

    /**
     * @return current state is customise state
     */
    @SuppressWarnings("unused")
    public boolean isCustomizeState() {
        return !mIsSystemState;
    }

    @SuppressWarnings("unused")
    public void setCustomState(int customStateKey) {
        setCustomState(customStateKey, false);
    }

    @SuppressWarnings("unused")
    public void setCustomState(int customStateKey, boolean displayContentLayout) {
        clearTargetViewAnimation();
        if (mIsSystemState) {
            hideViewByState(mCurState, displayContentLayout);
        } else {
            hideCustomViewByState(mCurCustomStateKey, displayContentLayout);
        }
        showCustomViewByState(customStateKey);
    }

    /**
     * show custom view by the custom state key 根据key显示布局
     * 显示自定义布局类型
     *
     * @param customStateKey custom state key
     */
    private void showCustomViewByState(int customStateKey) {
        View customStateView = findCustomStateViewByKey(customStateKey);
        if (null != customStateView) {
            customStateView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(customStateView);
            }
        }

        mCurCustomStateKey = customStateKey;
        mIsSystemState = false;
    }

    /**
     * set state
     */
    @SuppressLint("Assert")
    public void setState(@MultiState int state) {
        setState(state, false);
    }

    /**
     * set state
     *
     * @param state                MultiState
     * @param displayContentLayout display or conceal content layout
     */
    @SuppressLint("Assert")
    public void setState(@MultiState int state, boolean displayContentLayout) {
        assert !(state < MultiState.CONTENT || state > MultiState.NET_ERROR);
        clearTargetViewAnimation();

        if (mIsSystemState) {
            hideViewByState(mCurState, displayContentLayout);
        } else {//xml中获取
            hideCustomViewByState(mCurCustomStateKey, displayContentLayout);
        }
        showViewByState(state);
    }

    /**
     * hide target view 隐藏某状态布局
     *
     * @param state                state
     * @param displayContentLayout display content layout true显示内容控件
     */
    private void hideViewByState(@MultiState int state, boolean displayContentLayout) {
        if (null != mContentView) {
            mContentView.setVisibility(displayContentLayout ? VISIBLE : GONE);
        }

        switch (state) {
            case MultiState.CONTENT:
                break;
            case MultiState.LOADING:
                if (null != mLoadingView) {
                    mLoadingView.setVisibility(GONE);
                }
                break;
            case MultiState.EMPTY:
                if (null != mEmptyView) {
                    mEmptyView.setVisibility(GONE);
                }
                break;
            case MultiState.ERROR:
                if (null != mErrorView) {
                    mErrorView.setVisibility(GONE);
                }
                break;
            case MultiState.NET_ERROR:
                if (null != mNetErrorView) {
                    mNetErrorView.setVisibility(GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * hide target view 根据key隐藏布局
     *
     * @param customStateKey       custom state key
     * @param displayContentLayout display content layout
     */
    private void hideCustomViewByState(int customStateKey, boolean displayContentLayout) {
        if (null != mContentView) {
            mContentView.setVisibility(displayContentLayout ? VISIBLE : GONE);
        }

        View customStateView = findCustomStateViewByKey(customStateKey);
        if (null != customStateView) {
            customStateView.setVisibility(GONE);
        }
    }

    @SuppressWarnings("unused")
    public View findCustomStateViewByKey(int customStateKey) {
        return mCustomStateViewArray != null ? mCustomStateViewArray.get(customStateKey) : null;
    }

    /**
     * show view by current state 根据状态显示布局
     *
     * @param state state
     */
    private void showViewByState(@MultiState int state) {
        switch (state) {
            case MultiState.CONTENT:
                showContentView();
                break;
            case MultiState.EMPTY:
                showEmptyView();
                break;
            case MultiState.LOADING:
                showLoadingView();
                break;
            case MultiState.ERROR:
                showErrorView();
                break;
            case MultiState.NET_ERROR:
                showNetErrorView();
                break;
            default:
                break;
        }
        mCurState = state;
        mIsSystemState = true;
    }

    /**
     * show content view without animation
     */
    private void showContentView() {
        //说明自定义传过来的值,手动加入
        if (null == mContentView && null != mIAddContentView) {
            mContentView = mIAddContentView.getContentView();

            addView(mContentView, mContentView.getLayoutParams());
            callViewCreated(mContentView, MultiState.CONTENT);
        }

        if (null != mContentView) {
            mContentView.setVisibility(VISIBLE);
        }
    }

    /**
     * show customize loading view
     */
    private void showLoadingView() {
        if (null == mLoadingView && mLoadingResId > -1) {
            mLoadingView = mInflater.inflate(mLoadingResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
            callViewCreated(mLoadingView, MultiState.LOADING);
        }
        if (null != mLoadingView) {
            mLoadingView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mLoadingView);
            }
        } else {
            throw new NullPointerException("Expect to have an loading view.");
        }
    }

    /**
     * show customize empty view
     */
    private void showEmptyView() {
        if (null == mEmptyView && mEmptyResId > -1) {
            mEmptyView = mInflater.inflate(mEmptyResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
            callViewCreated(mEmptyView, MultiState.EMPTY);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mEmptyView);
            }
        } else {
            throw new NullPointerException("Expect to have an empty view.");
        }
    }

    /**
     * show customize error view
     */
    private void showErrorView() {
        if (null == mErrorView && mErrorResId > -1) {
            mErrorView = mInflater.inflate(mErrorResId, this, false);
            addView(mErrorView, mErrorView.getLayoutParams());
            callViewCreated(mErrorView, MultiState.ERROR);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mErrorView);
            }
        } else {
            throw new NullPointerException("Expect to have one error view.");
        }
    }

    /**
     * show customize network error view
     */
    private void showNetErrorView() {
        if (null == mNetErrorView && mNetErrorResId > -1) {
            mNetErrorView = mInflater.inflate(mNetErrorResId, this, false);
            addView(mNetErrorView, mNetErrorView.getLayoutParams());
            callViewCreated(mNetErrorView, MultiState.NET_ERROR);
        }
        if (null != mNetErrorView) {
            mNetErrorView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mNetErrorView);
            }
        } else {
            throw new NullPointerException("Expect to have one network error view.");
        }
    }

    /**
     * set empty view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setEmptyView(@LayoutRes int resId) {
        if (null != mEmptyView) {
            removeView(mEmptyView);
            mEmptyView = null;
        }
        mEmptyResId = resId;
    }

    /**
     * set empty view by view that had created.
     *
     * @param emptyView view
     */
    @SuppressWarnings("unused")
    public void setEmptyView(View emptyView) {
        removeView(mEmptyView);
        mEmptyView = emptyView;
        mEmptyView.setVisibility(GONE);
        addView(mEmptyView);
    }

    /**
     * return empty view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getEmptyView() {
        if (null == mEmptyView) {
            if (mEmptyResId > -1) {
                mEmptyView = mInflater.inflate(mEmptyResId, this, false);
                addView(mEmptyView, mEmptyView.getLayoutParams());
                mEmptyView.setVisibility(GONE);
            }
        }
        return mEmptyView;
    }

    /**
     * set loading view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setLoadingView(@LayoutRes int resId) {
        if (null != mLoadingView) {
            removeView(mLoadingView);
            mLoadingView = null;
        }
        mLoadingResId = resId;
    }

    /**
     * set loading view by view that had created.
     *
     * @param loadingView view
     */
    @SuppressWarnings("unused")
    public void setLoadingView(View loadingView) {
        removeView(mLoadingView);
        mLoadingView = loadingView;
        mLoadingView.setVisibility(GONE);
        addView(mLoadingView);
    }

    /**
     * return loading view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getLoadingView() {
        if (null == mLoadingView) {
            if (mLoadingResId > -1) {
                mLoadingView = mInflater.inflate(mLoadingResId, this, false);
                addView(mLoadingView, mLoadingView.getLayoutParams());
                mLoadingView.setVisibility(GONE);
            }
        }
        return mLoadingView;
    }

    /**
     * set error view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setErrorView(@LayoutRes int resId) {
        if (null != mErrorView) {
            removeView(mErrorView);
            mErrorView = null;
        }
        mErrorResId = resId;
    }

    /**
     * set error view by view that had created.
     *
     * @param errorView view
     */
    @SuppressWarnings("unused")
    public void setErrorView(View errorView) {
        removeView(mErrorView);
        mErrorView = errorView;
        mErrorView.setVisibility(GONE);
        addView(mErrorView);
    }

    /**
     * return error view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getErrorView() {
        if (null == mErrorView) {
            if (mErrorResId > -1) {
                mErrorView = mInflater.inflate(mErrorResId, this, false);
                addView(mErrorView, mErrorView.getLayoutParams());
                mErrorView.setVisibility(GONE);
            }
        }
        return mErrorView;
    }

    /**
     * set network error view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setNetErrorView(@LayoutRes int resId) {
        if (null != mNetErrorView) {
            removeView(mNetErrorView);
            mNetErrorView = null;
        }
        mNetErrorResId = resId;
    }

    /**
     * set network error view by view that had created.
     *
     * @param netErrorView view
     */
    @SuppressWarnings("unused")
    public void setNetErrorView(View netErrorView) {
        removeView(mNetErrorView);
        mNetErrorView = netErrorView;
        mNetErrorView.setVisibility(GONE);
        addView(mNetErrorView);
    }

    /**
     * return network error view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getNetErrorView() {
        if (null == mNetErrorView) {
            if (mNetErrorResId > -1) {
                mNetErrorView = mInflater.inflate(mNetErrorResId, this, false);
                addView(mNetErrorView, mNetErrorView.getLayoutParams());
                mNetErrorView.setVisibility(GONE);
            }
        }
        return mNetErrorView;
    }

    /**
     * open/close optional animation
     *
     * @param animEnable open/close
     */
    @SuppressWarnings("unused")
    public void setAnimEnable(boolean animEnable) {
        mAnimEnable = animEnable;
    }

    /**
     * get animation status
     *
     * @return enable
     */
    @SuppressWarnings("unused")
    public boolean isAnimEnable() {
        return mAnimEnable;
    }

    /**
     * set animation duration
     *
     * @param duration duration
     */
    @SuppressWarnings("unused")
    public void setAnimDuration(int duration) {
        mAnimDuration = duration;
    }

    /**
     * get animation duration
     */
    @SuppressWarnings("unused")
    public int getAnimDuration() {
        return mAnimDuration;
    }

    /**
     * set transition animator
     */
    @SuppressWarnings("unused")
    public void setTransitionAnimator(TransitionAnimatorLoader animatorLoader) {
        mTransitionAnimatorLoader = animatorLoader;
    }

    /**
     * start alpha animation 动画执行
     *
     * @param targetView target view
     */
    private void execAlphaAnimation(View targetView) {
        if (null == targetView) {
            return;
        }

        if (null == mTransitionAnimatorLoader || null == mTransitionAnimatorLoader.onCreateAnimator(targetView)) {
            mAlphaAnimator = ObjectAnimator.ofFloat(targetView, "alpha", 0.0f, 1.0f);
            mAlphaAnimator.setInterpolator(new AccelerateInterpolator());
            mAlphaAnimator.setDuration(mAnimDuration);
        } else {
            mAlphaAnimator = mTransitionAnimatorLoader.onCreateAnimator(targetView);
        }
        mAlphaAnimator.start();
    }

    /**
     * cancel animation
     */
    private void clearTargetViewAnimation() {
        if (null != mAlphaAnimator && mAlphaAnimator.isRunning()) {
            mAlphaAnimator.cancel();
        }
    }

    /**
     * get anim status from common settings
     *
     * @return animEnable
     */
    private boolean isDefaultAnimEnable() {
        return null != mStateConfiguration && mStateConfiguration.isAnimEnable();
    }

    /**
     * get anim duration from common settings
     *
     * @return animDuration
     */
    private int getDefaultAnimDuration() {
        return null == mStateConfiguration ? DEFAULT_ANIM_DURATION : mStateConfiguration.getAnimDuration();
    }

    /**
     * set OnStateViewCreatedListener
     *
     * @param stateViewCreatedListener OnStateViewCreatedListener
     */
    @SuppressWarnings("unused")
    public void setOnStateViewCreatedListener(OnStateViewCreatedListener stateViewCreatedListener) {
        mOnStateViewCreatedListener = stateViewCreatedListener;
    }

    /**
     * called it on view created
     *
     * @param view  state view
     * @param state state
     */
    private void callViewCreated(View view, int state) {
        if (null != mOnStateViewCreatedListener) {
            mOnStateViewCreatedListener.onViewCreated(view, state);
        }
    }

    public void setCustomContent(View contentView) {
        mContentView = contentView;

        addView(mContentView, mContentView.getLayoutParams());
        callViewCreated(mContentView, MultiState.CONTENT);
    }

    public void setContentView(IAddContentView iAddContentView) {
        mIAddContentView = iAddContentView;
    }
}
