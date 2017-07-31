
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.contract.fahrschule.ISubjectIntensifyContract;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名
 * Update by:
 * Update day:
 */
public class SubjectIntensifyPresenter
        implements ISubjectIntensifyContract.ISubjectIntensifyPresenter {

    private final RepositoryManager mRepository;
    private final ISubjectIntensifyContract.ISubjectIntensifyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public SubjectIntensifyPresenter(@NonNull RepositoryManager repositoryManager,
                                     @NonNull ISubjectIntensifyContract.ISubjectIntensifyView view) {
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
