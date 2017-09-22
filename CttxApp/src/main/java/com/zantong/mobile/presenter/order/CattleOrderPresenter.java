
package com.zantong.mobile.presenter.order;

import android.support.annotation.NonNull;

import com.tzly.annual.base.bean.response.CattleOrderResponse;
import com.zantong.mobile.contract.ICattleOrderContract;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 订单模块
 * Update by:
 * Update day:
 */
public class CattleOrderPresenter
        implements ICattleOrderContract.ICattleOrderPresenter {

    private final RepositoryManager mRepository;
    private final ICattleOrderContract.ICattleOrderView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public CattleOrderPresenter(@NonNull RepositoryManager repositoryManager,
                                @NonNull ICattleOrderContract.ICattleOrderView view) {
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
     * h5 2.获取订单列表
     */
    @Override
    public void queryOrderList() {
        Subscription subscription = mRepository.queryOrderList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CattleOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.queryOrderListError(e.getMessage());
                    }

                    @Override
                    public void doNext(CattleOrderResponse result) {
//                        if (result != null && result.getCode() == 2000) {
//                            mAtyView.allPaymentData(result.getData());
//                            dataDistribution(result, 0);
//                            dataDistribution(result, 1);
//                            dataDistribution(result, 2);
//                        } else {
//                            mAtyView.getOrderListError(result != null
//                                    ? result.getResponseDesc() : "未知错误(H2)");
//                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
