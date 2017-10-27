package com.tzly.ctcyh.router.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.refresh.IPullListener;
import com.tzly.ctcyh.router.custom.refresh.PullToRefreshLayout;

/**
 * Fragment 下拉刷新基类
 */
public abstract class JxBaseRefreshFragment extends JxBaseFragment {

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_jx_base_refresh,null);
        final PullToRefreshLayout pullRefreshView = (PullToRefreshLayout) inflate.findViewById(R.id.custom_refresh);
        pullRefreshView.setPullDownEnable(isRefresh());
        pullRefreshView.setPullUpEnable(isLoadMore());
        pullRefreshView.setIPullListener(new IPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullRefreshView.postDelayed(
                        new Runnable() {
                            public void run() {
                                onRefreshData();
                                pullRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                            }

                        }, 2000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullRefreshView.postDelayed(
                        new Runnable() {
                            public void run() {
                                onLoadMoreData();
                                pullRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                            }

                        }, 2000);
            }
        });

        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.base_fragment_content);
        View fragmentView = inflater.inflate(initFragmentView(), linearLayout, true);
        bindFragmentView(fragmentView);
        return inflate;
    }

    /**
     * 手动刷新动作
     */
    protected abstract void onRefreshData();

    protected abstract void onLoadMoreData();

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
    protected abstract int initFragmentView();

    /**
     * 绑定控件
     */
    protected abstract void bindFragmentView(View fragmentView);

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

        onFirstDataVisible();
    }

    protected abstract void onFirstDataVisible();

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

}
