package com.zantong.mobilecttx.map.bean;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class WachCarPlaceDetail extends WachCarPlace{
    private String address;   //洗车地点地址
    private String telephone; //洗车地点电话;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
