package com.zantong.mobilecttx.base.basehttprequest;

import android.content.Context;
import android.os.Environment;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renyu on 2016/3/21.
 */
public class Retrofit2Utils {

    //okhttp build对象
    private OkHttpClient.Builder okhttpBuilder;
    //Retrofit build对象
    private Retrofit.Builder retrofitBuilder;
    //日志拦截器
    private HttpLoggingInterceptor interceptor;

    public Retrofit2Utils() {
        retrofitBuilder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        okhttpBuilder = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
        okhttpBuilder.addInterceptor(interceptor);
    }

    /**
     * 设置缓存
     *
     * @param flag
     */
    public void enableCache(boolean flag, Context context) {
        if (flag) {
            //缓存拦截器
            okhttpBuilder.addInterceptor(new CacheInterceptor(context))
                    .addNetworkInterceptor(new CacheInterceptor(context))
                    //设置缓存路径以及大小
                    .cache(new Cache(new File(Environment.getExternalStorageDirectory().getPath() + "/retrofit2demo"), 1024 * 1024 * 100));
            okhttpBuilder.interceptors().add(new CacheInterceptor(context));
        }
    }

    /**
     * 添加额外的拦截器
     *
     * @param interceptor
     * @return
     */
    public void addExtraInterceptor(Interceptor interceptor) {
        okhttpBuilder.interceptors().add(interceptor);
    }

    public <T> Retrofit getListRetrofit(String baseUrl, Class<T> class_) {
        return retrofitBuilder.addConverterFactory(ListGsonConverterFactory.create(class_)).client(okhttpBuilder.build()).baseUrl(baseUrl).build();
    }

    public Retrofit getRetrofit(String baseUrl) {
        return retrofitBuilder.addConverterFactory(GsonConverterFactory.create()).client(okhttpBuilder.build()).baseUrl(baseUrl).build();
    }

    public Retrofit getRetrofitHttps(String baseUrl) {
        return retrofitBuilder.addConverterFactory(GsonConverterFactory.create()).client(getUnsafeOkHttpClient()).baseUrl(baseUrl).build();
    }

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
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

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
//            builder.interceptors().add(new LoggingInterceptor());
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new StethoInterceptor());

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
