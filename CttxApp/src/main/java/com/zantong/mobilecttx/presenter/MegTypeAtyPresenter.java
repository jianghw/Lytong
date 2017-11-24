package com.zantong.mobilecttx.presenter;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.contract.IMegTypeAtyContract;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.user.bean.MessageType;
import com.zantong.mobilecttx.user.bean.MessageTypeResponse;
import com.zantong.mobilecttx.user.dto.MegDTO;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/4/26.
 * 消息总页面逻辑处理层
 */

public class MegTypeAtyPresenter implements IMegTypeAtyContract.IMegTypeAtyPresenter {
    private final RepositoryManager mRepository;
    private final IMegTypeAtyContract.IMegTypeAtyView mView;
    private final CompositeSubscription mSubscriptions;

    public MegTypeAtyPresenter(@NonNull RepositoryManager repositoryManager,
                               @NonNull IMegTypeAtyContract.IMegTypeAtyView view) {
        mRepository = repositoryManager;
        mView = view;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
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
    public void messageFindAll() {
        Subscription subscription = mRepository.messageFindAll(initBaseDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MessageTypeResponse>() {
                    @Override
                    public void doCompleted() {
                        mView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mView.responseError(e.getMessage());
                        mView.dismissLoading();
                    }

                    @Override
                    public void doNext(MessageTypeResponse messageTypeResult) {
                        if (messageTypeResult != null
                                && messageTypeResult.getResponseCode() == 2000) {
                            mView.responseSucceed(messageTypeResult);
                        } else {
                            mView.responseError(messageTypeResult != null
                                    ? messageTypeResult.getResponseDesc() : "未知错误(2.4.20.1)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public BaseDTO initBaseDTO() {
        return mRepository.initBaseDTO();
    }

    @Override
    public void deleteMessageDetail(MessageType meg, final int position) {
        Subscription subscription = mRepository.deleteMessageDetail(initDeleteMegDTO(meg))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MessageResponse>() {
                    @Override
                    public void doCompleted() {
                        mView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mView.deleteMessageDetailError(e.getMessage());
                        mView.dismissLoading();
                    }

                    @Override
                    public void doNext(MessageResponse messageResult) {
                        if (messageResult != null
                                && messageResult.getResponseCode() == 2000) {
                            mView.deleteMessageDetailSucceed(messageResult, position);
                        } else {
                            mView.deleteMessageDetailError(messageResult != null
                                    ? messageResult.getResponseDesc() : "未知错误(2.4.24)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public MegDTO initDeleteMegDTO(MessageType meg) {
        MegDTO dto = mRepository.initMegDTO();
        String id = meg.getMessageDetailId();
        dto.setId(id);
        return dto;
    }
}
