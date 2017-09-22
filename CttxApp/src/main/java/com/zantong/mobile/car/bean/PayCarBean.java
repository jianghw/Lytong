package com.zantong.mobile.car.bean;

import java.util.List;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayCarBean {

    private List<PayCar> UserCarsInfo;

    public void setUserCarsInfo(List<PayCar> userCarsInfo) {
        UserCarsInfo = userCarsInfo;
    }

    public List<PayCar> getUserCarsInfo() {
        return UserCarsInfo;
    }
}
