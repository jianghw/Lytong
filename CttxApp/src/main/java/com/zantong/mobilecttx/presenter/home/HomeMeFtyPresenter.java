
package com.zantong.mobilecttx.presenter.home;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.contract.IHomeMeFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.order.bean.CouponFragmentResponse;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;

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
        Subscription subscription = mRepository.usrCouponInfo(LoginData.getInstance().userID, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponFragmentResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getCouponCountError(e.getMessage());
                    }

                    @Override
                    public void doNext(CouponFragmentResponse result) {
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
                .subscribe(new BaseSubscriber<MessageCountResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.countMessageDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(MessageCountResponse result) {
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

    /**
     * 13.判断是否为司机
     */
    @Override
    public void getDriverCoach() {
        Subscription subscription = mRepository.getDriverCoach(initUserPhone())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<DriverCoachResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.driverCoachError(e.getMessage());
                    }

                    @Override
                    public void doNext(DriverCoachResponse result) {
                        if (result != null && result.getResponseCode() == 2000)
                            mAtyView.driverCoachSucceed(result);
                        else
                            mAtyView.driverCoachError(
                                    result != null ? result.getResponseDesc() : "未知错误N13");
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserPhone() {
        return mRepository.getDefaultUserPhone();
    }
}
