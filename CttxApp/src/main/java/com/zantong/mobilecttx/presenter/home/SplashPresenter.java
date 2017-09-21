package com.zantong.mobilecttx.presenter.home;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarBean;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.bean.VehicleLicenseResult;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.contract.ISplashAtyContract;
import com.zantong.mobilecttx.home.bean.StartPicResult;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseTestDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
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

    private String getStartTime() {
        String nowMonth = Tools.getYearDate().substring(4, 6);
        String oldMonth = PublicData.getInstance().mLoginInfoBean.getGetdate().substring(4, 6);
        String startTime = "";
        if (Integer.parseInt(nowMonth) > Integer.parseInt(oldMonth)) {
            startTime = Tools.getYearDate().substring(0, 4) + PublicData.getInstance().mLoginInfoBean.getGetdate().substring(4);
        } else {
            startTime = PublicData.getInstance().mLoginInfoBean.getGetdate();
        }
        return startTime;
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    /**
     * 历史数据操作,初始化全局数据
     */
    @Override
    public void readObjectLoginInfoBean() {
        RspInfoBean rspInfoBean = mRepository.readObjectLoginInfoBean();
        if (rspInfoBean == null) return;
        mRepository.initGlobalLoginInfo(rspInfoBean);

        getAllVehicles();
    }

    public String initUserCarsDTO() {
        UserCarsDTO params = new UserCarsDTO();
        params.setUsrid(mRepository.getDefaultUserID());

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
        params.setUsrid(mRepository.getDefaultUserID());

        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }

    /**
     * 两个网络请求 结果合并处理
     */
    public void getAllVehicles() {
        Subscription subscription = Observable.zip(
                mRepository.getRemoteCarInfo(initUserCarsDTO()),
                mRepository.getPayCars(initHomeDataDTO()),
                new Func2<UserCarsResult, PayCarResult, List<BindCarDTO>>() {
                    @Override
                    public List<BindCarDTO> call(UserCarsResult userCarsResult,
                                                 PayCarResult payCarResult) {

                        return getBindCarDTOList(userCarsResult, payCarResult);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<BindCarDTO>>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(List<BindCarDTO> result) {
                        if (result != null && !result.isEmpty())
                            addOrUpdateVehicleLicense(result);
                    }
                });
        mSubscriptions.add(subscription);
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
                    bindCarDTO.setUsrnum(mRepository.getDefaultRASUserID());
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
                    bindCarDTO.setUsrnum(mRepository.getDefaultRASUserID());
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
                .subscribe(new BaseSubscriber<VehicleLicenseResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(VehicleLicenseResult result) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 提交安盛服务器/成功后保存数据
     */
    @Override
    public void loadLoginPost() {
        Subscription subscription = mRepository.loadLoginPost(initLoginMessage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginInfoBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(LoginInfoBean result) {
                        if (result != null &&
                                result.getSYS_HEAD().getReturnCode().equals(
                                        PublicData.getInstance().success)) {

                            mRepository.saveLoginInfoRepeat(result);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadLoginPostTest() {
        Subscription subscription = mRepository.loadLoginPostTest(initLicenseFileNumDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LicenseResponseBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LicenseResponseBean result) {

                    }
                });
        mSubscriptions.add(subscription);
    }

    public String initLicenseFileNumDTO() {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.v003.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LicenseTestDTO bean = new LicenseTestDTO();

//        String fileNum = "3101057013022704";
        String fileNum = "3101147014160528";

        bean.setViolationnum(fileNum);
        bean.setToken(mRepository.getDefaultRASUserID());

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 提交安盛服务器登录数据
     * cip.cfc.u011.01
     */
    @Override
    public String initLoginMessage() {
        MessageFormat.getInstance().setTransServiceCode("cip.cfc.u011.01");
        JSONObject masp = new JSONObject();
        try {
            String pwd = mRepository.readLoginPassword();
            if (Tools.isStrEmpty(pwd)) throw new IllegalArgumentException("pwd is must not null");
            masp.put("phoenum", PublicData.getInstance().mLoginInfoBean.getPhoenum());
            SHATools sha = new SHATools();
            masp.put("pswd", SHATools.hexString(sha.eccryptSHA1(pwd)));
            masp.put("devicetoken", PublicData.getInstance().imei);
            masp.put("pushswitch", "0");
            masp.put("pushmode", "1");
            String token = RSAUtils.strByEncryption(PublicData.getInstance().deviceId, true);
            masp.put("token", token);
            MessageFormat.getInstance().setMessageJSONObject(masp);
        } catch (JSONException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return MessageFormat.getInstance().getMessageFormat();
    }

    /**
     * 引导页图片
     */
    @Override
    public void startGuidePic() {
        Subscription subscription = mRepository.startGetPic("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StartPicResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(StartPicResult result) {
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
                .subscribe(new Subscriber<StartPicResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(StartPicResult result) {
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
