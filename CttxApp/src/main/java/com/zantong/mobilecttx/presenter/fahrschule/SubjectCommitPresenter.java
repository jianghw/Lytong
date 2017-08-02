
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.fahrschule.ISubjectCommitContract;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
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
                .subscribe(new BaseSubscriber<CreateOrderResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.createOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(CreateOrderResult result) {
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
        orderDTO.setUserNum(mRepository.getDefaultRASUserID());
        orderDTO.setGoodsId(mAtyView.getGoodsId());
        orderDTO.setPrice(mAtyView.getPriceValue());

        orderDTO.setUserName(mAtyView.getEditName());
        orderDTO.setPhone(mAtyView.getEditPhone());

        orderDTO.setPayType("1");
        return orderDTO;
    }
}
