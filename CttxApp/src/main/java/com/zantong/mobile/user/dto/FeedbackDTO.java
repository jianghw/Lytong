package com.zantong.mobile.user.dto;

/**
 * 意见反馈实体封装
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class FeedbackDTO {

    private String usrid; //用户ID
    private String reqcontent; //输入内容

    public String getReqcontent() {
        return reqcontent;
    }

    public void setReqcontent(String reqcontent) {
        this.reqcontent = reqcontent;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }
}
