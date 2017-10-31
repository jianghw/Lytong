package com.tzly.ctcyh.user.serviceimple;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.user.data_m.UserDataManager;

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
    public boolean isUserLogin() {
        return mRepository.isUserLogin();
    }

    @Override
    public void initLoginData() {
        mRepository.initLoginData();
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

    /**
     * 统一获取用户id
     */
    @Override
    public String getUserID() {
        return mRepository.getUserID();
    }

    @Override
    public String getRASUserID() {
        return mRepository.getRASUserID();
    }
}
