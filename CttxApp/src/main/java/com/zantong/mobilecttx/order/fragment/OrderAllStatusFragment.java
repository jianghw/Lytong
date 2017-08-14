package com.zantong.mobilecttx.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.order.activity.OrderDetailActivity;
import com.zantong.mobilecttx.order.activity.OrderParentActivity;
import com.zantong.mobilecttx.order.adapter.OrderStatusAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;

import java.util.List;

import cn.qqtheme.framework.global.GlobalConstant;

/**
 * 所有订单
 */
public class OrderAllStatusFragment extends BaseRecyclerListJxFragment<OrderListBean> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OrderParentActivity.RefreshListener mRefreshListener;

    private OrderStatusAdapter mCurAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static OrderAllStatusFragment newInstance() {
        return new OrderAllStatusFragment();
    }

    public static OrderAllStatusFragment newInstance(String param1, String param2) {
        OrderAllStatusFragment fragment = new OrderAllStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof OrderListBean) {
            OrderListBean bean = (OrderListBean) data;
            String orderId = bean.getOrderId();
            //前往 订单详情页面
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra(GlobalConstant.putExtra.web_order_id_extra, orderId);
            startActivity(intent);
        }
    }

    @Override
    protected void onRefreshData() {
        if (mRefreshListener != null) mRefreshListener.refreshListData(0);
    }

    @Override
    protected void initFragmentView(View view) {
        mCurAdapter.setItemClickListener(new OrderStatusAdapter.ItemClickListener() {
            @Override
            public void doClickCancel(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickCancel(bean);
            }

            @Override
            public void doClickPay(OrderListBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickPay(bean);
            }
        });
    }

    @Override
    protected void onFirstDataVisible() {
    }

    @Override
    protected void DestroyViewAndThing() {
    }

    public void setPayOrderListData(List<OrderListBean> data) {
        setSimpleDataResult(data);
    }

    public void setRefreshListener(OrderParentActivity.RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }
}
