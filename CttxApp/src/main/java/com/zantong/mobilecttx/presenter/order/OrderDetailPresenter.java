
package com.zantong.mobilecttx.presenter.order;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IOrderDetailContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.order.bean.OrderDetailResponse;

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
public class OrderDetailPresenter
        implements IOrderDetailContract.IOrderDetailPresenter {

    private final RepositoryManager mRepository;
    private final IOrderDetailContract.IOrderDetailView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public OrderDetailPresenter(@NonNull RepositoryManager repositoryManager,
                                @NonNull IOrderDetailContract.IOrderDetailView view) {
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
     * 9.获取订单详情
     */
    @Override
    public void getOrderDetail() {
        Subscription subscription = mRepository.getOrderDetail(getOrderId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderDetailResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getOrderDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderDetailResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getOrderDetailSucceed(result);
                        } else {
                            mAtyView.getOrderDetailError(result != null
                                    ? result.getResponseDesc() : "未知错误(N9)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String getOrderId() {
        return mAtyView.getOrderId();
    }

}
