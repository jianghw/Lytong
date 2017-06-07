package com.zantong.mobilecttx.user.bean;

import java.util.List;

/**
 * 红包实体
 * @author Sandy
 * create at 16/12/27 下午1:50
 */
public class BonusBean {

    private String id;
    private String sharecount;    //累计分享次数
    private String successcount; //累计成功次数
    private String ranking;   //排行
    private String userphone; //用户手机

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSharecount() {
        return sharecount;
    }

    public void setSharecount(String sharecount) {
        this.sharecount = sharecount;
    }

    public String getSuccesscount() {
        return successcount;
    }

    public void setSuccesscount(String successcount) {
        this.successcount = successcount;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }
}
