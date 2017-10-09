package com.zantong.mobile.presenter;

import android.support.annotation.NonNull;

import com.zantong.mobile.contract.IMegDetailAtyContract;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;
import com.zantong.mobile.user.bean.MessageDetailResult;
import com.zantong.mobile.user.dto.MessageDetailDTO;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/4/26.
 * 消息详情页面逻辑处理层
 */

public class MegDetailAtyPresenter implements IMegDetailAtyContract.IMegDetailAtyPresenter {
    private final RepositoryManager mRepository;
    private final IMegDetailAtyContract.IMegDetailAtyView mView;
    private final CompositeSubscription mSubscriptions;
    /**
     * 是否为刷新操作
     */
    private boolean isRefresh = false;

    public MegDetailAtyPresenter(@NonNull RepositoryManager repositoryManager,
                                 @NonNull IMegDetailAtyContract.IMegDetailAtyView view) {
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
    public void findMessageDetail() {
        Subscription subscription = mRepository.findMessageDetail(initMessageDetailDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) mView.onShowLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MessageDetailResult>() {
                    @Override
                    public void doCompleted() {
                        isRefresh = true;
                    }

                    @Override
                    public void doError(Throwable e) {
                        mView.findMessageDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(MessageDetailResult result) {
                        if (result != null
                                && result.getResponseCode() == 2000) {
                            mView.findMessageDetailSucceed(result);
                        } else {
                            mView.findMessageDetailError(result != null
                                    ? result.getResponseDesc() : "未知错误(2.4.21)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public MessageDetailDTO initMessageDetailDTO() {
        MessageDetailDTO dto = mRepository.initMessageDetailDTO();
        int id = mView.getIdByArguments();
        dto.setId(id);
        return dto;
    }

}
