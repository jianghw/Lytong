package com.zantong.mobilecttx.user.bean;

import java.util.List;

/**
 * 我的车辆返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class UserCarsBean {

    private List<UserCarInfoBean> UserCarsInfo;//我的车辆

    public List<UserCarInfoBean> getUserCarsInfo() {
        return UserCarsInfo;
    }

    public void setUserCarsInfo(List<UserCarInfoBean> userCarsInfo) {
        UserCarsInfo = userCarsInfo;
    }
}
