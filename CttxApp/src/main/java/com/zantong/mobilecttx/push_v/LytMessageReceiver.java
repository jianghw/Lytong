package com.zantong.mobilecttx.push_v;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.eventbus.AddPushTrumpetEvent;
import com.zantong.mobilecttx.home.activity.HomeMainActivity;
import com.zantong.mobilecttx.home.bean.HomeNotice;
import com.zantong.mobilecttx.user.activity.MegDetailActivity;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.Tools;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.util.log.LogUtils;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder.getPackageName;

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
        LogUtils.i(title + "onNotification" + summary);

        CPushMessage message = new CPushMessage();
        message.setTitle(title);
        message.setContent(summary);
        buildNotification(context, message);

        PushBean pushBean = new PushBean();
        if (null != extraMap) {
            for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                if (entry.getKey().equals("id")) {
                    pushBean.setId(entry.getValue());
                }
                if (entry.getKey().equals("type")) {
                    pushBean.setType(entry.getValue());
                }
            }

            pushBean.setTitle(title);
            pushBean.setContent(summary);

            pushBean.setDate(Tools.getYearDate());
            pushBean.setNewMeg(true);

            if (pushBean.getType() != null && pushBean.getType().equals("1")) {
                EventBus.getDefault().postSticky(new AddPushTrumpetEvent(pushBean));
            }

        } else {
            LogUtils.i("@收到通知 && 自定义消息为空");
        }
    }

    /**
     * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,
     * 相关详情请参考https://help.aliyun.com/document_detail/30066.html?spm=5176.product30047.6.620.wjcC87#h3-3-4-basiccustompushnotification-api
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
        if (pushExtBean != null) {
            String type = pushExtBean.getType();
            if (type.equals("1")) {//小喇叭页面
                Intent intent = new Intent(context, HomeMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (type.equals("2")) {
                Intent intent = new Intent(context, MegDetailActivity.class);
                intent.putExtra("messageDetailId", pushExtBean.getId());
                intent.putExtra("title", title);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 接受到对应消息后，消息的弹出处理
     */
    public void buildNotification(Context context, CPushMessage message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_ali_push_notification);
        remoteViews.setImageViewResource(R.id.custom_icon, R.mipmap.app_icon);
        remoteViews.setTextViewText(R.id.tv_custom_title, message.getTitle());
        remoteViews.setTextViewText(R.id.tv_custom_content, message.getContent());
        remoteViews.setTextViewText(R.id.tv_custom_time, new SimpleDateFormat("HH:mm").format(new Date()));

        Notification notification = new NotificationCompat.Builder(context)
                .setContent(remoteViews)
                .setContentTitle(message.getTitle())
                .setContentText(message.getContent())
                .setSmallIcon(R.mipmap.app_icon)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build();
        notification.contentIntent = buildClickContent(context, message);
        notification.deleteIntent = buildDeleteContent(context, message);
        notificationManager.notify(message.hashCode(), notification);
    }

    public PendingIntent buildClickContent(Context context, CPushMessage message) {
        Intent clickIntent = new Intent();
        clickIntent.setAction("your notification click action");
        //添加其他数据
        clickIntent.putExtra("message key", message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, 1000, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent buildDeleteContent(Context context, CPushMessage message) {
        Intent deleteIntent = new Intent();
        deleteIntent.setAction("your notification click action");
        //添加其他数据
        deleteIntent.putExtra("message key", message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, 2000, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void saveData(Context context, PushBean pushBean, String key) {
        List<HomeNotice> list;
        boolean isHave = false;
        list = (List<HomeNotice>) UserInfoRememberCtrl.readObject(key);
        if (list != null) {
            for (HomeNotice item : list) {
                if (item.getId().equals(pushBean.getId())) {
                    isHave = true;
                }
            }
            if (!isHave) {
                HomeNotice homeNotice = new HomeNotice();
                homeNotice.setId(pushBean.getId());
                homeNotice.setDesc(pushBean.getContent());
                homeNotice.setType(Integer.valueOf(pushBean.getType()));
                homeNotice.setDate(pushBean.getDate());
                homeNotice.setNewMeg(pushBean.isNewMeg());
                list.add(homeNotice);
                UserInfoRememberCtrl.saveObject(key, list);
            }
        } else {
            list = new ArrayList<>();
            HomeNotice homeNotice = new HomeNotice();
            homeNotice.setId(pushBean.getId());
            homeNotice.setDesc(pushBean.getContent());
            homeNotice.setType(Integer.valueOf(pushBean.getType()));
            homeNotice.setDate(pushBean.getDate());
            homeNotice.setNewMeg(pushBean.isNewMeg());
            list.add(homeNotice);
            UserInfoRememberCtrl.saveObject(key, list);
        }
    }

    private void saveDataOpen(Context context, PushBean pushBean, String key) {
        try {
            List<HomeNotice> list;
            list = (List<HomeNotice>) UserInfoRememberCtrl.readObject(key);
            for (HomeNotice item : list) {
                if (item.getId().equals(pushBean.getId())) {
                    item.setNewMeg(false);
                }
            }
            UserInfoRememberCtrl.saveObject(key, list);
            try {
                // 刷新下消息列表

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        LogUtils.i("onNotificationClickedWithNoAction ====： " + " : " + title + " : " + summary + " : " + extraMap);
    }
}