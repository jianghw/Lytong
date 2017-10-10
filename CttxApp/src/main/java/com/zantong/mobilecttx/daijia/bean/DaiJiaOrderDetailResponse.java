package com.zantong.mobilecttx.daijia.bean;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * Created by zhoujie on 2017/2/21.
 */

public class DaiJiaOrderDetailResponse extends BaseResponse {

    private DaiJiaOrderDetailBean data;

    public void setData(DaiJiaOrderDetailBean data) {
        this.data = data;
    }

    public DaiJiaOrderDetailBean getData() {
        return data;
    }

    public class DaiJiaOrderDetailBean {
        /**
         * 订单id
         */
        String orderId;
        /**
         * 订单状态[派单中0|司机在途1|司机等待2|代驾中3|已完成4|已取消5|已预约6]
         */
        String orderStatus;
        /**
         * 代驾客户
         */
        String name;
        /**
         * 代驾客户手机
         */
        String mobile;
        /**
         * 出发地址
         */
        String address;
        /**
         * 出发地址经度(百度坐标)
         */
        double addressLng;
        /**
         * 出发地址纬度(百度坐标)
         */
        double addressLat;
        /**
         * 订单创建时间，格式为2013-11-28 12:00:00
         */
        String createTime;
        /**
         * 订单取消时间，格式为2013-11-28 12:00:00
         */
        String cancelTime;
        /**
         * 要求到达时间，格式：20:30
         */
        String requestTime;
        /**
         * 系统自动派单时间，格式为2013-11-28 12:00:00
         */
        String assignTime;
        /**
         * 司机接单时间，格式为2013-11-28 12:00:00
         */
        String acceptTime;
        /**
         * 司机到达时间，格式为2013-11-28 12:00:00
         */
        String arriveTime;
        /**
         * 代驾开始时间，格式为2013-11-28 12:00:00
         */
        String beginTime;
        /**
         * 代驾结束时间，格式为2013-11-28 12:00:00
         */
        String endTime;
        /**
         * 行驶距离，公里
         */
        String distance;
        /**
         * 等候时间，分钟
         */
        String waitTime;
        /**
         * 订单金额，元
         */
        String amount;
        /**
         * 实际抵扣金额，元。如果创建订单时指定的是免除公里数，则会换算为实际的优惠金额
         */
        String amountCoupon;
        /**
         * 备注信息
         */
        String comment;
        /**
         * 司机工号
         */
        String driverNo;
        /**
         * 司机手机号码
         */
        String driverMobile;
        /**
         * 如果司机在途，则计算路程，米
         */
        String driverDistance;
        /**
         * 如果司机在途，则计算剩余到达时间，秒
         */
        String remainingTime;
        /**
         * 出发地址经度(百度坐标)
         */
        String driverLng;
        /**
         * 出发地址纬度(百度坐标)
         */
        String driverLat;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getAddressLng() {
            return addressLng;
        }

        public void setAddressLng(double addressLng) {
            this.addressLng = addressLng;
        }

        public double getAddressLat() {
            return addressLat;
        }

        public void setAddressLat(double addressLat) {
            this.addressLat = addressLat;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(String requestTime) {
            this.requestTime = requestTime;
        }

        public String getAssignTime() {
            return assignTime;
        }

        public void setAssignTime(String assignTime) {
            this.assignTime = assignTime;
        }

        public String getAcceptTime() {
            return acceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            this.acceptTime = acceptTime;
        }

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getWaitTime() {
            return waitTime;
        }

        public void setWaitTime(String waitTime) {
            this.waitTime = waitTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmountCoupon() {
            return amountCoupon;
        }

        public void setAmountCoupon(String amountCoupon) {
            this.amountCoupon = amountCoupon;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDriverNo() {
            return driverNo;
        }

        public void setDriverNo(String driverNo) {
            this.driverNo = driverNo;
        }

        public String getDriverMobile() {
            return driverMobile;
        }

        public void setDriverMobile(String driverMobile) {
            this.driverMobile = driverMobile;
        }

        public String getDriverDistance() {
            return driverDistance;
        }

        public void setDriverDistance(String driverDistance) {
            this.driverDistance = driverDistance;
        }

        public String getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(String remainingTime) {
            this.remainingTime = remainingTime;
        }

        public String getDriverLng() {
            return driverLng;
        }

        public void setDriverLng(String driverLng) {
            this.driverLng = driverLng;
        }

        public String getDriverLat() {
            return driverLat;
        }

        public void setDriverLat(String driverLat) {
            this.driverLat = driverLat;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatus() {
            return orderStatus;
        }
    }
}
