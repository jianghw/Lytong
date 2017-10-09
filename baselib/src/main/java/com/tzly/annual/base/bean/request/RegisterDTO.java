package com.tzly.annual.base.bean.request;

/**
 * Created by zhoujie on 2016/12/8.
 */

public class RegisterDTO {
    private String phoenum;//手机号
    private String pswd;//密码
    private String usrid;//新增时为空
    private String nickname;//昵称
    private String token;
    private String pushswitch;
    private String pushmode;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }
}
