
package com.zantong.mobilecttx.fahrschule_p;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

import com.tzly.ctcyh.router.bean.response.SubjectGoodsResponse;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名
 * Update by:
 * Update day:
 */
public class SubjectIntensifyPresenter
        implements ISubjectIntensifyContract.ISubjectIntensifyPresenter {

    private final RepositoryManager mRepository;
    private final ISubjectIntensifyContract.ISubjectIntensifyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public SubjectIntensifyPresenter(@NonNull RepositoryManager repositoryManager,
                                     @NonNull ISubjectIntensifyContract.ISubjectIntensifyView view) {
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
     * 22.获取商品
     */
    @Override
    public void getGoods() {
        Subscription subscription = mRepository.getGoods("4")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SubjectGoodsResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.getGoodsError(e.getMessage());
                    }

                    @Override
                    public void doNext(SubjectGoodsResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getGoodsSucceed(result);
                        } else {
                            mAtyView.getGoodsError(result != null
                                    ? result.getResponseDesc() : "未知错误(N22)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
