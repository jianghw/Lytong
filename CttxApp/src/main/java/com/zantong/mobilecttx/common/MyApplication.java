package com.zantong.mobilecttx.common;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;
import com.zantong.mobilecttx.BuildConfig;

import cn.qqtheme.framework.util.LogUtils;


/**
 * Created by Administrator on 2016/5/19.
 */
public class MyApplication extends MultiDexApplication {
    public void onCreate(){
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        initImageLoader(this);
        initCloudChannel(this);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //bugly初始化
        CrashReport.initCrashReport(
                getApplicationContext(),
                BuildConfig.DEBUG ? "b7b596e1eb" : "62323a33e6", BuildConfig.DEBUG);
        //Log环境初始化
        LogUtils.initLogUtils(BuildConfig.DEBUG);
    }

    /**
     * 初始化加载图片工具类
     *
     * @param context
     *            上下文对象
     */
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50*1024*1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                 // Remove for release app
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtils.i("DeviceId=============:" + PushServiceFactory.getCloudPushService().getDeviceId());
                PublicData.getInstance().deviceId = pushService.getDeviceId();
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtils.i("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        // 初始化小米通道，自动判断是否支持小米系统推送，如不支持会跳过注册   小米appid   小米appkey
        MiPushRegister.register(applicationContext, "2882303761517529702", "5791752927702");
        // 初始化华为通道，自动判断是否支持华为系统推送，如不支持会跳过注册
        HuaWeiRegister.register(applicationContext);
    }

}
