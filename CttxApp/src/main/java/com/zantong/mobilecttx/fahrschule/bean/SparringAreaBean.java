package com.zantong.mobilecttx.fahrschule.bean;

/**
 * Created by jianghw on 2017/8/2.
 * Description:
 * Update by:
 * Update day:
 */

public class SparringAreaBean {

    /**
     * code : 310101
     * parentCode : 310100
     * type : 3
     * name : 黄                浦
     * fullName : 黄浦区
     */

    private int code;
    private int parentCode;
    private int type;
    private String name;
    private String fullName;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
