package com.zantong.mobile.chongzhi.dto;

/**
 * 油卡充值实体封装
 *
 * @author Sandy
 *         create at 16/6/1 下午7:34
 */
public class RechargeDTO {

    private String methodType; //方法名（传固定值）
    private int oilType; //加油卡类型 1中石化 2中石油
    private String rechargeTemplate; //模板号
    private String prodMoney; //面额
    private String rechargeMoney; //订单金额
    private String oilCardNum; //油卡卡号
    private String discount; //折扣  9.7
    private String userId; //用户ID
    private int payType;//0畅通卡  1其它卡
    private Integer userCouponId;//用户优惠券id

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public void setOilType(int oilType) {
        this.oilType = oilType;
    }

    public int getOilType() {
        return oilType;
    }

    public String getRechargeTemplate() {
        return rechargeTemplate;
    }

    public void setRechargeTemplate(String rechargeTemplate) {
        this.rechargeTemplate = rechargeTemplate;
    }

    public String getProdMoney() {
        return prodMoney;
    }

    public void setProdMoney(String prodMoney) {
        this.prodMoney = prodMoney;
    }

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getOilCardNum() {
        return oilCardNum;
    }

    public void setOilCardNum(String oilCardNum) {
        this.oilCardNum = oilCardNum;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
