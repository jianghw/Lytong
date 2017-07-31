
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.fahrschule.ISparringSubscribeContract;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名
 * Update by:
 * Update day:
 */
public class SparringSubscribePresenter
        implements ISparringSubscribeContract.ISparringSubscribePresenter {

    private final RepositoryManager mRepository;
    private final ISparringSubscribeContract.ISparringSubscribeView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public SparringSubscribePresenter(@NonNull RepositoryManager repositoryManager,
                                      @NonNull ISparringSubscribeContract.ISparringSubscribeView view) {
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


}
