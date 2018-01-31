
package com.zantong.mobile.login_p;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tzly.annual.base.bean.BaseResponse;
import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.request.RegisterDTO;
import com.tzly.annual.base.bean.request.UserLoginDTO;
import com.tzly.annual.base.util.LogUtils;
import com.tzly.annual.base.bean.Result;
import com.zantong.mobile.base.dto.RequestDTO;
import com.zantong.mobile.base.dto.RequestHeadDTO;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.user.bean.RspInfoBean;
import com.tzly.annual.base.bean.request.FileNumDTO;
import com.zantong.mobile.utils.rsa.Des3;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.utils.xmlparser.SHATools;

import java.security.NoSuchAlgorithmException;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 订单模块
 * Update by:
 * Update day:
 */
public class LoginPresenter implements ILoginContract.ILoginPresenter {

    private final RepositoryManager mRepository;
    private final ILoginContract.ILoginView mContentView;
    private final CompositeSubscription mSubscriptions;

    public LoginPresenter(@NonNull RepositoryManager repositoryManager,
                          @NonNull ILoginContract.ILoginView view) {
        mRepository = repositoryManager;
        mContentView = view;
        mSubscriptions = new CompositeSubscription();
        mContentView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mContentView.dismissLoadingDialog();
        mSubscriptions.clear();
    }


    @Override
    public void userLogin() {
        Subscription subscription = mRepository.loadLoginPost(initLoginDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContentView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LoginInfoBean>() {
                    @Override
                    public void doCompleted() {
                        mContentView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContentView.userLoginError(e.getMessage());
                    }

                    @Override
                    public void doNext(LoginInfoBean loginInfoBean) {
                        if (loginInfoBean != null &&
                                loginInfoBean.getSYS_HEAD().getReturnCode().equals("000000")) {

                            RspInfoBean rspInfoBean = loginInfoBean.getRspInfo();
                            rspInfoBean.setFilenum(Des3.decode(rspInfoBean.getFilenum()));
                            rspInfoBean.setPhoenum(Des3.decode(rspInfoBean.getPhoenum()));
                            rspInfoBean.setCtfnum(Des3.decode(rspInfoBean.getCtfnum()));
                            rspInfoBean.setRecdphoe(Des3.decode(rspInfoBean.getRecdphoe()));
                            mRepository.saveLoginInfoRepeat(loginInfoBean);

                            register();
                            if (!TextUtils.isEmpty(MemoryData.getInstance().filenum)) loginV004();
                        } else {
                            mContentView.userLoginError(loginInfoBean != null
                                    ? loginInfoBean.getSYS_HEAD().getReturnMessage() : "未知错误(u011)");
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

        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.u011.01");
        dto.setSYS_HEAD(requestHeadDTO);

        UserLoginDTO bean = new UserLoginDTO();
        String token = RSAUtils.strByEncryption(MemoryData.getInstance().deviceId, true);
        bean.setToken(token);
        bean.setPushmode("2");
        bean.setPushswitch("0");
        String phone = RSAUtils.strByEncryption(mContentView.getUserPhone(), true);
        bean.setPhoenum(phone);
        SHATools shaTools = new SHATools();
        try {
            String pwd = RSAUtils.strByEncryption(
                    SHATools.hexString(shaTools.eccryptSHA1(mContentView.getUserPassword())), true);
            bean.setPswd(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bean.setDevicetoken(MemoryData.getInstance().imei);
        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 同赞信息保存接口
     */
    @Override
    public void register() {
        Subscription subscription = mRepository.register(initRegisterDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResult>() {
                    @Override
                    public void doCompleted() {
                        mContentView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContentView.registerError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResult baseResult) {
                        mContentView.userLoginSucceed();
                        mContentView.registerSucceed();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public RegisterDTO initRegisterDTO() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPhoenum(Des3.encode(mContentView.getUserPhone()));
        registerDTO.setPswd(Des3.encode(mContentView.getUserPassword()));
        registerDTO.setUsrid(mRepository.getDefaultRASUserID());

        String token = RSAUtils.strByEncryption(MemoryData.getInstance().deviceId, true);
        registerDTO.setToken(token);
        registerDTO.setPushmode("2");
        registerDTO.setPushswitch("0");
        return registerDTO;
    }

    @Override
    public void loginV004() {
        Subscription subscription = mRepository.loginV004(initLoginV004DTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void doNext(Result baseResult) {
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initLoginV004DTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.v004.01");
        dto.setSYS_HEAD(requestHeadDTO);

        FileNumDTO bean = new FileNumDTO();
        bean.setFilenum(RSAUtils.strByEncryption(MemoryData.getInstance().filenum, true));

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 登录
     */
    @Override
    public void innerUserLogin() {
        Subscription subscription = mRepository
                .innerUserLogin(mContentView.getUserPhone(), mContentView.getUserPassword())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContentView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mContentView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContentView.dismissLoadingDialog();
                    }

                    @Override
                    public void doNext(BaseResponse baseResult) {
                    }
                });
        mSubscriptions.add(subscription);
    }
}
