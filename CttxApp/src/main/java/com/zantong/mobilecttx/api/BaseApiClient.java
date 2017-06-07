package com.zantong.mobilecttx.api;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.ErrorEvent;
import com.zantong.mobilecttx.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.qqtheme.framework.util.LogUtils;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * 网络请求基类
 * @author Sandy
 * create at 16/6/1 下午5:46
 */
public class BaseApiClient {

    public static final MediaType JSON = MediaType
            .parse("application/raw; charset=utf-8");
    public static OkHttpClient client = new OkHttpClient();
    public static final OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
    static {
        builder.connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS);
        try {
            // Create a trust manager that does not validate certificate chains
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
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            builder.sslSocketFactory(sslSocketFactory);
            builder.sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new RequestInterceptor())
                    .addNetworkInterceptor(new StethoInterceptor());
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.cookieJar(new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            });

            client = builder.build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


    }



    /**
     * get请求
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param asyncCallBack 异步线程回调
     */
    public static <T> void get(Context context, String url,
                               AsyncCallBack<T> asyncCallBack) {
//        PrefUtils pf = PrefUtils.getInstance(context);
//        if (pf.isLogin()) {
//            url += "?token=" + pf.getToken();
//        }
        Request request = new Request.Builder().tag(asyncCallBack.getTag())
                .url(url).get().build();
        enqueue(context, request, asyncCallBack);
    }

    /**
     * post请求
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param jsonParams    传输的数据
     * @param asyncCallBack 异步回调函数
     */
    public static <T> void post(Context context, String url, Object jsonParams,
                                AsyncCallBack<T> asyncCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        String data = asyncCallBack.getGson().toJson(jsonParams);
        if (data != null){
            LogUtils.i("msg:" + data);
            builder.add("msg", data);
        }
        if (PublicData.getInstance().loginFlag && !"".equals(PublicData.getInstance().userID)) {
            LogUtils.i("BaseApiClient token :"+PublicData.getInstance().userID);
            builder.add("usrid", PublicData.getInstance().userID);
        }
        Request request = new Request.Builder().tag(asyncCallBack.getTag())
                .url(url).post(builder.build()).build();
        enqueue(context, request, asyncCallBack);
    }

    /**
     * htmlpost请求
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param jsonParams    传输的数据
     * @param asyncCallBack 异步回调函数
     */
    public static <T> void htmlpost(Context context, String url, String jsonParams,
                                AsyncCallBack<T> asyncCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
//        String data = asyncCallBack.getGson().toJson(jsonParams);
        if (jsonParams != null){
            LogUtils.i("msg:" + jsonParams);
            builder.add("msg", jsonParams);
        }
        if (PublicData.getInstance().loginFlag && !"".equals(PublicData.getInstance().userID)) {
            LogUtils.i("BaseApiClient token :"+PublicData.getInstance().userID);
            builder.add("usrid", PublicData.getInstance().userID);
        }
        Request request = new Request.Builder().tag(asyncCallBack.getTag())
                .url(url).post(builder.build()).build();
        enqueue(context, request, asyncCallBack);
    }

    /**
     * 上传图片post
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param base64File    base64格式的文件字符串
     * @param asyncCallBack 回调函数
     */
    public static <T> void post(Context context, String url, String base64File,
                                AsyncCallBack<T> asyncCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        LogUtils.i("data:" + base64File);
        builder.add("data", base64File);
        Request request = new Request.Builder().tag(asyncCallBack.getTag())
                .url(url).post(builder.build()).build();
        enqueue(context, request, asyncCallBack);
    }

    /**
     * 请求执行
     *
     * @param context       上下文对象
     * @param request       请求对象
     * @param asyncCallback 回调函数
     */
    public static <T> void enqueue(final Context context, Request request,
                                   AsyncCallBack<T> asyncCallback) {
        if (context == null) {
            return;
        }
        LogUtils.i("http_request_url:" + request.url());
        if (NetUtils.getNetStatus(context) == NetUtils.NET_NONE) {
            asyncCallback.getCallback().sendFailMessage(Config.ERROR_NET,
                    Config.ERROR_NET_MSG);
            EventBus.getDefault().post(
                    new ErrorEvent(Config.ERROR_NET, Config.ERROR_NET_MSG,
                            asyncCallback.getTag(), context));
        } else {
            //为OkHttp设置自动携带Cookie的功能
            builder.followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
                   .followSslRedirects(false)
                   .cookieJar(new LocalCookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            });
            client = builder.build();
            client.newCall(request).enqueue(asyncCallback);
        }
    }

    /**
     * post请求
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param jsonParams    传输的数据
     * @param versionCallBack 异步回调函数
     */
    public static <T> void post(Context context, String url, Object jsonParams,
                                VersionCallBack<T> versionCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        String data = versionCallBack.getGson().toJson(jsonParams);
        if (data != null){
            LogUtils.i("msg:" + data);
            builder.add("msg", data);
        }
        Request request = new Request.Builder().tag(versionCallBack.getTag())
                .url(url).post(builder.build()).build();
        enqueue(context, request, versionCallBack);
    }

    /**
     * 请求获取版本号
     *
     * @param context       上下文对象
     * @param request       请求对象
     * @param versionCallback 回调函数
     */
    public static <T> void enqueue(final Context context, Request request,
                                   VersionCallBack<T> versionCallback) {
        if (context == null) {
            return;
        }
        LogUtils.i("http_request_url:" + request.url());
        if (NetUtils.getNetStatus(context) == NetUtils.NET_NONE) {
            versionCallback.getCallback().sendFailMessage(Config.ERROR_NET,
                    Config.ERROR_NET_MSG);
            EventBus.getDefault().post(
                    new ErrorEvent(Config.ERROR_NET, Config.ERROR_NET_MSG,
                            versionCallback.getTag(), context));
        } else {
            client.newCall(request).enqueue(versionCallback);

        }
    }

    /**
     * 根据tag取消网络请求
     *
     * @param tag 网络请求标记
     */
    public static void cancelCall(Object tag) {
//        client.dispatcher().cancelAll();
    }


    public static String getUrl(String relativeUrl) {
        return BuildConfig.CAR_MANGER_URL + relativeUrl;
    }

    public static String getDownUrl(String relativeUrl) {
        return BuildConfig.APP_DOWNLOD + relativeUrl;
    }

    /**
     * post请求
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param jsonParams    传输的数据
     * @param baseCallBack 异步回调函数
     */
    public static <T> void post(Context context, String url, Object jsonParams,
                                BaseCallBack<T> baseCallBack) {
        String data = baseCallBack.getGson().toJson(jsonParams);

        Request request = new Request.Builder().tag(baseCallBack.getTag())
                .url(url).post(RequestBody.create(JSON, data)).build();
        enqueue(context, request, baseCallBack);
    }

    /**
     * get请求
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param baseCallBack 异步线程回调
     */
    public static <T> void get(Context context, String url,
                               BaseCallBack<T> baseCallBack) {
        Request request = new Request.Builder().tag(baseCallBack.getTag())
                .url(url).get().build();
        enqueue(context, request, baseCallBack);
    }

    /**
     * 请求执行
     *
     * @param context       上下文对象
     * @param request       请求对象
     * @param baseCallBack 回调函数
     */
    public static <T> void enqueue(final Context context, Request request,
                                   BaseCallBack<T> baseCallBack) {
        if (context == null) {
            return;
        }
        LogUtils.i("http_request_url:" + request.url());
        if (NetUtils.getNetStatus(context) == NetUtils.NET_NONE) {
            baseCallBack.getCallback().sendFailMessage(Config.ERROR_NET,
                    Config.ERROR_NET_MSG);
            EventBus.getDefault().post(
                    new ErrorEvent(Config.ERROR_NET, Config.ERROR_NET_MSG,
                            baseCallBack.getTag(), context));
        } else {
            client.newCall(request).enqueue(baseCallBack);
        }
    }

    public static class LocalCookieJar implements CookieJar {
        List<Cookie> cookies;
        @Override
        public List<Cookie> loadForRequest(HttpUrl arg0) {
            if (cookies != null)
                return cookies;
            return new ArrayList<Cookie>();
        }

        @Override
        public void saveFromResponse(HttpUrl arg0, List<Cookie> cookies) {
            this.cookies = cookies;
        }

    }

    /**
     * post请求  上传行驶证、驾驶证照片
     *
     * @param context       上下文对象
     * @param url           请求地址
     * @param file    传输的数据
     * @param baseCallBack 异步回调函数
     */
    public static <T> void post(Context context, String url, File file,
                                OcrCallBack<T> baseCallBack) {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file","ocr_img.jpg", RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder().tag(baseCallBack.getTag())
                .url(url).post(requestBody).build();
        enqueue(context, request, baseCallBack);
    }


    /**
     * 请求执行
     *
     * @param context       上下文对象
     * @param request       请求对象
     * @param baseCallBack 回调函数
     */
    public static <T> void enqueue(final Context context, Request request,
                                   OcrCallBack<T> baseCallBack) {
        if (context == null) {
            return;
        }
        LogUtils.i("http_request_url:" + request.url());
        if (NetUtils.getNetStatus(context) == NetUtils.NET_NONE) {
            baseCallBack.getCallback().sendFailMessage(Config.ERROR_NET,
                    Config.ERROR_NET_MSG);
            EventBus.getDefault().post(
                    new ErrorEvent(Config.ERROR_NET, Config.ERROR_NET_MSG,
                            baseCallBack.getTag(), context));
        } else {
            client.newCall(request).enqueue(baseCallBack);
        }
    }
}
