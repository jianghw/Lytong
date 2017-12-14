package com.tzly.ctcyh.pay.coupon_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.pay.bean.response.CouponCodeResponse;
import com.tzly.ctcyh.pay.data_m.BaseSubscriber;
import com.tzly.ctcyh.pay.data_m.PayDataManager;
import com.tzly.ctcyh.pay.global.PayGlobal;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponCodePresenter implements ICouponCodeContract.ICouponCodePresenter {

    private final PayDataManager mRepository;
    private final ICouponCodeContract.ICouponCodeView mContractView;
    private final CompositeSubscription mSubscriptions;

    CouponCodePresenter(@NonNull PayDataManager payDataManager,
                        @NonNull ICouponCodeContract.ICouponCodeView view) {
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
     * 1． 码券列表
     */
    @Override
    public void getCodeList() {
        Subscription subscription = Observable.zip(
                mRepository.getCodeList(mRepository.getRASUserID(), "1"),
                mRepository.getCodeList(mRepository.getRASUserID(), "2"),
                new Func2<CouponCodeResponse, CouponCodeResponse, CouponCodeResponse>() {
                    @Override
                    public CouponCodeResponse call(CouponCodeResponse couponCodeResponse,
                                                   CouponCodeResponse couponCodeResponse2) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponCodeResponse>() {
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
                    public void doNext(CouponCodeResponse response) {
                        if (response != null &&
                                response.getResponseCode() == PayGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getResponseDesc() : "未知错误(CodeList)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 2.4.27删除用户优惠券
     */
    @Override
    public void deleteCode(String codeId, final int position) {
        Subscription subscription = mRepository
                .deleteCode(codeId, mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.deleteCodeError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse response) {
                        if (response != null &&
                                response.getResponseCode() == PayGlobal.Response.base_succeed) {
                            mContractView.deleteCodeSucceed(response, position);
                        } else {
                            mContractView.deleteCodeError(response != null
                                    ? response.getResponseDesc() : "未知错误(deleteCode)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
