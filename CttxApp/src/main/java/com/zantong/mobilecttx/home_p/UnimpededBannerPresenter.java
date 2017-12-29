
package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.router.bean.BaseResponse;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.base.bean.UnimpededBannerResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.global.MainGlobal;
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
public class UnimpededBannerPresenter implements IUnimpededBannerContract.IUnimpededBannerPresenter {

    private final RepositoryManager mRepository;
    private final IUnimpededBannerContract.IUnimpededBannerView ftyView;
    private final CompositeSubscription mSubscriptions;

    public UnimpededBannerPresenter(@NonNull RepositoryManager repositoryManager,
                                    @NonNull IUnimpededBannerContract.IUnimpededBannerView view) {
        mRepository = repositoryManager;
        ftyView = view;
        mSubscriptions = new CompositeSubscription();
        ftyView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        ftyView.dismissLoading();
        mSubscriptions.clear();
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
