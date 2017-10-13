
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.application.MemoryData;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;
import com.zantong.mobilecttx.contract.IFahrschuleShareFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

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
public class FahrschuleSharePresenter
        implements IFahrschuleShareFtyContract.IFahrschuleShareFtyPresenter {

    private final RepositoryManager mRepository;
    private final IFahrschuleShareFtyContract.IFahrschuleShareFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public FahrschuleSharePresenter(@NonNull RepositoryManager repositoryManager,
                                    @NonNull IFahrschuleShareFtyContract.IFahrschuleShareFtyView view) {
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

    @Override
    public String getType() {
        return mAtyView.getType();
    }

    @Override
    public String getPhone() {
        return MemoryData.getInstance().mLoginInfoBean!=null
                ?MemoryData.getInstance().mLoginInfoBean.getPhoenum():"00000000000000";
    }
    /**
     * 7.获取用户指定活动的统计总数
     */
    @Override
    public void getRecordCount() {
        Subscription subscription = mRepository.getRecordCount(getType(), getPhone())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordCountResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getRecordCountError(e.getMessage());
                    }

                    @Override
                    public void doNext(RecordCountResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getRecordCountSucceed(result);
                        } else {
                            mAtyView.getRecordCountError(result != null
                                    ? result.getResponseDesc() : "未知错误(N.7)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
