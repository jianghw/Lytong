package com.zantong.mobilecttx.alicloudpush;

import java.io.Serializable;

/**
 * Created by zhoujie on 2016/11/21.
 */

public class PushBean implements Serializable {
    private String id;
    private String type;
    private String content;
    private String title;
    private String date;
    private boolean isNewMeg;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
