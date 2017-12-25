package com.tzly.ctcyh.pay.html_p;

import android.location.Location;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.data_m.BaseSubscriber;
import com.tzly.ctcyh.pay.data_m.PayDataManager;
import com.tzly.ctcyh.pay.global.PayGlobal;

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
    public void onSubscribe() {
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    @Override
    public void orderDetail() {
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
                        if (result != null && result.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.getOrderDetailSucceed(result);
                        } else {
                            mContractView.getOrderDetailError(result != null
                                    ? result.getResponseDesc() : "未知错误(N9)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 5.获取工行支付页面
     */
    @Override
    public void getBankPayHtml(String orderId, String amount, String coupon) {
        Subscription subscription =
                mRepository.getBankPayHtml(orderId, amount, Integer.valueOf(coupon))
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

    @Override
    public String getOrderId() {
        return mContractView.getOrderId();
    }

    @JavascriptInterface
    public void ToastMsg(String msg) {
        mContractView.ToastMsg(msg);
    }

    @JavascriptInterface
    public boolean isLogin() {
        return mContractView.isLogin();
    }

    @JavascriptInterface
    public void gotoLogin() {
        mContractView.gotoLogin();
    }

    @JavascriptInterface
    public Location getLocaltion() {
        return mContractView.getLocaltion();
    }

    //绑畅通卡
    @JavascriptInterface
    public void bindCard() {
        mContractView.bindCard();
    }

    //加油充值
    @JavascriptInterface
    public void addOil() {
        //        mJSContext.startActivity(new Intent(mJSContext, RechargeActivity.class));
        mContractView.addOil();
    }

    //代驾
    @JavascriptInterface
    public void chaser() {
        //        mJSContext.startActivity(new Intent(mJSContext, DrivingActivity.class));
        mContractView.chaser();
    }

    //分享领积分
    @JavascriptInterface
    public void shareActivity() {
    }

    //获取用户ID
    @JavascriptInterface
    public String getUserId() {
        return mContractView.getUserId();
    }

    //获取绑卡状态 0已绑卡  1未绑卡
    @JavascriptInterface
    public int getBindCardStatus() {
        return mContractView.getBindCardStatus();
    }

    //查询违章
    @JavascriptInterface
    public void queryViolations() {
        mContractView.queryViolations();
    }

    //获取用户ID
    @JavascriptInterface
    public String getEncreptUserId() {
        return mContractView.getEncreptUserId();
    }

    //跳转到积分规则页面
    @JavascriptInterface
    public void popAttention() {
        //        mJSContext.startActivity(new Intent(mJSContext, HundredRuleActivity.class));
        mContractView.popAttention();
    }

    //去年检地图地址
    @JavascriptInterface
    public void goNianjianMap() {
        //        Act.getInstance().gotoIntentLogin(mJSContext, BaiduMapParentActivity.class);
        mContractView.goNianjianMap();
    }

    //去往违章列表页面
    @JavascriptInterface
    public void searchViolationList(String carnum, String enginenum, String carnumtype) {
        mContractView.searchViolationList(carnum, enginenum, carnumtype);
    }

    //js调摄像机
    @JavascriptInterface
    public void callCamera() {
        mContractView.callCamera();
    }

    //跳转支付页面
    @JavascriptInterface
    public void payMOTOrder(String coupon, String orderId, String amount) {
        mContractView.payMOTOrder(coupon, orderId, amount);
    }
}
