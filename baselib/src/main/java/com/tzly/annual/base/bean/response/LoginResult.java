package com.tzly.annual.base.bean.response;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by jianghw on 18-2-7.
 */

public class LoginResult extends BaseResult {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
