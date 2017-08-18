package com.zantong.mobilecttx.common;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.Bugly;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.BuildConfig;

import cn.qqtheme.framework.util.AppUtils;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.log.LogUtils;


/**
 * 启动吧
 */
public class MyApplication extends MultiDexApplication {
    public void onCreate() {
        super.onCreate();

        initThirdTools();
    }

    /**
     * 第三方工具类
     */
    private void initThirdTools() {
//有盟+统计初始化
        MobclickAgent.UMAnalyticsConfig umAnalyticsConfig =
                new MobclickAgent.UMAnalyticsConfig(
                        getApplicationContext(), BuildConfig.DEBUG
                        ? "592544d7b27b0a65a200069e" : "58b3880304e20581760018e7",
                        AppUtils.getAppMetaData(getApplicationContext(), "UMENG_CHANNEL"));

        MobclickAgent.startWithConfigure(umAnalyticsConfig);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        MobclickAgent.enableEncrypt(true);//日志加密
        MobclickAgent.setCatchUncaughtExceptions(BuildConfig.DEBUG);
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式

//工具类
        ContextUtils.init(this);
//图片
        initImageLoader(this);
//推送
        initCloudChannel(this);
//百度 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
//bugly初始化
        Bugly.init(getApplicationContext(),
                BuildConfig.DEBUG ? "b7b596e1eb"
                        : "62323a33e6", BuildConfig.DEBUG);
//Log环境初始化
        LogUtils.initLogUtils(BuildConfig.DEBUG);
    }

    /**
     * 初始化加载图片工具类
     *
     * @param context 上下文对象
     */
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
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
