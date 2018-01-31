
package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.router.bean.BaseResponse;
import com.zantong.mobilecttx.contract.home.IHomeFavorableFtyContract;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannerResponse;
import com.zantong.mobilecttx.home.bean.ModuleResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.router.MainRouter;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 畅通页面 p
 * Update by:
 * Update day:
 */
public class HomeFavorableFtyPresenter implements IHomeFavorableFtyContract.IHomeFavorableFtyPresenter {

    private final RepositoryManager mRepository;
    private final IHomeFavorableFtyContract.IHomeFavorableFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public HomeFavorableFtyPresenter(@NonNull RepositoryManager repositoryManager,
                                     @NonNull IHomeFavorableFtyContract.IHomeFavorableFtyView view) {
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
        mSubscriptions.clear();
    }

    /**
     * (1 首页banner；2 优惠页banner；3 优惠页中部广告)
     */
    @Override
    public void getBanner() {
        Subscription subscription = mRepository.getBanner("2,3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BannerResponse>() {
                    @Override
                    public void doCompleted() {}

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getBannerError(e.getMessage());
                    }

                    @Override
                    public void doNext(BannerResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            distributeByType(result, "2");
                            distributeByType(result, "3");
                        } else
                            mAtyView.getBannerError(result != null ? result.getResponseDesc() : "未知错误(N1)");
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void distributeByType(BannerResponse result, final String type) {
        Subscription subscription = Observable
                .from(result.getData())
                .filter(new Func1<BannerBean, Boolean>() {
                    @Override
                    public Boolean call(BannerBean bannerBean) {
                        return bannerBean.getType().equals(type);
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BannerBean>() {
                    @Override
                    public void doCompleted() {}

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getBannerError(e.getMessage());
                    }

                    @Override
                    public void doNext(BannerBean bannerBean) {
                        if (type.equals("2"))
                            mAtyView.getBannerSucceed(bannerBean);
                        else if (type.equals("3"))
                            mAtyView.getRewardSucceed(bannerBean);
                        else
                            mAtyView.getBannerError("未知错误,图片类型不存在");
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 25.模块化接口
     */
    @Override
    public void moduleTree() {
        Subscription subscription = mRepository.moduleTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ModuleResponse>() {
                    @Override
                    public void doCompleted() {}

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.responseError(e.getMessage());
                    }

                    @Override
                    public void doNext(ModuleResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.responseSucceed(result);
                        } else
                            mAtyView.responseError(result != null
                                    ? result.getResponseDesc() : "未知错误(N25)");
                    }
                });
        mSubscriptions.add(subscription);
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