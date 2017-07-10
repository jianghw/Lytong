
package com.zantong.mobilecttx.presenter.order;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.interf.IOrderParentFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.order.bean.OrderListResult;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 订单模块
 * Update by:
 * Update day:
 */
public class OrderParentPresenter
        implements IOrderParentFtyContract.IOrderParentFtyPresenter {

    private final RepositoryManager mRepository;
    private final IOrderParentFtyContract.IOrderParentFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public OrderParentPresenter(@NonNull RepositoryManager repositoryManager,
                                @NonNull IOrderParentFtyContract.IOrderParentFtyView view) {
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
     * 8.查询订单列表
     */
    @Override
    public void getOrderList() {
        Subscription subscription = mRepository.getOrderList(initUserId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderListResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getOrderListError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderListResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.allPaymentData(result.getData());
                            dataDistribution(result, 0);
                            dataDistribution(result, 1);
                            dataDistribution(result, 2);
                        } else {
                            mAtyView.getOrderListError(result != null
                                    ? result.getResponseDesc() : "未知错误(N8)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 订单状态,0未至付，1已支付,2取消或过期，3已锁定
     */
    private void dataDistribution(OrderListResult result, final int orderStatus) {
        Subscription subscription = Observable.just(result)
                .map(new Func1<OrderListResult, List<OrderListBean>>() {
                    @Override
                    public List<OrderListBean> call(OrderListResult orderListResult) {
                        return orderListResult.getData();
                    }
                })
                .filter(new Func1<List<OrderListBean>, Boolean>() {
                    @Override
                    public Boolean call(List<OrderListBean> orderListBeen) {
                        return null != orderListBeen;
                    }
                })
                .flatMap(new Func1<List<OrderListBean>, Observable<OrderListBean>>() {
                    @Override
                    public Observable<OrderListBean> call(List<OrderListBean> orderListBeen) {
                        return Observable.from(orderListBeen);
                    }
                })
                .filter(new Func1<OrderListBean, Boolean>() {
                    @Override
                    public Boolean call(OrderListBean orderListBean) {
                        return null != orderListBean;
                    }
                })
                .filter(new Func1<OrderListBean, Boolean>() {
                    @Override
                    public Boolean call(OrderListBean orderListBean) {
                        return orderListBean.getOrderStatus() == orderStatus;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<OrderListBean>>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dataDistribution(e.getMessage());
                    }

                    @Override
                    public void doNext(List<OrderListBean> orderList) {
                        if (orderStatus == 0)
                            mAtyView.nonPaymentData(orderList);
                        else if (orderStatus == 1)
                            mAtyView.havePaymentData(orderList);
                        else if (orderStatus == 2)
                            mAtyView.cancelPaymentData(orderList);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserId() {
        return mRepository.getDefaultRASUserID();
    }
}
