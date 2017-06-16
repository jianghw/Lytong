package com.zantong.mobilecttx.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home.activity.HomeActivity;

import cn.qqtheme.framework.util.log.LogUtils;


/**
 * Created by zhengyingbing on 16/6/22.
 */
public class MyReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_FLAG = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.i("content----" + intent.getStringExtra("content"));
        String content = intent.getStringExtra("content");
        showNotifyView(context,content);
    }

    private void showNotifyView(Context context,String content){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Notification myNotify = new Notification(R.drawable.message,
        // "自定义通知：您有新短信息了，请注意查收！", System.currentTimeMillis());
        Notification myNotify = new Notification();
        myNotify.icon = R.mipmap.app_icon;
        myNotify.tickerText = "畅通车友会推送消息！";
        myNotify.when = System.currentTimeMillis();
        myNotify.flags = Notification.FLAG_AUTO_CANCEL;// 不能够自动清除
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.view_notification);
        rv.setTextViewText(R.id.notification_content, content);
        myNotify.contentView = rv;
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1,intent, 0);
        myNotify.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFICATION_FLAG, myNotify);

    }

    public PendingIntent getDefalutIntent(Context context, int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }
}

