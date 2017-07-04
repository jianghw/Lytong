package com.zantong.mobilecttx.weizhang.fragment;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.adapter.ViolationResultAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import cn.qqtheme.framework.util.ToastUtils;

public class ViolationResultFragment extends BaseListFragment<ViolationBean> {
    private static int TEMP_STATE;
    private static ViolationDTO params;

    public static ViolationResultFragment newInstance(int tag, ViolationDTO dto) {
        ViolationResultFragment trade = new ViolationResultFragment();
        TEMP_STATE = tag;
        params = dto;
        return trade;
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {
        mCurrentPage = 1;
        searchViolation();
    }

    /**
     * item点击事件
     *
     * @param view
     * @param data
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {

    }

    @Override
    public BaseAdapter<ViolationBean> createAdapter() {
        return new ViolationResultAdapter();
    }

    @Override
    public void initData() {
        searchViolation();
    }

    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    /**
     * 违章查询
     * TEMP_STATE 请求常数
     */
    private void searchViolation() {
        params.setProcessste(String.valueOf(TEMP_STATE));
        onShowLoading();

        if (!TextUtils.isEmpty(PublicData.getInstance().userID)) {
            params.setToken(RSAUtils.strByEncryption(
                    PublicData.getInstance().userID, true));
        } else if (!TextUtils.isEmpty(PublicData.getInstance().imei)) {
            params.setToken(RSAUtils.strByEncryption(
                    PublicData.getInstance().imei, true));
        } else {
            params.setToken(RSAUtils.strByEncryption(
                    PushServiceFactory.getCloudPushService().getDeviceId(), true));
        }

        UserApiClient.searchViolation(this.getActivity(), params, new CallBack<ViolationResultParent>() {
            @Override
            public void onSuccess(ViolationResultParent result) {
                SPUtils.getInstance().setViolation(params);

                if ("000000".equals(result.getSYS_HEAD().getReturnCode())) {
                    setDataResult(result.getRspInfo().getViolationInfo());
                } else {
                    ToastUtils.showShort(getContext(), result.getSYS_HEAD().getReturnMessage());
                    onShowFailed();
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                ToastUtils.showShort(getContext(), msg);
                onShowFailed();
            }
        });
    }

    @Override
    protected void onForceRefresh() {
        mCurrentPage = 1;
        searchViolation();
    }

}
