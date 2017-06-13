package com.zantong.mobilecttx.eventbus;

public class GetMsgAgainEvent {
    private boolean status;

    public GetMsgAgainEvent(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
