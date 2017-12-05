package com.zantong.mobilecttx.violation_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.user.dto.LogoutDTO;

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

public class IViolationPayPresenter
        implements IViolationPayContract.IViolationPayPresenter {

    private final RepositoryManager mRepository;
    private final IViolationPayContract.IViolationPayView mContractView;
    private final CompositeSubscription mSubscriptions;

    public IViolationPayPresenter(@NonNull RepositoryManager payDataManager,
                                  @NonNull IViolationPayContract.IViolationPayView view) {
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
     * cip.cfc.c002.01
     */
    @Override
    public void getPayCars() {
        Subscription subscription = mRepository.payCars_c002(initHomeDataDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayCarResult>() {
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
                    public void doNext(PayCarResult response) {
                        if (response != null
                                && response.getSYS_HEAD().getReturnCode().equals("000000")) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getSYS_HEAD().getReturnMessage()
                                    : "未知错误(cip.cfc.c002.01)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initHomeDataDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initServiceCodeDTO("cip.cfc.c002.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LogoutDTO params = new LogoutDTO();
        params.setUsrid(mRepository.getUserID());

        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }
}
