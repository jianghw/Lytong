package com.zantong.mobilecttx.violation_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.java.request.RequestDTO;
import com.tzly.ctcyh.java.request.RequestHeadDTO;
import com.tzly.ctcyh.java.request.violation.ViolationDetailsDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class IViolationDetailsPresenter
        implements IViolationDetailsContract.IViolationDetailsPresenter {

    private final RepositoryManager mRepository;
    private final IViolationDetailsContract.IViolationDetailsView mContractView;
    private final CompositeSubscription mSubscriptions;

    public IViolationDetailsPresenter(@NonNull RepositoryManager payDataManager,
                                      @NonNull IViolationDetailsContract.IViolationDetailsView view) {
        mRepository = payDataManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {}

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 违章查询详情
     */
    @Override
    public void violationDetails_v003() {
        Subscription subscription = mRepository.violationDetails_v003(initV003DTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ViolationDetailsBean>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.responseError(e.getMessage());
                    }

                    @Override
                    public void doNext(ViolationDetailsBean response) {
                        if (response != null && response.getSYS_HEAD().getReturnCode().equals("000000")) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseCustomError(response);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 构建请求bean
     */
    @Override
    public String initV003DTO() {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.initServiceCodeDTO("cip.cfc.v003.01");
        dto.setSYS_HEAD(requestHeadDTO);

        ViolationDetailsDTO bean = new ViolationDetailsDTO();
        String token = mRepository.getRASByStr(MainRouter.getPhoneDeviceId());
        bean.setToken(token);
        bean.setViolationnum(mContractView.getViolationNum());

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }
}
