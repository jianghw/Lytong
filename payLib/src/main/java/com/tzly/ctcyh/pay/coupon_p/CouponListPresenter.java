package com.tzly.ctcyh.pay.coupon_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.response.CouponResponse;
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

public class CouponListPresenter implements ICouponListContract.ICouponListPresenter {

    private final PayDataManager mRepository;
    private final ICouponListContract.ICouponListView mContractView;
    private final CompositeSubscription mSubscriptions;

    public CouponListPresenter(@NonNull PayDataManager payDataManager,
                               @NonNull ICouponListContract.ICouponListView view) {
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
     * 57.获取指定类型优惠券
     * 1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练 6 年检，7 保养，8 海外驾驶培训，9 换电瓶，998 第三方券，999 通用券
     */
    @Override
    public void getCouponByType() {
        Subscription subscription = mRepository
                .getCouponByType(getUserId(), mContractView.getExtraType(), mContractView.getPayType())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponResponse>() {
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
                    public void doNext(CouponResponse response) {
                        if (response != null && response.getResponseCode() == PayGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(getCouponByType)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String getUserId() {
        return mRepository.getRASUserID();
    }
}