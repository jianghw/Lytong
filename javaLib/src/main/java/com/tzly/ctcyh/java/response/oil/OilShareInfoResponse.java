package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 油卡 分享
 */

public class OilShareInfoResponse extends BaseResponse {


    /**
     * responseCode : 2000
     * data : {"coupons":1,"accepters":1}
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
         * coupons : 1
         * accepters : 1
         */

        private int coupons;
        private int accepters;

        public int getCoupons() {
            return coupons;
        }

        public void setCoupons(int coupons) {
            this.coupons = coupons;
        }

        public int getAccepters() {
            return accepters;
        }

        public void setAccepters(int accepters) {
            this.accepters = accepters;
        }
    }
}
