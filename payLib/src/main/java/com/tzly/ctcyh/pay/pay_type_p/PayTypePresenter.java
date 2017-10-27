package com.tzly.ctcyh.pay.pay_type_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.bean.response.PayTypeResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
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

public class PayTypePresenter implements IPayTypeContract.IPayTypePresenter {

    private final PayDataManager mRepository;
    private final IPayTypeContract.IPayTypeView mContractView;
    private final CompositeSubscription mSubscriptions;

    public PayTypePresenter(@NonNull PayDataManager payDataManager,
                            @NonNull IPayTypeContract.IPayTypeView view) {
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
     * 31.创建订单后获取订单信息
     */
    @Override
    public void getOrderInfo() {
        Subscription subscription =
                mRepository.getOrderInfo(mContractView.getExtraOrderId())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mContractView.showLoading();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<PayTypeResponse>() {
                            @Override
                            public void doCompleted() {
                                mContractView.dismissLoading();
                            }

                            @Override
                            public void doError(Throwable e) {
                                mContractView.dismissLoading();
                                mContractView.getOrderInfoError(e.getMessage());
                            }

                            @Override
                            public void doNext(PayTypeResponse response) {
                                if (response != null && response.getResponseCode() == 2000) {
                                    mContractView.getOrderInfoSucceed(response);
                                } else {
                                    mContractView.getOrderInfoError(response != null
                                            ? response.getResponseDesc() : "未知错误(57)");
                                }
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 5.获取工行支付页面
     */
    @Override
    public void getBankPayHtml(String amount, int couponUserId) {
        Subscription subscription =
                mRepository.getBankPayHtml(mContractView.getExtraOrderId(), amount, couponUserId)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mContractView.showLoading();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<PayUrlResponse>() {
                            @Override
                            public void doCompleted() {
                                mContractView.dismissLoading();
                            }

                            @Override
                            public void doError(Throwable e) {
                                mContractView.dismissLoading();
                                mContractView.getBankPayHtmlError(e.getMessage());
                            }

                            @Override
                            public void doNext(PayUrlResponse response) {
                                if (response != null && response.getResponseCode() == 2000) {
                                    mContractView.getBankPayHtmlSucceed(response);
                                } else {
                                    mContractView.getBankPayHtmlError(response != null
                                            ? response.getResponseDesc() : "未知错误(5)");
                                }
                            }
                        });
        mSubscriptions.add(subscription);
    }
}
