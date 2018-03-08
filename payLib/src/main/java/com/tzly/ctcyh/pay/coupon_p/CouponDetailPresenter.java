package com.tzly.ctcyh.pay.coupon_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.response.CodeDetailResponse;
import com.tzly.ctcyh.pay.response.CouponDetailResponse;
import com.tzly.ctcyh.router.api.BaseSubscriber;
import com.tzly.ctcyh.pay.data_m.PayDataManager;
import com.tzly.ctcyh.pay.global.PayGlobal;

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

public class CouponDetailPresenter implements ICouponDetailContract.ICouponDetailPresenter {

    private final PayDataManager mRepository;
    private final ICouponDetailContract.ICouponDetailView mContractView;
    private final CompositeSubscription mSubscriptions;

    public CouponDetailPresenter(@NonNull PayDataManager payDataManager,
                                 @NonNull ICouponDetailContract.ICouponDetailView view) {
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

    /**
     * 优惠券详情
     */
    @Override
    public void couponDetail() {
        Subscription subscription = mRepository
                .couponDetail(mContractView.couponId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponDetailResponse>() {
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
                    public void doNext(CouponDetailResponse response) {
                        if (response != null && response.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(优惠券详情)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getCodeDetail() {
        Subscription subscription = mRepository
                .getCodeDetail(mContractView.couponId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CodeDetailResponse>() {
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
                    public void doNext(CodeDetailResponse response) {
                        if (response != null && response.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(CodeDetail)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
