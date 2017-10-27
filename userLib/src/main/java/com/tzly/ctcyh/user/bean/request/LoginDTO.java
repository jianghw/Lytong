package com.tzly.ctcyh.user.bean.request;

/**
 * 登录请求bean
 */
public class LoginDTO {
    /**
     * token	是	string	推送的token 加密
     * pushmode	是	string	推送方式
     * pushswitch	是	string	推送开关
     * phoenum	是	string	手机号
     * pswd	是	string	密码
     * devicetoken	是	string	设备标识号
     */

    private String token;
    private String pushmode;
    private String pushswitch;
    private String phoenum;
    private String pswd;
    private String devicetoken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushmode() {
        return pushmode;
    }

    public void setPushmode(String pushmode) {
        this.pushmode = pushmode;
    }

    public String getPushswitch() {
        return pushswitch;
    }

    public void setPushswitch(String pushswitch) {
        this.pushswitch = pushswitch;
    }

    public String getPhoenum() {
        return phoenum;
    }

    public void setPhoenum(String phoenum) {
        this.phoenum = phoenum;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }
}
