package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.active.ActiveConfigResponse;
import com.tzly.ctcyh.router.api.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 */

public class RouterPresenter implements IRouterContract.IRouterPresenter {

    private final RepositoryManager mRepository;
    private final IRouterContract.IRouterView mContractView;
    private final CompositeSubscription mSubscriptions;

    public RouterPresenter(@NonNull RepositoryManager payDataManager,
                           @NonNull IRouterContract.IRouterView view) {
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
     *
     * @param channel
     * @param date
     */
    @Override
    public void getConfig(String channel, String date) {
        Subscription subscription = mRepository.getConfig(channel, date)
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
                    }

                    @Override
                    public void doNext(ActiveConfigResponse response) {
                        if (response != null &&
                                response.getResponseCode() == MainGlobal.Response.base_succeed) {
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
                .subscribe(new BaseSubscriber<BaseResponse>() {
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
                    public void doNext(BaseResponse response) {
                        if (response != null && response.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(receiveCoupon)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 广告统计
     * 渠道(1 安卓 2 ios)
     */
    @Override
    public void advertCount(String keyId) {
        Subscription subscription = mRepository.advertCount(keyId, "1")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
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
                    public void doNext(BaseResponse response) {
                        if (response != null && response.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(receiveCoupon)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 小峰统计
     */
    @Override
    public void saveStatisticsCount(String statisticsId) {
        if (!MainRouter.isUserLogin()) return;

        Subscription subscription = mRepository
                .saveStatisticsCount(statisticsId, mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(BaseResponse result) {
                    }
                });
        mSubscriptions.add(subscription);
    }
}
