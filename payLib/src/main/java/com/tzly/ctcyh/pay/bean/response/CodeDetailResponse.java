package com.tzly.ctcyh.pay.bean.response;

import com.tzly.ctcyh.pay.bean.BaseResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CodeDetailResponse extends BaseResponse {

    private CodeDetailBean data;

    public void setData(CodeDetailBean data) {
        this.data = data;
    }

    public CodeDetailBean getData() {
        return data;
    }
}
