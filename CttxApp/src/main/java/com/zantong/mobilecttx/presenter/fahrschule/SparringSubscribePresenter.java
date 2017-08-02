
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.fahrschule.ISparringSubscribeContract;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResult;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResult;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

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
public class SparringSubscribePresenter
        implements ISparringSubscribeContract.ISparringSubscribePresenter {

    private final RepositoryManager mRepository;
    private final ISparringSubscribeContract.ISparringSubscribeView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public SparringSubscribePresenter(@NonNull RepositoryManager repositoryManager,
                                      @NonNull ISparringSubscribeContract.ISparringSubscribeView view) {
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
     * 20.新手陪练获取服务地区
     */
    @Override
    public void getServiceArea() {
        Subscription subscription = mRepository.getServiceArea()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SparringAreaResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.serviceAreaError(e.getMessage());
                    }

                    @Override
                    public void doNext(SparringAreaResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.serviceAreaSucceed(result);
                        } else {
                            mAtyView.serviceAreaError(result != null
                                    ? result.getResponseDesc() : "未知错误(N20)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 22.获取商品
     */
    @Override
    public void getGoods() {
        Subscription subscription = mRepository.getGoodsFive("5")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SparringGoodsResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.goodsError(e.getMessage());
                    }

                    @Override
                    public void doNext(SparringGoodsResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.goodsSucceed(result);
                        } else {
                            mAtyView.goodsError(result != null
                                    ? result.getResponseDesc() : "未知错误(N22)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
