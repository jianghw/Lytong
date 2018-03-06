package com.tzly.ctcyh.java.response.coupon;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 优惠券
 */

public class CouponInfoResponse extends BaseResponse {


    /**
     * responseCode : 2000
     * data : {"coupon":{"id":1,"couponName":"加油50元优惠券","couponContent":"加油","couponImage":"烦烦烦","couponUse":"仅限:加油充值业务使用\r\n其他业务不可用zheliyouyigejiayoudeyouhuiquanhahaha","couponValidityStart":"2017-04-26","couponValidityEnd":"2018-11-11","isDeleted":1,"couponLimit":1,"couponValue":75,"couponType":2,"couponBusiness":1}}
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
         * coupon : {"id":1,"couponName":"加油50元优惠券","couponContent":"加油","couponImage":"烦烦烦","couponUse":"仅限:加油充值业务使用\r\n其他业务不可用zheliyouyigejiayoudeyouhuiquanhahaha","couponValidityStart":"2017-04-26","couponValidityEnd":"2018-11-11","isDeleted":1,"couponLimit":1,"couponValue":75,"couponType":2,"couponBusiness":1}
         */

        private CouponBean coupon;

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public static class CouponBean {
            /**
             * id : 1
             * couponName : 加油50元优惠券
             * couponContent : 加油
             * couponImage : 烦烦烦
             * couponUse : 仅限:加油充值业务使用
             其他业务不可用zheliyouyigejiayoudeyouhuiquanhahaha
             * couponValidityStart : 2017-04-26
             * couponValidityEnd : 2018-11-11
             * isDeleted : 1
             * couponLimit : 1
             * couponValue : 75
             * couponType : 2
             * couponBusiness : 1
             */

            private int id;
            private String couponName;
            private String couponContent;
            private String couponImage;
            private String couponUse;
            private String couponValidityStart;
            private String couponValidityEnd;
            private int isDeleted;
            private int couponLimit;
            private int couponValue;
            private int couponType;
            private int couponBusiness;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public String getCouponContent() {
                return couponContent;
            }

            public void setCouponContent(String couponContent) {
                this.couponContent = couponContent;
            }

            public String getCouponImage() {
                return couponImage;
            }

            public void setCouponImage(String couponImage) {
                this.couponImage = couponImage;
            }

            public String getCouponUse() {
                return couponUse;
            }

            public void setCouponUse(String couponUse) {
                this.couponUse = couponUse;
            }

            public String getCouponValidityStart() {
                return couponValidityStart;
            }

            public void setCouponValidityStart(String couponValidityStart) {
                this.couponValidityStart = couponValidityStart;
            }

            public String getCouponValidityEnd() {
                return couponValidityEnd;
            }

            public void setCouponValidityEnd(String couponValidityEnd) {
                this.couponValidityEnd = couponValidityEnd;
            }

            public int getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(int isDeleted) {
                this.isDeleted = isDeleted;
            }

            public int getCouponLimit() {
                return couponLimit;
            }

            public void setCouponLimit(int couponLimit) {
                this.couponLimit = couponLimit;
            }

            public int getCouponValue() {
                return couponValue;
            }

            public void setCouponValue(int couponValue) {
                this.couponValue = couponValue;
            }

            public int getCouponType() {
                return couponType;
            }

            public void setCouponType(int couponType) {
                this.couponType = couponType;
            }

            public int getCouponBusiness() {
                return couponBusiness;
            }

            public void setCouponBusiness(int couponBusiness) {
                this.couponBusiness = couponBusiness;
            }
        }
    }
}
