package com.zantong.mobilecttx.base.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.touch.Closeable;
import com.jcodecraeer.xrecyclerview.touch.OnSwipeMenuItemClickListener;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenu;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenuCreator;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenuItem;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.R;

import java.util.List;

import butterknife.Bind;

/**
 * ListFragment基类
 * @author Sandy
 * create at 16/6/2 下午1:35
 */
public abstract class BaseListFragment<T> extends BaseFragment{
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
    protected int getLayoutResId() {
        return R.layout.base_list_recyclerview;
    }

    @Override
    public void initView(View view) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.loading);

        mRecyclerView.setPullRefreshEnabled(isRefresh());
        mRecyclerView.setLoadingMoreEnabled(isLoadMore());

        //        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.ds_3);
        //        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        if (getRecyclerHeader() != null) {
            mRecyclerView.addHeaderView(getRecyclerHeader());
        }

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    public void run() {
                        if (mRecyclerView == null) return;
                        mCurrentPage = 1;
                        onRefreshData();
                        mRecyclerView.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    public void run() {
                        if (mRecyclerView == null) return;
                        mRecyclerView.loadMoreComplete();
                        onLoadMoreData();
                        mRecyclerView.refreshComplete();
                    }
                }, 1500);
            }
        });

        if (isDeleteItem()) {
            // 设置菜单创建器。
            mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
            // 设置菜单Item点击监听。
            mRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        }

        mAdapter = createAdapter();
        if (mAdapter == null) throw new IllegalArgumentException("adapter is must not null");
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_delete_width);
            int height = resetDeleteItemHeight();
            // 设置菜单方向为竖型的。
            swipeRightMenu.setOrientation(SwipeMenu.HORIZONTAL);
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext().getApplicationContext())
                    .setBackgroundDrawable(R.drawable.selector_item_delete_red)
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();
            onSwipeItemClickListener(adapterPosition, menuPosition, direction);
        }
    };

    private BaseAdapter.OnRecyclerViewItemClickListener onItemClickListener =
            new BaseAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, Object data) {
                    onRecyclerItemClick(view, data);
                }
            };

    protected abstract void onRefreshData();

    protected abstract void onLoadMoreData();

    protected void onSwipeItemClickListener(int adapterPosition, int menuPosition, int direction) {
//TODO 供子类实现
    }

    protected View getEmptyView() {
        return mEmptyView;
    }

    protected void setEmptyText(String text) {
        mEmptyText.setText(text);
    }

    /**
     * item 点击事件
     *
     * @param view
     * @param data
     */
    protected abstract void onRecyclerItemClick(View view, Object data);

    @Override
    public void initData() {
        getData();
    }

    protected int getPageSize() {
        return Config.PAGE_SIZE;
    }

    /**
     * 统一显示设置数据源
     *
     * @param list
     */
    protected void setDataResult(List<T> list) {
        if (mRecyclerView == null) onShowFailed();
        else showListViewLayout(list);
    }

    /**
     * list数据操作 线程不安全
     *
     * @param list
     */
    private void showListViewLayout(List<T> list) {
        onShowContent();
        hideDialogLoading();

        if (mCurrentPage == 1) mAdapter.removeAll();

        if (list != null && list.size() > 0) {
            mAdapter.append(list);
            mCurrentPage += 1;
        } else {
            mAdapter.removeAll();
        }

        if (isRefresh()) mRecyclerView.refreshComplete();

        if (isLoadMore()) mRecyclerView.refreshComplete();

        mEmptyView.setVisibility(mAdapter.getItemCount() < 1 ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取ListRecycler
     *
     * @return
     */
    public XRecyclerView getListView() {
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
        mHeader = view;
    }


    @Override
    protected void onForceRefresh() {
        super.onForceRefresh();
        getData();
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

    /**
     * 是否侧滑删除
     *
     * @return
     */
    protected boolean isDeleteItem() {
        return false;
    }

    protected int resetDeleteItemHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    protected void getData() {

    }

    protected boolean isFirst = true;

    @Override
    protected void onErrorMsg(String status, String message) {
        super.onErrorMsg(status, message);
        hideDialogLoading();

        if (isFirst) {
            onShowFailed();
        } else {
            onShowContent();
        }
    }

    protected View getmEmptyView() {
        return mEmptyView;
    }

    @Override
    public void onClick(View v) {

    }
}
