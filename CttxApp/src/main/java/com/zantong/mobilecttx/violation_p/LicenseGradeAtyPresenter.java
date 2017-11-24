package com.zantong.mobilecttx.violation_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

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

    public LicenseGradeAtyPresenter(@NonNull RepositoryManager repositoryManager,
                                    @NonNull ILicenseGradeAtyContract.ILicenseGradeAtyView view) {
        mRepository = repositoryManager;
        mView = view;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这
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
                    mView.onShowDefaultData();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LicenseResponseBean>() {
                    @Override
                    public void doCompleted() {}

                    @Override
                    public void doError(Throwable e) {
                        mView.driverLicenseCheckGradeError(e.getMessage());
                    }

                    @Override
                    public void doNext(LicenseResponseBean responseBean) {
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

        String fileNum = mRepository.getRASByStr(bean.getFilenum());
        LicenseFileNumDTO fileNumDTO = new LicenseFileNumDTO();
        fileNumDTO.setFilenum(fileNum);
        fileNumDTO.setStrtdt(bean.getStrtdt());
        fileNumDTO.setEnddt(bean.getEnddt());

        dto.setReqInfo(fileNumDTO);
        return new Gson().toJson(dto);
    }

}

