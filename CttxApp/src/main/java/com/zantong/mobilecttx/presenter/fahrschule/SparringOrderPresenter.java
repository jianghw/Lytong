
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.fahrschule.ISparringOrderContract;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名
 * Update by:
 * Update day:
 */
public class SparringOrderPresenter
        implements ISparringOrderContract.ISparringOrderPresenter {

    private final RepositoryManager mRepository;
    private final ISparringOrderContract.ISparringOrderView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public SparringOrderPresenter(@NonNull RepositoryManager repositoryManager,
                                  @NonNull ISparringOrderContract.ISparringOrderView view) {
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
