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

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
