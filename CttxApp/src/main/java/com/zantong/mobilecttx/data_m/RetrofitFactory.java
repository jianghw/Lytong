package com.zantong.mobilecttx.data_m;

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
        switch (type) {
            case 1:
                return MainRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 2:
                return MainRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 3:
                return MainRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            default:
                throw new IllegalArgumentException("pay retrofit type is not right");
        }
    }

    /**
     * 手动修改值
     */
    private String getBaseUrl(int type) {
        return getBaseUrl(type, BuildConfig.App_Url);
    }

    private String getBaseUrl(int type, boolean isDebug) {
        switch (type) {
            case 1://同赞自己服务器
                return isDebug
                        ? "http://dev.liyingtong.com/" : "http://api2.liyingtong.com/";
            case 2:
                return isDebug
                        ? "https://ctkapptest.icbc-axa.com/ecip/" : "https://ctkapp.icbc-axa.com/ecip/";
            case 3:
                return "http://liyingtong.com:8080/";
            case 4:
                return "http://192.168.1.127:8082/";
            default:
                return "http://192.168.1.147:80/";
        }
    }
}
