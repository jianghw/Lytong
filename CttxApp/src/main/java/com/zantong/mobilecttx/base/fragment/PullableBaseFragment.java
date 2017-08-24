package com.zantong.mobilecttx.base.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.widght.refresh.PullToRefreshLayout;

import butterknife.Bind;

/**
 * 可下拉刷新页面
 */
public abstract class PullableBaseFragment extends BaseFragment {

    @Bind(R.id.pull_refresh_view)
    PullToRefreshLayout mPullRefreshView;
    /**
     * 动态添加布局
     */
    private View rootView;

    @Override
    protected int getLayoutResId() {
        return R.layout.base_pullable_fragment;
    }

    @Override
    public void initView(View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.base_content);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (rootView == null) {
            rootView = inflater.inflate(getFragmentLayoutResId(), null);
        }
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
    public void initData() {
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

                }, 1500);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (mPullRefreshView != null) mPullRefreshView.postDelayed(new Runnable() {
                    public void run() {
                        onLoadMoreData();
                        if (mPullRefreshView != null)
                            mPullRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }

                }, 1500);
            }
        });

        loadingData();
    }

    /**
     * 下拉数据设置
     */
    protected abstract void onRefreshData();

    protected abstract void onLoadMoreData();

    protected abstract void initFragmentView(View view);

    protected abstract void loadingData();

    protected abstract int getFragmentLayoutResId();

    @Override
    protected void onForceRefresh() {
        loadingData();
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

    @Override
    public void onClick(View v) {
    }

}
