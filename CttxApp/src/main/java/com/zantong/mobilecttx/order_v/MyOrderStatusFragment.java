package com.zantong.mobilecttx.order_v;

import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.zantong.mobilecttx.order.adapter.OrderStatusAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.router.MainRouter;

import java.util.List;

/**
 * 所有订单
 */
public class MyOrderStatusFragment extends RecyclerListFragment<OrderListBean> {

    private MyOrderActivity.RefreshListener mRefreshListener;

    private OrderStatusAdapter mCurAdapter;

    public static MyOrderStatusFragment newInstance() {
        return new MyOrderStatusFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * adapter
     */
    @Override
    public BaseAdapter<OrderListBean> createAdapter() {
        mCurAdapter = new OrderStatusAdapter();
        return mCurAdapter;
    }

    @Override
    protected void initPresenter() {
        mCurAdapter.setItemClickListener(new OrderStatusAdapter.ItemClickListener() {
            @Override
            public void doClickCancel(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickCancel(bean);
            }

            @Override
            public void doClickPay(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickPay(bean);
            }

            @Override
            public void doClickDriving(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickDriving(bean);
            }

            @Override
            public void doClickCourier(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickCourier(bean);
            }

            @Override
            public void doClickSubscribe(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickSubscribe(bean);
            }

            @Override
            public void doClickUnSubscribe(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickUnSubscribe(bean);
            }
        });
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof OrderListBean) {
            OrderListBean bean = (OrderListBean) data;
            String orderId = bean.getOrderId();
            String targetType = bean.getTargetType();
            if (targetType.equals("0")) {//前往 订单详情页面
                if (bean.getType() == 6)
                    MainRouter.gotoAnnualDetailActivity(getActivity(), orderId);
                else
                    MainRouter.gotoOrderDetailActivity(getActivity(), orderId);
            } else {
                MainRouter.gotoWebHtmlActivity(getActivity(),
                        bean.getGoodsName(), bean.getTargetUrl() + "?orderId=" + orderId);
            }
        }
    }

    /**
     * 下拉
     */
    @Override
    protected void loadingFirstData() {
        this.mCurrentPage = 1;
        if (mRefreshListener != null) mRefreshListener.refreshListData(mCurrentPage);
    }


    protected void setCustomPage(int page) {
        this.mCurrentPage = page;
    }

    /**
     * 上拉
     */
    protected boolean isLoadMore() {
        return true;
    }

    @Override
    protected void onLoadMoreData() {
        if (mRefreshListener != null) mRefreshListener.refreshListData(mCurrentPage);
    }

    public synchronized void setPayOrderListData(List<OrderListBean> data) {
        if (this.mCurrentPage > 1 && (data == null || data.isEmpty())) {
        } else {
            setDataResult(data);
        }
    }

    public void setRefreshListener(MyOrderActivity.RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }
}
