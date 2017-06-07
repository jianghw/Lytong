package com.zantong.mobilecttx.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 监听日期的服务
 * Created by zhengyingbing on 16/6/23.
 */
public class InspectService extends Service {


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

        public MyHandler(InspectService ds) {
            wr = new WeakReference(ds);
        }

        @Override
        public void handleMessage(Message msg) {
            InspectService ds = (InspectService) wr.get();
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

    }
}
