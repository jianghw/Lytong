
package com.zantong.mobile.presenter.order;

import android.support.annotation.NonNull;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.response.CattleOrderResponse;
import com.tzly.annual.base.bean.response.OrderListBean;
import com.tzly.annual.base.bean.response.OrderListResult;
import com.zantong.mobile.contract.ICattleOrderContract;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;

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
                    }

                    @Override
                    public void doNext(CattleOrderResponse result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getAnnualInspectionOrders() {
        Subscription subscription = mRepository.getAnnualInspectionOrders(initUserId())
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
                        mAtyView.annualInspectionOrdersError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderListResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.allStatusData(result.getData());
                            dataDistribution(result, 1);
                            dataDistribution(result, 2);
                            dataDistribution(result, 3);
                            dataDistribution(result, 4);
                        } else {
                            mAtyView.annualInspectionOrdersError(result != null
                                    ? result.getResponseDesc() : "未知错误(order)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserId() {
        return mRepository.getDefaultRASUserID();
    }

    /**
     * 1--"已接单" 0,1,3,5,13
     * 2--资料审核中 7,8
     * 3--办理中 6
     * 4--完成 9,10,11,12,4
     */
    private void dataDistribution(OrderListResult result, final int position) {
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
                        int status = orderListBean.getOrderStatus();
                        if (position == 1) {
                            return status == 0 || status == 1 || status == 3 || status == 5 || status == 13;
                        } else if (position == 2) {
                            return status == 7 || status == 8;
                        } else if (position == 3) {
                            return status == 6;
                        } else {
                            return status == 9 || status == 10 || status == 11 || status == 12 || status == 4;
                        }
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
                        mAtyView.dataDistribution(e.getMessage(), position);
                    }

                    @Override
                    public void doNext(List<OrderListBean> orderList) {
                        if (position == 1)
                            mAtyView.haveStatusData(orderList);
                        else if (position == 2)
                            mAtyView.auditStatusData(orderList);
                        else if (position == 3)
                            mAtyView.processStatusData(orderList);
                        else if (position == 4)
                            mAtyView.completedStatusData(orderList);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 资料审核中
     */
    @Override
    public void annualOrderTargetState(String orderId, String status, String remark) {
        Subscription subscription =
                mRepository.getAnnualInspectionOrderTargetState(orderId, status, remark, mRepository.getDefaultRASUserID())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mAtyView.showLoadingDialog();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<BaseResult>() {
                            @Override
                            public void doCompleted() {
                                mAtyView.dismissLoadingDialog();
                            }

                            @Override
                            public void doError(Throwable e) {
                                mAtyView.annualOrderTargetStateError(e.getMessage());
                            }

                            @Override
                            public void doNext(BaseResult result) {
                                if (result != null && result.getResponseCode() == 2000) {
                                    mAtyView.annualOrderTargetStateSucceed(result);
                                } else {
                                    mAtyView.annualOrderTargetStateError(result != null
                                            ? result.getResponseDesc() : "未知错误(state)");
                                }
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 输入快递单号
     */
    @Override
    public void addBackExpressInfo(String orderId, String expressNo) {
        Subscription subscription =
                mRepository.addBackExpressInfo(orderId, expressNo, mRepository.getDefaultRASUserID())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mAtyView.showLoadingDialog();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<BaseResult>() {
                            @Override
                            public void doCompleted() {
                                mAtyView.dismissLoadingDialog();
                            }

                            @Override
                            public void doError(Throwable e) {
                                mAtyView.annualOrderTargetStateError(e.getMessage());
                            }

                            @Override
                            public void doNext(BaseResult result) {
                                if (result != null && result.getResponseCode() == 2000) {
                                    mAtyView.annualOrderTargetStateSucceed(result);
                                } else {
                                    mAtyView.annualOrderTargetStateError(result != null
                                            ? result.getResponseDesc() : "未知错误(state)");
                                }
                            }
                        });
        mSubscriptions.add(subscription);
    }
}
