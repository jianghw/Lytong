package com.zantong.mobile.push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.tzly.annual.base.bean.HomeNotice;
import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobile.cattle.MainClubActivity;
import com.zantong.mobile.eventbus.AddPushTrumpetEvent;
import com.zantong.mobile.user.activity.MegDetailActivity;
import com.zantong.mobile.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobile.utils.Tools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于接收推送的通知和消息
 */
public class LytMessageReceiver extends MessageReceiver {

    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    /**
     * 推送通知的回调方法
     * 1是小喇叭
     * 2是消息
     */
    @Override
    public void onNotification(Context context, String title,
                               String summary, Map<String, String> extraMap) {
        LogUtils.e(title + "onNotification" + summary);

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
            LogUtils.e("@收到通知 && 自定义消息为空");
        }
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary,
                                               Map<String, String> extraMap, int openType,
                                               String openActivity, String openUrl) {
        LogUtils.e("onNotificationReceivedInApp ====： "
                + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
    }

    /**
     * 推送消息的回调方法
     *
     * @param context
     * @param cPushMessage
     */
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        try {
            LogUtils.i("收到一条推送消息 ====： " + cPushMessage.getTitle());
            // 持久化推送的消息到数据库

        } catch (Exception e) {
            Log.i(REC_TAG, e.toString());
        }
    }

    /**
     * 从通知栏打开通知的扩展处理
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        LogUtils.e("onNotificationOpened");

        AliPushExtBean pushExtBean = new Gson().fromJson(extraMap, AliPushExtBean.class);
        if (pushExtBean != null) {
            String type = pushExtBean.getType();
            if (type.equals("1")) {//小喇叭页面
                Intent intent = new Intent(context, MainClubActivity.class);
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

    @Override
    public void onNotificationRemoved(Context context, String messageId) {
        LogUtils.i("onNotificationRemoved=== ： " + messageId);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        LogUtils.i("onNotificationClickedWithNoAction ====： " + " : " + title + " : " + summary + " : " + extraMap);
    }
}