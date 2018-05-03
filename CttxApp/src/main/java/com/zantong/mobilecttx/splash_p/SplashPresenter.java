package com.zantong.mobilecttx.splash_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.module.NewsFlagResponse;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.SPUtils;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.home.bean.StartPicResponse;
import com.zantong.mobilecttx.router.MainRouter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description:
 * Update by:
 * Update day:
 */
public class SplashPresenter implements ISplashAtyContract.ISplashAtyPresenter {

    private final RepositoryManager mRepository;
    private final ISplashAtyContract.ISplashAtyView mSplashAtyView;
    private final CompositeSubscription mSubscriptions;

    public SplashPresenter(@NonNull RepositoryManager repositoryManager,
                           @NonNull ISplashAtyContract.ISplashAtyView view) {
        mRepository = repositoryManager;
        mSplashAtyView = view;
        mSubscriptions = new CompositeSubscription();
        mSplashAtyView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    /**
     * 引导页图片
     */
    @Override
    public void startGuidePic() {
        Subscription subscription = mRepository.startGetPic("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StartPicResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(StartPicResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mSplashAtyView.displayGuideImage(result);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void startGetPic() {
        Subscription subscription = mRepository.startGetPic("0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StartPicResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(StartPicResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mSplashAtyView.displayAdsImage(result);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 倒计时
     */
    @Override
    public void startCountDown() {
        Subscription subCount = Observable
                .interval(10, 1000, TimeUnit.MILLISECONDS)
                .take(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mSplashAtyView.countDownOver();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mSplashAtyView.countDownTextView(5 - aLong);
                    }
                });
        mSubscriptions.add(subCount);
    }

    @Override
    public void updateToken() {
        if (!MainRouter.isUserLogin()) return;

        Subscription subscription = mRepository
                .updateToken(mRepository.getRASByStr(MainRouter.getPushId()), mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseResponse result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    /**
     * 是否导航
     */
    @Override
    public void newsFlag() {
        Subscription subscription = mRepository.newsFlag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsFlagResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(NewsFlagResponse result) {
                        boolean resultData = result.isData();
                        SPUtils.instance().put(SPUtils.IS_HAS_FIND, resultData);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
