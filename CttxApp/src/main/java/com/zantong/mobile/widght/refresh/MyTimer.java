package com.zantong.mobile.widght.refresh;

import android.os.Handler;

import java.util.Timer;

/**
 * Created by jianghw on 2017/8/24.
 * Description:
 * Update by:
 * Update day:
 */
public class MyTimer {

    private Handler mHandler;
    private Timer mTimer;
    private MyTask mTask;

    MyTimer(Handler handler) {
        this.mHandler = handler;
        mTimer = new Timer();
    }

    public void schedule(long period) {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTask(mHandler);
        mTimer.schedule(mTask, 0, period);
    }

    public void cancel() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
    }

}
