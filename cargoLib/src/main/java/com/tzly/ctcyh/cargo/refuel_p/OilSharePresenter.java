package com.tzly.ctcyh.cargo.refuel_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.java.response.oil.OilAccepterInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilShareResponse;
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

public class OilSharePresenter implements IOilShareContract.IOilSharePresenter {

    private final CargoDataManager mRepository;
    private final IOilShareContract.IOilShareView mContractView;
    private final CompositeSubscription mSubscriptions;

    public OilSharePresenter(@NonNull CargoDataManager payDataManager,
                             @NonNull IOilShareContract.IOilShareView view) {
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
     * 22.分享人操作(新)
     */
    @Override
    public void shareInfo() {
        Subscription subscription = mRepository.shareInfo(mContractView.getConfigId(), mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilShareResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.shareError(e.getMessage());
                    }

                    @Override
                    public void doNext(OilShareResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.shareSucceed(response);
                        } else {
                            mContractView.shareError(response != null
                                    ? response.getResponseDesc() : "未知错误(分享统计信息失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 23.获取分享统计信息(新)
     */
    @Override
    public void getShareInfo() {
        Subscription subscription = mRepository.getShareInfo(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilShareInfoResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.shareInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(OilShareInfoResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.shareInfoSucceed(response);
                        } else {
                            mContractView.shareInfoError(response != null
                                    ? response.getResponseDesc() : "未知错误(分享统计信息失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 24.被邀请人行为列表
     */
    @Override
    public void getAccepterInfoList(int position) {
        Subscription subscription = mRepository.getAccepterInfoList(mRepository.getRASUserID(), position)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilAccepterInfoResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.accepterInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(OilAccepterInfoResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.accepterInfoSucceed(response);
                        } else {
                            mContractView.accepterInfoError(response != null
                                    ? response.getResponseDesc() : "未知错误(分享统计信息失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 26.分享内容
     */
    @Override
    public void shareModule() {
        Subscription subscription = mRepository.shareModule()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilShareModuleResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.ashareModuleError(e.getMessage());
                    }

                    @Override
                    public void doNext(OilShareModuleResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.ashareModuleSucceed(response);
                        } else {
                            mContractView.accepterInfoError(response != null
                                    ? response.getResponseDesc() : "未知错误(分享内容)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
