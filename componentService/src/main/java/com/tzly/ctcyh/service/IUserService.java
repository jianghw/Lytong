package com.tzly.ctcyh.service;

import android.app.Activity;
import android.content.Context;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface IUserService {

    boolean isUserByLogin();

    boolean isLogin();

    void gotoLoginActivity(Context context);

    void saveUserFilenum(String filenum);

    void saveUserPhoenum(String phoenum);

    void saveUserPortrait(String portrait);

    void saveUserGetdate(String getdate);

    void saveUserNickname(String nickname);

    void savePushId(String pushId);

    String getPhoneDeviceId();

    String getPushId();

    String getUserID();

    String getRASUserID();

    String getUserPhoenum();

    String getUserFilenum();

    String getUserGetdate();

    String getUserPortrait();

    String getUserNickname();

    void cleanUserLogin();

    void saveLoginBean(Activity activity, String user, String userString);
}
