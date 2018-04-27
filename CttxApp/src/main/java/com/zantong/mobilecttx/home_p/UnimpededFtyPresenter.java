
package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.news.NewsInfoResponse;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.base.bean.ModuleBannerResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerResponse;
import com.zantong.mobilecttx.home.bean.VersionResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.home.dto.VersionDTO;
import com.zantong.mobilecttx.router.MainRouter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Description: 畅通页面 p
 */
public class UnimpededFtyPresenter implements IUnimpededFtyContract.IUnimpededFtyPresenter {

    private final RepositoryManager mRepository;
    private final IUnimpededFtyContract.IUnimpededFtyView mContractView;
    private final CompositeSubscription mSubscriptions;

    public UnimpededFtyPresenter(@NonNull RepositoryManager repositoryManager,
                                 @NonNull IUnimpededFtyContract.IUnimpededFtyView view) {
        mRepository = repositoryManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 是否提供活动
     */
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
                        mContractView.dataError(e.getMessage());
                    }

                    @Override
                    public void doNext(IndexLayerResponse result) {
                        if (result != null && result.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.indexLayerSucceed(result);
                        } else {
                            mContractView.dataError(result != null
                                    ? result.getResponseDesc() : "未知错误(是否提供活动)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 坑位
     */
    @Override
    public void getBanner() {
        Subscription subscription = mRepository.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ModuleBannerResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dataError(e.getMessage());
                    }

                    @Override
                    public void doNext(ModuleBannerResponse result) {
                        if (result != null && result.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.bannerSucceed(result);
                        } else {
                            mContractView.dataError(result != null
                                    ? result.getResponseDesc() : "未知错误(banner)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
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
                        mContractView.dataError(e.getMessage());
                    }

                    @Override
                    public void doNext(HomeResponse result) {
                        if (result != null && result.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.homePageSucceed(result);
                        } else {
                            mContractView.dataError(result != null
                                    ? result.getResponseDesc() : "未知错误(homePage)");
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
                        mContractView.remoteCarInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(HomeCarResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.getTextNoticeInfo(result);
                        } else {
                            mContractView.remoteCarInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(NoticeInfo)");
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
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

    /**
     * 获取版本信息
     */
    @Override
    public void versionInfo() {
        VersionDTO versionDTO = new VersionDTO();
        versionDTO.setVersionType("0");
        Subscription subscription = mRepository.versionInfo(versionDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<VersionResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.versionInfoError();
                    }

                    @Override
                    public void doNext(VersionResponse result) {
                        if (result != null && result.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.versionInfoSucceed(result);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 资讯列表news/findByType
     */
    @Override
    public void findByType() {
        Subscription subscription = mRepository.findByType(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<NewsInfoResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.findByTypeError(e.getMessage());
                    }

                    @Override
                    public void doNext(NewsInfoResponse result) {
                        if (result != null && result.getResponseCode() ==
                                MainGlobal.Response.base_succeed) {
                            mContractView.findByTypeSucceed(result);
                        } else {
                            mContractView.findByTypeError(result != null
                                    ? result.getResponseDesc() : "未知错误(findByType)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
