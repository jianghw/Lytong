package com.zantong.mobile.eventbus;

import com.zantong.mobile.push.PushBean;

/**
 * 小喇叭推送
 */

public class AddPushTrumpetEvent {

    private PushBean pushBean;

    public AddPushTrumpetEvent(PushBean status) {
        this.pushBean = status;
    }

    public PushBean getPushBean() {
        return pushBean;
    }

    public void setPushBean(PushBean pushBean) {
        this.pushBean = pushBean;
    }
}
