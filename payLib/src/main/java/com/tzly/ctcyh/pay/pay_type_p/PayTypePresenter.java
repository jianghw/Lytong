package com.tzly.ctcyh.pay.pay_type_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.PayTypeResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.bean.response.PayWeixinResponse;
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
    public void onSubscribe() {
    }

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
        Subscription subscription = mRepository
                .getOrderInfo(mContractView.getExtraOrderId())
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
                        mContractView.responseError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayTypeResponse response) {
                        if (response != null && response.getResponseCode() == 2000) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
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
    public void getBankPayHtml(String orderId, String amount, int couponUserId) {
        Subscription subscription = mRepository
                .getBankPayHtml(orderId, amount, couponUserId)
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

    /**
     * 57.获取指定类型优惠券
     * 1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练 6 年检，7 保养，8 海外驾驶培训，9 换电瓶，998 第三方券，999 通用券
     */
    @Override
    public void getCouponByType() {
        Subscription subscription = mRepository
                .getCouponByType(mRepository.getRASUserID(), mContractView.getExtraType(), mContractView.getPayType())
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
    public void weChatPay(String extraOrderId, String amount, String phontIP) {
        Subscription subscription = mRepository
                .weChatPay(extraOrderId, amount, phontIP, mContractView.getCouponUserId())
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
                        if (response != null && response.getResponseCode() == 2000) {
                            mContractView.weChatPaySucceed(response);
                        } else {
                            mContractView.weChatPayError(response != null
                                    ? response.getResponseDesc() : "未知错误(5)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
