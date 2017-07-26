package com.zantong.mobilecttx.base.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.touch.*;
import com.zantong.mobilecttx.R;

import java.util.List;

/**
 * Fragment 刷新list基类
 */
public abstract class BaseRecyclerListJxFragment<T> extends BaseJxFragment {

    protected XRecyclerView mCustomRecycler;
    private android.widget.RelativeLayout layEmpty;
    private android.widget.ImageView imgEmpty;
    private android.widget.TextView tvEmpty;
    /**
     * 头布局
     */
    private View mHeaderView;

    protected int mOrientation;
    /**
     * 默认页数
     */
    protected int mCurrentPage = 1;

    /**
     * 数据源
     */
    protected BaseAdapter<T> mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    /**
     * 实现上级布局
     */
    @Override
    protected int getContentLayoutResID() {
        return R.layout.fragment_base_recycler_list;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        initParentView(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(mOrientation == 0 ?
                LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);

        mCustomRecycler.setLayoutManager(layoutManager);
        mCustomRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mCustomRecycler.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mCustomRecycler.setArrowImageView(R.mipmap.loading);
        mCustomRecycler.setPullRefreshEnabled(isRefresh());
        mCustomRecycler.setLoadingMoreEnabled(isLoadMore());

        initRecyclerHeader(customViewHeader());

        if (getRecyclerHeader() != null) {
            mCustomRecycler.addHeaderView(getRecyclerHeader());
        }

        mCustomRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mCustomRecycler.postDelayed(new Runnable() {
                    public void run() {
                        if (mCustomRecycler == null) return;
                        mCurrentPage = 1;
                        onRefreshData();
                        mCustomRecycler.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                mCustomRecycler.postDelayed(new Runnable() {
                    public void run() {
                        if (mCustomRecycler == null) return;
                        mCustomRecycler.loadMoreComplete();
                        onLoadMoreData();
                        mCustomRecycler.refreshComplete();
                    }
                }, 1500);
            }
        });

        if (isDeleteItem()) {
            // 设置菜单创建器。
            mCustomRecycler.setSwipeMenuCreator(swipeMenuCreator);
            // 设置菜单Item点击监听。
            mCustomRecycler.setSwipeMenuItemClickListener(menuItemClickListener);
        }

        mAdapter = createAdapter();
        if (mAdapter == null) throw new IllegalArgumentException("adapter is must not null");
        mAdapter.setOnItemClickListener(onItemClickListener);
        mCustomRecycler.setAdapter(mAdapter);

        initFragmentView(view);
    }

    protected View customViewHeader() {
        return null;
    }

    /**
     * 获取RecyclerHeader
     */
    protected void initRecyclerHeader(View view) {
        mHeaderView = view;
    }

    protected View getRecyclerHeader() {
        return mHeaderView;
    }

    private void initParentView(View view) {
        mCustomRecycler = (XRecyclerView) view.findViewById(R.id.custom_recycler);
        layEmpty = (RelativeLayout) view.findViewById(R.id.lay_empty);
        imgEmpty = (ImageView) view.findViewById(R.id.img_empty);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty);
    }

    /**
     * 是否侧滑删除
     */
    protected boolean isDeleteItem() {
        return false;
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

    protected int resetDeleteItemHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

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

    /**
     * 数据适配器
     *
     * @return
     */
    public abstract BaseAdapter<T> createAdapter();

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

    protected void onSwipeItemClickListener(int adapterPosition, int menuPosition, int direction) {
        //TODO 供子类实现
    }

    /**
     * item 点击事件
     */
    protected abstract void onRecyclerItemClick(View view, Object data);

    @Override
    protected void onFirstUserVisible() {
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
     * 布局控件初始化
     */
    protected abstract void initFragmentView(View view);

    /**
     * 第一次加载数据
     */
    protected abstract void onFirstDataVisible();

    @Override
    protected void onForceRefresh() {
        mCurrentPage = 1;
        onFirstDataVisible();
    }

    protected void setSimpleDataResult(List<T> list) {
        mCurrentPage = 1;
        setDataResult(list);
    }

    /**
     * 统一显示设置数据源
     *
     * @param list
     */
    protected void setDataResult(List<T> list) {
        if (mCustomRecycler == null) showFailedView();
        else showListViewLayout(list);
    }

    /**
     * list数据操作 线程不安全
     *
     * @param list
     */
    private void showListViewLayout(List<T> list) {
        showSucceedView();
        hideDialogLoading();

        if (mCurrentPage == 1) mAdapter.removeAllOnly();

        if (list != null && list.size() > 0) {
            mAdapter.append(list);
            mCurrentPage += 1;
        } else {
            mAdapter.removeAll();
        }

        if (isRefresh()) mCustomRecycler.refreshComplete();

        if (isLoadMore()) mCustomRecycler.refreshComplete();

        layEmpty.setVisibility(mAdapter.getItemCount() < 1 ? View.VISIBLE : View.GONE);
    }

    protected XRecyclerView getCustomRecycler() {
        return mCustomRecycler;
    }
}
