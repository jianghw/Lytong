package com.zantong.mobilecttx.widght.refresh;

import android.os.Handler;

import java.util.TimerTask;

/**
 * Created by jianghw on 2017/8/24.
 * Description:
 * Update by:
 * Update day:
 */
public class MyTask extends TimerTask {
    private Handler handler;

    MyTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.obtainMessage().sendToTarget();
    }

}
