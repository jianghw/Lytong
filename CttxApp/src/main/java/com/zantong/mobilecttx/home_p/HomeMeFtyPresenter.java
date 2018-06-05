
package com.zantong.mobilecttx.home_p;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.java.response.card.CancelCardResponse;
import com.zantong.mobilecttx.base.bean.ValidCountResponse;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
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

    /**
     * 优惠券
     */
    @Override
    public void getCouponCount() {
        Subscription subscription = mRepository
                .usrCouponInfo(mRepository.getRASUserID(), "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponFragmentResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(CouponFragmentResponse result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 未读消息
     */
    @Override
    public void getUnReadMsgCount() {
        Subscription subscription = mRepository
                .countMessageDetail(initBaseDTO())
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
        baseDTO.setUsrId(mRepository.getRASUserID());
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

    @Override
    public void getValidCount() {
        Subscription subscription = mRepository
                .getValidCount(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ValidCountResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.validCountError(e.getMessage());
                    }

                    @Override
                    public void doNext(ValidCountResponse result) {
                        if (result != null && result.getResponseCode() == 2000)
                            mAtyView.validCountSucceed(result);
                        else
                            mAtyView.validCountError(result != null
                                    ? result.getResponseDesc() : "未知错误(validCount)");
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 注销畅通卡
     */
    @Override
    public void cancelCard() {
        Subscription subscription = mRepository.cancelCard(mRepository.getRASUserID(), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CancelCardResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.cancelCardError(e.getMessage());
                    }

                    @Override
                    public void doNext(CancelCardResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.cancelCardSucceed(result);
                        } else {
                            mAtyView.cancelCardError(result.getResponseDesc());
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
