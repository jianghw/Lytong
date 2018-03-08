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
        return MainRetrofit.getInstance().createRetrofit(getBaseUrl(type));
    }

    /**
     * 手动修改值
     */
    private String getBaseUrl(int type) {
        switch (type) {
            case 1://同赞自己服务器
                return BuildConfig.base_url;
            case 2://银行服务
                return BuildConfig.bank_app_url;
            case 3://上传图片
                return "http://liyingtong.com:8080/";
            case 4:
                return "http://192.168.1.127:8082/";
            default:
                return "http://192.168.1.147:80/";
        }
    }
}
