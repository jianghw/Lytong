package com.zantong.mobilecttx.alicloudpush;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.bean.HomeNotice;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.map.activity.BaiduMapActivity;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.home.fragment.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: 正纬
 * @since: 15/4/9
 * @version: 1.1
 * @feature: 用于接收推送的通知和消息
 */
public class MyMessageReceiver extends MessageReceiver {

    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "devi";

    /**
     * 推送通知的回调方法
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        if (null != extraMap) {
            PushBean pushBean = new PushBean();
            for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                if (entry.getKey().equals("id")) {
                    pushBean.setId(entry.getValue());
                }
                if (entry.getKey().equals("type")) {
                    try {
                        pushBean.setType(Integer.valueOf(entry.getValue()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                if (entry.getKey().equals("content")) {
                    pushBean.setContent(entry.getValue());
                }
                if (entry.getKey().equals("title")) {
                    pushBean.setTitle(entry.getValue());
                }
                Log.i(REC_TAG, "@Get diy param : Key=" + entry.getKey() + " , Value=" + entry.getValue());
            }
            pushBean.setDate(Tools.getYearDate());
            pushBean.setNewMeg(true);
            if (pushBean.getType() == 1) {
                saveData(context, pushBean, "nianjian");
            } else if (pushBean.getType() == 2) {

            } else if (pushBean.getType() == 3) {
                saveData(context, pushBean, "youjia");
            } else if (pushBean.getType() == 4) {
                saveData(context, pushBean, PublicData.getInstance().userID);
            }
            try {
                // 刷新下消息列表
                HomeFragment.homeFragment.updateNotice();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(REC_TAG, "@收到通知 && 自定义消息为空");
        }
        Log.i(REC_TAG, "收到一条推送通知 ： " + title);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary,
                                               Map<String, String> extraMap, int openType,
                                               String openActivity, String openUrl) {
        Log.i(REC_TAG, "onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
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

            Log.i(REC_TAG, "收到一条推送消息 ： " + cPushMessage.getTitle());

            // 持久化推送的消息到数据库
            new MessageDao(context).add(new MessageEntity(cPushMessage.getMessageId().substring(6, 16),
                    Integer.valueOf(cPushMessage.getAppId()), cPushMessage.getTitle(),
                    cPushMessage.getContent(), new SimpleDateFormat("HH:mm:ss").format(new Date())));

        } catch (Exception e) {
            Log.i(REC_TAG, e.toString());
        }
    }

    /**
     * 从通知栏打开通知的扩展处理
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.i(REC_TAG, "onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
        if (!TextUtils.isEmpty(extraMap)) {
            Gson gson = new Gson();
            PushBean pushBean = gson.fromJson(extraMap, PushBean.class);
            pushBean.setNewMeg(false);
            UserInfoRememberCtrl.saveObject(context, "pushBean", pushBean);
            if (pushBean.getType() == 1) {
                //年检
                saveDataOpen(context, pushBean, "nianjian");
                PublicData.getInstance().mapType = BaiduMapActivity.TYPE_NIANJIAN;
                Intent intent = new Intent(context, BaiduMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (pushBean.getType() == 2) {
                //驾照换证提醒

            } else if (pushBean.getType() == 3) {
                //油价提醒
                saveDataOpen(context, pushBean, "youjia");
                PublicData.getInstance().webviewUrl = Config.HOME_NOTICE_URL;
                PublicData.getInstance().webviewTitle = "油价通知";
                Intent intent = new Intent(context, BrowserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (pushBean.getType() == 4) {
            }
        }
    }

    private void saveData(Context context, PushBean pushBean, String key) {
        List<HomeNotice> list;
        boolean isHave = false;
        list = (List<HomeNotice>) UserInfoRememberCtrl.readObject(context, key);
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
                homeNotice.setType(pushBean.getType());
                homeNotice.setDate(pushBean.getDate());
                homeNotice.setNewMeg(pushBean.isNewMeg());
                list.add(homeNotice);
                UserInfoRememberCtrl.saveObject(context, key, list);
            }
        } else {
            list = new ArrayList<HomeNotice>();
            HomeNotice homeNotice = new HomeNotice();
            homeNotice.setId(pushBean.getId());
            homeNotice.setDesc(pushBean.getContent());
            homeNotice.setType(pushBean.getType());
            homeNotice.setDate(pushBean.getDate());
            homeNotice.setNewMeg(pushBean.isNewMeg());
            list.add(homeNotice);
            UserInfoRememberCtrl.saveObject(context, key, list);
        }
    }

    private void saveDataOpen(Context context, PushBean pushBean, String key) {
        try {
            List<HomeNotice> list;
            list = (List<HomeNotice>) UserInfoRememberCtrl.readObject(context, key);
            for (HomeNotice item : list) {
                if (item.getId().equals(pushBean.getId())) {
                    item.setNewMeg(false);
                }
            }
            UserInfoRememberCtrl.saveObject(context, key, list);
            try {
                // 刷新下消息列表
                HomeFragment.homeFragment.updateNotice();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onNotificationRemoved(Context context, String messageId) {
        Log.i(REC_TAG, "onNotificationRemoved ： " + messageId);
    }


    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.i(REC_TAG, "onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
    }
}