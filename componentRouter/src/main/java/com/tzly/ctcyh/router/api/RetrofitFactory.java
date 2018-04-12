package com.tzly.ctcyh.router.api;

import com.tzly.ctcyh.router.BuildConfig;

import retrofit2.Retrofit;

/**
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
                return HttpsRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 2:
                return HttpsRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            case 3:
                return HttpsRetrofit.getInstance().createRetrofit(getBaseUrl(type));
            default:
                return HttpsRetrofit.getInstance().createRetrofit(getBaseUrl(type));
        }
    }

    /**
     * 手动修改值
     */
    private String getBaseUrl(int type) {
        switch (type) {
            case 1:
                return BuildConfig.isDeta
                        ? BuildConfig.beta_base_url : BuildConfig.release_base_url;
            case 2:
                return BuildConfig.isDeta
                        ? BuildConfig.beta_bank_url : BuildConfig.release_bank_url;
            case 3://上传图片
                return "http://liyingtong.com:8080/";
            case 4://测试
                return "http://192.168.1.127:8082/";
            case 5:
                return BuildConfig.beta_base_url;
            case 6:
                return BuildConfig.beta_bank_url;
            case 7:
                return BuildConfig.isDeta
                        ? "https://dev.liyingtong.com:8011/admin/index.php/"
                        : "http://liyingtong.com:8011/admin/index.php/";
            default:
                return "http://192.168.1.147:80/";
        }
    }
}
