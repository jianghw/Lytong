
package com.zantong.mobilecttx.presenter.car;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarBean;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.car.bean.VehicleLicenseResponse;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.contract.IManageCarFtyContract;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.LogoutDTO;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 车辆管理 p
 * Update by:
 * Update day:
 */
public class ManageCarFtyPresenter implements IManageCarFtyContract.IManageCarFtyPresenter {

    private final RepositoryManager mRepository;
    private final IManageCarFtyContract.IManageCarFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public ManageCarFtyPresenter(@NonNull RepositoryManager repositoryManager,
                                 @NonNull IManageCarFtyContract.IManageCarFtyView view) {
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
     * 新获取违章信息
     */
    @Override
    public void getTextNoticeInfo() {
        Subscription subscription = mRepository
                .getTextNoticeInfo(mRepository.getRASUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<HomeCarResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.textNoticeInfoError(e.getMessage());
                    }

                    @Override
                    public void doNext(HomeCarResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.textNoticeInfoSucceed(result);
                        } else {
                            mAtyView.textNoticeInfoError(result != null
                                    ? result.getResponseDesc() : "未知错误(NoticeInfo)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getRemoteCarInfo() {
        Subscription subscription = mRepository.getRemoteCarInfo(initUserCarsDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UserCarsResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(UserCarsResult result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 不加密
     */
    @Override
    public String initUserCarsDTO() {
        UserCarsDTO params = new UserCarsDTO();
        params.setUsrid(mRepository.getUserID());

        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initServiceCodeDTO("cip.cfc.c003.01");

        dto.setSYS_HEAD(requestHeadDTO);
        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }


    /**
     * cip.cfc.c002.01
     */
    @Override
    public void getPayCars() {
        Subscription subscription = mRepository.payCars_c002(initHomeDataDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayCarResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(PayCarResult result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 不加密
     */
    @Override
    public String initHomeDataDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initServiceCodeDTO("cip.cfc.c002.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LogoutDTO params = new LogoutDTO();
        params.setUsrid(mRepository.getUserID());

        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }

    /**
     * 两个网络请求 结果合并处理
     */
    @Override
    public void getAllVehicles() {
        Subscription subscription = Observable.zip(
                mRepository.getRemoteCarInfo(initUserCarsDTO()),
                mRepository.payCars_c002(initHomeDataDTO()),
                new Func2<UserCarsResult, PayCarResult, List<BindCarDTO>>() {
                    @Override
                    public List<BindCarDTO> call(UserCarsResult userCarsResult,
                                                 PayCarResult payCarResult) {
                        return getBindCarDTOList(userCarsResult, payCarResult);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<BindCarDTO>>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        LogUtils.e("=========" + e.getMessage());
                        mAtyView.allVehiclesError(e.getMessage());
                    }

                    @Override
                    public void doNext(List<BindCarDTO> result) {
                        if (result != null && !result.isEmpty()) addOrUpdateVehicleLicense(result);
                        else mAtyView.addVehicleLicenseError("银行获取车辆数据为空，请添加车辆");
                    }
                });
        mSubscriptions.add(subscription);
    }

    @NonNull
    protected List<BindCarDTO> getBindCarDTOList(
            UserCarsResult userCarsResult, PayCarResult payCarResult) {

        List<BindCarDTO> bindCarDTOList = new ArrayList<>();

        UserCarsBean userCarsBean = userCarsResult.getRspInfo();
        if (userCarsBean != null && userCarsResult.getSYS_HEAD().getReturnCode().equals("000000")) {
            List<UserCarInfoBean> carInfoBeanList = userCarsBean.getUserCarsInfo();
            if (carInfoBeanList != null && carInfoBeanList.size() > 0) {
                for (UserCarInfoBean userCarInfoBean : carInfoBeanList) {
                    BindCarDTO bindCarDTO = new BindCarDTO();

                    bindCarDTO.setPlateNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getCarnum()), true));
                    bindCarDTO.setEngineNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getEnginenum()), true));
                    bindCarDTO.setUsrnum(mRepository.getRASUserID());

                    bindCarDTO.setVehicleType(userCarInfoBean.getCarnumtype());
                    bindCarDTO.setIsPay(!TextUtils.isEmpty(userCarInfoBean.getIspaycar())
                            ? Integer.valueOf(userCarInfoBean.getIspaycar()) : 0);
                    bindCarDTO.setIssueDate(userCarInfoBean.getInspectdate());
                    bindCarDTOList.add(bindCarDTO);
                }
            }
        }

        PayCarBean payCarBean = payCarResult.getRspInfo();
        if (payCarBean != null && payCarResult.getSYS_HEAD().getReturnCode().equals("000000")) {
            List<PayCar> payCarList = payCarBean.getUserCarsInfo();
            if (payCarList != null && payCarList.size() > 0) {
                for (PayCar userCarInfoBean : payCarList) {
                    BindCarDTO bindCarDTO = new BindCarDTO();
                    bindCarDTO.setPlateNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getCarnum()), true));
                    bindCarDTO.setEngineNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getEnginenum()), true));
                    bindCarDTO.setUsrnum(mRepository.getRASUserID());

                    bindCarDTO.setVehicleType(userCarInfoBean.getCarnumtype());
                    bindCarDTO.setIsPay(1);

                    bindCarDTOList.add(bindCarDTO);
                }
            }
        }
        return bindCarDTOList;
    }

    /**
     * @deprecated
     */
    private Observable<List<BindCarDTO>> processingCarData(UserCarsResult userCarsResult) {
        return Observable
                .just(userCarsResult)
                .filter(new Func1<UserCarsResult, Boolean>() {
                    @Override
                    public Boolean call(UserCarsResult userCarsResult) {
                        return null != userCarsResult;
                    }
                })
                .map(new Func1<UserCarsResult, UserCarsBean>() {
                    @Override
                    public UserCarsBean call(UserCarsResult userCarsResult) {
                        return userCarsResult.getRspInfo();
                    }
                })
                .filter(new Func1<UserCarsBean, Boolean>() {
                    @Override
                    public Boolean call(UserCarsBean userCarsBean) {
                        return null != userCarsBean;
                    }
                })
                .map(new Func1<UserCarsBean, List<UserCarInfoBean>>() {
                    @Override
                    public List<UserCarInfoBean> call(UserCarsBean userCarsBean) {
                        return userCarsBean.getUserCarsInfo();
                    }
                })
                .filter(new Func1<List<UserCarInfoBean>, Boolean>() {
                    @Override
                    public Boolean call(List<UserCarInfoBean> carInfoBeanList) {
                        return null != carInfoBeanList;
                    }
                })
                .flatMap(new Func1<List<UserCarInfoBean>, Observable<BindCarDTO>>() {
                    @Override
                    public Observable<BindCarDTO> call(List<UserCarInfoBean> carInfoBeanList) {
                        return Observable
                                .from(carInfoBeanList)
                                .filter(new Func1<UserCarInfoBean, Boolean>() {
                                    @Override
                                    public Boolean call(UserCarInfoBean userCarInfoBean) {
                                        return null != userCarInfoBean;
                                    }
                                })
                                .map(new Func1<UserCarInfoBean, BindCarDTO>() {
                                    @Override
                                    public BindCarDTO call(UserCarInfoBean userCarInfoBean) {
                                        BindCarDTO bindCarDTO = new BindCarDTO();
                                        bindCarDTO.setPlateNo(RSAUtils.strByEncryption(
                                                Des3.decode(userCarInfoBean.getCarnum()), true));
                                        bindCarDTO.setEngineNo(RSAUtils.strByEncryption(
                                                Des3.decode(userCarInfoBean.getEnginenum()), true));
                                        bindCarDTO.setVehicleType(userCarInfoBean.getCarnumtype());
                                        bindCarDTO.setUsrnum(mRepository.getRASUserID());
                                        bindCarDTO.setIsPay(!TextUtils.isEmpty(userCarInfoBean.getIspaycar())
                                                ? Integer.valueOf(userCarInfoBean.getIspaycar()) : 0);
                                        return bindCarDTO;
                                    }
                                });
                    }
                })
                .toList();
    }

    /**
     * @deprecated
     */
    private Observable<List<BindCarDTO>> processingPayCarData(PayCarResult userCarsResult) {
        return Observable
                .just(userCarsResult)
                .filter(new Func1<PayCarResult, Boolean>() {
                    @Override
                    public Boolean call(PayCarResult payCarResult) {
                        return null != payCarResult;
                    }
                })
                .map(new Func1<PayCarResult, PayCarBean>() {
                    @Override
                    public PayCarBean call(PayCarResult userCarsResult) {
                        return userCarsResult.getRspInfo();
                    }
                })
                .filter(new Func1<PayCarBean, Boolean>() {
                    @Override
                    public Boolean call(PayCarBean payCarBean) {
                        return null != payCarBean;
                    }
                })
                .map(new Func1<PayCarBean, List<PayCar>>() {
                    @Override
                    public List<PayCar> call(PayCarBean userCarsBean) {
                        return userCarsBean.getUserCarsInfo();
                    }
                })
                .filter(new Func1<List<PayCar>, Boolean>() {
                    @Override
                    public Boolean call(List<PayCar> payCarList) {
                        return null != payCarList;
                    }
                })
                .flatMap(new Func1<List<PayCar>, Observable<BindCarDTO>>() {
                    @Override
                    public Observable<BindCarDTO> call(List<PayCar> carInfoBeanList) {
                        return Observable
                                .from(carInfoBeanList)
                                .filter(new Func1<PayCar, Boolean>() {
                                    @Override
                                    public Boolean call(PayCar payCar) {
                                        return null != payCar;
                                    }
                                })
                                .map(new Func1<PayCar, BindCarDTO>() {
                                    @Override
                                    public BindCarDTO call(PayCar userCarInfoBean) {
                                        BindCarDTO bindCarDTO = new BindCarDTO();
                                        bindCarDTO.setPlateNo(RSAUtils.strByEncryption(
                                                Des3.decode(userCarInfoBean.getCarnum()), true));
                                        bindCarDTO.setEngineNo(RSAUtils.strByEncryption(
                                                Des3.decode(userCarInfoBean.getEnginenum()), true));
                                        bindCarDTO.setVehicleType(userCarInfoBean.getCarnumtype());
                                        bindCarDTO.setUsrnum(mRepository.getRASUserID());
                                        bindCarDTO.setIsPay(1);

                                        return bindCarDTO;
                                    }
                                });
                    }
                })
                .toList();
    }

    /**
     * 19.同步银行车辆
     */
    @Override
    public void addOrUpdateVehicleLicense(List<BindCarDTO> result) {
        Subscription subscription = mRepository.addOrUpdateVehicleLicense(result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<VehicleLicenseResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.addVehicleLicenseError(e.getMessage());
                    }

                    @Override
                    public void doNext(VehicleLicenseResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            if (result.getData().isEmpty())
                                mAtyView.addVehicleLicenseError("车辆数据为空，请添加车辆");
                            else
                                sortCarListData(result);
                        } else {
                            mAtyView.addVehicleLicenseError(result != null
                                    ? result.getResponseDesc()
                                    : "未知错误(N19)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 数据分开处理
     */
    public void sortCarListData(VehicleLicenseResponse result) {

        List<VehicleLicenseBean> resultData = result.getData();
        Observable<List<VehicleLicenseBean>> isPay = Observable.from(resultData)
                .filter(new Func1<VehicleLicenseBean, Boolean>() {
                    @Override
                    public Boolean call(VehicleLicenseBean vehicleLicenseBean) {
                        return null != vehicleLicenseBean;
                    }
                })
                .filter(new Func1<VehicleLicenseBean, Boolean>() {
                    @Override
                    public Boolean call(VehicleLicenseBean vehicleLicenseBean) {
                        return vehicleLicenseBean.getIsPayable() == 1;
                    }
                })
                .toList();

        Observable<List<VehicleLicenseBean>> unPay = Observable.from(resultData)
                .filter(new Func1<VehicleLicenseBean, Boolean>() {
                    @Override
                    public Boolean call(VehicleLicenseBean vehicleLicenseBean) {
                        return null != vehicleLicenseBean;
                    }
                })
                .filter(new Func1<VehicleLicenseBean, Boolean>() {
                    @Override
                    public Boolean call(VehicleLicenseBean vehicleLicenseBean) {
                        return vehicleLicenseBean.getIsPayable() == 0;
                    }
                })
                .toList();
        //清空车辆数据
        LoginData.getInstance().mServerCars.clear();

        Subscription subscription = Observable
                .zip(isPay, unPay,
                        new Func2<List<VehicleLicenseBean>, List<VehicleLicenseBean>, List<VehicleLicenseBean>>() {
                            @Override
                            public List<VehicleLicenseBean> call(List<VehicleLicenseBean> beanList,
                                                                 List<VehicleLicenseBean> licenseBeanList) {

                                List<VehicleLicenseBean> zipFunction = new ArrayList<>();

                                if (beanList != null && !beanList.isEmpty()) {
                                    VehicleLicenseBean vehicleLicenseBean = new VehicleLicenseBean(-1);
                                    zipFunction.add(vehicleLicenseBean);
                                    zipFunction.addAll(beanList);

                                    toServerCar(beanList);
                                }

                                if (licenseBeanList != null && !licenseBeanList.isEmpty()) {
                                    VehicleLicenseBean vehicleLicenseBean = new VehicleLicenseBean(-2);
                                    zipFunction.add(vehicleLicenseBean);
                                    zipFunction.addAll(licenseBeanList);

                                    toServerCar(licenseBeanList);
                                }
                                return zipFunction;
                            }
                        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<VehicleLicenseBean>>() {
                            @Override
                            public void call(List<VehicleLicenseBean> result) {
                                mAtyView.addVehicleLicenseSucceed(result);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e("=========" + throwable.getMessage());
                                mAtyView.addVehicleLicenseError(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    private void toServerCar(List<VehicleLicenseBean> licenseBeen) {

        List<UserCarInfoBean> beanArrayList = new ArrayList<>();
        for (VehicleLicenseBean bean : licenseBeen) {
            UserCarInfoBean userCarInfoBean = new UserCarInfoBean();
            userCarInfoBean.setCarnum(Des3.decode(bean.getPlateNo()));
            userCarInfoBean.setEnginenum(Des3.decode(bean.getEngineNo()));
            userCarInfoBean.setIspaycar(String.valueOf(bean.getIsPayable()));
            userCarInfoBean.setCarnumtype(bean.getVehicleType());
            beanArrayList.add(userCarInfoBean);
        }
        LoginData.getInstance().mServerCars.addAll(beanArrayList);
    }

}
