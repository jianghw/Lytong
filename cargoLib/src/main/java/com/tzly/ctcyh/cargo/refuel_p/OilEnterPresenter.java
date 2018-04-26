package com.tzly.ctcyh.cargo.refuel_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.router.api.BaseSubscriber;

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

public class OilEnterPresenter implements IOilEnterContract.IOilEnterPresenter {

    private final CargoDataManager mRepository;
    private final IOilEnterContract.IOilEnterView mContractView;
    private final CompositeSubscription mSubscriptions;

    public OilEnterPresenter(@NonNull CargoDataManager payDataManager,
                             @NonNull IOilEnterContract.IOilEnterView view) {
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
     * 获取办卡人数
     */
    @Override
    public void getCounts() {
        Subscription subscription = mRepository.getCounts()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilEnterResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.countError(e.getMessage());
                    }

                    @Override
                    public void doNext(OilEnterResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.countSucceed(response);
                        } else {
                            mContractView.countError(response != null
                                    ? response.getResponseDesc() : "未知错误(办卡数获取失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 加油活动页OilModuleResponse
     */
    @Override
    public void getOilModuleList() {
        Subscription subscription = mRepository.getOilModuleList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilModuleResponse>() {
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
                    public void doNext(OilModuleResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(加油活动)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }


}
