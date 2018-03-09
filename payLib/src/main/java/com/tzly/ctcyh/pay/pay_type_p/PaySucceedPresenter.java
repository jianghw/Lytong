package com.tzly.ctcyh.pay.pay_type_p;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.coupon.CouponInfoResponse;
import com.tzly.ctcyh.pay.data_m.PayDataManager;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.api.BaseSubscriber;
import com.tzly.ctcyh.router.custom.image.BitmapUtils;
import com.tzly.ctcyh.router.custom.image.EncodingUtils;
import com.tzly.ctcyh.router.util.LogUtils;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class PaySucceedPresenter implements IPaySucceedContract.IPaySucceedPresenter {

    private final PayDataManager mRepository;
    private final IPaySucceedContract.IPaySucceedView mContractView;
    private final CompositeSubscription mSubscriptions;

    public PaySucceedPresenter(@NonNull PayDataManager payDataManager,
                               @NonNull IPaySucceedContract.IPaySucceedView view) {
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

    @Override
    public void getCouponInfo() {
        Subscription subscription = mRepository
                .getCouponInfo(mContractView.getGoodsType())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponInfoResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.couponInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(CouponInfoResponse response) {
                        if (response != null && response.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                            mContractView.couponInfoSucceed(response);
                        } else {
                            mContractView.couponInfoError(response != null
                                    ? response.getResponseDesc() : "未知错误(couponInfo)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void mergeBitmap(final Bitmap bitmap, final String codeUrl, final Bitmap logio) {
        Subscription subscription = Observable.just(codeUrl)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return EncodingUtils.createQRCode(codeUrl, 280, 280, logio);
                    }
                })
                .map(new Func1<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap call(Bitmap qrCodeBitmap) {
                        return BitmapUtils.mergeBitmap(bitmap, qrCodeBitmap);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Bitmap>() {
                            @Override
                            public void call(Bitmap bitmap) {
                                mContractView.mergeSucceed(bitmap);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 分享人信息统计
     */
    @Override
    public void shareUser() {
        Subscription subscription = mRepository
                .shareUser(mRepository.getRASUserID(), mContractView.getGoodsType(), "1")
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
                        mContractView.couponInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse response) {
                        if (response != null && response.getResponseCode()
                                == PayGlobal.Response.base_succeed) {
                        } else {
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
