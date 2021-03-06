package com.zantong.mobilecttx.presenter;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.interf.IMegDetailAtyContract;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.user.bean.MessageDetailResult;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;

import rx.Subscriber;
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
                .subscribe(new Subscriber<MessageDetailResult>() {
                    @Override
                    public void onCompleted() {
                        isRefresh = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.findMessageDetailError(e.getMessage());
                    }

                    @Override
                    public void onNext(MessageDetailResult result) {
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
