package com.tzly.ctcyh.cargo.refuel_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.cargo.bean.response.ChildrenBean;
import com.tzly.ctcyh.cargo.bean.response.ChildrenBeanX;
import com.tzly.ctcyh.cargo.bean.response.OrderExpressBean;
import com.tzly.ctcyh.cargo.bean.response.OrderExpressResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.BaseSubscriber;
import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.cargo.global.CargoGlobal;

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
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class BidOilPresenter implements IBidOilContract.IBidOilPresenter {

    private final CargoDataManager mRepository;
    private final IBidOilContract.IBidOilView mContractView;
    private final CompositeSubscription mSubscriptions;

    public BidOilPresenter(@NonNull CargoDataManager payDataManager,
                           @NonNull IBidOilContract.IBidOilView view) {
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
     * 办卡信息
     */
    @Override
    public void handleOilCard() {
        Subscription subscription = mRepository
                .handleOilCard()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BidOilResponse>() {
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
                    public void doNext(BidOilResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(油卡信息获取失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 创建订单
     */
    @Override
    public void createOrder() {
        Subscription subscription = mRepository.createOrder(initDTO(),2)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RefuelOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.createOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(RefuelOrderResponse response) {
                        if (response != null && response.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            mContractView.createOrderSucceed(response);
                        } else {
                            mContractView.createOrderError(response != null
                                    ? response.getResponseDesc() : "未知错误(订单创建)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    private RefuelOilDTO initDTO() {
        RefuelOilDTO dto = new RefuelOilDTO();
        dto.setType(mContractView.getSubmitBean().getType());
        dto.setPrice(mContractView.getSubmitBean().getPrice());
        dto.setGoodsId(mContractView.getSubmitBean().getId());
        dto.setUserNum(mRepository.getRASUserID());

        dto.setName(mContractView.getStrEdtName());
        dto.setPhone(mContractView.getStrEditPhone());
        String[] area = mContractView.getTvArea().split("/");
        dto.setSheng(area[0]);
        dto.setShi(area[1]);
        dto.setXian(area[2]);
        dto.setAddressDetail(mContractView.getEditDetailedAddress());
        return dto;
    }

    /**
     * 获取城市
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
                        if (result != null && result.getResponseCode()
                                == CargoGlobal.Response.base_succeed) {
                            dataDistributionProcessing(result.getData());
                        } else {
                            mContractView.allAreasError(result != null
                                    ? result.getResponseDesc() : "未知错误(N32)");
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

}
