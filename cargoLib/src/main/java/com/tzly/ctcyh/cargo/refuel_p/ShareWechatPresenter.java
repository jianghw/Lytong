package com.tzly.ctcyh.cargo.refuel_p;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.router.custom.image.BitmapUtils;
import com.tzly.ctcyh.router.custom.image.EncodingUtils;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.RudenessScreenHelper;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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

public class ShareWechatPresenter implements IShareWechatContract.IShareWechatPresenter {

    private final CargoDataManager mRepository;
    private final IShareWechatContract.IShareWechatView mContractView;
    private final CompositeSubscription mSubscriptions;

    public ShareWechatPresenter(@NonNull CargoDataManager payDataManager,
                                @NonNull IShareWechatContract.IShareWechatView view) {
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
    public void mergeBitmap(final Bitmap bitmap, final String codeUrl, final Bitmap logio) {
        Subscription subscription = Observable.just(codeUrl)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return EncodingUtils.createQRCode(codeUrl,
                                RudenessScreenHelper.ptInpx(120F),
                                RudenessScreenHelper.ptInpx(120F), logio);
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
}
