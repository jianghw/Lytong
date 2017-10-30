package com.zantong.mobilecttx.push_v;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.CPushMessage;

/**
 * Created by jianghw on 2017/10/29.
 * Description:
 * Update by:
 * Update day:
 */

public class PushNotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals("com.push_v.PushNotificationService.click")) {
            //添加您的通知点击处理逻辑
            CPushMessage message = intent.getParcelableExtra("message_click");//获取message
            PushServiceFactory.getCloudPushService().clickMessage(message);//上报通知点击事件，点击事件相关信息可以在推送控制台查看到
        } else if (action.equals("com.push_v.PushNotificationService.delete")) {
            //添加您的通知删除处理逻辑
            CPushMessage message = intent.getParcelableExtra("message_delete");//获取message
            PushServiceFactory.getCloudPushService().dismissMessage(message);//上报通知删除事件，点击事件相关信息可以在推送控制台查看到
        }
        return super.onStartCommand(intent, flags, startId);
    }


}
