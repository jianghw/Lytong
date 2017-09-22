package com.zantong.mobile.common.bean;

import java.io.Serializable;

/**
 * Created by zhoujie on 2017/1/3.
 */

public class CommonTwoLevelMenuBean implements Serializable{
    private int id;
    private int imgId;
    private String context;

    public CommonTwoLevelMenuBean() {
    }

    public CommonTwoLevelMenuBean(int id, String context) {
        this.id = id;
        this.context = context;
    }
    public CommonTwoLevelMenuBean(int id, String context, int imgId){
        this.id = id;
        this.context = context;
        this.imgId = imgId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
