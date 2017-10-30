package com.zantong.mobilecttx.model.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.application.LoginData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

public class DefaultRetrofit implements IRetrofitUrl {

    private OkHttpClient client = null;

    private static class SingletonHolder {
        private static final DefaultRetrofit INSTANCE = new DefaultRetrofit();
    }

    public static DefaultRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DefaultRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("DefaultOKHttp", message);
                    }
                })
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC
                        : HttpLoggingInterceptor.Level.NONE);

        //拦截 Token添加器
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                Request.Builder requestBuilder = oldRequest.newBuilder();

                // 添加head
                Headers.Builder headBuilder = oldRequest.headers().newBuilder();
                headBuilder.add("DvcToken", LoginData.getInstance().imei);
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

        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30*1000, TimeUnit.MILLISECONDS)
                .writeTimeout(30*1000, TimeUnit.MILLISECONDS)
                .readTimeout(30*1000, TimeUnit.MILLISECONDS)
                .addInterceptor(mTokenInterceptor)
//                .authenticator(mAuthenticator)
                .build();

//        SSLContext sslContext;
//        try {
//            sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

    //    private Authenticator mAuthenticator = new Authenticator() {
//        @Override
//        public Request authenticate(Route route, Response response) throws IOException {
//            TokenBean refreshToken = refreshTokenBean();
//            return response.request().newBuilder()
//                    .header("Authorization", "Bearer " + refreshToken.getAccessToken())
//                    .build();
//        }
//    };
}
