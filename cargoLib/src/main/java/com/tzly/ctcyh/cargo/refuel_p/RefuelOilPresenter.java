package com.tzly.ctcyh.cargo.refuel_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.java.response.oil.NorOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.java.response.oil.OilCardsResponse;
import com.tzly.ctcyh.java.response.oil.OilRemainderResponse;
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
    public void onSubscribe() {
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * @deprecated
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
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
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
     * @deprecated
     */
    @Override
    public void findAndSaveCards() {
        String urid = "ySrAlChNAilSkv4jeC4cW8tk3ighSGQIjdGKFrAp6wschmmZlM//IfGsyf/L0Y/gsqY/KjvWixxix4dxGYvPzneysiigzKWbNsFBofk/n89o/03eiuGQg/5L8vTY0LO4Bp30DwrvzZ0hgzwM1O8Vf3PcCkhem9g9wSNEiYKCmm8=";

        Subscription subscription = mRepository
                .findAndSaveCards(mRepository.getRASUserID(), mContractView.getStrEditOil())
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
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(油卡信息获取失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 993加油
     *
     * @deprecated
     */
    @Override
    public void findOilCards() {
        Subscription subscription = mRepository.findOilCards(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<NorOilResponse>() {
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
                    public void doNext(NorOilResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(油卡信息获取失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 997加油
     */
    @Override
    public void findCaiNiaoCard() {
        Subscription subscription = mRepository.findCaiNiaoCard(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<NorOilResponse>() {
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
                    public void doNext(NorOilResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(油卡信息获取失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 获取加油卡号及商品信息
     */
    @Override
    public void findOilCardsAll() {
        Subscription subscription = mRepository
                .findOilCardsAll(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilCardsResponse>() {
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
                    public void doNext(OilCardsResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(油卡信息获取失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 2.判断余额是否充足
     */
    @Override
    public void getRemainder() {
        Subscription subscription = mRepository
                .getRemainder(mContractView.getCardInfo().getId(), mContractView.getStrEditOil())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OilRemainderResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.remainderError(e.getMessage());
                    }

                    @Override
                    public void doNext(OilRemainderResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.remainderSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(判断余额是否充足失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 创建订单
     */
    @Override
    public void createOrder() {
        Subscription subscription = mRepository.createOrder(initDTO(), 1)
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
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {

                            getRemainder();
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
        oilDTO.setUserNum(mRepository.getRASUserID());
        oilDTO.setGoodsId(mContractView.getCardInfo().getId());
        oilDTO.setOilCard(mContractView.getStrEditOil());
        oilDTO.setPrice(mContractView.getCardInfo().getPrice());
        oilDTO.setType(mContractView.getCardInfo().getType());
        return oilDTO;
    }
}
