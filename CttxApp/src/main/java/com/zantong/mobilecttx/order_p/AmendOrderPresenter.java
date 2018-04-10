
package com.zantong.mobilecttx.order_p;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tzly.ctcyh.java.request.order.UpdateOrderDTO;
import com.tzly.ctcyh.java.response.order.OrderInfoResponse;
import com.tzly.ctcyh.java.response.order.UpdateOrderResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.order.bean.ChildrenBean;
import com.zantong.mobilecttx.order.bean.ChildrenBeanX;
import com.zantong.mobilecttx.order.bean.OrderExpressBean;
import com.zantong.mobilecttx.order.bean.OrderExpressResponse;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func6;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Update by:
 * Update day:
 */
public class AmendOrderPresenter
        implements IAmendOrderContract.IAmendOrderPresenter {

    private final RepositoryManager mRepository;
    private final IAmendOrderContract.IAmendOrderView mContractView;
    private final CompositeSubscription mSubscriptions;

    public AmendOrderPresenter(@NonNull RepositoryManager repositoryManager,
                               @NonNull IAmendOrderContract.IAmendOrderView view) {
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
     * 修改订单详情
     */
    @Override
    public void updateOrderDetail() {
        Subscription subscription = mRepository.updateOrderDetail(initUpdateOrder())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UpdateOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.updateOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(UpdateOrderResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.updateOrderSucceed(result);
                        } else {
                            mContractView.updateOrderError(result != null
                                    ? result.getResponseDesc() : "未知错误(修改信息)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    public UpdateOrderDTO initUpdateOrder() {
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setOrderId(mContractView.getOrderId());
        updateOrderDTO.setName(mContractView.getName());
        updateOrderDTO.setPhone(mContractView.getPhone());

        String address = mContractView.getAddress();
        String[] addr = address.split("/");
        updateOrderDTO.setSheng(addr.length >= 1 ? addr[0] : "");
        updateOrderDTO.setShi(addr.length >= 2 ? addr[1] : "");
        updateOrderDTO.setXian(addr.length >= 3 ? addr[2] : "");

        updateOrderDTO.setAddressDetail(mContractView.getAddressDetail());
        String title = mContractView.getTimeTitle();
        if (!TextUtils.isEmpty(title)) {
            if (title.contains("预约"))
                updateOrderDTO.setBespeakDate(mContractView.getBespeakDate());
            else
                updateOrderDTO.setExpressTime(mContractView.getBespeakDate());
        }
        updateOrderDTO.setSupplement(mContractView.getSupplement());

        updateOrderDTO.setShengCode(mContractView.getShengCode());
        updateOrderDTO.setShicode(mContractView.getShicode());
        return updateOrderDTO;
    }

    /**
     * 32.获取地区列表
     */
    @Override
    public void getAllAreas() {
        Subscription subscription = mRepository.getAllAreas()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderExpressResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.allAreasError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderExpressResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            dataDistributionProcessing(result.getData());
                        } else {
                            mContractView.allAreasError(result != null
                                    ? result.getResponseDesc() : "未知错误(获取地址)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 三级联动地址
     */
    private void dataDistributionProcessing(List<OrderExpressBean> data) {

        Observable<List<String>> firstList = Observable.from(data)
                .filter(new Func1<OrderExpressBean, Boolean>() {
                    @Override
                    public Boolean call(OrderExpressBean orderExpressBean) {
                        return null != orderExpressBean;
                    }
                })
                .map(new Func1<OrderExpressBean, String>() {
                    @Override
                    public String call(OrderExpressBean orderExpressBean) {
                        return orderExpressBean.getName();
                    }
                })
                .toList();

        Observable<List<String>> firstCode = Observable.from(data)
                .filter(new Func1<OrderExpressBean, Boolean>() {
                    @Override
                    public Boolean call(OrderExpressBean orderExpressBean) {
                        return null != orderExpressBean;
                    }
                })
                .map(new Func1<OrderExpressBean, String>() {
                    @Override
                    public String call(OrderExpressBean orderExpressBean) {
                        return String.valueOf(orderExpressBean.getCode());
                    }
                })
                .toList();

        Observable<List<List<String>>> secondList = Observable.from(data)
                .filter(new Func1<OrderExpressBean, Boolean>() {
                    @Override
                    public Boolean call(OrderExpressBean orderExpressBean) {
                        return null != orderExpressBean;
                    }
                })
                .map(new Func1<OrderExpressBean, List<ChildrenBeanX>>() {
                    @Override
                    public List<ChildrenBeanX> call(OrderExpressBean orderExpressBean) {
                        return orderExpressBean.getChildren();
                    }
                })
                .flatMap(new Func1<List<ChildrenBeanX>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(List<ChildrenBeanX> childrenBeanXes) {
                        return Observable.from(childrenBeanXes)
                                .map(new Func1<ChildrenBeanX, String>() {
                                    @Override
                                    public String call(ChildrenBeanX childrenBeanX) {
                                        return childrenBeanX.getName();
                                    }
                                })
                                .toList();
                    }
                })
                .toList();

        Observable<List<List<String>>> secondCode = Observable.from(data)
                .filter(new Func1<OrderExpressBean, Boolean>() {
                    @Override
                    public Boolean call(OrderExpressBean orderExpressBean) {
                        return null != orderExpressBean;
                    }
                })
                .map(new Func1<OrderExpressBean, List<ChildrenBeanX>>() {
                    @Override
                    public List<ChildrenBeanX> call(OrderExpressBean orderExpressBean) {
                        return orderExpressBean.getChildren();
                    }
                })
                .flatMap(new Func1<List<ChildrenBeanX>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(List<ChildrenBeanX> childrenBeanXes) {
                        return Observable.from(childrenBeanXes)
                                .map(new Func1<ChildrenBeanX, String>() {
                                    @Override
                                    public String call(ChildrenBeanX childrenBeanX) {
                                        return String.valueOf(childrenBeanX.getCode());
                                    }
                                })
                                .toList();
                    }
                })
                .toList();

        Observable<List<List<List<String>>>> thirdList = Observable.from(data)
                .filter(new Func1<OrderExpressBean, Boolean>() {
                    @Override
                    public Boolean call(OrderExpressBean orderExpressBean) {
                        return null != orderExpressBean;
                    }
                })
                .map(new Func1<OrderExpressBean, List<ChildrenBeanX>>() {
                    @Override
                    public List<ChildrenBeanX> call(OrderExpressBean orderExpressBean) {
                        return orderExpressBean.getChildren();
                    }
                })
                .flatMap(new Func1<List<ChildrenBeanX>, Observable<List<List<String>>>>() {
                    @Override
                    public Observable<List<List<String>>> call(final List<ChildrenBeanX> childrenBeanXes) {
                        return Observable.from(childrenBeanXes)
                                .map(new Func1<ChildrenBeanX, List<ChildrenBean>>() {
                                    @Override
                                    public List<ChildrenBean> call(ChildrenBeanX childrenBeanX) {
                                        return childrenBeanX.getChildren();
                                    }
                                })
                                .flatMap(new Func1<List<ChildrenBean>, Observable<List<String>>>() {
                                    @Override
                                    public Observable<List<String>> call(List<ChildrenBean> childrenBeen) {
                                        return Observable.from(childrenBeen)
                                                .map(new Func1<ChildrenBean, String>() {
                                                    @Override
                                                    public String call(ChildrenBean childrenBean) {
                                                        return childrenBean.getName();
                                                    }
                                                })
                                                .toList();
                                    }
                                })
                                .toList();
                    }
                })
                .toList();
        Observable<List<List<List<String>>>> thirdCode = Observable.from(data)
                .filter(new Func1<OrderExpressBean, Boolean>() {
                    @Override
                    public Boolean call(OrderExpressBean orderExpressBean) {
                        return null != orderExpressBean;
                    }
                })
                .map(new Func1<OrderExpressBean, List<ChildrenBeanX>>() {
                    @Override
                    public List<ChildrenBeanX> call(OrderExpressBean orderExpressBean) {
                        return orderExpressBean.getChildren();
                    }
                })
                .flatMap(new Func1<List<ChildrenBeanX>, Observable<List<List<String>>>>() {
                    @Override
                    public Observable<List<List<String>>> call(final List<ChildrenBeanX> childrenBeanXes) {
                        return Observable.from(childrenBeanXes)
                                .map(new Func1<ChildrenBeanX, List<ChildrenBean>>() {
                                    @Override
                                    public List<ChildrenBean> call(ChildrenBeanX childrenBeanX) {
                                        return childrenBeanX.getChildren();
                                    }
                                })
                                .flatMap(new Func1<List<ChildrenBean>, Observable<List<String>>>() {
                                    @Override
                                    public Observable<List<String>> call(List<ChildrenBean> childrenBeen) {
                                        return Observable.from(childrenBeen)
                                                .map(new Func1<ChildrenBean, String>() {
                                                    @Override
                                                    public String call(ChildrenBean childrenBean) {
                                                        return String.valueOf(childrenBean.getCode());
                                                    }
                                                })
                                                .toList();
                                    }
                                })
                                .toList();
                    }
                })
                .toList();

        Observable.zip(firstList, secondList, thirdList, firstCode, secondCode, thirdCode,
                new Func6<List<String>, List<List<String>>, List<List<List<String>>>,
                        List<String>, List<List<String>>, List<List<List<String>>>, Object[]>() {
                    @Override
                    public Object[] call(List<String> listList1, List<List<String>> listList2, List<List<List<String>>> listList3,
                                         List<String> listCode1, List<List<String>> listCode2, List<List<List<String>>> listCode3) {

                        return new Object[]{listList1, listList2, listList3, listCode1, listCode2, listCode3};
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Object[]>() {
                            @Override
                            public void call(Object[] objects) {
                                mContractView.allAreasSucceed(objects);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mContractView.allAreasError(throwable.getMessage());
                            }
                        });
    }

    /**
     * 反显用户信息
     */
    @Override
    public void getUserOrderInfo() {
        Subscription subscription = mRepository.getUserOrderInfo(mContractView.getOrderId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderInfoResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.UserOrderInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderInfoResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.UserOrderInfoSucceed(result);
                        } else {
                            mContractView.UserOrderInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(用户信息)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
