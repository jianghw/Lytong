package com.zantong.mobile.presenter.map;

import android.support.annotation.NonNull;

import com.zantong.mobile.contract.IBaiduMapContract;
import com.zantong.mobile.map.bean.GasStationDetailResult;
import com.zantong.mobile.map.bean.GasStationResult;
import com.zantong.mobile.map.bean.YearCheckDetailResult;
import com.zantong.mobile.map.bean.YearCheckResult;
import com.zantong.mobile.map.dto.AnnualDTO;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 地图操作
 */
public class BaiduMapPresenter implements IBaiduMapContract.IBaiduMapPresenter {
    private final RepositoryManager mRepository;
    private final IBaiduMapContract.IBaiduMapView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public BaiduMapPresenter(@NonNull RepositoryManager repositoryManager,
                             @NonNull IBaiduMapContract.IBaiduMapView view) {
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
        mAtyView.dismissLoadingDialog();
        mSubscriptions.clear();
    }

    /**
     * 24.获取年检网点
     */
    @Override
    public void annualInspectionList() {
        Subscription subscription = mRepository.annualInspectionList(getAnnualDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<YearCheckResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.annualInspectionListError(e.getMessage());
                    }

                    @Override
                    public void doNext(YearCheckResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.annualInspectionListSucceed(result);
                        } else {
                            mAtyView.annualInspectionListError(
                                    result != null ? result.getResponseDesc() : "未知错误(N24)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public AnnualDTO getAnnualDTO() {

        return mAtyView.getAnnualDTO();
    }

    /**
     * 获得年检地点详情
     */
    @Override
    public void annualInspection(int id) {
        Subscription subscription = mRepository.annualInspection(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<YearCheckDetailResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.annualInspectionError(e.getMessage());
                    }

                    @Override
                    public void doNext(YearCheckDetailResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.annualInspectionSucceed(result);
                        } else {
                            mAtyView.annualInspectionError(
                                    result != null ? result.getResponseDesc() : "未知错误(年检地点详情)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 获得加油站地点详情
     */
    @Override
    public void gasStation(int id) {
        Subscription subscription = mRepository.gasStation(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GasStationDetailResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.gasStationError(e.getMessage());
                    }

                    @Override
                    public void doNext(GasStationDetailResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.gasStationSucceed(result);
                        } else {
                            mAtyView.gasStationError(
                                    result != null ? result.getResponseDesc() : "未知错误(加油站地点)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 23.获取加油网点
     */
    @Override
    public void gasStationList() {
        Subscription subscription = mRepository.gasStationList(getAnnualDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GasStationResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.gasStationListError(e.getMessage());
                    }

                    @Override
                    public void doNext(GasStationResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.gasStationListSucceed(result);
                        } else {
                            mAtyView.gasStationListError(
                                    result != null ? result.getResponseDesc() : "未知错误(N23)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
