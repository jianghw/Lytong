package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 油卡 分享
 */

public class OilAccepterInfoResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : [{"stage":"1","coupon":[{"couponType":2,"counts":1,"start":1493136000000,"end":1541865600000,"id":957,"couponId":1,"title":"加油50元优惠券","businessType":1,"couponValue":75}],"phone":"13111111111"},{"stage":"0","coupon":[],"phone":"13111111111"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stage : 1
         * coupon : [{"couponType":2,"counts":1,"start":1493136000000,"end":1541865600000,"id":957,"couponId":1,"title":"加油50元优惠券","businessType":1,"couponValue":75}]
         * phone : 13111111111
         */

        private String stage;
        private String phone;
        private List<CouponBean> coupon;

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public static class CouponBean {
            /**
             * couponType : 2
             * counts : 1
             * start : 1493136000000
             * end : 1541865600000
             * id : 957
             * couponId : 1
             * title : 加油50元优惠券
             * businessType : 1
             * couponValue : 75
             */

            private int couponType;
            private int counts;
            private long start;
            private long end;
            private int id;
            private int couponId;
            private String title;
            private int businessType;
            private int couponValue;

            public int getCouponType() {
                return couponType;
            }

            public void setCouponType(int couponType) {
                this.couponType = couponType;
            }

            public int getCounts() {
                return counts;
            }

            public void setCounts(int counts) {
                this.counts = counts;
            }

            public long getStart() {
                return start;
            }

            public void setStart(long start) {
                this.start = start;
            }

            public long getEnd() {
                return end;
            }

            public void setEnd(long end) {
                this.end = end;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCouponId() {
                return couponId;
            }

            public void setCouponId(int couponId) {
                this.couponId = couponId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }

            public int getCouponValue() {
                return couponValue;
            }

            public void setCouponValue(int couponValue) {
                this.couponValue = couponValue;
            }
        }
    }
}
