package com.zantong.mobilecttx.order_v;

import android.app.Activity;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.zantong.mobilecttx.order.adapter.OrderStatusAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.router.MainRouter;

import java.util.List;

/**
 * 所有订单
 */
public class OrderListsFragment extends RecyclerListFragment<OrderListBean> {

    /**
     * 接口通讯
     */
    private IOrderListsItem i_order_list;

    /**
     * 上拉
     */
    protected boolean isLoadMore() {
        return true;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    public static OrderListsFragment newInstance() {
        return new OrderListsFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity != null && activity instanceof IOrderListsItem) {
            this.i_order_list = (IOrderListsItem) activity;
        }
    }

    /**
     * adapter
     */
    @Override
    public BaseAdapter<OrderListBean> createAdapter() {
        return new OrderStatusAdapter(this.i_order_list);
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data != null && data instanceof OrderListBean) {
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

    @Override
    protected void loadingFirstData() {
        this.mCurrentPage = 1;
    }

    /**
     * 下拉
     */
    @Override
    protected void onRefreshData() {
        if (this.i_order_list != null) this.i_order_list.refreshListData();
    }

    @Override
    protected void onLoadMoreData() {
        if (this.i_order_list != null) this.i_order_list.loadMoreData();
    }

    public void setPayOrderListData(List<OrderListBean> data) {
        showStateContent();

        if (this.mCurrentPage > 1 && (data == null || data.isEmpty())) {
        } else {
            setDataResult(data);
        }
    }

}
