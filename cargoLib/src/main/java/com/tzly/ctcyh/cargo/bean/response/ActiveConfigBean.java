package com.tzly.ctcyh.cargo.bean.response;

import java.util.List;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class ActiveConfigBean {

    /**
     * couponInfo : [{"couponName":"超市优惠娟娟","couponBusiness":"加油充值","couponType":"3","couponLimit":"0","couponId":"8","couponValue":"3"}]
     * extra : [{"couponId":8,"url":"www.www.com"}]
     * channel : 1
     * startTime : 2017-12-05 13:41:49
     * id : 1
     * endTime : 2018-12-24 13:41:53
     * configType : 1
     */

    private String extra;
    private String channel;
    private String startTime;
    private String id;
    private String endTime;
    private String configType;
    private List<CouponInfoBean> couponInfo;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public List<CouponInfoBean> getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(List<CouponInfoBean> couponInfo) {
        this.couponInfo = couponInfo;
    }

    public static class CouponInfoBean {
        /**
         * couponName : 超市优惠娟娟
         * couponBusiness : 加油充值
         * couponType : 3
         * couponLimit : 0
         * couponId : 8
         * couponValue : 3
         */

        private String couponName;
        private String couponBusiness;
        private String couponType;
        private String couponLimit;
        private String couponId;
        private String couponValue;

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponBusiness() {
            return couponBusiness;
        }

        public void setCouponBusiness(String couponBusiness) {
            this.couponBusiness = couponBusiness;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getCouponLimit() {
            return couponLimit;
        }

        public void setCouponLimit(String couponLimit) {
            this.couponLimit = couponLimit;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(String couponValue) {
            this.couponValue = couponValue;
        }
    }
}
