package com.zantong.mobilecttx.car.bean;

import cn.qqtheme.framework.bean.BaseResponse;

import java.util.List;

/**
 * Created by zhoujie on 2016/12/9.
 */

public class VehicleLicenseResponse extends BaseResponse {
    public List<VehicleLicenseBean> data;

    public List<VehicleLicenseBean>  getData() {
        return data;
    }

    public void setData(List<VehicleLicenseBean>  data) {
        this.data = data;
    }

}