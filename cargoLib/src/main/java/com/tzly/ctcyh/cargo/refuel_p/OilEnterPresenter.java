package com.tzly.ctcyh.cargo.refuel_p;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.router.api.BaseSubscriber;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
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
                        mContractView.OilModuleError(e.getMessage());
                    }

                    @Override
                    public void doNext(OilModuleResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            ProcessImageSize(response);
                        } else {
                            mContractView.OilModuleError(response != null
                                    ? response.getResponseDesc() : "未知错误(加油活动)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void ProcessImageSize(final OilModuleResponse response) {
        Subscription subscription = Observable.from(response.getData())
                .flatMap(new Func1<OilModuleResponse.DataBean, Observable<OilModuleResponse.DataBean>>() {
                    @Override
                    public Observable<OilModuleResponse.DataBean> call(final OilModuleResponse.DataBean dataBean) {
                        return Observable.create(new Observable.OnSubscribe<OilModuleResponse.DataBean>() {
                            @Override
                            public void call(final Subscriber<? super OilModuleResponse.DataBean> subscriber) {
                                ImageLoader.getInstance().loadImage(dataBean.getImg(), new ImageLoadingListener() {
                                    @Override
                                    public void onLoadingStarted(String s, View view) {

                                    }

                                    @Override
                                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                                        subscriber.onError(new Exception(s));
                                    }

                                    @Override
                                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                        dataBean.setWidth(bitmap.getWidth());
                                        dataBean.setHeight(bitmap.getHeight());
                                        try {
                                            subscriber.onNext(dataBean);
                                            subscriber.onCompleted();
                                        } catch (Exception e) {
                                            subscriber.onError(new Exception(e));
                                        }
                                    }

                                    @Override
                                    public void onLoadingCancelled(String s, View view) {
                                        subscriber.onCompleted();
                                    }
                                });
                            }
                        });
                    }
                })
                .toSortedList(new Func2<OilModuleResponse.DataBean, OilModuleResponse.DataBean, Integer>() {
                    @Override
                    public Integer call(OilModuleResponse.DataBean dataBean, OilModuleResponse.DataBean dataBean2) {
                        return dataBean.getId()-dataBean2.getId();
                    }
                })
                .map(new Func1<List<OilModuleResponse.DataBean>, OilModuleResponse>() {
                    @Override
                    public OilModuleResponse call(List<OilModuleResponse.DataBean> dataBeans) {
                        response.setData(dataBeans);
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilModuleResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void doNext(OilModuleResponse res) {
                        mContractView.OilModuleSucceed(res);
                    }
                });
        mSubscriptions.add(subscription);
    }


}
