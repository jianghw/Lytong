package com.tzly.ctcyh.router.custom.refresh;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 刷新结果停留的handler
 */
class RemainHandler extends Handler {

    private WeakReference<PullToRefreshLayout> mLayout;

    public RemainHandler(PullToRefreshLayout layout) {
        mLayout = new WeakReference<>(layout);
    }
    @Override
    public void handleMessage(Message msg) {
        PullToRefreshLayout layout = mLayout.get();
        if (null != layout) {
            layout.changeState(PullToRefreshLayout.DONE);
            layout.hide();
        }
    }
}
