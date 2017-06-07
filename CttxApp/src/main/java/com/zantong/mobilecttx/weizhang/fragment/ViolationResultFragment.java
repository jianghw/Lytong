package com.zantong.mobilecttx.weizhang.fragment;

import android.text.TextUtils;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.adapter.ViolationResultAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

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

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        ViolationBean msg = (ViolationBean)data;
//        ToastUtils.showShort(this.getActivity(),msg.getMsg_title());
    }

    @Override
    public BaseAdapter<ViolationBean> createAdapter() {
        return new ViolationResultAdapter();
    }

    @Override
    public void initData() {
        super.initData();
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
     */
    private void searchViolation(){
        params.setProcessste(String.valueOf(TEMP_STATE));
        onShowLoading();
        if (TextUtils.isEmpty(PublicData.getInstance().imei)) {
            params.setToken(RSAUtils.strByEncryption(this.getActivity(),"00000000",true));
        } else {
            params.setToken(RSAUtils.strByEncryption(this.getActivity(),PublicData.getInstance().imei,true));
        }
        UserApiClient.searchViolation(this.getActivity(), params, new CallBack<ViolationResultParent>() {
            @Override
            public void onSuccess(ViolationResultParent result) {
                SPUtils.getInstance(ViolationResultFragment.this.getActivity()).setViolation(params);
                if ("000000".equals(result.getSYS_HEAD().getReturnCode())){
                    setDataResult(result.getRspInfo().getViolationInfo());
                }
            }

        });
    }

    @Override
    protected void onForceRefresh() {
        super.onForceRefresh();
        searchViolation();
    }

}
