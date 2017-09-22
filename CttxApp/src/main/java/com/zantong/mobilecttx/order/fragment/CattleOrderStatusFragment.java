package com.zantong.mobilecttx.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.annual.base.bean.response.CattleOrderBean;
import com.tzly.annual.base.imple.CattleOrderItemListener;
import com.tzly.annual.base.imple.CattleOrderListener;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.browser.HtmlBrowserActivity;
import com.zantong.mobilecttx.order.activity.AnnualOrderDetailActivity;
import com.zantong.mobilecttx.order.activity.OrderDetailActivity;
import com.zantong.mobilecttx.order.adapter.CattleOrderStatusAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.List;

import com.tzly.annual.base.global.JxGlobal;

/**
 * 所有订单
 */
public class CattleOrderStatusFragment extends BaseRecyclerListJxFragment<CattleOrderBean> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CattleOrderStatusAdapter mCurAdapter;
    private CattleOrderListener mRefreshListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static CattleOrderStatusFragment newInstance() {
        return new CattleOrderStatusFragment();
    }

    public static CattleOrderStatusFragment newInstance(String param1, String param2) {
        CattleOrderStatusFragment fragment = new CattleOrderStatusFragment();
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
    public BaseAdapter<CattleOrderBean> createAdapter() {
        mCurAdapter = new CattleOrderStatusAdapter();
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
                        ? AnnualOrderDetailActivity.class : OrderDetailActivity.class);
                intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
                startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, bean.getGoodsName());
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, bean.getTargetUrl() + "?orderId=" + orderId);
                Act.getInstance().gotoLoginByIntent(getActivity(), HtmlBrowserActivity.class, intent);
            }
        }
    }

    @Override
    protected void onRefreshData() {
        if (mRefreshListener != null) mRefreshListener.refreshListData(0);
    }

    @Override
    protected void initFragmentView(View view) {
        mCurAdapter.setItemClickListener(new CattleOrderItemListener() {
            @Override
            public void doClickHave(CattleOrderBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickHave(bean);
            }

            @Override
            public void doClickAudit(CattleOrderBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickAudit(bean);
            }

            @Override
            public void doClickProcess(CattleOrderBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickProcess(bean);
            }

            @Override
            public void doClickCompleted(CattleOrderBean bean) {
                if (mRefreshListener != null) mRefreshListener.doClickCompleted(bean);
            }
        });
    }

    @Override
    protected void onFirstDataVisible() {
    }

    @Override
    protected void DestroyViewAndThing() {
    }

    public void setPayOrderListData(List<CattleOrderBean> data) {
        setSimpleDataResult(data);
    }

    public void setRefreshListener(CattleOrderListener refreshListener) {
        mRefreshListener = refreshListener;
    }
}
