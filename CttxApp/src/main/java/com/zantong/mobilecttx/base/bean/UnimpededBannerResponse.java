package com.zantong.mobilecttx.base.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerBean;

import java.util.List;

/**
 * 首页返回实体
 */
public class UnimpededBannerResponse extends BaseResponse {
    private List<UnimpededBannerBean> data;

    public List<UnimpededBannerBean> getData() {
        return data;
    }

    public void setData(List<UnimpededBannerBean> data) {
        this.data = data;
    }
}
