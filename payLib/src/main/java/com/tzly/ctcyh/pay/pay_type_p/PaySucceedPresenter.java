package com.tzly.ctcyh.pay.pay_type_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.coupon.CouponInfoResponse;
import com.tzly.ctcyh.pay.data_m.PayDataManager;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.api.BaseSubscriber;

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

public class PaySucceedPresenter implements IPaySucceedContract.IPaySucceedPresenter {

    private final PayDataManager mRepository;
    private final IPaySucceedContract.IPaySucceedView mContractView;
    private final CompositeSubscription mSubscriptions;

    public PaySucceedPresenter(@NonNull PayDataManager payDataManager,
                               @NonNull IPaySucceedContract.IPaySucceedView view) {
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
    public void getCouponInfo() {
        Subscription subscription = mRepository
                .getCouponInfo(mContractView.getGoodsType())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponInfoResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.couponInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(CouponInfoResponse response) {
                        if (response != null && response.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.couponInfoSucceed(response);
                        } else {
                            mContractView.couponInfoError(response != null
                                    ? response.getResponseDesc() : "未知错误(couponInfo)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
