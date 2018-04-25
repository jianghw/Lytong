package com.tzly.ctcyh.router.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.touch.Closeable;
import com.jcodecraeer.xrecyclerview.touch.OnSwipeMenuItemClickListener;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenu;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenuCreator;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenuItem;
import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.SpaceItemDecoration;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * Fragment 刷新list基类
 */
public abstract class RecyclerListFragment<T> extends AbstractBaseFragment {

    protected XRecyclerView mCustomRecycler;
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
    protected int contentView() {
        return R.layout.fragment_jx_base_recycler_list;
    }

    @Override
    protected void bindContent(View contentView) {
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.lay_linear);
        View view = extraTopView() > 0
                ? LayoutInflater.from(getContext()).inflate(extraTopView(), linearLayout, true)
                : new View(getContext());
        bindExtraTopView(view);

        mCustomRecycler = (XRecyclerView) contentView.findViewById(R.id.rv_base);

        if (reSetOrientation() == 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mCustomRecycler.setLayoutManager(layoutManager);
        } else if (reSetOrientation() == 1) {
            //每行显示5个，水平显示
            GridItemManager layoutManager = new GridItemManager(getActivity(), 5);
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mCustomRecycler.setLayoutManager(layoutManager);
        }

        mCustomRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mCustomRecycler.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mCustomRecycler.setArrowImageView(R.mipmap.ic_refresh_loading);
        mCustomRecycler.setPullRefreshEnabled(isRefresh());
        mCustomRecycler.setLoadingMoreEnabled(isLoadMore());

        if (getCustomDecoration() > 0)
            mCustomRecycler.addItemDecoration(new SpaceItemDecoration(getCustomDecoration()));

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
                        if (isRefresh()) onRefreshData();
                        mCustomRecycler.refreshComplete();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                mCustomRecycler.postDelayed(new Runnable() {
                    public void run() {
                        if (mCustomRecycler == null) return;
                        mCustomRecycler.loadMoreComplete();
                        if (isLoadMore()) onLoadMoreData();
                        mCustomRecycler.refreshComplete();
                    }
                }, 3000);
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
        if (isNeedItemClick()) mAdapter.setOnItemClickListener(onItemClickListener);
        mCustomRecycler.setAdapter(mAdapter);

        initPresenter();
    }

    /**
     * 显示类型
     */
    protected int reSetOrientation() {
        return mOrientation;
    }

    protected int getCustomDecoration() {
        return 0;
    }

    /**
     * 额外的布局
     */
    protected int extraTopView() {
        return 0;
    }

    protected void bindExtraTopView(View view) {
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
            int width = getResources().getDimensionPixelSize(R.dimen.res_x_166);
            int height = resetDeleteItemHeight();
            // 设置菜单方向为竖型的。
            swipeRightMenu.setOrientation(SwipeMenu.HORIZONTAL);
            SwipeMenuItem deleteItem = new SwipeMenuItem(Utils.getContext())
                    .setBackgroundColor(getResources().getColor(R.color.res_color_red_ef))
                    .setImage(R.mipmap.ic_list_item_delete)
                    .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(getResources().getColor(R.color.res_color_white))
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

    /**
     * 是否有点击效果
     */
    protected boolean isNeedItemClick() {
        return true;
    }

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 第一次加载数据
     */
    @Override
    protected void clickRefreshData() {
        showStateLoading();
        loadingFirstData();
    }

    /**
     * 下拉数据设置
     */
    protected void onRefreshData() {
        loadingFirstData();
    }

    protected abstract void initPresenter();

    /**
     * 加载更多数据
     */
    protected void onLoadMoreData() {
        //TODO 子类想实现请重载
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
        if (mCustomRecycler == null) showStateError();
        else showListViewLayout(list);
    }

    /**
     * list数据操作 线程不安全
     *
     * @param list
     */
    private void showListViewLayout(List<T> list) {
        if (list == null || list.isEmpty()) {
            mAdapter.removeAll();
            showStateEmpty();
        } else {
            if (mCurrentPage == 1) mAdapter.cleanListData();
            mAdapter.append(list);
            mCurrentPage += 1;
        }

        if (isRefresh()) mCustomRecycler.refreshComplete();
        if (isLoadMore()) mCustomRecycler.refreshComplete();
    }

    protected XRecyclerView getCustomRecycler() {
        return mCustomRecycler;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mAdapter != null) mAdapter.cleanListData();
    }
}
