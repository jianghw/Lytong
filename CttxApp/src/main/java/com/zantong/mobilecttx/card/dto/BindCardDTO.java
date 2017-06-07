package com.zantong.mobilecttx.card.dto;

/**
 * Created by zhengyingbing on 16/10/17.
 * 绑定畅通卡请求实体
 */

public class BindCardDTO {
    private String usrid;
    private String ctftp;
    private String ctfnum;
    private String filenum;
    private String relatedphone;
    private String getdate;

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getCtftp() {
        return ctftp;
    }

    public void setCtftp(String ctftp) {
        this.ctftp = ctftp;
    }

    public String getCtfnum() {
        return ctfnum;
    }

    public void setCtfnum(String ctfnum) {
        this.ctfnum = ctfnum;
    }

    public String getFilenum() {
        return filenum;
    }

    public void setFilenum(String filenum) {
        this.filenum = filenum;
    }

    public String getRelatedphone() {
        return relatedphone;
    }

    public void setRelatedphone(String relatedphone) {
        this.relatedphone = relatedphone;
    }

    public String getGetdate() {
        return getdate;
    }

    public void setGetdate(String getdate) {
        this.getdate = getdate;
    }
}
