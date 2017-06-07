package com.zantong.mobilecttx.user.dto;

/**
 * 注册实体封装
 * @author Sandy
 * create at 16/6/21 下午5:17
 */
public class RegisterDTO {

    private String phoenum; //手机号
    private String pswd; //手机号
    private String devicetoken;//设备标识号
    private String pushswitch;//推送开关
    private String pushmode;//推送方式
    private String token; //阿里云deviceId

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

    public String getPushswitch() {
        return pushswitch;
    }

    public void setPushswitch(String pushswitch) {
        this.pushswitch = pushswitch;
    }

    public String getPushmode() {
        return pushmode;
    }

    public void setPushmode(String pushmode) {
        this.pushmode = pushmode;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
