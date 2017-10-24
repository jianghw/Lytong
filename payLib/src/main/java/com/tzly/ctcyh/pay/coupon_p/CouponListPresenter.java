package com.tzly.ctcyh.pay.coupon_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.data_m.BaseSubscriber;
import com.tzly.ctcyh.pay.data_m.RepositoryManager;

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

public class CouponListPresenter implements ICouponListContract.ICouponListPresenter {

    private final RepositoryManager mRepository;
    private final ICouponListContract.ICouponListView mContractView;
    private final CompositeSubscription mSubscriptions;

    public CouponListPresenter(@NonNull RepositoryManager repositoryManager,
                               @NonNull ICouponListContract.ICouponListView view) {
        mRepository = repositoryManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {}

    @Override
    public void unSubscribe() {
        mContractView.dismissDialog();
        mSubscriptions.clear();
    }

    /**
     * 获取指定类型优惠券
     * 优惠券业务：1 加油充值；2 代驾；3 洗车
     */
    @Override
    public void getCouponByType() {
        Subscription subscription =
                mRepository.getCouponByType(getUserId(), mContractView.getExtraType())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mContractView.loadingDialog();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<CouponResponse>() {
                            @Override
                            public void doCompleted() {
                                mContractView.dismissDialog();
                            }

                            @Override
                            public void doError(Throwable e) {
                                mContractView.dismissDialog();
                                mContractView.couponByTypeError(e.getMessage());
                            }

                            @Override
                            public void doNext(CouponResponse response) {
                                if (response != null && response.getResponseCode() == 2000) {
                                    mContractView.couponByTypeSucceed(response);
                                } else {
                                    mContractView.couponByTypeError(response != null
                                            ? response.getResponseDesc() : "未知错误(57)");
                                }
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public String getUserId() {
        return "";
        //        return mRepository.getRASUserID();
    }
}
