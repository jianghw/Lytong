
package com.zantong.mobile.presenter.login_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.annual.base.bean.request.UserLoginDTO;
import com.zantong.mobile.base.dto.RequestDTO;
import com.zantong.mobile.base.dto.RequestHeadDTO;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.model.repository.BaseSubscriber;
import com.zantong.mobile.model.repository.RepositoryManager;
import com.zantong.mobile.user.bean.LoginInfoBean;
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
public class LoginPresenter
        implements ILoginContract.ILoginPresenter {

    private final RepositoryManager mRepository;
    private final ILoginContract.ILoginView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public LoginPresenter(@NonNull RepositoryManager repositoryManager,
                          @NonNull ILoginContract.ILoginView view) {
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


    @Override
    public void userLogin() {
        Subscription subscription = mRepository.loadLoginPost(initLoginDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LoginInfoBean>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.userLoginError(e.getMessage());
                    }

                    @Override
                    public void doNext(LoginInfoBean loginInfoBean) {
                        if (loginInfoBean != null &&
                                loginInfoBean.getSYS_HEAD().getReturnCode().equals("000000")) {
                            mRepository.saveLoginInfoRepeat(loginInfoBean);
                        } else {
                            mAtyView.userLoginError(loginInfoBean != null
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
        String token = RSAUtils.strByEncryption(PublicData.getInstance().deviceId, true);
        bean.setToken(token);
        bean.setPushmode("2");
        bean.setPushswitch("0");
        String phone = RSAUtils.strByEncryption(mAtyView.getUserPhone(), true);
        bean.setPhoenum(phone);
        SHATools shaTools = new SHATools();
        try {
            String pwd = RSAUtils.strByEncryption(
                    SHATools.hexString(shaTools.eccryptSHA1(mAtyView.getUserPassword())), true);
            bean.setPswd(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bean.setDevicetoken(PublicData.getInstance().imei);
        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }
}