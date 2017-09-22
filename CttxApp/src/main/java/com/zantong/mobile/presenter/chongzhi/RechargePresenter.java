
package com.zantong.mobile.presenter.chongzhi;

import android.support.annotation.NonNull;

import com.zantong.mobile.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobile.chongzhi.bean.RechargeResult;
import com.zantong.mobile.chongzhi.dto.RechargeDTO;
import com.zantong.mobile.contract.IRechargeAtyContract;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description:
 * Update by:
 * Update day:
 */
public class RechargePresenter implements IRechargeAtyContract.IRechargeAtyPresenter {

    private final RepositoryManager mRepository;
    private final IRechargeAtyContract.IRechargeAtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public RechargePresenter(@NonNull RepositoryManager repositoryManager,
                             @NonNull IRechargeAtyContract.IRechargeAtyView view) {
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
        mAtyView.dismissLoadingDialog();
        mSubscriptions.clear();
    }

    /**
     * 获取指定类型优惠券
     * 优惠券业务：1 加油充值；2 代驾；3 洗车
     */
    @Override
    public void getCouponByType() {
        Subscription subscription = mRepository.getCouponByType(initUserId(), "1")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RechargeCouponResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.onCouponByTypeError(e.getMessage());
                    }

                    @Override
                    public void doNext(RechargeCouponResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.onCouponByTypeSucceed(result);
                        } else {
                            mAtyView.onCouponByTypeError(result != null
                                    ? result.getResponseDesc() : "未知错误(57)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserId() {
        return mRepository.getDefaultRASUserID();
    }

    /**
     * 10.创建加油订单
     */
    @Override
    public void addOilCreateOrder() {
        Subscription subscription = mRepository.addOilCreateOrder(initRechargeDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RechargeResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.addOilCreateOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(RechargeResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.addOilCreateOrderSucceed(result);
                        } else {
                            mAtyView.addOilCreateOrderError(result != null
                                    ? result.getResponseDesc() : "未知错误(57)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public RechargeDTO initRechargeDTO() {
        RechargeDTO bean = mAtyView.initRechargeDTO();
        bean.setUserId(mRepository.getDefaultRASUserID());
        return bean;
    }

    /**
     * 54.充值接口
     */
    @Override
    public void onPayOrderByCoupon(String orderId, String orderPrice, String payType) {
        Subscription subscription = mRepository.onPayOrderByCoupon(orderId, orderPrice, payType)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayOrderResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.onPayOrderByCouponError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayOrderResult result) {
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
}
