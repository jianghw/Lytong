package com.zantong.mobilecttx.model.repository;

import com.zantong.mobilecttx.BuildConfig;

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
        return type == 1 || type == 5 ? DefaultRetrofit.getInstance().createRetrofit(getBaseUrl(type))
                : type != 4 ? AnShengRetrofit.getInstance().createRetrofit(getBaseUrl(type))
                : DefaultRetrofit.getInstance().createRetrofit(getBaseUrl(type));
    }

    private String getBaseUrl(int type) {
        switch (type) {
            case 1://同赞自己服务器
                return BuildConfig.CAR_MANGER_URL;
            case 2:
                return BuildConfig.APP_URL;
            case 3:
                return BuildConfig.BASE_URL;
            case 4:
                return "http://192.168.1.126:80/";
            case 5:
                return "http://liyingtong.com:8080/";
            default:
                return "http://192.168.1.147:80/";
        }
    }
}
