package com.zantong.mobilecttx.weizhang.bean;

import com.tzly.annual.base.bean.BaseResult;

import java.util.ArrayList;

/**
 * Created by zhengyingbing on 17/5/5.
 */

public class ViolationItemBean extends BaseResult {
    private ArrayList<ViolationItemInfo> data;

    public void setData(ArrayList<ViolationItemInfo> data) {
        this.data = data;
    }

    public ArrayList<ViolationItemInfo> getData() {
        return data;
    }
}
