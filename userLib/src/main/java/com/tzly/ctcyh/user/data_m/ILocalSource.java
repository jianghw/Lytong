package com.tzly.ctcyh.user.data_m;

import android.content.Context;

import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface ILocalSource {

    void saveLoginResponseToSp(LoginResponse loginResponse);

    RequestHeadDTO getRequestHeadDTO();

    void savePhoneDeviceId();

    String getPhoneDeviceId();

    String getLoginResponseNewOrOld();

    Context getWeakReference();

    String getUserId();

    void setPushId(String pushId);

    String getPushId();

    String getPhoenum();

    String getFilenum();

    String getGetdate();

    String getPortrait();

    String getNickname();
}
