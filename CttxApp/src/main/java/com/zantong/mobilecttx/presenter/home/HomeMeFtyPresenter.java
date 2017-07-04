
package com.zantong.mobilecttx.presenter.home;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.interf.IHomeMeFtyContract;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.user.bean.CouponFragmentResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 畅通页面 p
 * Update by:
 * Update day:
 */
public class HomeMeFtyPresenter implements IHomeMeFtyContract.IHomeMeFtyPresenter {

    private final RepositoryManager mRepository;
    private final IHomeMeFtyContract.IHomeMeFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public HomeMeFtyPresenter(@NonNull RepositoryManager repositoryManager,
                              @NonNull IHomeMeFtyContract.IHomeMeFtyView view) {
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
        mSubscriptions.clear();
    }

    @Override
    public void getCouponCount() {
        Subscription subscription = mRepository.usrCouponInfo(PublicData.getInstance().userID, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponFragmentResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CouponFragmentResult result) {
                        if (result.getResponseCode() == 2000) {
//                            if (result.getData() != null) {
//                                mYouHui.setText(String.valueOf(result.getData().getCouponList().size()));
//                            } else {
//                                ToastUtils.toastShort(result.getResponseDesc());
//                            }
                        }
                    }
                });

    }

    @Override
    public void getUnReadMsgCount() {

    }
}
