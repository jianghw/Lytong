package com.tzly.ctcyh.service;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface IUserService {

    boolean isUserLogin();

    void initLoginData();

    void saveUserFilenum(String filenum);

    void saveUserPhoenum(String phoenum);

    void saveUserPortrait(String portrait);

    void saveUserGetdate(String getdate);

    void saveUserNickname(String nickname);

    void savePushId(String pushId);

    String getPhoneDeviceId();

    String getUserID();

    String getRASUserID();
}
