package com.zantong.mobile.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.annual.base.global.JxGlobal;
import com.zantong.mobile.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobile.browser.BrowserHtmlActivity;
import com.zantong.mobile.order.activity.AnnualDetailActivity;
import com.zantong.mobile.order.activity.OrderDetailActivity;
import com.zantong.mobile.order.activity.MyOrderActivity;
import com.zantong.mobile.order.adapter.MyOrderStatusAdapter;
import com.tzly.annual.base.bean.response.OrderListBean;
import com.zantong.mobile.utils.jumptools.Act;

import java.util.List;

/**
 * 我的 所有订单
 */
public class MyOrderStatusFragment extends BaseRecyclerListJxFragment<OrderListBean> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MyOrderActivity.RefreshListener mRefreshListener;

    private MyOrderStatusAdapter mCurAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static MyOrderStatusFragment newInstance() {
        return new MyOrderStatusFragment();
    }

    public static MyOrderStatusFragment newInstance(String param1, String param2) {
        MyOrderStatusFragment fragment = new MyOrderStatusFragment();
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
        mCurAdapter = new MyOrderStatusAdapter();
        return mCurAdapter;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof OrderListBean) {
            OrderListBean bean = (OrderListBean) data;
            String orderId = bean.getOrderId();
            String targetType = bean.getTargetType();
            if (targetType.equals("0")) {//前往 订单详情页面
                Intent intent = new Intent(getActivity(), bean.getType() == 6
                        ? AnnualDetailActivity.class : OrderDetailActivity.class);
                intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
                startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, bean.getGoodsName());
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, bean.getTargetUrl() + "?orderId=" + orderId);
                Act.getInstance().gotoLoginByIntent(getActivity(), BrowserHtmlActivity.class, intent);
            }
        }
    }

    @Override
    protected void onRefreshData() {
        if (mRefreshListener != null) mRefreshListener.refreshListData(0);
    }

    @Override
    protected void initFragmentView(View view) {
        mCurAdapter.setItemClickListener(new MyOrderStatusAdapter.ItemClickListener() {
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
    protected void onFirstDataVisible() {}

    @Override
    protected void DestroyViewAndThing() {}

    public void setPayOrderListData(List<OrderListBean> data) {
        setSimpleDataResult(data);
    }

    public void setRefreshListener(MyOrderActivity.RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }
}
