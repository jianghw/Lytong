package com.tzly.ctcyh.user.data_m;

import android.support.annotation.Nullable;

import com.tzly.ctcyh.java.response.BankResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.api.RetrofitFactory;
import com.tzly.ctcyh.user.api.IBankService;
import com.tzly.ctcyh.user.api.ICttxService;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.bean.response.VCodeResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 远程数据处理
 */

public class RemoteData implements IRemoteSource {

    @Nullable
    private static RemoteData INSTANCE = null;

    /**
     * 懒汉式，线程不安全
     */
    public static RemoteData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteData();
        }
        return INSTANCE;
    }

    private Retrofit baseRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(1);
    }

    private Retrofit bankRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(2);
    }

    private Retrofit xiaoFengRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(7);
    }

    private Retrofit imageRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(3);
    }

    private Retrofit localRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(4);
    }

    /**
     * 安盛登录
     * https://ctkapptest.icbc-axa.com/ecip/mobilecall_call
     */
    @Override
    public Observable<LoginResponse> bank_u011_01(String msg) {
        return bankRetrofit().create(IBankService.class).loadPost(msg);
    }

    /**
     * 8.用户注册修改接口
     */
    @Override
    public Observable<BaseResponse> register(RegisterDTO loginDTO) {
        return baseRetrofit().create(ICttxService.class).register(loginDTO);
    }

    @Override
    public Observable<BankResponse> bank_v004_01(String msg) {
        return bankRetrofit().create(IBankService.class).loginV004(msg);
    }

    /**
     * 获取验证码
     */
    @Override
    public Observable<VCodeResponse> bank_u015_01(String msg) {
        return bankRetrofit().create(IBankService.class).sendVerificationCode(msg);
    }

    /**
     * 发送验证码
     */
    @Override
    public Observable<BankResponse> bank_p002_01(String msg) {
        return bankRetrofit().create(IBankService.class).v_p002_01(msg);
    }

    /**
     * 注册
     */
    @Override
    public Observable<LoginResponse> bank_u001_01(String msg) {
        return bankRetrofit().create(IBankService.class).v_u001_01(msg);
    }
}