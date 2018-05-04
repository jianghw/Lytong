package com.zantong.mobilecttx.reservation_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.reservation.ReservationResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.global.MainGlobal;

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

public class ReservationPresenter implements IReservationContract.IReservationPresenter {

    private final RepositoryManager mRepository;
    private final IReservationContract.IReservationView mContractView;
    private final CompositeSubscription mSubscriptions;

    public ReservationPresenter(@NonNull RepositoryManager payDataManager,
                                @NonNull IReservationContract.IReservationView view) {
        mRepository = payDataManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    @Override
    public void getBespeakList() {
        Subscription subscription = mRepository.getBespeakList(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ReservationResponse>() {
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
                    public void doNext(ReservationResponse responseBean) {
                        if (responseBean != null &&
                                responseBean.getResponseCode() == MainGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(responseBean);
                        } else {
                            mContractView.responseError(responseBean != null
                                    ? responseBean.getResponseDesc()
                                    : "未知错误(bespeakList)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
