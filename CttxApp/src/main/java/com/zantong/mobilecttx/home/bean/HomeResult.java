package com.zantong.mobilecttx.home.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * Created by zhoujie on 2016/9/19.
 * 首页返回实体
 */
public class HomeResult extends BaseResult {
    private HomeBean data;

    public HomeBean getData() {
        return data;
    }

    public void setData(HomeBean data) {
        this.data = data;
    }
}
