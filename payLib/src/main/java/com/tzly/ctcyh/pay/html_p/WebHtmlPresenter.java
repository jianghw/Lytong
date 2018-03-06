package com.tzly.ctcyh.pay.html_p;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tzly.ctcyh.java.request.RequestDTO;
import com.tzly.ctcyh.java.request.RequestHeadDTO;
import com.tzly.ctcyh.java.request.violation.ViolationDetailsDTO;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.violation.ViolationNum;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.bean.response.PayWeixinResponse;
import com.tzly.ctcyh.pay.data_m.PayDataManager;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.api.BaseSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
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
    public void intervalOrder(final String orderId) {
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
                        orderDetail(orderId);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 9.获取订单详情
     */
    @Override
    public void orderDetail(String orderId) {
        Subscription subscription = mRepository.getOrderDetail(orderId)
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
                            mContractView.bankPayHtmlSucceed(response, orderId);
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

    @Override
    public void bank_v003_01(String violationNum) {
        Subscription subscription = mRepository
                .bank_v003_01(violationNumDTO(violationNum))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ViolationNumBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.bank_v003_01Error(
                                e.getMessage() != null ? e.getMessage() : "未知错误(V003)");
                    }

                    @Override
                    public void onNext(ViolationNumBean result) {
                        ViolationNum violationNum = result.getRspInfo();
                        List<ViolationNum> list = new ArrayList<>();
                        list.add(violationNum);
                        updateState(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public String violationNumDTO(String violationNum) {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.requestHeadDTO("cip.cfc.v003.01");
        dto.setSYS_HEAD(requestHeadDTO);

        ViolationDetailsDTO bean = new ViolationDetailsDTO();

        bean.setViolationnum(violationNum);
        bean.setToken(mRepository.rasByStr(PayRouter.getDeviceId()));

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }


    /**
     * 46.更新违章缴费状态
     */
    public void updateState(List<ViolationNum> violationUpdateDTO) {
        Subscription subscription = mRepository
                .updateState(violationUpdateDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.updateStateError(e.getMessage() != null
                                ? e.getMessage() : "未知错误(updateState)");
                    }

                    @Override
                    public void doNext(BaseResponse result) {
                        if (result != null && result.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.updateStateSucceed(result);
                        } else {
                            mContractView.updateStateError(result != null
                                    ? result.getResponseDesc() : "未知错误(updateState)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
