package com.zantong.mobilecttx.presenter.map;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IBaiduMapContract;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.GasStationResponse;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.map.dto.AnnualDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

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
        mAtyView.dismissLoading();
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
                .subscribe(new BaseSubscriber<YearCheckResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.annualInspectionListError(e.getMessage());
                    }

                    @Override
                    public void doNext(YearCheckResponse result) {
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
                .subscribe(new BaseSubscriber<YearCheckDetailResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.annualInspectionError(e.getMessage());
                    }

                    @Override
                    public void doNext(YearCheckDetailResponse result) {
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
                .subscribe(new BaseSubscriber<GasStationDetailResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.gasStationError(e.getMessage());
                    }

                    @Override
                    public void doNext(GasStationDetailResponse result) {
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
                .subscribe(new BaseSubscriber<GasStationResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.gasStationListError(e.getMessage());
                    }

                    @Override
                    public void doNext(GasStationResponse result) {
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
