
package com.zantong.mobilecttx.presenter.home;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.router.bean.BaseResponse;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.contract.IUnimpededFtyContract;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
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
 * Description: 畅通页面 p
 * Update by:
 * Update day:
 */
public class UnimpededFtyPresenter implements IUnimpededFtyContract.IUnimpededFtyPresenter {

    private final RepositoryManager mRepository;
    private final IUnimpededFtyContract.IUnimpededFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public UnimpededFtyPresenter(@NonNull RepositoryManager repositoryManager,
                                 @NonNull IUnimpededFtyContract.IUnimpededFtyView view) {
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
        mAtyView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 1.首页信息
     */
    @Override
    public void homePage() {
        Subscription subscription = mRepository.homePage(initHomeDataDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<HomeResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.homePageError(e.getMessage());
                    }

                    @Override
                    public void doNext(HomeResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.homePageSucceed(result);
                        } else {
                            mAtyView.homePageError(result != null ? result.getResponseDesc() : "未知错误(1)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public HomeDataDTO initHomeDataDTO() {
        HomeDataDTO params = new HomeDataDTO();
        params.setUserId(MainRouter.isUserLogin()
                ? mRepository.getRASUserID() : mRepository.getRASEmptyID());
        return params;
    }

    /**
     * 新获取违章信息
     */
    @Override
    public void getTextNoticeInfo() {
        Subscription subscription = mRepository
                .getTextNoticeInfo(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<HomeCarResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.remoteCarInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(HomeCarResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getTextNoticeInfo(result);
                        } else {
                            mAtyView.remoteCarInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(NoticeInfo)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getIndexLayer() {
        Subscription subscription = mRepository.getIndexLayer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<IndexLayerResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.indexLayerError(e.getMessage());
                    }

                    @Override
                    public void doNext(IndexLayerResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.indexLayerSucceed(result);
                        } else {
                            mAtyView.indexLayerError(result != null
                                    ? result.getResponseDesc() : "未知错误(IndexLayer)");
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
                .take(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mAtyView.countDownCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mAtyView.countDownTextView(3 - aLong);
                    }
                });
        mSubscriptions.add(subCount);
    }

    /**
     * 统计
     * 不登录不使用
     */
    @Override
    public void saveStatisticsCount(String contentId) {
        if (!MainRouter.isUserLogin()) return;

        Subscription subscription = mRepository
                .saveStatisticsCount(contentId, mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(BaseResponse result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

}
