package com.zantong.mobilecttx.model.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.common.PublicData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianghw on 2017/4/26.
 * 网络请求配置
 */

public class AnShengRetrofit implements IRetrofitUrl {

    private OkHttpClient client = null;

    private static class SingletonHolder {
        private static final AnShengRetrofit INSTANCE = new AnShengRetrofit();
    }

    public static AnShengRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private AnShengRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("AnShengRetrofit", message);
                    }
                })
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE);

        //拦截 Token添加器
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                Request.Builder requestBuilder = oldRequest.newBuilder();
                // 添加head
                Headers.Builder headBuilder = oldRequest.headers().newBuilder();
                headBuilder.add("DvcToken", PublicData.getInstance().imei);
                requestBuilder.headers(headBuilder.build());

                CacheControl.Builder builder = new CacheControl.Builder();
                builder.noCache();//不使用缓存，全部走网络
                builder.noStore();//不使用缓存，也不存储缓存
                CacheControl cache = builder.build();
                // 重新组成新的请求
                Request newRequest = requestBuilder
                        .cacheControl(cache)
                        .build();
                return chain.proceed(newRequest);
            }
        };

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create an ssl socket factory with our all-trusting manager
    }

    private TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    @Override
    public Retrofit createRetrofit(String url) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
