package com.tzly.ctcyh.cargo.license_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.cargo.bean.response.ScoreCaptchaResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreResponse;
import com.tzly.ctcyh.cargo.data_m.CargoDataManager;
import com.tzly.ctcyh.router.api.BaseSubscriber;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class LienseGradePresenter implements ILienseGradeContract.ILienseGradePresenter {

    private final CargoDataManager mRepository;
    private final ILienseGradeContract.ILienseGradeView mContractView;
    private final CompositeSubscription mSubscriptions;

    public LienseGradePresenter(@NonNull CargoDataManager payDataManager,
                                @NonNull ILienseGradeContract.ILienseGradeView view) {
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
     * 2.1 获取验证码
     */
    @Override
    public void scoresCaptcha() {
        Subscription subscription = mRepository
                .scoresCaptcha(mContractView.getmEditSerial())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ScoreCaptchaResponse>() {
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
                    public void doNext(ScoreCaptchaResponse response) {
                        if (response != null && response.getCode().equals("200")) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getMessage() : "未知错误(获取验证码失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 2.2 获取驾照扣分
     */
    @Override
    public void apiScores() {
        Subscription subscription = mRepository
                .apiScores(mContractView.getmEditLicense(),
                        mContractView.getmEditSerial(),
                        mContractView.getmEditVerification(), mContractView.getCookie())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ScoreResponse>() {
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
                    public void doNext(ScoreResponse response) {
                        if (response != null &&  response.getCode().equals("200")) {
                            mContractView.responseSucceed(response);
                        } else {
                            mContractView.responseError(response != null
                                    ? response.getMessage() : "未知错误(获取驾照扣分失败)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
