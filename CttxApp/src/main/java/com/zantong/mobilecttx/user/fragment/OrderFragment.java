package com.zantong.mobilecttx.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.user.adapter.OrderAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.user.bean.OrderItem;
import com.zantong.mobilecttx.user.bean.OrderResult;
import com.zantong.mobilecttx.user.dto.OrderDTO;
import com.zantong.mobilecttx.user.activity.OrderDetailActivity;

/**
 * 我的订单
 * @author Sandy
 * create at 16/6/2 下午2:16
 */
public class OrderFragment extends BaseListFragment<OrderItem>{



    @Override
    protected void onLoadMoreData() {
        getData();
    }

    @Override
    protected void onRefreshData() {
        getData();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        OrderItem item = (OrderItem)data;
        Intent intent = new Intent(this.getActivity(), OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderDetailActivity.ORDER_ITEM, item);
        intent.putExtras(bundle);
        this.getActivity().startActivity(intent);
    }

    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    public BaseAdapter<OrderItem> createAdapter() {
        return new OrderAdapter();
    }

    @Override
    public void initData() {
        setEmptyText("无订单或查询结果为空");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst){
            showDialogLoading();
        }else{
            onShowLoading();
        }
        mCurrentPage = 1;
        getData();

        isFirst = false;
    }

    @Override
    protected void getData() {
        super.getData();
        OrderDTO dto = new OrderDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        dto.setCurrpage(mCurrentPage);
        dto.setPageflag(4);
        dto.setPagenum(Config.PAGE_SIZE);
        UserApiClient.getOrderList(this.getActivity(), dto, new CallBack<OrderResult>() {
            @Override
            public void onSuccess(OrderResult result) {
                if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                    setDataResult(result.getRspInfo().getOrderInfo());
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
            }
        });
    }

}
