package com.zantong.mobilecttx.eventbus;

/**
 * 作者：王海洋
 * 时间：2016/7/7 10:53
 */
public class PayBackH {

    private boolean status;
    private Object tag;

    public PayBackH(boolean status, Object tag) {
        super();
        this.status = status;
        this.tag = tag;
    }

    public PayBackH(boolean status) {
        super();
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

}
