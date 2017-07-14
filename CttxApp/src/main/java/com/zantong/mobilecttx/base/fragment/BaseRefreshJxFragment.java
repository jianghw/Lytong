package com.zantong.mobilecttx.base.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.PullToRefreshLayout;

/**
 * Fragment 刷新基类
 */
public abstract class BaseRefreshJxFragment extends BaseJxFragment {

    private PullToRefreshLayout mPullRefreshView;

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    /**
     * 实现上级布局
     */
    @Override
    protected int getContentLayoutResID() {
        return R.layout.base_pullable_fragment;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mPullRefreshView = (PullToRefreshLayout) view.findViewById(R.id.pull_refresh_view);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.base_content);

        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(getFragmentLayoutResId(), null);
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        linearLayout.addView(rootView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        initFragmentView(view);
    }

    @Override
    protected void onFirstUserVisible() {
        mPullRefreshView.setPullDownEnable(isRefresh());
        mPullRefreshView.setPullUpEnable(isLoadMore());

        mPullRefreshView.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                if (mPullRefreshView != null) mPullRefreshView.postDelayed(new Runnable() {
                    public void run() {
                        onRefreshData();
                        if (mPullRefreshView != null)
                            mPullRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }

                }, 2000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (mPullRefreshView != null) mPullRefreshView.postDelayed(new Runnable() {
                    public void run() {
                        onLoadMoreData();
                        if (mPullRefreshView != null)
                            mPullRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }

                }, 2000);
            }
        });
        onFirstDataVisible();
    }

    /**
     * 下拉数据设置
     */
    protected abstract void onRefreshData();

    /**
     * 加载更多数据
     */
    protected void onLoadMoreData() {
        //TODO 子类想实现请重载
    }

    /**
     * fragment布局
     */
    protected abstract int getFragmentLayoutResId();

    /**
     * 布局控件初始化
     */
    protected abstract void initFragmentView(View view);

    /**
     * 第一次加载数据
     */
    protected abstract void onFirstDataVisible();

    @Override
    protected void onForceRefresh() {
        onFirstDataVisible();
    }

    /**
     * 是否下拉刷新 默认为不支持
     *
     * @return true 是，false 否
     */
    protected boolean isRefresh() {
        return true;
    }

    /**
     * 是否加载更多 默认支持加载更多
     *
     * @return true 是，false 否
     */
    protected boolean isLoadMore() {
        return false;
    }


    protected void setPullDownEnable(boolean isRefresh) {
        mPullRefreshView.setPullDownEnable(isRefresh);
        mPullRefreshView.setEnabled(isRefresh);
    }

    protected PullToRefreshLayout getPullRefreshView(){
        return mPullRefreshView;
    }
}
