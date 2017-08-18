package com.zantong.mobilecttx.presenter.map;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IBaiduMapContract;
import com.zantong.mobilecttx.map.bean.YearCheckResult;
import com.zantong.mobilecttx.map.dto.AnnualDTO;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

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
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        mAtyView.showLoadingDialog();
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
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
}
