package com.zantong.mobile.presenter.home;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobile.base.MessageFormat;
import com.zantong.mobile.base.dto.RequestDTO;
import com.zantong.mobile.base.dto.RequestHeadDTO;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.contract.ISplashAtyContract;
import com.zantong.mobile.home.bean.StartPicResult;
import com.zantong.mobile.model.repository.RepositoryManager;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.user.bean.RspInfoBean;
import com.zantong.mobile.utils.Tools;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.utils.xmlparser.SHATools;
import com.zantong.mobile.weizhang.bean.LicenseResponseBean;
import com.zantong.mobile.weizhang.dto.LicenseTestDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
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
