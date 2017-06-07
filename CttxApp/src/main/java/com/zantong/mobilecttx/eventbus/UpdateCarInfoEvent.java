package com.zantong.mobilecttx.eventbus;

/**
 * Created by zhoujie on 2016/10/15.
 */

public class UpdateCarInfoEvent {

    private boolean status;

    public UpdateCarInfoEvent(boolean status) {
        super();
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
