package com.tzly.ctcyh.router.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.refresh.IPullListener;
import com.tzly.ctcyh.router.custom.refresh.PullToRefreshLayout;
import com.tzly.ctcyh.router.custom.refresh.PullToRefreshScrollView;

/**
 * Fragment 下拉刷新基类
 */
public abstract class RefreshFragment extends AbstractBaseFragment {

    /**
     * 当该fragment被添加到Activity时回调，该方法只会被调用一次
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 创建fragment时被回调。该方法只会调用一次。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onPause() {
        super.onPause();
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

    /**
     * 布局
     */
    @Override
    protected int contentView() {
        return R.layout.fragment_base_refresh;
    }

    @Override
    protected void bindContent(View contentView) {
        final PullToRefreshLayout pullRefreshView = (PullToRefreshLayout) contentView.findViewById(R.id.custom_refresh);
        PullToRefreshScrollView scrollView = (PullToRefreshScrollView) contentView.findViewById(R.id.custom_refresh_sv);
        pullRefreshView.setPullDownEnable(isRefresh());
        pullRefreshView.setPullUpEnable(isLoadMore());
        pullRefreshView.setIPullListener(new IPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullRefreshView.postDelayed(
                        new Runnable() {
                            public void run() {
                                if (isRefresh()) onRefreshData();
                                pullRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                            }

                        }, 3000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullRefreshView.postDelayed(
                        new Runnable() {
                            public void run() {
                                if (isLoadMore()) onLoadMoreData();
                                pullRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                            }

                        }, 3000);
            }
        });
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View fragmentView = inflater.inflate(fragmentView(), scrollView, false);
        scrollView.addView(fragmentView);
        bindFragment(fragmentView);
    }

    @Override
    protected void clickRefreshData() {
        showStateLoading();
        loadingFirstData();
    }

    /**
     * 手动刷新动作
     */
    protected void onRefreshData() {
        loadingFirstData();
    }

    protected void onLoadMoreData() {}

    /**
     * 是否可刷新
     */
    protected boolean isRefresh() {
        return true;
    }

    protected boolean isLoadMore() {
        return false;
    }

    /**
     * 获取fragment 布局
     */
    protected abstract int fragmentView();

    /**
     * 绑定控件
     *
     * @param fragment
     */
    protected abstract void bindFragment(View fragment);
}
