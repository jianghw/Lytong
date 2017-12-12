package com.tzly.ctcyh.user.data_m;

import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.bean.response.VCodeResponse;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IRemoteSource {
    /**
     * https://ctkapptest.icbc-axa.com/ecip/mobilecall_call
     */
    Observable<LoginResponse> bank_u011_01(String msg);

    /**
     * 8.用户注册修改接口
     */
    Observable<BaseResponse> register(RegisterDTO registerDTO);

    Observable<BankResponse> bank_v004_01(String msg);

    /**
     * 获取验证码
     */
    Observable<VCodeResponse> bank_u015_01(String msg);

    /**
     * 发送验证码
     */
    Observable<BankResponse> bank_p002_01(String msg);

    /**
     * 注册
     */
    Observable<LoginResponse> bank_u001_01(String msg);
}
