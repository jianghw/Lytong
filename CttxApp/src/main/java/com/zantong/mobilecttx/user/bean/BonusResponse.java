package com.zantong.mobilecttx.user.bean;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * 红包实体
 * @author Sandy
 * create at 16/12/27 下午1:51
 */
public class BonusResponse extends BaseResponse {

    private BonusBean data;//我的车辆

    public void setData(BonusBean data) {
        this.data = data;
    }

    public BonusBean getData() {
        return data;
    }
}
