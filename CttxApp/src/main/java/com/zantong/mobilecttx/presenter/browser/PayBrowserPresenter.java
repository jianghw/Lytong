
package com.zantong.mobilecttx.presenter.browser;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.browser.IPayBrowserFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;

import java.util.concurrent.TimeUnit;

import rx.Observable;
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
public class PayBrowserPresenter
        implements IPayBrowserFtyContract.IPayBrowserFtyPresenter {

    private final RepositoryManager mRepository;
    private final IPayBrowserFtyContract.IPayBrowserFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public PayBrowserPresenter(@NonNull RepositoryManager repositoryManager,
                               @NonNull IPayBrowserFtyContract.IPayBrowserFtyView view) {
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
     * 定时任务
     */
    @Override
    public void orderDetail() {
        Subscription subscription = Observable.interval(2, 3000, TimeUnit.MILLISECONDS)
                .take(4)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Long>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                        mAtyView.orderDetailCompleted();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.intervalOrderDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(Long aLong) {
                        getOrderDetail();
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 9.获取订单详情
     */
    @Override
    public void getOrderDetail() {
        Subscription subscription = mRepository.getOrderDetail(getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderDetailResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getOrderDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderDetailResult result) {
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
