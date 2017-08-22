package com.zantong.mobilecttx.presenter;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.contract.IMegTypeAtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.order.bean.MessageResult;
import com.zantong.mobilecttx.user.bean.MessageType;
import com.zantong.mobilecttx.user.bean.MessageTypeResult;
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
    /**
     * 是否为刷新操作
     */
    private boolean isRefresh = false;

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
                        if (!isRefresh) mView.onShowLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MessageTypeResult>() {
                    @Override
                    public void doCompleted() {
                        isRefresh = true;
                    }

                    @Override
                    public void doError(Throwable e) {
                        mView.findAllMessageError(e.getMessage());
                    }

                    @Override
                    public void doNext(MessageTypeResult messageTypeResult) {
                        if (messageTypeResult != null
                                && messageTypeResult.getResponseCode() == 2000) {
                            mView.findAllMessageSucceed(messageTypeResult);
                        } else {
                            mView.findAllMessageError(messageTypeResult != null
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
                        mView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MessageResult>() {
                    @Override
                    public void doCompleted() {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mView.deleteMessageDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(MessageResult messageResult) {
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
