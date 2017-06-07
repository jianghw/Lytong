package com.zantong.mobilecttx.weizhang.dto;

/**
 * 缴费记录实体封装
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class PayHistoryDTO {

    String filenum;//档案号
    String strtdt;//开始日期
    String enddt;//结束日期

    public String getFilenum() {
        return filenum;
    }

    public void setFilenum(String filenum) {
        this.filenum = filenum;
    }

    public String getStrtdt() {
        return strtdt;
    }

    public void setStrtdt(String strtdt) {
        this.strtdt = strtdt;
    }

    public String getEnddt() {
        return enddt;
    }

    public void setEnddt(String enddt) {
        this.enddt = enddt;
    }
}
