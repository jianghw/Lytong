package com.tzly.ctcyh.java.response;

/**
 * 银行接口响应 base
 */
public class BankResponse {
    private APPHEADBean APP_HEAD;
    private SYSHEADBean SYS_HEAD;

    public APPHEADBean getAPP_HEAD() {
        return APP_HEAD;
    }

    public void setAPP_HEAD(APPHEADBean APP_HEAD) {
        this.APP_HEAD = APP_HEAD;
    }

    public SYSHEADBean getSYS_HEAD() {
        return SYS_HEAD;
    }

    public void setSYS_HEAD(SYSHEADBean SYS_HEAD) {
        this.SYS_HEAD = SYS_HEAD;
    }
}
