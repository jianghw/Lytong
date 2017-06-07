package com.zantong.mobilecttx.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.interf.ILicenseGradeAtyContract;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/4/26.
 * 消息总页面逻辑处理层
 */

public class LicenseGradeAtyPresenter implements ILicenseGradeAtyContract.ILicenseGradeAtyPresenter {
    private final RepositoryManager mRepository;
    private final ILicenseGradeAtyContract.ILicenseGradeAtyView mView;
    private final CompositeSubscription mSubscriptions;
    /**
     * 是否为刷新操作
     */
    private boolean isRefresh = false;

    public LicenseGradeAtyPresenter(@NonNull RepositoryManager repositoryManager,
                                    @NonNull ILicenseGradeAtyContract.ILicenseGradeAtyView view) {
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

    /**
     * 1、加载显示布局 成功后不用再调用
     */
    @Override
    public void driverLicenseCheckGrade() {
        Subscription subscription = mRepository.driverLicenseCheckGrade(initLicenseFileNumDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) mView.onShowDefaultData();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LicenseResponseBean>() {
                    @Override
                    public void onCompleted() {
                        isRefresh = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.driverLicenseCheckGradeError("网络出错,请检查网络是否连接");
                        isRefresh = false;
                    }

                    @Override
                    public void onNext(LicenseResponseBean responseBean) {
                        if (responseBean != null
                                && responseBean.getSYS_HEAD().getReturnCode().equals("000000")) {
                            mView.driverLicenseCheckGradeSucceed(responseBean);
                        } else {
                            mView.driverLicenseCheckGradeError(responseBean != null
                                    ? responseBean.getSYS_HEAD().getReturnMessage() : "未知错误(cip.cfc.v001.01)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 保存数据
     */
    @Override
    public void saveLicenseFileNumDTO() {
        mRepository.saveLicenseFileNumDTO(mView.initLicenseFileNumDTO());
    }

    /**
     * 构建请求bean
     */
    @Override
    public String initLicenseFileNumDTO() {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.v001.01");
        dto.setSYS_HEAD(requestHeadDTO);
        LicenseFileNumDTO bean = mView.initLicenseFileNumDTO();
        LogUtils.e(bean.getFilenum());
        LogUtils.e(bean.getStrtdt());
        LogUtils.e(bean.getEnddt());

        String fileNum = mRepository.getStrByEncryption(bean.getFilenum());
        LicenseFileNumDTO fileNumDTO = new LicenseFileNumDTO();
        fileNumDTO.setFilenum(fileNum);
        fileNumDTO.setStrtdt(bean.getStrtdt());
        fileNumDTO.setEnddt(bean.getEnddt());

        dto.setReqInfo(fileNumDTO);
        return new Gson().toJson(dto);
    }

}

