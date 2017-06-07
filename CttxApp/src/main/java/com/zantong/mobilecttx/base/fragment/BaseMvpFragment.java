package com.zantong.mobilecttx.base.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by zhengyingbing on 16/6/8.
 */
public abstract class BaseMvpFragment<V, T extends BasePresenter<V>> extends BaseFragment {
    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @Bind(R.id.pull_refresh_empty_layout)
    View mEmptyView;
    @Bind(R.id.pull_refresh_empty_text)
    TextView mEmptyText;
    @Bind(R.id.pull_refresh_empty_image)
    ImageView mEmptyImage;
    /**
     * 数据源
     */
    protected BaseAdapter<T> mAdapter;
    protected int mCurrentPage = 1;
    protected View mHeader;
    @Override
    public void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.pull_icon_big);
        mRecyclerView.setLoadingMoreEnabled(isLoadMore());
        mRecyclerView.setPullRefreshEnabled(isRefresh());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.ds_3);
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        if (getRecyclerHeader() != null) {
            mRecyclerView.addHeaderView(getRecyclerHeader());
        }
        mAdapter = createAdapter();
        if (mAdapter == null)
            return;
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, Object data) {
                onRecyclerItemClick(view,data);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if(mRecyclerView==null){
                            return;
                        }
                        mCurrentPage=1;
                        onRefreshData();
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if(mRecyclerView==null){
                            return;
                        }
                        mRecyclerView.loadMoreComplete();
                        onLoadMoreData();
                        mRecyclerView.refreshComplete();
                    }
                }, 1000);
            }
        });

    }
    protected abstract void onLoadMoreData();
    protected abstract void onRefreshData();

    protected View getEmptyView() {
        return mEmptyView;
    }
    protected void setEmptyText(int resId){
        mEmptyText.setText(resId);
    }
    protected abstract void onRecyclerItemClick(View view, Object data);

    @Override
    public void initData() {
//        Config.NOTICE_CLASS= StringUtils.getClassName(this.getClass());
        onShowLoading();
    }

    protected int getPageSize() {
        return Config.PAGE_SIZE;
    }

    /**
     * 设置数据源
     *
     * @param list
     */
    protected void setDataResult(List<T> list) {
        if (mRecyclerView == null) {
            return;
        }
        onShowContent();
        hideDialogLoading();
        if (mCurrentPage == 1) {
            mAdapter.removeAll();
            if (isRefresh()) {
                if (mRecyclerView == null) {
                    return;
                }
                mRecyclerView.refreshComplete();
            }
        }
        if (list != null && list.size() > 0) {
            mAdapter.append(list);
            mCurrentPage += 1;
            if (list.size() < getPageSize()) {
                mRecyclerView.refreshComplete();
            }
        } else {
            mRecyclerView.refreshComplete();
        }
        if (isLoadMore()) {
            mRecyclerView.refreshComplete();
        }
        if(mAdapter.getItemCount()==0 && getRecyclerHeader() == null){
            mEmptyView.setVisibility(View.VISIBLE);
        }else{
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取ListRecycler
     *
     * @return
     */
    public RecyclerView getListView() {
        return mRecyclerView;
    }
    /**
     * 获取RecyclerHeader
     *
     * @return
     */
    public View getRecyclerHeader() {
        return mHeader;
    }

    /**
     * 获取RecyclerHeader
     *
     * @return
     */
    public void setRecyclerHeader(View view) {
        mHeader=view;
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.base_list_recyclerview;
    }


    @Override
    protected void onForceRefresh() {
        onShowLoading();
        super.onForceRefresh();

    }

    /**
     * 数据适配器
     *
     * @return
     */
    public abstract BaseAdapter<T> createAdapter();

    /**
     * 是否加载更多 默认支持加载更多
     *
     * @return true 是，false 否
     */
    protected boolean isLoadMore() {
        return true;
    }

    /**
     * 是否下拉刷新 默认为不支持
     *
     * @return true 是，false 否
     */
    protected boolean isRefresh() {
        return false;
    }
}
