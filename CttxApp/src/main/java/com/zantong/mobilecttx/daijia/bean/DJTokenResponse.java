package com.zantong.mobilecttx.daijia.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * Created by zhoujie on 2017/2/21.
 */

public class DJTokenResponse extends BaseResponse {

    private DJToken data;

    public DJToken getData() {
        return data;
    }

    public void setData(DJToken data) {
        this.data = data;
    }

    public class DJToken{
//        "nearByNum":"4","expectTime":"30","shortestDistance":"1084.88"
        private String nearByNum;
        private String shortestDistance;
        private String expectTime;

        public String getNearByNum() {
            return nearByNum;
        }

        public void setNearByNum(String nearByNum) {
            this.nearByNum = nearByNum;
        }

        public String getShortestDistance() {
            return shortestDistance;
        }

        public void setShortestDistance(String shortestDistance) {
            this.shortestDistance = shortestDistance;
        }

        public String getExpectTime() {
            return expectTime;
        }

        public void setExpectTime(String expectTime) {
            this.expectTime = expectTime;
        }
    }
}
