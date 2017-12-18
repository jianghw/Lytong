package com.tzly.ctcyh.cargo.active_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.ActiveConfigResponse;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.BaseSubscriber;
import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.cargo.refuel_p.IRefuelOilContract;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class ActivePresenter implements IActiveContract.IActivePresenter {

    private final CargoDataManager mRepository;
    private final IActiveContract.IActiveView mContractView;
    private final CompositeSubscription mSubscriptions;

    public ActivePresenter(@NonNull CargoDataManager payDataManager,
                           @NonNull IActiveContract.IActiveView view) {
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
     * 获取配置接口
     * /config/getConfig POST 键值对
     * 参数：channel 1.违章查询 2.违章查询支付 3.绑定畅通卡 4.ETC 5.年检 6.车牌贷
     * 7.拍牌 8.二手车线下估值 9.电瓶 10.海外驾驶培训 11.嗨修保养 12.惠保养
     * <p>
     * channel=2时 传registerDate参数（车辆注册日期）yyyy-MM-dd
     */
    @Override
    public void getConfig() {
        Subscription subscription = mRepository
                .getConfig(mContractView.getChannel(),mContractView.getResisterDate())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ActiveConfigResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.configError(e.getMessage());
                    }

                    @Override
                    public void doNext(ActiveConfigResponse response) {
                        if (response != null &&
                                response.getResponseCode() == CargoGlobal.Response.base_succeed) {
                            mContractView.configSucceed(response);
                        } else {
                            mContractView.configError(response != null
                                    ? response.getResponseDesc() : "未知错误(getConfig)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 1. 领券
     * /activity/receiveCoupon POST 键值对
     * 参数：userId，couponId，channel
     */
    @Override
    public void receiveCoupon(String couponId) {
        Subscription subscription = mRepository
                .receiveCoupon(mRepository.getRASUserID(), couponId, mContractView.getChannel())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ReceiveCouponResponse>() {
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
                    public void doNext(ReceiveCouponResponse response) {
                        if (response != null &&
                                response.getResponseCode() == CargoGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(receiveCoupon)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
