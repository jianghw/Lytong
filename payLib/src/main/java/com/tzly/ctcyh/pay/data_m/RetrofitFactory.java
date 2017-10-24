package com.tzly.ctcyh.pay.data_m;

import com.tzly.ctcyh.pay.BuildConfig;

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
                return PayRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 2:
                return PayRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 3:
                return PayRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            default:
                throw new IllegalArgumentException("pay retrofit type is not right");
        }
    }

    private String getBaseUrl(int type) {
        switch (type) {
            case 1://同赞自己服务器
                return BuildConfig.DEBUG
                        ?"http://dev.liyingtong.com/":"http://biz.liyingtong.com/";
            case 2:
                return BuildConfig.DEBUG
                        ?"https://ctkapptest.icbc-axa.com/ecip/":"https://ctkapp.icbc-axa.com/ecip/";
            default:
                return "http://192.168.1.147:80/";
        }
    }
}
