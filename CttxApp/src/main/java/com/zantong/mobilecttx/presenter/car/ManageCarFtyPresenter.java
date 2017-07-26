
package com.zantong.mobilecttx.presenter.car;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.home.bean.HomeCarResult;
import com.zantong.mobilecttx.interf.IManageCarFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 车辆管理 p
 * Update by:
 * Update day:
 */
public class ManageCarFtyPresenter implements IManageCarFtyContract.IManageCarFtyPresenter {

    private final RepositoryManager mRepository;
    private final IManageCarFtyContract.IManageCarFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public ManageCarFtyPresenter(@NonNull RepositoryManager repositoryManager,
                                 @NonNull IManageCarFtyContract.IManageCarFtyView view) {
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
     * 新获取违章信息
     */
    @Override
    public void getTextNoticeInfo() {
        Subscription subscription = mRepository.getTextNoticeInfo(mRepository.getDefaultRASUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<HomeCarResult>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.textNoticeInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(HomeCarResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.textNoticeInfoSucceed(result);
                        } else {
                            mAtyView.textNoticeInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(NoticeInfo)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
