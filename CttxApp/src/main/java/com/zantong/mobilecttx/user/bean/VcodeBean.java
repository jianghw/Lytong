package com.zantong.mobilecttx.user.bean;

import cn.qqtheme.framework.bean.BankResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class VcodeBean extends BankResponse {

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
