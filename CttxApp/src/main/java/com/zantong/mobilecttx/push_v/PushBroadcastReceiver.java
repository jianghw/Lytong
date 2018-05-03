package com.zantong.mobilecttx.push_v;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.router.util.SPUtils;

/**
 * Created by jianghw on 2017/11/23.
 * Description:
 * Update by:
 * Update day:
 */
public class PushBroadcastReceiver extends BroadcastReceiver {

    public final static String PUSH_TIP_ACTION = "com.custom.push.PushBroadcastReceiver";
    private IPushBroadcastReceiver mCustomListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PUSH_TIP_ACTION)) {
            int count = intent.getIntExtra(PUSH_TIP_ACTION, 0);
            boolean isFind = SPUtils.instance().getBoolean(SPUtils.IS_HAS_FIND, false);
            if (mCustomListener != null) mCustomListener.tipByNumber(isFind ? 3 : 2, count);
        }
    }

    public void setCustomListener(IPushBroadcastReceiver customListener) {
        mCustomListener = customListener;
    }
}
