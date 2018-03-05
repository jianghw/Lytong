package com.tzly.ctcyh.router.api;

import retrofit2.Retrofit;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IRetrofitUrl {
    Retrofit createRetrofit(String url);
}
