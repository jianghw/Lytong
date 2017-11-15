package com.zantong.mobilecttx.daijia.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

import java.util.List;

/**
 * Created by zhoujie on 2017/2/21.
 */

public class DaiJiaOrderListResponse extends BaseResponse {

    private List<DaiJiaOrderListBean> data;

    public void setData(List<DaiJiaOrderListBean> data) {
        this.data = data;
    }

    public List<DaiJiaOrderListBean> getData() {
        return data;
    }

}
