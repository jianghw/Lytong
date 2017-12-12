package com.tzly.ctcyh.user.bean.request;

/**
 * 登录请求bean
 */
public class VCodeDTO {
    private String phoenum; //手机号
    private String smsscene; //短信场景  001 注册 / 002更换手机
    private String captcha;//验证码
    private String onlyflag;//唯一标识

    public void setPhoenum(String phoenum) {
        this.phoenum = phoenum;
    }

    public void setSmsscene(String smsscene) {
        this.smsscene = smsscene;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public void setOnlyflag(String onlyflag) {
        this.onlyflag = onlyflag;
    }
}
