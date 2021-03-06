
package com.zantong.mobilecttx.presenter.home;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.interf.IHomeMeFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.user.bean.CouponFragmentResult;
import com.zantong.mobilecttx.user.bean.MessageCountResult;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 畅通页面 p
 * Update by:
 * Update day:
 */
public class HomeMeFtyPresenter implements IHomeMeFtyContract.IHomeMeFtyPresenter {

    private final RepositoryManager mRepository;
    private final IHomeMeFtyContract.IHomeMeFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public HomeMeFtyPresenter(@NonNull RepositoryManager repositoryManager,
                              @NonNull IHomeMeFtyContract.IHomeMeFtyView view) {
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
        mSubscriptions.clear();
    }

    @Override
    public void getCouponCount() {
        Subscription subscription = mRepository.usrCouponInfo(PublicData.getInstance().userID, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponFragmentResult>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getCouponCountError(e.getMessage());
                    }

                    @Override
                    public void doNext(CouponFragmentResult result) {
                        if (result != null && result.getResponseCode() == 2000)
                            mAtyView.getCouponCountSucceed(result);
                        else
                            mAtyView.getCouponCountError(
                                    result != null ? result.getResponseDesc() : "未知错误2.4.2");
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getUnReadMsgCount() {
        Subscription subscription = mRepository.countMessageDetail(initBaseDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MessageCountResult>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.countMessageDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(MessageCountResult result) {
                        if (result != null && result.getResponseCode() == 2000)
                            mAtyView.countMessageDetailSucceed(result);
                        else
                            mAtyView.countMessageDetailError(
                                    result != null ? result.getResponseDesc() : "未知错误37");
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public BaseDTO initBaseDTO() {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setUsrId(mRepository.getDefaultRASUserID());
        return baseDTO;
    }
}
