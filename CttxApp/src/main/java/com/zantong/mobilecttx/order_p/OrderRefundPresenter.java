
package com.zantong.mobilecttx.order_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.order.OrderRefundResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Update by:
 * Update day:
 */
public class OrderRefundPresenter
        implements IOrderRefundContract.IOrderRefundPresenter {

    private final RepositoryManager mRepository;
    private final IOrderRefundContract.IOrderRefundView mContractView;
    private final CompositeSubscription mSubscriptions;

    public OrderRefundPresenter(@NonNull RepositoryManager repositoryManager,
                                @NonNull IOrderRefundContract.IOrderRefundView view) {
        mRepository = repositoryManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 催单,退款
     */
    @Override
    public void info() {
        Subscription subscription = mRepository.info(mContractView.getChannel(),
                mContractView.getOrderId(), mContractView.getRemark())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderRefundResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.infoError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderRefundResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.infoSucceed(result);
                        } else {
                            mContractView.infoError(result != null
                                    ? result.getResponseDesc() : "未知错误(订单操作)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }


}
