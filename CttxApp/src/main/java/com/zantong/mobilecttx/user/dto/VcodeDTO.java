package com.zantong.mobilecttx.user.dto;

/**
 * 验证码实体封装
 * @author Sandy
 * create at 16/6/21 下午5:17
 */
public class VcodeDTO {

    private String phoenum; //手机号
    private String smsscene; //短信场景  001 注册 / 002更换手机
    private String captcha;//验证码
    private String onlyflag;//唯一标识

    public String getPhoenum() {
        return phoenum;
    }

    public void setPhoenum(String phoenum) {
        this.phoenum = phoenum;
    }

    public String getSmsscene() {
        return smsscene;
    }

    public void setSmsscene(String smsscene) {
        this.smsscene = smsscene;
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
}
