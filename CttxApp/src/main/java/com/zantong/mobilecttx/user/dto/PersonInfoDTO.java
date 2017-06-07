package com.zantong.mobilecttx.user.dto;

/**
 * 提交邀请码 实体
 * @author Sandy
 * create at 16/6/8 下午11:48
 */
public class PersonInfoDTO {

    private String usrid; //用户id
    private String phoenum; //用户手机号
    private String devicetoken; //用户设备号
    private int pushswitch; //是否推送 0不  1推
    private String recdphoe; //邀请码 推荐人手机号
    private String getdate; //首次领证日期

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getPhoenum() {
        return phoenum;
    }

    public void setPhoenum(String phoenum) {
        this.phoenum = phoenum;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getRecdphoe() {
        return recdphoe;
    }

    public void setRecdphoe(String recdphoe) {
        this.recdphoe = recdphoe;
    }

    public int getPushswitch() {
        return pushswitch;
    }

    public void setPushswitch(int pushswitch) {
        this.pushswitch = pushswitch;
    }

    public String getGetdate() {
        return getdate;
    }

    public void setGetdate(String getdate) {
        this.getdate = getdate;
    }
}
