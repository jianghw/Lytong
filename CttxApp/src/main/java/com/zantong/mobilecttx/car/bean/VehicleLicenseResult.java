package com.zantong.mobilecttx.car.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

import java.util.List;

/**
 * Created by zhoujie on 2016/12/9.
 */

public class VehicleLicenseResult extends BaseResult {
    public List<VehicleLicenseBean> data;

    public List<VehicleLicenseBean>  getData() {
        return data;
    }

    public void setData(List<VehicleLicenseBean>  data) {
        this.data = data;
    }

}
