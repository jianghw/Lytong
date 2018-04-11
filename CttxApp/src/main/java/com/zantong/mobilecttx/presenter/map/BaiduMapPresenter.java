package com.zantong.mobilecttx.presenter.map;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IBaiduMapContract;
import com.zantong.mobilecttx.map.bean.GasStation;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.GasStationResponse;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.map.dto.AnnualDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 地图操作
 */
public class BaiduMapPresenter implements IBaiduMapContract.IBaiduMapPresenter {
    private final RepositoryManager mRepository;
    private final IBaiduMapContract.IBaiduMapView mContractView;
    private final CompositeSubscription mSubscriptions;

    public BaiduMapPresenter(@NonNull RepositoryManager repositoryManager,
                             @NonNull IBaiduMapContract.IBaiduMapView view) {
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
                        mContractView.annualInspectionListError(e.getMessage());
                    }

                    @Override
                    public void doNext(YearCheckResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.annualInspectionListSucceed(result);
                        } else {
                            mContractView.annualInspectionListError(
                                    result != null ? result.getResponseDesc() : "未知错误(N24)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public AnnualDTO getAnnualDTO() {

        return mContractView.getAnnualDTO();
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
                        mContractView.annualInspectionError(e.getMessage());
                    }

                    @Override
                    public void doNext(YearCheckDetailResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.annualInspectionSucceed(result);
                        } else {
                            mContractView.annualInspectionError(
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
                        mContractView.gasStationError(e.getMessage());
                    }

                    @Override
                    public void doNext(GasStationDetailResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.gasStationSucceed(result);
                        } else {
                            mContractView.gasStationError(
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
                        mContractView.gasStationListError(e.getMessage());
                    }

                    @Override
                    public void doNext(GasStationResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {

                            boolean check = mContractView.isCheckNinetyFour();
                            if (check) {
                                filterNinetyData(result);
                            } else//不过滤
                                mContractView.gasStationListSucceed(result.getData());
                        } else {
                            mContractView.gasStationListError(
                                    result != null ? result.getResponseDesc() : "未知错误(N23)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    protected void filterNinetyData(GasStationResponse result) {
        Subscription subscription = Observable.just(result)
                .filter(new Func1<GasStationResponse, Boolean>() {
                    @Override
                    public Boolean call(GasStationResponse stationResponse) {
                        return null != stationResponse;
                    }
                })
                .map(new Func1<GasStationResponse, List<GasStation>>() {
                    @Override
                    public List<GasStation> call(GasStationResponse response) {
                        return response.getData();
                    }
                })
                .flatMap(new Func1<List<GasStation>, Observable<GasStation>>() {
                    @Override
                    public Observable<GasStation> call(List<GasStation> gasStations) {
                        return Observable.from(gasStations);
                    }
                })
                .filter(new Func1<GasStation, Boolean>() {
                    @Override
                    public Boolean call(GasStation gasStation) {
                        return gasStation.isHasNinetyFour();
                    }
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<GasStation>>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {

                    }

                    @Override
                    public void doNext(List<GasStation> stations) {
                        mContractView.gasStationListSucceed(stations);
                    }
                });
        mSubscriptions.add(subscription);
    }


}
