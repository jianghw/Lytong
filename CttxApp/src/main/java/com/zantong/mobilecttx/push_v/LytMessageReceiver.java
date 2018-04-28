package com.zantong.mobilecttx.push_v;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.tzly.ctcyh.router.util.ActivityUtils;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.Tools;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.eventbus.AddPushTrumpetEvent;
import com.zantong.mobilecttx.router.MainRouter;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder.getPackageName;
import static com.tzly.ctcyh.router.util.ActivityUtils.startActivity;

/**
 * 用于接收推送的通知和消息
 */
public class LytMessageReceiver extends MessageReceiver {

    /**
     * 推送通知的回调方法
     * 1是小喇叭
     * 2是消息
     */
    @Override
    public void onNotification(Context context, String title,
                               String summary, Map<String, String> extraMap) {
        LogUtils.i(title + "<--onNotification->" + summary);
        if (null == extraMap) return;

        PushBean pushBean = new PushBean();
        pushBean.setTitle(title);
        pushBean.setContent(summary);
        pushBean.setDate(Tools.getYearDate());
        pushBean.setNewMeg(true);

        for (Map.Entry<String, String> entry : extraMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("id")) {
                pushBean.setId(value);
            } else if (key.equals("type")) {
                pushBean.setType(value);
            }
        }

        if (pushBean.getType() != null && pushBean.getType().equals("1")) {
            EventBus.getDefault().postSticky(new AddPushTrumpetEvent(pushBean));
        }
        //启动服务标记小圆点
        Intent i = new Intent(context, PushTipService.class);
        i.setAction("com.custom.service.push.tip");
        context.startService(i);
    }

    /**
     * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,
     * 相关详情请参考
     * https://help.aliyun.com/document_detail/30066.html?spm=5176.product30047.6.620.wjcC87#h3-3-4-basiccustompushnotification-api
     */
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary,
                                               Map<String, String> extraMap, int openType,
                                               String openActivity, String openUrl) {
        LogUtils.i("onNotificationReceivedInApp ====： "
                + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
    }

    /**
     * 推送消息的回调方法
     */
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        try {
            LogUtils.i("收到一条推送消息 ====： " + cPushMessage.getTitle());
            buildNotification(context, cPushMessage);
        } catch (Exception e) {
            LogUtils.i(e.toString());
        }
    }

    /**
     * 从通知栏打开通知的扩展处理
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        LogUtils.i("onNotificationOpened");

        AliPushExtBean pushExtBean = new Gson().fromJson(extraMap, AliPushExtBean.class);
        if (pushExtBean == null) return;
        String type = pushExtBean.getType();
        if (TextUtils.isEmpty(type)) return;
        //前台工作
        if (type.equals("1"))//主页
            MainRouter.gotoMainActivity(context, 0);
        else if (type.equals("2"))//消息详情
            MainRouter.gotoMegDetailActivity(context, title, pushExtBean.getId());
        else if (type.equals("3"))//优惠详情
            MainRouter.gotoCouponStatusActivity(context);
        else if (type.equals("4"))//html详情
            MainRouter.gotoWebHtmlActivity(context, title, pushExtBean.getUrl());
        else if (type.equals("5"))//违章查询
            MainRouter.gotoViolationActivity(context);
        else if (type.equals("14") || !TextUtils.isEmpty(pushExtBean.getUrl())) {
            Activity activity = ActivityUtils.getTopActivity();
            if (activity instanceof FragmentActivity) {
                FragmentActivity fragmentActivity = (FragmentActivity) activity;
                MainRouter.gotoByTargetPath(pushExtBean.getUrl(), fragmentActivity);
            }
        } else
            gotoAppLauncher(context);
    }

    private void gotoAppLauncher(Context context) {
        try {
            Intent LaunchIntent = context.getPackageManager()
                    .getLaunchIntentForPackage("com.zantong.mobilecttx");
            startActivity(LaunchIntent);
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("geo1://......"));
            startActivity(intent);
        }
    }

    /**
     * 接受到对应消息后，消息的弹出处理
     */
    public void buildNotification(Context context, CPushMessage message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_ali_push_notification);
        remoteViews.setImageViewResource(R.id.custom_icon, R.mipmap.ic_global_app);
        remoteViews.setTextViewText(R.id.tv_custom_title, message.getTitle());
        remoteViews.setTextViewText(R.id.tv_custom_content, message.getContent());
        remoteViews.setTextViewText(R.id.tv_custom_time, new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINESE).format(new Date()));

        Notification notification = new NotificationCompat.Builder(context)
                .setContent(remoteViews)
                .setContentTitle(message.getTitle())
                .setContentText(message.getContent())
                .setTicker(message.getTitle()) //通知首次出现在通知栏，带上升动画效果的
                .setSmallIcon(R.mipmap.ic_global_app)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setVibrate(new long[]{0, 300, 500, 700})// 设置震动的时间
                .setDefaults(Notification.DEFAULT_ALL)// 添加默认以上3种全部提醒
                .build();
        notification.contentIntent = buildClickContent(context, message);
        notification.deleteIntent = buildDeleteContent(context, message);
        notificationManager.notify(message.hashCode(), notification);
    }

    public PendingIntent buildClickContent(Context context, CPushMessage message) {
        Intent clickIntent = new Intent();
        clickIntent.setAction("com.push_v.PushNotificationService.click");
        //添加其他数据
        clickIntent.putExtra("message_click", message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, 1000, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent buildDeleteContent(Context context, CPushMessage message) {
        Intent deleteIntent = new Intent();
        deleteIntent.setAction("com.push_v.PushNotificationService.delete");
        //添加其他数据
        deleteIntent.putExtra("message_delete", message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, 2000, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 通知删除回调
     */
    @Override
    public void onNotificationRemoved(Context context, String messageId) {
        LogUtils.i("onNotificationRemoved=== ： " + messageId);
    }

    /**
     * 无动作通知点击回调。
     * 当在后台或阿里云控制台指定的通知动作为无逻辑跳转时,
     * 通知点击回调为onNotificationClickedWithNoAction而不是onNotificationOpened
     */
    @Override
    protected void onNotificationClickedWithNoAction(Context context,
                                                     String title, String summary, String extraMap) {
        LogUtils.i("onNotificationClickedWithNoAction ====： " + " : " + title + " : " + summary + " : " + extraMap);
    }
}