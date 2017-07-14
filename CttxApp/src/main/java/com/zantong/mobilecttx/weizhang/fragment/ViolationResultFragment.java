package com.zantong.mobilecttx.weizhang.fragment;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.adapter.ViolationResultAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import cn.qqtheme.framework.util.ToastUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
     * cip.cfc.v002.01
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

                    handleViolations(result.getRspInfo());
                } else {
                    ToastUtils.toastShort(result.getSYS_HEAD().getReturnMessage());
                    onShowFailed();
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                ToastUtils.toastShort(msg);
                onShowFailed();
            }
        });
    }

    public void handleViolations(ViolationResult violationResult) {
        Injection.provideRepository(getActivity().getApplicationContext())
                .handleViolations(violationResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResult>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {

                    }

                    @Override
                    public void doNext(BaseResult result) {

                    }
                });
    }

    @Override
    protected void onForceRefresh() {
        mCurrentPage = 1;
        searchViolation();
    }

}
