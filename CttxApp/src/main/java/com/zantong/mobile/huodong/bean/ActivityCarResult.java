package com.zantong.mobile.huodong.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhoujie on 2017/2/17.
 */

public class ActivityCarResult extends BaseResult {
    private ActivityCar data;

    public ActivityCar getData() {
        return data;
    }

    public void setData(ActivityCar data) {
        this.data = data;
    }

    public class ActivityCar{
        private String plateNo;

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }
    }
}
