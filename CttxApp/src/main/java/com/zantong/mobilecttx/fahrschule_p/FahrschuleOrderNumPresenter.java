
package com.zantong.mobilecttx.fahrschule_p;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IFahrschuleOrderNumFtyContract;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名支付
 * Update by:
 * Update day:
 */
public class FahrschuleOrderNumPresenter
        implements IFahrschuleOrderNumFtyContract.IFahrschuleOrderNumFtyPresenter {

    private final RepositoryManager mRepository;
    private final IFahrschuleOrderNumFtyContract.IFahrschuleOrderNumFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public FahrschuleOrderNumPresenter(@NonNull RepositoryManager repositoryManager,
                                       @NonNull IFahrschuleOrderNumFtyContract.IFahrschuleOrderNumFtyView view) {
        mRepository = repositoryManager;
        mAtyView = view;
        mSubscriptions = new CompositeSubscription();
        mAtyView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mAtyView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 54.充值接口  加油旧接口 不用
     *
     * @deprecated
     */
    @Override
    public void onPayOrderByCoupon(String orderId, String orderPrice, String payType) {
        Subscription subscription = mRepository.onPayOrderByCoupon(orderId, orderPrice, payType)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.onPayOrderByCouponError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayOrderResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.onPayOrderByCouponSucceed(result);
                        } else {
                            mAtyView.onPayOrderByCouponError(result != null
                                    ? result.getResponseDesc() : "未知错误(54)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * N 5.获取工行支付页面
     */
    @Override
    public void getBankPayHtml(String orderId, String orderPrice) {
        Subscription subscription = mRepository.getBankPayHtml(orderId, orderPrice)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.onPayOrderByCouponError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayOrderResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.onPayOrderByCouponSucceed(result);
                        } else {
                            mAtyView.onPayOrderByCouponError(result != null
                                    ? result.getResponseDesc() : "未知错误(N5)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
