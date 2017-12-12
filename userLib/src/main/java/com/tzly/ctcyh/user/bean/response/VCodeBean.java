package com.tzly.ctcyh.user.bean.response;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class VCodeBean {
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
