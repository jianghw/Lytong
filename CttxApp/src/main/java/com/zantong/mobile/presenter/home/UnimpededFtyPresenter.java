
package com.zantong.mobile.presenter.home;

import android.support.annotation.NonNull;

import com.zantong.mobile.contract.IUnimpededFtyContract;
import com.zantong.mobile.home.bean.HomeResult;
import com.zantong.mobile.home.dto.HomeDataDTO;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;

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
        mAtyView.hideLoadingProgress();
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
                .subscribe(new BaseSubscriber<HomeResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.homePageError(e.getMessage());
                    }

                    @Override
                    public void doNext(HomeResult result) {
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
        params.setUserId(mRepository.getDefaultRASUserID());
        return params;
    }

}
