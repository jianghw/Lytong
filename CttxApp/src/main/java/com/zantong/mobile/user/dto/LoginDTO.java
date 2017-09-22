package com.zantong.mobile.user.dto;

/**
 * 登录实体封装
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class LoginDTO {

    private String phoenum; //手机号
    private String captcha;//验证码
    private String onlyflag;//唯一标示
    private String devicetoken;//设备标识号
    private String pushswitch;//推送开关
    private String pushmode;//推送方式
    private String chkflg;//验证标志

    public String getPhoenum() {
        return phoenum;
    }

    public void setPhoenum(String phoenum) {
        this.phoenum = phoenum;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getOnlyflag() {
        return onlyflag;
    }

    public void setOnlyflag(String onlyflag) {
        this.onlyflag = onlyflag;
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

    public String getChkflg() {
        return chkflg;
    }

    public void setChkflg(String chkflg) {
        this.chkflg = chkflg;
    }
}
