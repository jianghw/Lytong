package com.tzly.ctcyh.java.response.module;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 资讯导航
 */

public class NewsFlagResponse extends BaseResponse {


    /**
     * responseCode : 2000
     * data : false
     */

    private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
