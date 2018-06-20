package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.violation.AdvModuleResponse;
import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.tzly.ctcyh.router.api.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 */

public class AdvActivePresenter implements IAdvActiveContract.IAdvActivePresenter {

    private final RepositoryManager mRepository;
    private final IAdvActiveContract.IAdvActiveView mContractView;
    private final CompositeSubscription mSubscriptions;

    public AdvActivePresenter(@NonNull RepositoryManager payDataManager,
                              @NonNull IAdvActiveContract.IAdvActiveView view) {
        mRepository = payDataManager;
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
     * 查违章小广告
     */
    @Override
    public void findIsValidAdvert() {
        Subscription subscription = mRepository.findIsValidAdvert(mContractView.carNum())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ValidAdvResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.validAdvertError(e.getMessage());
                    }

                    @Override
                    public void doNext(ValidAdvResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.validAdvertSucceed(result);
                        } else {
                            mContractView.validAdvertError(result != null
                                    ? result.getResponseDesc() : "未知错误(优惠信息)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void moduleList() {
        Subscription subscription = mRepository.moduleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<AdvModuleResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.moduleListError(e.getMessage());
                    }

                    @Override
                    public void doNext(AdvModuleResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContractView.moduleListSucceed(result);
                        } else {
                            mContractView.moduleListError(result != null
                                    ? result.getResponseDesc() : "未知错误(优惠信息)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
