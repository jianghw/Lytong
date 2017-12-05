package com.tzly.ctcyh.cargo.camera_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.cargo.bean.BaseResponse;
import com.tzly.ctcyh.cargo.bean.request.BindCarBean;
import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingBean;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.data_m.BaseSubscriber;
import com.tzly.ctcyh.cargo.data_m.CargoDataManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class CameraPresenter implements ICameraContract.ICameraPresenter {

    private final CargoDataManager mRepository;
    private final ICameraContract.ICameraView mContractView;
    private final CompositeSubscription mSubscriptions;

    public CameraPresenter(@NonNull CargoDataManager payDataManager,
                           @NonNull ICameraContract.ICameraView view) {
        mRepository = payDataManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {}

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 驾驶证
     * Name   姓名
     * Num    身份证号
     * Sex     性别
     * Birt     生日
     * Addr    地址
     * Issue    初次领证日期
     * ValidPeriod 有效期
     * Nation   国籍
     * DrivingType  准驾车型
     * RegisterDate 有效起始日期
     */
    @Override
    public void bindingDriving(String drivingDTO) {
        BindDrivingBean drivingBean = new Gson().fromJson(drivingDTO, BindDrivingBean.class);

        BindDrivingDTO bindDrivingDTO = new BindDrivingDTO();
        bindDrivingDTO.setName(drivingBean.getName());
        bindDrivingDTO.setLicenseno(drivingBean.getNum());
        bindDrivingDTO.setSex(drivingBean.getSex().contains("男") ? "0" : "1");
        bindDrivingDTO.setDateOfBirth(drivingBean.getBirt());
        bindDrivingDTO.setAddress(drivingBean.getAddr());
        bindDrivingDTO.setDateOfFirstIssue(drivingBean.getIssue());
        bindDrivingDTO.setValidPeriodEnd(drivingBean.getValidPeriod());
        bindDrivingDTO.setNationality(drivingBean.getNation().contains("中国") ? "1" : "2");
        bindDrivingDTO.setAllowType(drivingBean.getDrivingType());
        bindDrivingDTO.setValidPeriodStart(drivingBean.getRegisterDate());

        bindDrivingDTO.setUserId(mRepository.getUserID());

        Subscription subscription = mRepository.bindingDriving(bindDrivingDTO)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doNext(BaseResponse response) {}
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 行驶证
     * 号牌号码： getCardNo()
     * 车辆类型： getVechieleType()
     * 所有人：   getName())
     * 住址：     getAddress()
     * 使用性质： getUseCharace()
     * 品牌型号： getModel()
     * 车辆识别代码： getVin()
     * 发动机号码 getEngine_pn()
     * 注册日期： getRegisterdate()
     * 发证日期： getIssuaedate()
     */
    @Override
    public void addVehicleLicense(String carDTO) {
        BindCarBean carBean = new Gson().fromJson(carDTO, BindCarBean.class);

        BindCarDTO bindCarDTO = new BindCarDTO();
        bindCarDTO.setPlateNo(mRepository.getRAStr(carBean.getCardNo()));
        bindCarDTO.setVehicleType(carBean.getVechieleType());
        bindCarDTO.setOwnerName(carBean.getName());
        bindCarDTO.setAddress(carBean.getAddr());
        bindCarDTO.setUseCharacter(carBean.getUseCharace());
        bindCarDTO.setCarModel(carBean.getModel());
        bindCarDTO.setVin(carBean.getVin());
        bindCarDTO.setEngineNo(mRepository.getRAStr(carBean.getEnginePN()));
        bindCarDTO.setRegisterDate(carBean.getRegisterDate());
        bindCarDTO.setIssueDate(carBean.getIssueDate());
        bindCarDTO.setUsrnum(mRepository.getRASUserID());

        Subscription subscription = mRepository.addVehicleLicense(bindCarDTO)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override

                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doNext(BaseResponse response) {}
                });
        mSubscriptions.add(subscription);
    }


}
