package com.zantong.mobilecttx.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 监听日期的服务
 * Created by zhengyingbing on 16/6/23.
 */
public class DateService extends Service {


    private Handler mHandler;
    private static final int MSG_UPDATE_CURRENT_TIME = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new MyHandler(this);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_CURRENT_TIME, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    private static class MyHandler extends Handler {
        private WeakReference wr;

        public MyHandler(DateService ds) {
            wr = new WeakReference(ds);
        }

        @Override
        public void handleMessage(Message msg) {
            DateService ds = (DateService) wr.get();
            switch (msg.what) {
                case MSG_UPDATE_CURRENT_TIME:
                    try {
                        ds.updateCurrentTime();
                        sendEmptyMessageDelayed(MSG_UPDATE_CURRENT_TIME, 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void updateCurrentTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm:ss");
        String currentTm = sdf.format(now).replace("/","").replace(":","").replace(" ","");
        RspInfoBean b = (RspInfoBean)UserInfoRememberCtrl.readObject();
        String date = "";
        if (!Tools.isStrEmpty(b.getGetdate())){
            date = b.getGetdate().replace("-","").substring(4) + "095930";
        }
        if (date.equals(currentTm)) {
            Intent intent = new Intent("com.zantong.mobilecttx.USER_ACTION");
            intent.putExtra("content", "您的记分周期需要更新啦");
            sendBroadcast(intent);
        }

    }
}
