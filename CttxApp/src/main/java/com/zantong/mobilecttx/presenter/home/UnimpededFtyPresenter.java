
package com.zantong.mobilecttx.presenter.home;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.contract.IUnimpededFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.user.bean.UserCarsResult;

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
        params.setUserId(mRepository.getDefaultRASUserID());
        return params;
    }

    @Override
    public void getRemoteCarInfo() {
        Subscription subscription = mRepository.getRemoteCarInfo(initUserCarsDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UserCarsResult>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.remoteCarInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(UserCarsResult result) {
                        if (result != null && "000000".equals(result.getSYS_HEAD().getReturnCode())) {
                            mAtyView.getRemoteCarInfoSucceed(result);
                        } else {
                            mAtyView.remoteCarInfoError(result != null
                                    ? result.getSYS_HEAD().getReturnMessage()
                                    : "未知错误(cip.cfc.c003.01)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserCarsDTO() {
        UserCarsDTO params = new UserCarsDTO();
        params.setUsrid(mRepository.getDefaultUserID());

        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.c003.01");

        dto.setSYS_HEAD(requestHeadDTO);
        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }

    /**
     * 新获取违章信息
     */
    @Override
    public void getTextNoticeInfo() {
        Subscription subscription = mRepository.getTextNoticeInfo(mRepository.getDefaultRASUserID())
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

}
