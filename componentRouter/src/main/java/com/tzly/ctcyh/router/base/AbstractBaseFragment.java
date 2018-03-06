package com.tzly.ctcyh.router.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianghw.multi.state.layout.MultiState;
import com.jianghw.multi.state.layout.MultiStateLayout;
import com.jianghw.multi.state.layout.OnStateViewCreatedListener;
import com.tzly.ctcyh.router.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Fragment 下拉刷新基类
 */
public abstract class AbstractBaseFragment extends Fragment {

    private MultiStateLayout multiStateLayout;

    /**
     * 当该fragment被添加到Activity时回调，该方法只会被调用一次
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * 创建fragment时被回调。该方法只会调用一次。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 每次创建、绘制该fragment的View组件时回调该方法，fragment将会显示该方法的View组件
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //此处不用container,
        View view = inflater.inflate(R.layout.activity_jx_content, null);
        multiStateLayout = (MultiStateLayout) view.findViewById(R.id.lay_state);
        multiStateLayout.setOnStateViewCreatedListener(new OnStateViewCreatedListener() {
            @Override
            public void onViewCreated(View view, int state) {
                if (state == MultiState.CONTENT) {
                    enhanceContentView(view, state);
                } else if (state == MultiState.LOADING) {
                    enhanceLoadingView(view, state);
                } else if (state == MultiState.EMPTY) {
                    enhanceEmptyView(view, state);
                } else if (state == MultiState.ERROR) {
                    enhanceErrorView(view, state);
                } else if (state == MultiState.NET_ERROR) {
                    enhanceNetErrorView(view, state);
                }
            }
        });

        if (contentView() > 0) {
            View contentView = inflater.inflate(contentView(), multiStateLayout, false);
            multiStateLayout.setCustomContent(contentView);
            bindContent(contentView);
        }
        int multiState = initMultiState();
        multiStateLayout.setState(multiState);
        if (multiState == MultiState.CONTENT) loadingFirstData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 当fragment所在的Activity被创建完成后调用该方法。
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 统计页面
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    /**
     * 销毁该fragment所包含的View组件时调用。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 销毁fragment时被回调，该方法只会被调用一次。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当该fragment从Activity中被删除、被替换完成时回调该方法，
     * onDestory()方法后一定会回调onDetach()方法。该方法只会被调用一次。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.LOADING;
    }

    /**
     * 页面布局 增强控制
     */
    private void enhanceEmptyView(View view, int state) {
        doClickRefreshView(view, state);
    }

    private void enhanceErrorView(View view, int state) {
        doClickRefreshView(view, state);
    }

    private void enhanceNetErrorView(View view, int state) {
        doClickRefreshView(view, state);
    }

    private void enhanceLoadingView(View view, int state) {
        loadingFirstData();
    }

    private void enhanceContentView(View view, int state) {}

    private void doClickRefreshView(View view, int state) {
        View tvEmpty = view.findViewById(R.id.tv_empty);
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRefreshData();
            }
        });
    }

    /**
     * 加载提示框
     */
    public void showLoading() {
        FragmentActivity activity = getActivity();
        if (activity instanceof AbstractBaseActivity)
            ((AbstractBaseActivity) activity).showLoading();
    }

    public void dismissLoading() {
        FragmentActivity activity = getActivity();
        if (activity instanceof AbstractBaseActivity)
            ((AbstractBaseActivity) activity).dismissLoading();
    }

    /**
     * 统一封装
     */
    public void toastShort(String message) {
        FragmentActivity activity = getActivity();
        if (activity instanceof AbstractBaseActivity)
            ((AbstractBaseActivity) activity).shortToast(message);
    }

    /**
     * 手动控制布局显示效果
     */
    protected void showStateContent() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.CONTENT);
    }

    protected void showStateLoading() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.LOADING);
    }

    protected void showStateEmpty() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.EMPTY);
    }

    protected void showStateNetError() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.NET_ERROR);
    }

    protected void showStateError() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.ERROR);
    }

    /**
     * 子布局
     */
    protected abstract int contentView();

    protected abstract void bindContent(View contentView);

    /**
     * 第一次加载页面加载数据
     */
    protected abstract void loadingFirstData();

    /**
     * 用户手动刷新数据
     */
    protected abstract void clickRefreshData();

    /**
     * 初次获取数据
     */
    public void responseError() {
        responseError("数据出错,未知错误");
    }

    public void responseError(String message) {
        toastShort(message);
        showStateError();

        errorData(message);
    }

    protected void errorData(String message) {}

    public void responseSucceed(Object response) {
        if (response == null) {
            toastShort("数据为空,未知错误");
            showStateEmpty();
        } else {
            showStateContent();
            responseData(response);
        }
    }

    protected abstract void responseData(Object response);

    @Override
    public Context getContext() {
        if (super.getContext() != null && super.getContext().getApplicationContext() != null) {
            return super.getContext().getApplicationContext();
        }
        return super.getContext();
    }
}
