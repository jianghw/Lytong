package com.zantong.mobile.user.bean;

import com.tzly.annual.base.bean.Result;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class VcodeBean extends Result {

    private String onlyflag;
    private String rgstste;//0 未注册  1已注册

    public String getOnlyflag() {
        return onlyflag;
    }

    public void setOnlyflag(String onlyflag) {
        this.onlyflag = onlyflag;
    }

    public String getRgstste() {
        return rgstste;
    }

    public void setRgstste(String rgstste) {
        this.rgstste = rgstste;
    }
}
