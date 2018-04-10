
package com.zantong.mobilecttx.order_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.order.OrderInfoResponse;
import com.tzly.ctcyh.java.response.order.OrderRefundResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.order.bean.OrderDetailResponse;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名
 * Update by:
 * Update day:
 */
public class OrderDetailPresenter
        implements IOrderDetailContract.IOrderDetailPresenter {

    private final RepositoryManager mRepository;
    private final IOrderDetailContract.IOrderDetailView mContractView;
    private final CompositeSubscription mSubscriptions;

    public OrderDetailPresenter(@NonNull RepositoryManager repositoryManager,
                                @NonNull IOrderDetailContract.IOrderDetailView view) {
        mRepository = repositoryManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 9.获取订单详情
     */
    @Override
    public void getOrderDetail() {
        Subscription subscription = mRepository.getOrderDetail(getOrderId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderDetailResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.getOrderDetailError(e.getMessage());
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

    /**
     * 催单,退款
     */
    @Override
    public void info() {
        Subscription subscription = mRepository.info(mContractView.getChannel(),
                mContractView.getOrderId(), "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderRefundResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.infoError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderRefundResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.infoSucceed(result);
                        } else {
                            mContractView.infoError(result != null
                                    ? result.getResponseDesc() : "未知错误(订单操作)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 反显用户信息
     */
    @Override
    public void getUserOrderInfo() {
        Subscription subscription = mRepository.getUserOrderInfo(mContractView.getOrderId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderInfoResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.UserOrderInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderInfoResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.UserOrderInfoSucceed(result);
                        } else {
                            mContractView.UserOrderInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(用户信息)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
