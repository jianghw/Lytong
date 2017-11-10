package com.tzly.ctcyh.user.serviceimple;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;
import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.data_m.BaseSubscriber;
import com.tzly.ctcyh.user.data_m.UserDataManager;
import com.tzly.ctcyh.user.router.UserRouter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class UserDataService implements IUserService {

    private final UserDataManager mRepository;

    public UserDataService(@NonNull UserDataManager userDataManager) {
        mRepository = userDataManager;
    }

    /**
     * 是否登录
     */
    @Override
    public boolean isUserByLogin() {
        return mRepository.isUserLogin();
    }

    /**
     * 只初始化数据
     */
    @Override
    public boolean isLogin() {
        return mRepository.isLogin();
    }

    @Override
    public void gotoLoginActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.user_scheme + "://" + RouterGlobal.Host.login_host,
                bundle);
    }

    /**
     * 保存驾挡编号
     */
    @Override
    public void saveUserFilenum(String filenum) {
        mRepository.saveUserFilenum(filenum);
    }

    /**
     * 保存手机号码
     */
    @Override
    public void saveUserPhoenum(String phoenum) {
        mRepository.saveUserPhoenum(phoenum);
    }

    /**
     * 保存头像
     */
    @Override
    public void saveUserPortrait(String portrait) {
        mRepository.saveUserPortrait(portrait);
    }

    /**
     * 保存注册时间
     */
    @Override
    public void saveUserGetdate(String getdate) {
        mRepository.saveUserGetdate(getdate);
    }

    /**
     * 保存匿名
     */
    @Override
    public void saveUserNickname(String nickname) {
        mRepository.saveUserNickname(nickname);
    }

    /**
     * 保存推送id
     */
    @Override
    public void savePushId(String pushId) {
        mRepository.savePushId(pushId);
    }

    @Override
    public String getPhoneDeviceId() {
        return mRepository.getPhoneDeviceId();
    }

    @Override
    public String getPushId() {
        return mRepository.getPushId();
    }

    /**
     * 统一获取用户id
     */
    @Override
    public String getUserID() {
        return mRepository.getUserID();
    }

    @Override
    public String getUserID(boolean isNeedLogin) {
        return mRepository.getUserID(isNeedLogin);
    }

    @Override
    public String getRASUserID() {
        return mRepository.getRASUserID();
    }

    /**
     * 手机号码
     */
    @Override
    public String getUserPhoenum() {
        return mRepository.getUserPhoenum();
    }

    @Override
    public String getUserFilenum() {
        return mRepository.getUserFilenum();
    }

    @Override
    public String getUserGetdate() {
        return mRepository.getUserGetdate();
    }

    @Override
    public String getUserPortrait() {
        return mRepository.getUserPortrait();
    }

    @Override
    public String getUserNickname() {
        return mRepository.getUserNickname();
    }

    /**
     * 退出登录
     */
    @Override
    public void cleanUserLogin() {
        mRepository.getCleanUser();
    }

    @Override
    public void saveLoginBean(final Activity activity, String user, String pwd) {
        mRepository.saveLoginBean(user);

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPhoenum(RSAUtils.strByEncryption(mRepository.getUserPhoenum(), true));
        registerDTO.setPswd(RSAUtils.strByEncryption(pwd, true));
        registerDTO.setUsrid(mRepository.getRASUserID());
        String token = RSAUtils.strByEncryption(mRepository.getPushId(), true);
        registerDTO.setToken(token);
        registerDTO.setPushmode("2");
        registerDTO.setPushswitch("0");

        mRepository.register(registerDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {}

                    @Override
                    public void doError(Throwable e) {
                        ToastUtils.toastShort("注册成功,登录失败~");
                        UserRouter.gotoLoginActivity(activity);
                        if (activity != null) activity.finish();
                    }

                    @Override
                    public void doNext(BaseResponse baseResponse) {
                        if (baseResponse != null
                                && baseResponse.getResponseCode() == 2000) {
                            UserRouter.loginFilenumDialog(activity);
                        } else {
                            ToastUtils.toastShort("注册成功,登录失败~");
                            UserRouter.gotoLoginActivity(activity);
                            if (activity != null) activity.finish();
                        }
                    }
                });
    }

}
