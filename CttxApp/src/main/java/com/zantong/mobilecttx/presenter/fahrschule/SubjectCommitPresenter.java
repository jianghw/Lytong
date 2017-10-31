
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectCommitContract;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

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
public class SubjectCommitPresenter
        implements ISubjectCommitContract.ISubjectCommitPresenter {

    private final RepositoryManager mRepository;
    private final ISubjectCommitContract.ISubjectCommitView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public SubjectCommitPresenter(@NonNull RepositoryManager repositoryManager,
                                  @NonNull ISubjectCommitContract.ISubjectCommitView view) {
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

    /**
     * 2.创建订单
     */
    @Override
    public void createOrder() {
        Subscription subscription = mRepository.createOrder(getCreateOrder())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CreateOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.createOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(CreateOrderResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.createOrderSucceed(result);
                        } else {
                            mAtyView.createOrderError(result != null
                                    ? result.getResponseDesc() : "未知错误(N2)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public CreateOrderDTO getCreateOrder() {
        CreateOrderDTO orderDTO = new CreateOrderDTO();
        orderDTO.setType("4");
        orderDTO.setUserNum(initUserId());
        orderDTO.setGoodsId(mAtyView.getGoodsId());
        orderDTO.setPrice(mAtyView.getPriceValue());

        orderDTO.setUserName(mAtyView.getEditName());
        orderDTO.setPhone(mAtyView.getEditPhone());

        orderDTO.setPayType("1");
        return orderDTO;
    }

    /**
     * 57.获取指定类型优惠券
     * 优惠券业务：1 加油充值；2 代驾；3 洗车
     */
    @Override
    public void getCouponByType() {
        Subscription subscription = mRepository.getCouponByType(initUserId(), "4")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RechargeCouponResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.couponByTypeError(e.getMessage());
                    }

                    @Override
                    public void doNext(RechargeCouponResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.couponByTypeSucceed(result);
                        } else {
                            mAtyView.couponByTypeError(result != null
                                    ? result.getResponseDesc() : "未知错误(57)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserId() {
        return mRepository.getDefaultUserID();
    }
}
