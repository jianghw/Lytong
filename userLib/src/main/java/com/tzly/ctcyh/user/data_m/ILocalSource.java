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

    void initPhoneDeviceId();

    String getPhoneDeviceId();

    String getPushDeviceId();

    String getLoginResponseFromSp();

    Context getWeakReference();

    String getUserId();
}
