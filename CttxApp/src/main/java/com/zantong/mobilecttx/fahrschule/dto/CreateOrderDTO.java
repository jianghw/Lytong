package com.zantong.mobilecttx.fahrschule.dto;

/**
 * Created by jianghw on 2017/7/6.
 * Description: 请求bean
 * Update by:
 * Update day:
 */

public class CreateOrderDTO {
    /**
     * <p>
     * type	是	string	商品类型	1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练
     * userNum	是	string	用户id	1，2，3，4，5
     * goodsId	是	string	商品id	1，2，3，4，5
     * price	是	string	价格	1，2，3，4，5
     * remark	否	string	补充说明	1，2，3，4，5
     * oilCardNum	否	string	油卡号	1
     * payType	是	string	付款方式0畅通卡，1其他卡	1
     * couponId	否	string	优惠券id	1
     * userName	是	string	用户名	3,5
     * phone	是	string	手机号	3,5
     * idCard	是	string	身份证	3
     * serviceArea	是	string	服务地区	5
     * serviceAddress	是	string	服务地址	5
     * goodsId	是	string	汽车类型(商品id)	5
     * speedType	是	string	变速箱类型	5
     * startTime	是	string	服务开始时间	5
     * endTime	是	string	服务结束时间	5
     * driveNum	是	string	驾驶证号	5
     */

    private String type;
    private String userNum;
    private String goodsId;
    private String price;
    private String remark;
    private String oilCardNum;
    private String payType;
    private String couponId;
    private String userName;
    private String phone;
    private String idCard;

    private String serviceArea;
    private String serviceAddress;
    private String speedType;
    private String startTime;
    private String endTime;
    private String driveNum;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getOilCardNum() {
        return oilCardNum;
    }

    public void setOilCardNum(String oilCardNum) {
        this.oilCardNum = oilCardNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
