package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

/**
 * 红包实体
 * @author Sandy
 * create at 16/12/27 下午1:51
 */
public class BonusResult extends BaseResult {

    private BonusBean data;//我的车辆

    public void setData(BonusBean data) {
        this.data = data;
    }

    public BonusBean getData() {
        return data;
    }
}
