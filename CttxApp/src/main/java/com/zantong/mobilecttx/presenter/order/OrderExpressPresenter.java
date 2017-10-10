
package com.zantong.mobilecttx.presenter.order;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IOrderExpressContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.order.bean.ChildrenBean;
import com.zantong.mobilecttx.order.bean.ChildrenBeanX;
import com.zantong.mobilecttx.order.bean.OrderExpressBean;
import com.zantong.mobilecttx.order.bean.OrderExpressResponse;
import com.zantong.mobilecttx.order.bean.ReceiveInfoResponse;
import com.zantong.mobilecttx.order.dto.ExpressDTO;

import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;
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
 * Description: 快递订单
 * Update by:
 * Update day:
 */
public class OrderExpressPresenter
        implements IOrderExpressContract.IOrderExpressPresenter {

    private final RepositoryManager mRepository;
    private final IOrderExpressContract.IOrderExpressView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public OrderExpressPresenter(@NonNull RepositoryManager repositoryManager,
                                 @NonNull IOrderExpressContract.IOrderExpressView view) {
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
     * 32.获取地区列表
     */
    @Override
    public void getAllAreas() {
        Subscription subscription = mRepository.getAllAreas()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderExpressResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.allAreasError(e.getMessage());
                    }

                    @Override
                    public void doNext(OrderExpressResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            dataDistributionProcessing(result.getData());
                        } else {
                            mAtyView.allAreasError(result != null
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
                                mAtyView.allAreasSucceed(objects);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mAtyView.allAreasError(throwable.getMessage());
                            }
                        });
    }

    /**
     * 29.填写快递信息
     */
    @Override
    public void addExpressInfo() {
        Subscription subscription = mRepository.addExpressInfo(initExpressDTO())
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
                        mAtyView.addExpressInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.addExpressInfoSucceed(result);
                        } else {
                            mAtyView.addExpressInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(N32)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public ExpressDTO initExpressDTO() {
        ExpressDTO expressDTO = new ExpressDTO();
        expressDTO.setUserNum(mRepository.getDefaultRASUserID());
        expressDTO.setExpressId("1");
        expressDTO.setOrderId(mAtyView.getOrderId());
        expressDTO.setSendName(mAtyView.getUserName());
        expressDTO.setSendPhone(mAtyView.getUserPhone());

        String province = mAtyView.getProvince();
        String address[] = province.split("/");

        expressDTO.setSendProvince(address[0]);
        expressDTO.setSendCity(address[1]);
        expressDTO.setSendAddress(address[2] + mAtyView.getAddress());
        return expressDTO;
    }

    /**
     * 33.获取收件人信息
     */
    @Override
    public void getReceiveInfo() {
        Subscription subscription = mRepository.getReceiveInfo(mAtyView.getOrderId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ReceiveInfoResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getReceiveInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(ReceiveInfoResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getReceiveInfoSucceed(result);
                        } else {
                            mAtyView.getReceiveInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(N33)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
