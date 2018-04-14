package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 油卡
 */

public class OilEnterResponse extends BaseResponse {


    /**
     * responseCode : 2000
     * data : {"count":44}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * count : 44
         */

        private int count1;
        private int count2;

        public int getCount1() {
            return count1;
        }

        public int getCount2() {
            return count2;
        }
    }
}
