
package com.zantong.mobilecttx.share_p;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountResponse;
import com.zantong.mobilecttx.router.MainRouter;

import java.util.Random;

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
        mAtyView.dismissLoading();
        mSubscriptions.clear();
    }

    @Override
    public String getType() {
        return mAtyView.getType();
    }

    /**
     * 测试用
     */
    @Override
    public String getPhone() {
        boolean positon = new Random().nextBoolean();
        if (BuildConfig.isDeta && positon) {
            return "18811025031";
        } else if (BuildConfig.isDeta) {
            return "18811025033";
        } else {
            return MainRouter.getUserPhoenum();
        }
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
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordCountResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.recordCountError(e.getMessage());
                    }

                    @Override
                    public void doNext(RecordCountResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.recordCountSucceed(result);
                        } else {
                            mAtyView.recordCountError(result != null
                                    ? result.getResponseDesc() : "未知错误(N.7)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 分享  新
     */
    @Override
    public void getStatisticsCount() {
        Subscription subscription = mRepository.getStatisticsCount(getPhone())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<StatistCountResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.recordCountError(e.getMessage());
                    }

                    @Override
                    public void doNext(StatistCountResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.recordCountSucceed(result);
                        } else {
                            mAtyView.recordCountError(result != null
                                    ? result.getResponseDesc() : "未知错误(StatisticsCount)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
