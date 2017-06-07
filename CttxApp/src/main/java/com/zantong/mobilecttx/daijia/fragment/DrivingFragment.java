package com.zantong.mobilecttx.daijia.fragment;

import android.view.View;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.daijia.adapter.DrivingOrderAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderListBean;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderListResult;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderListDTO;
import com.zantong.mobilecttx.eventbus.DrivingCancelEvent;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.user.activity.DODetailActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by zhoujie on 2017/2/17.
 */

public class DrivingFragment extends BaseListFragment<DaiJiaOrderListBean> {

    @Override
    protected void getData() {
        super.getData();
        DaiJiaOrderListDTO dto = new DaiJiaOrderListDTO();
        dto.setUsrId(RSAUtils.strByEncryption(this.getActivity(),PublicData.getInstance().userID,true));
        onShowLoading();
        CarApiClient.getDaiJiaOrderList(this.getActivity(), dto, new CallBack<DaiJiaOrderListResult>() {
            @Override
            public void onSuccess(DaiJiaOrderListResult result) {
                onShowContent();
                if (result.getResponseCode() == 2000){
                    setDataResult(result.getData());
                }
            }
        });

    }

    @Override
    protected void onLoadMoreData() {
    }

    @Override
    protected void onRefreshData() {
        getData();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        DaiJiaOrderListBean item = (DaiJiaOrderListBean) data;
        Act.getInstance().gotoIntent(this.getActivity(), DODetailActivity.class,item.getOrderId());
    }

    @Override
    public BaseAdapter<DaiJiaOrderListBean> createAdapter() {
        return new DrivingOrderAdapter();
    }

    @Override
    protected boolean isRefresh() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(DrivingCancelEvent event) {
        getData();
    }


}
