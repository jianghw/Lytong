package com.zantong.mobilecttx.api;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.zantong.mobilecttx.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/4/20.
 */
public class APPHttpClient {

    private  CTTXHttpPOSTInterface cttxHttpPOSTInterface;

    private static APPHttpClient instance;
    private APPHttpClient (){}
    public static synchronized APPHttpClient getInstance() {
        if (instance == null) {
            instance = new APPHttpClient();
        }
        return instance;
    }

    public CTTXHttpPOSTInterface getCttxHttpInterface(){

        if(cttxHttpPOSTInterface == null){

            OkHttpClient mClient = getUnsafeOkHttpClient();
            Retrofit  restAdapter =new Retrofit.Builder()
                    .baseUrl(BuildConfig.APP_URL)
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            cttxHttpPOSTInterface = restAdapter.create(CTTXHttpPOSTInterface.class);
        }
        return cttxHttpPOSTInterface;
    }
    //网络请求
    public CTTXHttpPOSTInterface getCTTXHttpPOSTInterface (){

        if(cttxHttpPOSTInterface == null){
            OkHttpClient mClient = getUnsafeOkHttpClient();
            Retrofit  restAdapter =new Retrofit.Builder()
                    .baseUrl(BuildConfig.APP_URL)
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            cttxHttpPOSTInterface = restAdapter.create(CTTXHttpPOSTInterface.class);
        }
        return cttxHttpPOSTInterface;
    }

    /**
     * 上传文件接口
     */
    public CTTXHttpPOSTInterface getCTTXHttpPOSTFileInterface (){

        if(cttxHttpPOSTInterface == null){
            OkHttpClient mClient = getUnsafeOkHttpClient();
            Retrofit  restAdapter =new Retrofit.Builder()
                    .baseUrl(BuildConfig.APPFileUrl)
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            cttxHttpPOSTInterface = restAdapter.create(CTTXHttpPOSTInterface.class);
        }
        return cttxHttpPOSTInterface;
    }

    public static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
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
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            builder.sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })

                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new RequestInterceptor());

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
