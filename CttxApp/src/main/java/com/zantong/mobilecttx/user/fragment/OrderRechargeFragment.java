package com.zantong.mobilecttx.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.chongzhi.adapter.RechargeOrderAdapter;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderBean;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeOrderDTO;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.OrderCancelEvent;
import com.zantong.mobilecttx.user.activity.OrderRechargeDetailActivity;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.qqtheme.framework.util.log.LogUtils;

public class OrderRechargeFragment extends BaseListFragment<RechargeOrderBean> {

    private static final String POSITION = "position";
    public static int mStatus;

    public static OrderRechargeFragment newInstance(int status) {
        OrderRechargeFragment f = new OrderRechargeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, status);
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected void onLoadMoreData() {
    }

    @Override
    protected void onRefreshData() {
        showDialogLoading();
        getData();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        RechargeOrderBean item = (RechargeOrderBean)data;
        Intent intent = new Intent(this.getActivity(), OrderRechargeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderRechargeDetailActivity.RECHARGE_ORDER_ITEM, item);
        intent.putExtras(bundle);
        this.getActivity().startActivity(intent);
    }

    @Override
    public BaseAdapter<RechargeOrderBean> createAdapter() {
        return new RechargeOrderAdapter();
    }

    protected void getData() {
        Bundle bundle = getArguments();
        mStatus = bundle.getInt(POSITION, -1);
        RechargeOrderDTO dto = new RechargeOrderDTO();
        dto.setUserId(RSAUtils.strByEncryptionLiYing(this.getActivity(), PublicData.getInstance().userID, true));
        LogUtils.i("------->"+mStatus);
        dto.setOrderStatus(mStatus);
        dto.setCurrentIndex(mCurrentPage);
        CarApiClient.queryOrders(this.getActivity(), dto, new CallBack<RechargeOrderResult>() {
            @Override
            public void onSuccess(RechargeOrderResult result) {
                hideDialogLoading();
                if (result.getResponseCode() == 2000) {
                    setDataResult(result.getData());
                }
            }
        });
    }


    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(OrderCancelEvent event) {
        getData();
    }

}
