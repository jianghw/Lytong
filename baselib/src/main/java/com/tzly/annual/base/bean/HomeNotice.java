package com.tzly.annual.base.bean;

import java.io.Serializable;

public class HomeNotice implements Serializable {
    private String id;            //通知ID
    private int type;    //通知类型  1.年检  2.驾照换证  3.油价通知  4.违章详情
    private String desc;    //通知内容
    private String date; //日期
    private boolean isNewMeg;  //是否是一个新的消息

    public HomeNotice() {

    }

    public HomeNotice(String id, int type, String desc) {
        this.id = id;
        this.type = type;
        this.desc = desc;
    }

    public HomeNotice(String id, int type, String desc, boolean isNewMeg) {
        this(id, type, desc);
        this.isNewMeg = isNewMeg;
    }

    public boolean isNewMeg() {
        return isNewMeg;
    }

    public void setNewMeg(boolean newMeg) {
        isNewMeg = newMeg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
