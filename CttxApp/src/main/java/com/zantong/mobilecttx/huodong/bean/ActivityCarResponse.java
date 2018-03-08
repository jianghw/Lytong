package com.zantong.mobilecttx.huodong.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * Created by zhoujie on 2017/2/17.
 */

public class ActivityCarResponse extends BaseResponse {
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