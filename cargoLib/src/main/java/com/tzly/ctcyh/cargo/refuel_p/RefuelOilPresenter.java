package com.tzly.ctcyh.cargo.refuel_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.BaseSubscriber;
import com.tzly.ctcyh.cargo.data_m.CargoDataManager;

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

public class RefuelOilPresenter implements IRefuelOilContract.IRefuelOilPresenter {

    private final CargoDataManager mRepository;
    private final IRefuelOilContract.IRefuelOilView mContractView;
    private final CompositeSubscription mSubscriptions;

    public RefuelOilPresenter(@NonNull CargoDataManager payDataManager,
                              @NonNull IRefuelOilContract.IRefuelOilView view) {
        mRepository = payDataManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {}

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 获取商品
     */
    @Override
    public void getGoods() {
        Subscription subscription = mRepository.getGoods()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RefuelOilResponse>() {
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
                    public void doNext(RefuelOilResponse response) {
                        if (response != null && response.getResponseCode() == 2000) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(加油订单)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 数据分发处理
     */
    @Override
    public void dataDistribution(final int oilType, List<RefuelOilBean> data) {
        Subscription subscription = Observable.from(data)
                .filter(new Func1<RefuelOilBean, Boolean>() {
                    @Override
                    public Boolean call(RefuelOilBean refuelOilBean) {
                        return null != refuelOilBean;
                    }
                })
                .filter(new Func1<RefuelOilBean, Boolean>() {
                    @Override
                    public Boolean call(RefuelOilBean refuelOilBean) {
                        return refuelOilBean.getOilType() == oilType;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<RefuelOilBean>>() {
                            @Override
                            public void call(List<RefuelOilBean> beanList) {
                                mContractView.dataDistributionSucceed(beanList);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mContractView.dataDistributionError(throwable);
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void createOrder() {
        Subscription subscription = mRepository.createOrder(initDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RefuelOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.createOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(RefuelOrderResponse response) {
                        if (response != null && response.getResponseCode() == 2000) {
                            mContractView.createOrderSucceed(response);
                        } else {
                            mContractView.createOrderError(response != null
                                    ? response.getResponseDesc() : "未知错误(订单创建)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    private RefuelOilDTO initDTO() {
        RefuelOilDTO oilDTO = new RefuelOilDTO();
        oilDTO.setRechargeMoney(mContractView.getRechargeMoney());
        oilDTO.setMethodType("rechargeOrder");
        oilDTO.setUserId(mRepository.getRASUserID());
        oilDTO.setGoodsId(mContractView.getGoodsId());
        oilDTO.setOilCardNum(mContractView.getOilCardNum());
        return oilDTO;
    }
}
