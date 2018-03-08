package com.tzly.ctcyh.user.login_p;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tzly.ctcyh.java.response.BankResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.api.BaseSubscriber;
import com.tzly.ctcyh.router.util.SHATools;
import com.tzly.ctcyh.router.custom.rea.Des3;
import com.tzly.ctcyh.user.bean.RequestDTO;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.request.FileNumDTO;
import com.tzly.ctcyh.user.bean.request.LoginDTO;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginBean;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.data_m.UserDataManager;
import com.tzly.ctcyh.user.global.UserGlobal;

import java.security.NoSuchAlgorithmException;

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

public class LoginPresenter implements ILoginContract.ILoginPresenter {

    private final UserDataManager mRepository;
    private final ILoginContract.ILoginView mContractView;
    private final CompositeSubscription mSubscriptions;

    public LoginPresenter(@NonNull UserDataManager userDataManager,
                          @NonNull ILoginContract.ILoginView view) {
        mRepository = userDataManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    @Override
    public void userLogin() {
        Subscription subscription = mRepository
                .bank_u011_01(initLoginDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LoginResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.userLoginError(e.getMessage());
                    }

                    @Override
                    public void doNext(LoginResponse loginResponse) {
                        if (loginResponse != null && loginResponse.getSYS_HEAD().getReturnCode()
                                .equals(UserGlobal.Response.bank_succeed)) {

                            register(loginResponse);

                            LoginBean loginBean = loginResponse.getRspInfo();
                            String filenum = Des3.decode(loginBean.getFilenum());
                            if (!TextUtils.isEmpty(filenum)) loginV004(filenum);

                            mContractView.userLoginSucceed(loginResponse);
                        } else {
                            mContractView.userLoginError(loginResponse != null
                                    ? loginResponse.getSYS_HEAD().getReturnMessage() : "未知错误(u011)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * token	是	string	推送的token 加密
     * pushmode	是	string	推送方式
     * pushswitch	是	string	推送开关
     * phoenum	是	string	手机号
     * pswd	是	string	密码
     * devicetoken	是	string	设备标识号
     */
    @Override
    public String initLoginDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.requestHeadDTO("cip.cfc.u011.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LoginDTO bean = new LoginDTO();
        String token = mRepository.getRASByStr(mRepository.getPushId());
        bean.setToken(token);

        bean.setPushmode("2");
        bean.setPushswitch("0");

        String phone = mRepository.getRASByStr(mContractView.getUserPhone());
        bean.setPhoenum(phone);

        SHATools shaTools = new SHATools();
        try {
            String pwd = mRepository.getRASByStr(
                    SHATools.hexString(shaTools.eccryptSHA1(mContractView.getUserPassword())));
            bean.setPswd(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bean.setDevicetoken(mRepository.getPhoneDeviceId());
        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 同赞信息保存接口
     */
    @Override
    public void register(final LoginResponse loginResponse) {
        Subscription subscription = mRepository
                .register(initRegisterDTO(loginResponse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {

                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.registerError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse baseResult) {
                        if (baseResult != null && baseResult.getResponseCode() == 2000) {
                            //备份数据
                            mRepository.saveLoginResponseToSp(loginResponse);
                            mRepository.isLogin();
                            mContractView.registerSucceed();
                        } else {
                            mContractView.registerError(baseResult != null
                                    ? baseResult.getResponseDesc() : "未知错误");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public RegisterDTO initRegisterDTO(LoginResponse loginResponse) {
        LoginBean loginBean = loginResponse.getRspInfo();

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPhoenum(mRepository.getRASByStr(mContractView.getUserPhone()));
        registerDTO.setPswd(mRepository.getRASByStr(mContractView.getUserPassword()));
        registerDTO.setUsrid(mRepository.getRASByStr(loginBean.getUsrid()));
        registerDTO.setNickname(loginBean.getNickname());

        String token = mRepository.getRASByStr(mRepository.getPushId());
        registerDTO.setToken(token);
        registerDTO.setPushmode("2");
        registerDTO.setPushswitch("0");
        return registerDTO;
    }

    @Override
    public void loginV004(String filenum) {
        Subscription subscription = mRepository
                .bank_v004_01(initLoginV004DTO(filenum))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BankResponse>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                    }

                    @Override
                    public void doNext(BankResponse baseResult) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initLoginV004DTO(String filenum) {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.requestHeadDTO("cip.cfc.v004.01");
        dto.setSYS_HEAD(requestHeadDTO);

        FileNumDTO bean = new FileNumDTO();
        bean.setFilenum(mRepository.getRASByStr(filenum));

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 设备保存device id
     */
    @Override
    public void initPhoneDeviceId() {
        mRepository.savePhoneDeviceId();
    }
}
