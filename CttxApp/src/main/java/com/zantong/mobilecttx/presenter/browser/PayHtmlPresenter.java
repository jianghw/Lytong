
package com.zantong.mobilecttx.presenter.browser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.bean.BaseResponse;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.contract.browser.IPayHtmlContract;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.weizhang.bean.ViolationNum;
import com.zantong.mobilecttx.weizhang.bean.ViolationNumBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseTestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 支付
 * Update by:
 * Update day:
 */
public class PayHtmlPresenter
        implements IPayHtmlContract.IPayHtmlPresenter {

    private final RepositoryManager mRepository;
    private final IPayHtmlContract.IPayHtmlView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public PayHtmlPresenter(@NonNull RepositoryManager repositoryManager,
                            @NonNull IPayHtmlContract.IPayHtmlView view) {
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
     * 定时任务
     */
    public void orderDetail() {
        Subscription subscription = Observable.interval(2, 3000, TimeUnit.MILLISECONDS)
                .take(4)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Long>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(Long aLong) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void numberedQuery() {
        Subscription subscription = mRepository.numberedQuery(initLicenseFileNumDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ViolationNumBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAtyView.numberedQueryError(e.getMessage() != null ? e.getMessage() : "未知错误(V003)");
                    }

                    @Override
                    public void onNext(ViolationNumBean result) {
                        ViolationNum violationNum = result.getRspInfo();

                        List<ViolationNum> list = new ArrayList<>();
                        list.add(violationNum);
                        updateState(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public String initLicenseFileNumDTO() {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.v003.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LicenseTestDTO bean = new LicenseTestDTO();
        String fileNum = mAtyView.getViolationNum();

        bean.setViolationnum(fileNum);
        bean.setToken(mRepository.getRASUserID());

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 46.更新违章缴费状态
     *
     * @param violationUpdateDTO
     */
    public void updateState(List<ViolationNum> violationUpdateDTO) {
        Subscription subscription = mRepository.updateState(violationUpdateDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.updateStateError(e.getMessage() != null ? e.getMessage() : "未知错误(46)");
                    }

                    @Override
                    public void doNext(BaseResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.updateStateSucceed(result);
                        } else {
                            mAtyView.updateStateError(result != null ? result.getResponseDesc() : "未知错误(46)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
