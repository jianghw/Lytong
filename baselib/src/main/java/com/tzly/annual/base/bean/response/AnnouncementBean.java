package com.tzly.annual.base.bean.response;

/**
 * Created by jianghw on 18-1-31.
 */

public class AnnouncementBean {


    /**
     * id : 3
     * content : 12arr
     * state : 1
     */

    private int id;
    private String content;
    private int state;

    public AnnouncementBean(int id, String content, int state) {
        this.id = id;
        this.content = content;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
