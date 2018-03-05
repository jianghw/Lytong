package com.tzly.ctcyh.user.data_m;


import com.tzly.ctcyh.user.BuildConfig;

import retrofit2.Retrofit;

/**
 * Created by jianghw on 2017/4/26.
 * 构建bases url工厂
 */

public class RetrofitFactory {
    public static RetrofitFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public Retrofit createRetrofit(int type) {
        switch (type) {
            case 1:
                return UserRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 2:
                return UserRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 3:
                return UserRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            default:
                throw new IllegalArgumentException("pay retrofit type is not right");
        }
    }

    /**
     * 手动修改值
     */
    private String getBaseUrl(int type) {
        switch (type) {
            case 1:
                return BuildConfig.isDeta ? BuildConfig.beta_base_url : BuildConfig.release_base_url;
            case 2:
                return BuildConfig.isDeta ? BuildConfig.beta_bank_url : BuildConfig.release_bank_url;
            default:
                return "http://192.168.1.127:8081/";
        }
    }
}
