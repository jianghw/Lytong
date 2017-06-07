package com.zantong.mobilecttx.daijia.dto;

/**
 * Created by zhoujie on 2017/2/23.
 */

public class DaiJiaOrderDetailDTO {
    private String time;
    private String orderId;
    private String hash;
    private String usrId;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrId() {
        return usrId;
    }


}
