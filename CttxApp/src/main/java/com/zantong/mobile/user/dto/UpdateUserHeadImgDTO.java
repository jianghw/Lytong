package com.zantong.mobile.user.dto;

/**
 * Created by zhoujie on 2017/2/14.
 */

public class UpdateUserHeadImgDTO {
    private String usrid;
    private String portrait;
    private String devicetoken;
    private String pushswitch;

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getPushswitch() {
        return pushswitch;
    }

    public void setPushswitch(String pushswitch) {
        this.pushswitch = pushswitch;
    }
}
