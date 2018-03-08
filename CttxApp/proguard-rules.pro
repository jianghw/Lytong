# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Users\Administrator\AppData\Local\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:


##---------------Begin: proguard configuration for XRecyclerView  ----------
-dontwarn com.jcodecraeer.xrecyclerview.**
-keep class com.jcodecraeer.xrecyclerview.**{*;}
##---------------End: proguard configuration for XRecyclerView  ----------

##---------------Begin: proguard configuration for CordovaLib  ----------
-dontwarn org.apache.cordova.**
-keep class org.apache.cordova.**{*;}
##---------------End: proguard configuration for CordovaLib  ----------
#
########################     常用第三方模块的混淆选项         ###################################
##gson
##如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
#-keepattributes Signature
## Gson specific classes
#-keep class sun.misc.Unsafe { *; }
## Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.** { *; }
#-keep class com.google.gson.stream.** { *; }
#
##butterknife
#-keep class android.support.v7.** { *; }
#-dontwarn android.support.v7.internal.**
#
## 如果使用了Gson之类的工具要使被它解析的JavaBean类即实体类不被混淆。
#-keep class com.matrix.app.entity.json.** { *; }
#-keep class com.matrix.appsdk.network.model.** { *; }

#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
#-optimizations !code/simplificnative ation/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod
#-keepclassmembers class * {@com.xxx.Subscribe ;}
#-keepclassmembers class * {@com.xxx.Action ;}
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.AppCompatActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
#
#如果有引用v4包可以添加下面这行
#-keep public class * extends android.support.v4.app.Fragment
-keep class android.support.** {*;}
#忽略警告
-ignorewarning
#####################记录生成的日志数据,gradle build时在本项目根目录输出################
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#####################记录生成的日志数据，gradle build时 在本项目根目录输出-end################
################<span></span>混淆保护自己项目的部分代码以及引用的第三方jar包library#########################
#-libraryjars libs/umeng-analytics-v5.2.4.jar
#-libraryjars libs/alipaysd<span></span>k.jar
#<span></span>-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/wup-1.0.0-SNAPSHOT.jar
#-libraryjars libs/weibosdkcore.jar
#三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
#-libraryjars libs/sdk-v1.0.0.jar
#-libraryjars libs/look-v1.0.1.jar
#我是以libaray的形式引用了一个图片加载框架,如果不想混淆 keep 掉
-keep class com.nostra13.universalimageloader.** { *; }
#友盟
-keep class com.umeng.**{*;}
#支付宝
-keep class com.alipay.android.app.IAliPay{*;}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.lib.ResourceMap{*;}
#信鸽推送
-keep class com.tencent.android.tpush.**  {* ;}
-keep class com.tencent.mid.**  {* ;}

#自己项目特殊处理代码
#忽略警告
-dontwarn com.veidy.mobile.common.**
#保留一个完整的包
-keep class com.veidy.mobile.common.** {
    *;
 }
 -keep class com.zantong.mobilecttx.adapter.OpenViewAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.car.adapter.BindCarSuccessAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.car.adapter.CanPayCarAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.adapter.DefaultCardAdapter$* {
     *;
 }

 -keep class com.zantong.mobilecttx.weizhang.adapter.IllegalViolationAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.adapter.ManageVehiclesAdapter$* {
     *;
 }

 -keep class com.zantong.mobilecttx.adapter.MyPopWindowAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.weizhang.adapter.PayHistoryAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.adapter.PayRecordAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.adapter.PeopleAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.weizhang.adapter.QueryHistoryAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.car.adapter.SetPayCarAdapter$* {
     *;
 }
 -keep class com.zantong.mobilecttx.weizhang.adapter.VehicleTypeAdapter$* {
     *;
 }


-keep class com.zantong.mobilecttx.base.**{*;}
-keep class com.zantong.mobilecttx.view.viewinterface.**{*;}
-keep class com.zantong.mobilecttx.view.activity.**{*;}
-keep class com.zantong.mobilecttx.view.fragment.**{*;}
-keep class com.zantong.mobilecttx.interf.**{*;}
-keep class com.zantong.mobilecttx.model.modelinterface.**{*;}
-keep class com.zantong.mobilecttx.presenter.presenterinterface.**{*;}
-keep class com.zantong.mobilecttx.bean.**{*;}


#如果引用了v4或者v7包
-keep class android.support.**{*;}
-dontwarn android.support.**

############<span></span>混淆保护自己项目的部分代码以及引用的第三方jar包library-end##################
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
#避免混淆泛型 如果混淆报错建议关掉
#–keepattributes Signature
#移除log 测试了下没有用还是建议自己定义一个开关控制是否输出日志
#-assumenosideeffects class android.util.Log {
#    public static boolean isLoggable(java.lang.String, int);
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#    public static int e(...);
#}
#接口不混淆
-keep interface com.zantong.mobilecttx.base.interf.IBaseView{*;}
-keep interface com.zantong.mobilecttx.api.ActBackToUI{*;}
-keep interface com.zantong.mobilecttx.api.CTTXHttpPOSTInterface{*;}
-keep interface com.zantong.mobilecttx.api.OnLoadServiceBackUI{*;}
-keep interface com.zantong.mobilecttx.api.FileDownloadApi{*;}
-keep interface com.zantong.mobilecttx.api.FileUploadApi{*;}

###---------------End: proguard configuration for Commons-Codec  ----------
#
##---------------Begin: proguard configuration for EventBus  ----------
-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
#retrofit2 混淆配置
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
#retrofit2 混淆配置okhttp3混淆配置
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
#RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#Glide混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
##---------------End: proguard configuration for EventBus  ----------

-keep class com.baidu.kirin.** { *; }
-keep class com.baidu.mobstat.** { *; }
-keep class com.baidu.bottom.** { *; }
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

-keepclasseswithmembernames class * {
    native <methods>;
}