package com.zantong.mobilecttx.presenter;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.IMegSecondLevelAtyContract;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.user.bean.Meg;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.user.dto.MegDTO;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/4/26.
 * 消息总页面逻辑处理层
 */

public class MegSecondLevelAtyPresenter implements IMegSecondLevelAtyContract.IMegSecondLevelAtyPresenter {
    private final RepositoryManager mRepository;
    private final IMegSecondLevelAtyContract.IMegSecondLevelAtyView mView;
    private final CompositeSubscription mSubscriptions;
    /**
     * 是否为刷新操作
     */
    private boolean isRefresh = false;

    public MegSecondLevelAtyPresenter(@NonNull RepositoryManager repositoryManager,
                                      @NonNull IMegSecondLevelAtyContract.IMegSecondLevelAtyView view) {
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
    public void findMessageDetailByMessageId() {
        Subscription subscription = mRepository.findMessageDetailByMessageId(initMegDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) mView.onShowLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MessageResponse>() {
                    @Override
                    public void onCompleted() {
                        isRefresh = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.findMessageDetailByMessageIdError(e.getMessage());
                    }

                    @Override
                    public void onNext(MessageResponse messageResult) {
                        if (messageResult != null
                                && messageResult.getResponseCode() == 2000) {
                            mView.findMessageDetailByMessageIdSucceed(messageResult);
                        } else {
                            mView.findMessageDetailByMessageIdError(messageResult != null
                                    ? messageResult.getResponseDesc() : "未知错误(2.4.21)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public MegDTO initMegDTO() {
        MegDTO dto = mRepository.initMegDTO();
        String id = mView.getIdByArguments();
        dto.setId(id);
        return dto;
    }

    /**
     * 数据处理过滤出未删除的
     *
     * @param megList
     */
    @Override
    public void processingDataFiltrate(List<Meg> megList) {
        if (megList == null) {
            mView.setListDataResult(null);
        } else {
            Subscription subscription = Observable.from(megList)
                    .filter(new Func1<Meg, Boolean>() {
                        @Override
                        public Boolean call(Meg meg) {
                            return meg != null;
                        }
                    })
                    .filter(new Func1<Meg, Boolean>() {
                        @Override
                        public Boolean call(Meg meg) {
                            return meg.getIsDeleted().equals("0");
                        }
                    })
                    .toList()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Meg>>() {
                        @Override
                        public void call(List<Meg> megs) {
                            mView.setListDataResult(megs);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.findMessageDetailByMessageIdError(throwable != null
                                    ? throwable.getMessage() : "未知错误(2.4.21)");
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    @Override
    public void deleteMessageDetail(Meg meg, final int position) {
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
                .subscribe(new Subscriber<MessageResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.deleteMessageDetailError(e.getMessage());
                    }

                    @Override
                    public void onNext(MessageResponse messageResult) {
                        if (messageResult != null
                                && messageResult.getResponseCode() == 2000) {
                            mView.deleteMessageDetailSucceed(messageResult,position);
                        } else {
                            mView.deleteMessageDetailError(messageResult != null
                                    ? messageResult.getResponseDesc() : "未知错误(2.4.24)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public MegDTO initDeleteMegDTO(Meg meg) {
        MegDTO dto = mRepository.initMegDTO();
        String id = meg.getMessageDetailId();
        dto.setId(id);
        return dto;
    }

}
