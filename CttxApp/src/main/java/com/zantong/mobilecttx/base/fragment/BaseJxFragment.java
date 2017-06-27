package com.zantong.mobilecttx.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;

import butterknife.ButterKnife;

/**
 * Fragment基类
 */
public abstract class BaseJxFragment extends Fragment {

    /**
     * 加载中布局，加载失败布局，加载成功显示数据布局
     */
    private View mLoadingLayout, mLoadingFailedLayout, mLoadingSucceedLayout;

    /**
     * 加载中
     */
    public static final int LOADING = 1;
    /**
     * 加载失败
     */
    public static final int LOADING_FAILED = 2;
    /**
     * 加载成功
     */
    public static final int LOADING_SUCCESS = 3;
    /**
     * 加载成功,无数据
     */
    public static final int LOADING_SUCCESS_NULL = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            View inflate = inflater.inflate(R.layout.fragment_base_jx, container, false);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.lay_base_content);

            View rootView = inflater.inflate(getContentLayoutResID(), null);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            linearLayout.addView(rootView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            return inflate;
        }
    }

    /**
     * 全新的布局控件
     */
    protected abstract int getContentViewLayoutID();

    protected abstract int getContentLayoutResID();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isNeedKnife()) ButterKnife.bind(this, view);

        mLoadingLayout = view.findViewById(R.id.base_loading_bg);
        mLoadingSucceedLayout = view.findViewById(R.id.lay_base_content);
        mLoadingFailedLayout = view.findViewById(R.id.base_loading_failed_layout);
        View mLoadingFailedRefresh = view.findViewById(R.id.base_loading_failed_refresh);
        mLoadingFailedRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForceRefresh();
            }
        });
        initViewsAndEvents(view);
    }

    /**
     * 默认不用ButterKnife
     *
     * @return true -->使用
     */
    protected boolean isNeedKnife() {
        return false;
    }

    /**
     * 刷新数据
     */
    protected abstract void onForceRefresh();

    /**
     * 布局的初始化
     */
    protected abstract void initViewsAndEvents(View view);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onFirstUserVisible();
    }

    /**
     * 第一次用户可见数据加载
     */
    protected abstract void onFirstUserVisible();

    @Override
    public void onDestroy() {
        if (isNeedKnife()) ButterKnife.unbind(this);
        DestroyViewAndThing();
        super.onDestroy();
    }

    protected abstract void DestroyViewAndThing();

    /**
     * 显示加载中遮罩层Dialog，带消息提示
     *
     * @param msg
     */
    public void showDialogLoading(String msg) {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).showDialogLoading(msg);
        } else if (activity != null && activity instanceof MvpBaseActivity) {
            ((MvpBaseActivity) activity).showDialogLoading(msg);
        }
    }

    /**
     * 隐藏遮罩层
     */
    public void hideDialogLoading() {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).hideDialogLoading();
        } else if (activity != null && activity instanceof MvpBaseActivity) {
            ((MvpBaseActivity) activity).hideDialogLoading();
        }
    }

    /**
     * 判断显示的View
     *
     * @param index
     */
    private void showViewByStatus(int index) {
        mLoadingLayout.setVisibility(index == LOADING ? View.VISIBLE : View.GONE);
        mLoadingFailedLayout.setVisibility(index == LOADING_FAILED ? View.VISIBLE : View.GONE);
        mLoadingSucceedLayout.setVisibility(index == LOADING_SUCCESS ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingView() {
        showViewByStatus(LOADING);
    }

    /**
     * 显示加载成功布局
     */
    public void showSucceedView() {
        showViewByStatus(LOADING_SUCCESS);
    }

    /**
     * 显示加载失败布局
     */
    public void showFailedView() {
        showViewByStatus(LOADING_FAILED);
    }
}
