package com.zantong.mobile.home.bean;

import com.tzly.annual.base.bean.BaseResult;
import com.zantong.mobile.user.bean.UserCarInfoBean;

import java.util.List;

/**
 * Created by zhoujie on 2016/9/19.
 * 首页返回实体
 */
public class HomeCarResult extends BaseResult {
    private List<UserCarInfoBean> data;

    public List<UserCarInfoBean> getData() {
        return data;
    }

    public void setData(List<UserCarInfoBean> data) {
        this.data = data;
    }
}
