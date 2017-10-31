package com.tzly.ctcyh.user.data_m;

import android.support.annotation.Nullable;

import com.tzly.ctcyh.user.api.IBankService;
import com.tzly.ctcyh.user.api.ICttxService;
import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

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

    /**
     * 安盛登录
     * https://ctkapptest.icbc-axa.com/ecip/mobilecall_call
     */
    @Override
    public Observable<LoginResponse> loadLoginPost(String msg) {
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
    public Observable<BankResponse> bank_V004_01(String msg) {
        return bankRetrofit().create(IBankService.class).loginV004(msg);
    }
}
