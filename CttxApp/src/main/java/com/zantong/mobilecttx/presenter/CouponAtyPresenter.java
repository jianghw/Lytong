package com.zantong.mobilecttx.presenter;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.ICouponAtyContract;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.order.bean.CouponFragmentBean;
import com.zantong.mobilecttx.order.bean.CouponFragmentResult;
import com.zantong.mobilecttx.order.bean.MessageResult;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠劵页面逻辑处理层
 */

public class CouponAtyPresenter implements ICouponAtyContract.ICouponAtyPresenter {
    private final RepositoryManager mRepository;
    private final ICouponAtyContract.ICouponAtyView mView;
    private final CompositeSubscription mSubscriptions;
    /**
     * 是否为刷新操作
     */
    private boolean isRefresh = false;

    public CouponAtyPresenter(@NonNull RepositoryManager repositoryManager,
                              @NonNull ICouponAtyContract.ICouponAtyView view) {
        mRepository = repositoryManager;
        mView = view;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    /**
     * 18.查看优惠券信息
     */
    @Override
    public void usrCouponInfo() {
        Subscription subscription = mRepository.usrCouponInfo(mRepository.getDefaultUserID(), mView.getCouponStatus())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponFragmentResult>() {
                    @Override
                    public void onCompleted() {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.usrCouponInfoError(e.getMessage());
                    }

                    @Override
                    public void onNext(CouponFragmentResult couponResult) {
                        if (couponResult != null
                                && couponResult.getResponseCode() == 2000) {
                            mView.usrCouponInfoSucceed(couponResult);
                        } else {
                            mView.usrCouponInfoError(couponResult != null
                                    ? couponResult.getResponseDesc() : "未知错误(2.4.2)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 数据过滤
     */
    @Override
    public void processingDataFiltrate(List<CouponFragmentBean> beanList) {
        if (beanList == null) {
            mView.setListDataResult(null);
        } else {
            Subscription subscription = Observable.from(beanList)
                    .filter(new Func1<CouponFragmentBean, Boolean>() {
                        @Override
                        public Boolean call(CouponFragmentBean bean) {
                            return bean != null;
                        }
                    })
//                    .filter(new Func1<CouponFragmentBean, Boolean>() {
//                        @Override
//                        public Boolean call(CouponFragmentBean meg) {
//                            return meg.getCouponStatus().equals(mView.getCouponStatus());
//                        }
//                    })
                    .toList()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Action1<List<CouponFragmentBean>>() {
                                @Override
                                public void call(List<CouponFragmentBean> megs) {
                                    mView.setListDataResult(megs);
                                }
                            },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    mView.usrCouponInfoError(throwable != null
                                            ? throwable.getMessage() : "未知错误(2.4.2)");
                                }
                            });
            mSubscriptions.add(subscription);
        }
    }

    /**
     * 滑动删除
     *
     * @param meg
     * @param position
     */
    @Override
    public void delUsrCoupon(CouponFragmentBean meg, final int position) {
        Subscription subscription = mRepository.delUsrCoupon(meg.getCouponId(), mRepository.initDelUsrCouponDTODTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MessageResult>() {
                    @Override
                    public void onCompleted() {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.delUsrCouponError(e.getMessage());
                    }

                    @Override
                    public void onNext(MessageResult messageResult) {
                        if (messageResult != null
                                && messageResult.getResponseCode() == 2000) {
                            mView.delUsrCouponSucceed(messageResult, position);
                        } else {
                            mView.delUsrCouponError(messageResult != null
                                    ? messageResult.getResponseDesc() : "未知错误(2.4.27)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
