
package com.zantong.mobile.share_p;

import android.support.annotation.NonNull;

import com.tzly.annual.base.bean.response.StatistCountResult;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名
 * Update by:
 * Update day:
 */
public class SharePresenter implements IShareFtyContract.IShareFtyPresenter {

    private final RepositoryManager mRepository;
    private final IShareFtyContract.IShareFtyView mContentView;
    private final CompositeSubscription mSubscriptions;

    public SharePresenter(@NonNull RepositoryManager repositoryManager,
                          @NonNull IShareFtyContract.IShareFtyView view) {
        mRepository = repositoryManager;
        mContentView = view;
        mSubscriptions = new CompositeSubscription();
        mContentView.setPresenter(this);
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
     * 分享  新
     */
    @Override
    public void getStatisticsCount() {
        Subscription subscription = mRepository.getStatisticsCount(mContentView.getPhone())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<StatistCountResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContentView.statisticsCountError(e.getMessage());
                    }

                    @Override
                    public void doNext(StatistCountResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mContentView.statisticsCountSucceed(result);
                        } else {
                            mContentView.statisticsCountError(result != null
                                    ? result.getResponseDesc() : "未知错误(StatisticsCount)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
