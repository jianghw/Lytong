package com.tzly.ctcyh.pay.bean.response;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponDetailBean {

    /**
     * mCouponBean : {"couponId":"25","couponName":"代驾优惠券",
     * "couponContent":"1234567890","couponValidityEnd":"2017-11-30",
     * "couponStatus":"1",
     * "couponImage":"http://139.196.183.121:8011/admin/uploads/20170612/20170612173803691.png",
     * "couponCode":"EX3PVFPU9E95NG2F","couponUse":"1234567890","couponType":"1",
     * "couponValue":"0","couponLimit":"0","couponBusiness":"代驾"}
     */

    private CouponBean couponDetail;

    public CouponBean getCouponBean() { return couponDetail;}

    public void setCouponBean(CouponBean couponBean) { this.couponDetail = couponBean;}

    public static class CouponBean {
        /**
         * couponId : 25
         * couponName : 代驾优惠券
         * couponContent : 1234567890
         * couponValidityEnd : 2017-11-30
         * couponStatus : 1
         * couponImage : http://139.196.183.121:8011/admin/uploads/20170612/20170612173803691.png
         * couponCode : EX3PVFPU9E95NG2F
         * couponUse : 1234567890
         * couponType : 1
         * couponValue : 0
         * couponLimit : 0
         * couponBusiness : 代驾
         */

        private String couponId;
        private String couponName;
        private String couponContent;
        private String couponValidityEnd;
        private String couponStatus;
        private String couponImage;
        private String couponCode;
        private String couponUse;
        private String couponType;
        private String couponValue;
        private String couponLimit;
        private String couponBusiness;

        public String getCouponId() { return couponId;}

        public void setCouponId(String couponId) { this.couponId = couponId;}

        public String getCouponName() { return couponName;}

        public void setCouponName(String couponName) { this.couponName = couponName;}

        public String getCouponContent() { return couponContent;}

        public void setCouponContent(String couponContent) { this.couponContent = couponContent;}

        public String getCouponValidityEnd() { return couponValidityEnd;}

        public void setCouponValidityEnd(String couponValidityEnd) { this.couponValidityEnd = couponValidityEnd;}

        public String getCouponStatus() { return couponStatus;}

        public void setCouponStatus(String couponStatus) { this.couponStatus = couponStatus;}

        public String getCouponImage() { return couponImage;}

        public void setCouponImage(String couponImage) { this.couponImage = couponImage;}

        public String getCouponCode() { return couponCode;}

        public void setCouponCode(String couponCode) { this.couponCode = couponCode;}

        public String getCouponUse() { return couponUse;}

        public void setCouponUse(String couponUse) { this.couponUse = couponUse;}

        public String getCouponType() { return couponType;}

        public void setCouponType(String couponType) { this.couponType = couponType;}

        public String getCouponValue() { return couponValue;}

        public void setCouponValue(String couponValue) { this.couponValue = couponValue;}

        public String getCouponLimit() { return couponLimit;}

        public void setCouponLimit(String couponLimit) { this.couponLimit = couponLimit;}

        public String getCouponBusiness() { return couponBusiness;}

        public void setCouponBusiness(String couponBusiness) { this.couponBusiness = couponBusiness;}
    }
}
