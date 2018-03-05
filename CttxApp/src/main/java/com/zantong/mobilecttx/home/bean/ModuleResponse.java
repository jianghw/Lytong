package com.zantong.mobilecttx.home.bean;

import java.util.List;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 25.模块化接口
 */
public class ModuleResponse extends BaseResponse {

    private List<ModuleBean> data;

    public void setData(List<ModuleBean> data) {
        this.data = data;
    }

    public List<ModuleBean> getData() {
        return data;
    }
}
