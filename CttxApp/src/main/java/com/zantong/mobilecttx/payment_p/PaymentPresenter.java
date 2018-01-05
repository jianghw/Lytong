package com.zantong.mobilecttx.payment_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.RspInfoBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import drawthink.expandablerecyclerview.bean.RecyclerViewData;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class PaymentPresenter implements IPaymentContract.IPaymentPresenter {

    private final RepositoryManager mRepository;
    private final IPaymentContract.IPaymentView mContractView;
    private final CompositeSubscription mSubscriptions;

    public PaymentPresenter(@NonNull RepositoryManager payDataManager,
                            @NonNull IPaymentContract.IPaymentView view) {
        mRepository = payDataManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }


    @Override
    public void bank_v001_01() {
        Subscription subscription = mRepository
                .driverLicenseCheckGrade(initLicenseFileNumDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LicenseResponseBean>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.responseError(e.getMessage());
                    }

                    @Override
                    public void doNext(LicenseResponseBean responseBean) {
                        if (responseBean != null
                                && responseBean.getSYS_HEAD().getReturnCode().equals("000000")) {
                            mContractView.responseSucceed(responseBean);
                        } else {
                            mContractView.responseError(responseBean != null
                                    ? responseBean.getSYS_HEAD().getReturnMessage()
                                    : "未知错误(cip.cfc.v001.01)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 构建请求bean
     */
    @Override
    public String initLicenseFileNumDTO() {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.initServiceCodeDTO("cip.cfc.v001.01");
        dto.setSYS_HEAD(requestHeadDTO);
        LicenseFileNumDTO bean = mContractView.licenseFileNumDTO();

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

    /**
     * 数据处理
     * 1、日期过滤时间 支付时间
     */
    @Override
    public void dataProcessing(List<RspInfoBean.ViolationInfoBean> mTempDatas, final String month) {
        final List<RecyclerViewData> mCarDatas = new ArrayList<>();

        Subscription subscription = Observable
                .from(mTempDatas)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<RspInfoBean.ViolationInfoBean, Boolean>() {
                    @Override
                    public Boolean call(RspInfoBean.ViolationInfoBean infoBean) {
                        return null != infoBean;
                    }
                })
                .filter(new Func1<RspInfoBean.ViolationInfoBean, Boolean>() {
                    @Override
                    public Boolean call(RspInfoBean.ViolationInfoBean violationInfoBean) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        int d = month.equals("1个月") ? -1 : month.equals("6个月") ? -6 : month.equals("12个月") ? -12 : -12;
                        calendar.add(Calendar.MONTH, d);  //设置为前3月

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
                        Date payDate = new Date();
                        try {
                            payDate = sdf.parse(violationInfoBean.getPaydate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar c = Calendar.getInstance();
                        c.setTime(payDate);

                        return c.getTimeInMillis() >= calendar.getTimeInMillis();
                    }
                })
                .switchIfEmpty(Observable.<RspInfoBean.ViolationInfoBean>error(new Throwable("数据为空")))
                .groupBy(new Func1<RspInfoBean.ViolationInfoBean, String>() {
                    @Override
                    public String call(RspInfoBean.ViolationInfoBean keySelector) {
                        return keySelector.getCarnum();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<GroupedObservable<String, RspInfoBean.ViolationInfoBean>>() {
                            @Override
                            public void call(GroupedObservable<String, RspInfoBean.ViolationInfoBean> groupedObservable) {

                                groupedData(groupedObservable, mCarDatas);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable e) {
                                mContractView.dismissLoading();
                                mContractView.dataProcessingError(e.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    private void groupedData(GroupedObservable<String,
            RspInfoBean.ViolationInfoBean> groupedObservable, final List<RecyclerViewData> mCarDatas) {
        groupedObservable.toList().subscribe(new Subscriber<List<RspInfoBean.ViolationInfoBean>>() {
            @Override
            public void onCompleted() {
                mContractView.dismissLoading();
                mContractView.processingSucceed(mCarDatas);
            }

            @Override
            public void onError(Throwable e) {
                mContractView.dismissLoading();
                mContractView.dataProcessingError(e.getMessage());
            }

            @Override
            public void onNext(List<RspInfoBean.ViolationInfoBean> infoBeanList) {
                mCarDatas.add(new RecyclerViewData(infoBeanList.get(0).getCarnum(), infoBeanList, false));
            }
        });
    }
}
