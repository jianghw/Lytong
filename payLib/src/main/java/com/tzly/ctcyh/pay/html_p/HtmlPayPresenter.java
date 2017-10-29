package com.tzly.ctcyh.pay.html_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.data_m.BaseSubscriber;
import com.tzly.ctcyh.pay.data_m.PayDataManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
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
public class HtmlPayPresenter implements IHtmlPayContract.IHtmlPayPresenter {

    private final PayDataManager mRepository;
    private final IHtmlPayContract.IHtmlPayView mContractView;
    private final CompositeSubscription mSubscriptions;

    public HtmlPayPresenter(@NonNull PayDataManager payDataManager,
                            @NonNull IHtmlPayContract.IHtmlPayView view) {
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

    @Override
    public void orderDetail() {
        Subscription subscription = Observable.interval(2, 3000, TimeUnit.MILLISECONDS)
                .take(4)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Long>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                        mContractView.orderDetailCompleted();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.intervalError(
                                e.getMessage() != null ? e.getMessage() : "未知错误(interval)");
                    }

                    @Override
                    public void doNext(Long aLong) {
                        getOrderDetail();
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 9.获取订单详情
     */
    public void getOrderDetail() {
        Subscription subscription = mRepository.getOrderDetail(getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderDetailResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.getOrderDetailError(
                                e.getMessage() != null ? e.getMessage() : "未知错误(N9)");
                    }

                    @Override
                    public void doNext(OrderDetailResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.getOrderDetailSucceed(result);
                        } else {
                            mContractView.getOrderDetailError(result != null
                                    ? result.getResponseDesc() : "未知错误(N9)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String getOrderId() {
        return mContractView.getOrderId();
    }
}
