package com.tzly.ctcyh.pay.coupon_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.pay.bean.response.CouponStatusResponse;
import com.tzly.ctcyh.pay.data_m.BaseSubscriber;
import com.tzly.ctcyh.pay.data_m.PayDataManager;

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

public class CouponStatusPresenter implements ICouponStatusContract.ICouponStatusPresenter {

    private final PayDataManager mRepository;
    private final ICouponStatusContract.ICouponStatusView mContractView;
    private final CompositeSubscription mSubscriptions;

    public CouponStatusPresenter(@NonNull PayDataManager payDataManager,
                                 @NonNull ICouponStatusContract.ICouponStatusView view) {
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
     * 获取优惠券列表
     */
    @Override
    public void couponUserList() {
        Subscription subscription = mRepository.couponUserList(
                mRepository.getRASUserID(), mContractView.getCouponStatus())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponStatusResponse>() {
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
                    public void doNext(CouponStatusResponse response) {
                        if (response != null && response.getResponseCode() == 2000) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(优惠券列表)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
    /**
     * 2.4.27删除用户优惠券
     */
    @Override
    public void delUsrCoupon(String couponId, final int position) {
        Subscription subscription = mRepository.delUsrCoupon(
                mRepository.getRASUserID(), couponId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.delUsrCouponError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse response) {
                        if (response != null && response.getResponseCode() == 2000) {
                            mContractView.delUsrCouponSucceed(response,position);
                        } else {
                            mContractView.delUsrCouponError(response != null
                                    ? response.getResponseDesc() : "未知错误(2.4.27)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
