
package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.base.bean.ModuleBannerResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.global.MainGlobal;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Description: 资讯页面
 */
public class HomeInfomationPresenter
        implements IHomeInfomationContract.IHomeInfomationPresenter {

    private final RepositoryManager mRepository;
    private final IHomeInfomationContract.IHomeInfomationView mContractView;
    private final CompositeSubscription mSubscriptions;

    public HomeInfomationPresenter(@NonNull RepositoryManager repositoryManager,
                                   @NonNull IHomeInfomationContract.IHomeInfomationView view) {
        mRepository = repositoryManager;
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
     * 资讯Icons
     */
    @Override
    public void getIcons() {
        Subscription subscription = mRepository.getIcons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ModuleBannerResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.iconsError(e.getMessage());
                    }

                    @Override
                    public void doNext(ModuleBannerResponse result) {
                        if (result != null && result.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.iconsSucceed(result);
                        } else
                            mContractView.iconsError(result != null
                                    ? result.getResponseDesc() : "未知错误(icons)");
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 资讯导航
     */
    @Override
    public void getNavigations() {
        Subscription subscription = mRepository.getNavigations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ModuleBannerResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.navigationError(e.getMessage());
                    }

                    @Override
                    public void doNext(ModuleBannerResponse result) {
                        if (result != null && result.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.navigationSucceed(result);
                        } else
                            mContractView.navigationError(result != null
                                    ? result.getResponseDesc() : "未知错误(naviations)");
                    }
                });
        mSubscriptions.add(subscription);
    }
}