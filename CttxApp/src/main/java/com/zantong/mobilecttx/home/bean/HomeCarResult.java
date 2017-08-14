package com.zantong.mobilecttx.home.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;

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
