package com.tzly.ctcyh.user.data_m;

import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IRemoteSource {
    /**
     * https://ctkapptest.icbc-axa.com/ecip/mobilecall_call
     */
    Observable<LoginResponse> loadLoginPost(String msg);
    /**
     * 8.用户注册修改接口
     */
    Observable<BaseResponse> register(RegisterDTO registerDTO);

    Observable<BankResponse> bank_V004_01(String msg);
}
