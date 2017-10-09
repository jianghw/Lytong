package com.zantong.mobile.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tzly.annual.base.util.AppUtils;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobile.BuildConfig;


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
        String channel = AppUtils.getAppMetaData(getApplicationContext(), "UMENG_CHANNEL");

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
        BuglyStrategy strategy = new BuglyStrategy();
        //设置渠道
        strategy.setAppChannel(channel);
        String version = AppUtils.getAppVersionName()
                + "_" + AppUtils.getAppVersionCode();
        //App的版本
        strategy.setAppVersion(version);
        Beta.autoInit = true;
        Bugly.init(getApplicationContext(),
                BuildConfig.LOG_DEBUG ? "ed72069020" : "0268e1b821",
                BuildConfig.LOG_DEBUG, strategy);
        //自动检查更新开关
        Beta.autoCheckUpgrade = false;
//Log环境初始化
        LogUtils.initLogUtils(BuildConfig.LOG_DEBUG);
    }

    /**
     * 初始化加载图片工具类
     *
     * @param context 上下文对象
     */
    private void initImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
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
                MemoryData.getInstance().deviceId = pushService.getDeviceId();
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtils.i("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        // 初始化小米通道，自动判断是否支持小米系统推送，如不支持会跳过注册   小米appid   小米appkey
//        MiPushRegister.register(applicationContext, "2882303761517529702", "5791752927702");
        // 初始化华为通道，自动判断是否支持华为系统推送，如不支持会跳过注册
//        HuaWeiRegister.register(applicationContext);
//        GcmRegister.register(applicationContext, "send_id", "application_id"); // 接入FCM/GCM初始化推送
    }

}
