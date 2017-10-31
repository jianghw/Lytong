
package com.zantong.mobilecttx.presenter.order;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IOrderParentFtyContract;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.order.bean.OrderListResponse;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;
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
                .subscribe(new BaseSubscriber<OrderListResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getOrderListError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderListResponse result) {
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
     * 0--待支付
     * 1--已支付
     * 2--已取消
     * 3--进行中
     * 4--完成
     */
    private void dataDistribution(OrderListResponse result, final int orderStatus) {
        Subscription subscription = Observable.just(result)
                .map(new Func1<OrderListResponse, List<OrderListBean>>() {
                    @Override
                    public List<OrderListBean> call(OrderListResponse orderListResult) {
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
                        if (orderStatus == 1)//0,1,2
                            return orderListBean.getOrderStatus() != 0 && orderListBean.getOrderStatus() != 2;
                        else
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
                        mAtyView.dataDistribution(e.getMessage(), orderStatus);
                    }

                    @Override
                    public void doNext(List<OrderListBean> orderList) {
                        if (orderStatus == 0)
                            mAtyView.nonPaymentData(orderList);
                        else if (orderStatus == 2)
                            mAtyView.cancelPaymentData(orderList);
                        else
                            mAtyView.havePaymentData(orderList);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserId() {
        return mRepository.getDefaultUserID();
    }

    /**
     * 10.更新订单状态
     */
    @Override
    public void updateOrderStatus(OrderListBean bean) {
        Subscription subscription = mRepository.updateOrderStatus(bean.getOrderId(), 2)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.updateOrderStatusError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.updateOrderStatusSucceed(result);

                        } else {
                            mAtyView.updateOrderStatusError(result != null
                                    ? result.getResponseDesc() : "未知错误(N10)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 10.取消订单
     */
    @Override
    public void cancelOrder(OrderListBean bean) {
        Subscription subscription = mRepository.cancelOrder(bean.getOrderId(), mRepository.getDefaultUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.updateOrderStatusError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.updateOrderStatusSucceed(result);

                        } else {
                            mAtyView.updateOrderStatusError(result != null
                                    ? result.getResponseDesc() : "未知错误(N10)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 54.充值接口  加油旧接口 不用
     */
    @Override
    public void onPayOrderByCoupon(String orderId, String orderPrice, String payType) {
        Subscription subscription = mRepository.onPayOrderByCoupon(orderId, orderPrice, payType)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.onPayOrderByCouponError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayOrderResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.onPayOrderByCouponSucceed(result);
                        } else {
                            mAtyView.onPayOrderByCouponError(result != null
                                    ? result.getResponseDesc() : "未知错误(54)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }


    /**
     * N 5.获取工行支付页面
     */
    @Override
    public void getBankPayHtml(final String orderId, String orderPrice) {
        Subscription subscription = mRepository.getBankPayHtml(orderId, orderPrice)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.onPayOrderByCouponError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayOrderResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getBankPayHtmlSucceed(result, orderId);
                        } else {
                            mAtyView.onPayOrderByCouponError(result != null
                                    ? result.getResponseDesc() : "未知错误(N5)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 支付宝接口
     */
    @Override
    public void aliPayHtml(String orderId) {}
}
