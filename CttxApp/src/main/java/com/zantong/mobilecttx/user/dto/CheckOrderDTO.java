package com.zantong.mobilecttx.user.dto;

/**
 * 保险签单实体
 * @author Sandy
 * create at 16/6/8 下午11:48
 */
public class CheckOrderDTO {

    private String origtranserlnum;//原来交易流水号
    private String polcyprignum;//投保单号


    public String getOrigtranserlnum() {
        return origtranserlnum;
    }

    public void setOrigtranserlnum(String origtranserlnum) {
        this.origtranserlnum = origtranserlnum;
    }

    public String getPolcyprignum() {
        return polcyprignum;
    }

    public void setPolcyprignum(String polcyprignum) {
        this.polcyprignum = polcyprignum;
    }

}
