package com.tzly.ctcyh.pay.html_p;

import android.location.Location;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.bean.response.PayWeixinResponse;
import com.tzly.ctcyh.pay.data_m.PayDataManager;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.api.BaseSubscriber;

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
public class WebHtmlPresenter implements IWebHtmlContract.IWebHtmlPresenter {

    private final PayDataManager mRepository;
    private final IWebHtmlContract.IWebHtmlView mContractView;
    private final CompositeSubscription mSubscriptions;

    public WebHtmlPresenter(@NonNull PayDataManager payDataManager,
                            @NonNull IWebHtmlContract.IWebHtmlView view) {
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
    public void intervalOrderDetail() {
        Subscription subscription = Observable.interval(2, 3000, TimeUnit.MILLISECONDS)
                .take(3)
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
                        orderDetail();
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 9.获取订单详情
     */
    @Override
    public void orderDetail() {
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
                        mContractView.orderDetailError(
                                e.getMessage() != null ? e.getMessage() : "未知错误(N9)");
                    }

                    @Override
                    public void doNext(OrderDetailResponse result) {
                        if (result != null && result.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.orderDetailSucceed(result);
                        } else {
                            mContractView.orderDetailError(result != null
                                    ? result.getResponseDesc() : "未知错误(orderDetail)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String getOrderId() {
        return mContractView.getOrderId();
    }

    /**
     * 5.获取工行支付页面
     */
    @Override
    public void bankPayHtml(final String orderId, String amount, String coupon) {
        Subscription subscription = mRepository
                .getBankPayHtml(orderId, amount, Integer.valueOf(coupon))
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
                        mContractView.bankPayHtmlError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayUrlResponse response) {
                        if (response != null && response.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.bankPayHtmlSucceed(response,orderId);
                        } else {
                            mContractView.bankPayHtmlError(response != null
                                    ? response.getResponseDesc() : "未知错误(bankPayHtml)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 微信支付页面
     */
    @Override
    public void weChatPay(String couponUserId, final String orderId, String amount) {
        Subscription subscription = mRepository
                .weChatPay(orderId, amount, TextUtils.isEmpty(couponUserId) ? 0 : Integer.valueOf(couponUserId))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayWeixinResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.weChatPayError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayWeixinResponse response) {
                        if (response != null && response.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.weChatPaySucceed(response, orderId);
                        } else {
                            mContractView.weChatPayError(response != null
                                    ? response.getResponseDesc() : "未知错误(weChatPay)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
