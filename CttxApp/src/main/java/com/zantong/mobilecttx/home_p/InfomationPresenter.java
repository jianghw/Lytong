
package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.news.NewsInfoResponse;
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
public class InfomationPresenter implements InformationContract.InformationPresenter {

    private final RepositoryManager mRepository;
    private final InformationContract.InformationView mContractView;
    private final CompositeSubscription mSubscriptions;

    public InfomationPresenter(@NonNull RepositoryManager repositoryManager,
                               @NonNull InformationContract.InformationView view) {
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
     * 资讯列表news/findByType
     */
    @Override
    public void findByType() {
        Subscription subscription = mRepository.findByType(mContractView.getTypeItem())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<NewsInfoResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.responseError(e.getMessage());
                    }

                    @Override
                    public void doNext(NewsInfoResponse result) {
                        if (result != null && result.getResponseCode() ==
                                MainGlobal.Response.base_succeed) {
                            mContractView.responseSucceed(result);
                        } else {
                            mContractView.responseError(result != null
                                    ? result.getResponseDesc() : "未知错误(findByType)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}