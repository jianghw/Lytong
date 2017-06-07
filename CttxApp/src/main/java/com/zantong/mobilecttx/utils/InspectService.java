package com.zantong.mobilecttx.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm:ss");
        OpenQueryBean.RspInfoBean b = (OpenQueryBean.RspInfoBean) UserInfoRememberCtrl.readObject(this.getBaseContext(), PublicData.getInstance().CarLocalFlag);
        String currentTm = sdf.format(now).replace("/","").replace(":","").replace(" ","");
        if (b != null) {
            for (int i = 0; i < b.getUserCarsInfo().size(); i++) {
//                String tm = b.getUserCarsInfo().get(i).getInspectdate().substring(4) + "095930";
                String tm = b.getUserCarsInfo().get(i).getInspectdate().replace("-","").substring(4) + "173415";
                LogUtils.i("date-" + tm +" : "+currentTm);
                if ((tm).equals(currentTm)) {
                    Intent intent = new Intent("com.zantong.mobilecttx.USER_ACTION");
                    intent.putExtra("content", "您的车牌号为[" + b.getUserCarsInfo().get(i).getCarnum() + "]的车需要年检提醒了");
                    sendBroadcast(intent);
                }
            }
        }
    }
}
