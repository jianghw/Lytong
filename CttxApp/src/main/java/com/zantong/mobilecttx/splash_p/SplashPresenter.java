package com.zantong.mobilecttx.splash_p;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarBean;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.bean.VehicleLicenseResponse;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.home.bean.StartPicResponse;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.weizhang.dto.LicenseTestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description:
 * Update by:
 * Update day:
 */
public class SplashPresenter implements ISplashAtyContract.ISplashAtyPresenter {

    private final RepositoryManager mRepository;
    private final ISplashAtyContract.ISplashAtyView mSplashAtyView;
    private final CompositeSubscription mSubscriptions;

    public SplashPresenter(@NonNull RepositoryManager repositoryManager,
                           @NonNull ISplashAtyContract.ISplashAtyView view) {
        mRepository = repositoryManager;
        mSplashAtyView = view;
        mSubscriptions = new CompositeSubscription();
        mSplashAtyView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    public String initUserCarsDTO() {
        UserCarsDTO params = new UserCarsDTO();
        params.setUsrid(mRepository.getUserID());

        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.c003.01");

        dto.setSYS_HEAD(requestHeadDTO);
        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }

    public String initHomeDataDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.c002.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LogoutDTO params = new LogoutDTO();
        params.setUsrid(mRepository.getUserID());

        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }


    /**
     * 车辆数据合并
     */
    @NonNull
    protected List<BindCarDTO> getBindCarDTOList(
            UserCarsResult userCarsResult, PayCarResult payCarResult) {

        List<BindCarDTO> bindCarDTOList = new ArrayList<>();

        UserCarsBean userCarsBean = userCarsResult.getRspInfo();
        if (userCarsBean != null) {
            List<UserCarInfoBean> carInfoBeanList = userCarsBean.getUserCarsInfo();
            if (carInfoBeanList != null && carInfoBeanList.size() > 0) {
                for (UserCarInfoBean userCarInfoBean : carInfoBeanList) {
                    BindCarDTO bindCarDTO = new BindCarDTO();

                    bindCarDTO.setPlateNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getCarnum()), true));
                    bindCarDTO.setEngineNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getEnginenum()), true));
                    bindCarDTO.setVehicleType(userCarInfoBean.getCarnumtype());
                    bindCarDTO.setUsrnum(mRepository.getUserID());
                    bindCarDTO.setIsPay(!TextUtils.isEmpty(userCarInfoBean.getIspaycar())
                            ? Integer.valueOf(userCarInfoBean.getIspaycar()) : 0);
                    bindCarDTO.setIssueDate(userCarInfoBean.getInspectdate());

                    bindCarDTOList.add(bindCarDTO);
                }
            }
        }

        PayCarBean payCarBean = payCarResult.getRspInfo();
        if (payCarBean != null) {
            List<PayCar> payCarList = payCarBean.getUserCarsInfo();
            if (payCarList != null && payCarList.size() > 0) {
                for (PayCar userCarInfoBean : payCarList) {
                    BindCarDTO bindCarDTO = new BindCarDTO();
                    bindCarDTO.setPlateNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getCarnum()), true));
                    bindCarDTO.setEngineNo(RSAUtils.strByEncryption(
                            Des3.decode(userCarInfoBean.getEnginenum()), true));
                    bindCarDTO.setVehicleType(userCarInfoBean.getCarnumtype());
                    bindCarDTO.setUsrnum(mRepository.getUserID());
                    bindCarDTO.setIsPay(1);

                    bindCarDTOList.add(bindCarDTO);
                }
            }
        }
        return bindCarDTOList;
    }

    /**
     * 19.同步银行车辆
     */
    public void addOrUpdateVehicleLicense(List<BindCarDTO> result) {
        Subscription subscription = mRepository.addOrUpdateVehicleLicense(result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<VehicleLicenseResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(VehicleLicenseResponse result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    public String initLicenseFileNumDTO() {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.v003.01");
        dto.setSYS_HEAD(requestHeadDTO);
        LicenseTestDTO bean = new LicenseTestDTO();

        String fileNum = "3101147014160528";
        bean.setViolationnum(fileNum);
        bean.setToken(mRepository.getUserID());

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 引导页图片
     */
    @Override
    public void startGuidePic() {
        Subscription subscription = mRepository.startGetPic("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StartPicResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(StartPicResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mSplashAtyView.displayGuideImage(result);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void startGetPic() {
        Subscription subscription = mRepository.startGetPic("0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StartPicResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(StartPicResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mSplashAtyView.displayAdsImage(result);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 倒计时
     */
    @Override
    public void startCountDown() {
        Subscription subCount = Observable
                .interval(10, 1000, TimeUnit.MILLISECONDS)
                .take(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mSplashAtyView.countDownOver();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mSplashAtyView.countDownTextView(5 - aLong);
                    }
                });
        mSubscriptions.add(subCount);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
