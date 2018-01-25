package com.tzly.ctcyh.pay.bean.response;

import java.util.List;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public class PayTypeBean {

    /**
     * payTypes : [{"id":1,"typeName":"畅通卡","effective":true},{"id":2,"typeName":"其他卡","effective":true},{"id":3,"typeName":"支付宝","effective":true}]
     * business : 9
     * price : 0
     * goodsId : 30
     * name : 上门换电瓶
     * description :
     */

    /**
     * id	int	支付方式id
     * typeName	String	支付方式
     * effective	boolean	true为可用，false为不可用
     * business	int	优惠券业务：1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练 6 年检，7 保养，8 海外驾驶培训，9 换电瓶，10 一元购, 11 电影券
     * price	float	价格
     * goodsId	int	商品id
     * name	string	商品名
     * description	string	商品描述
     */

    private int business;
    private float price;
    private int goodsId;
    private String name;
    private String description;
    private int couponUserId;

    private String addressDetail;
    private String phone;
    private String sheng;
    private String shi;
    private String xian;

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public int getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(int couponUserId) {
        this.couponUserId = couponUserId;
    }

    private List<PayTypesBean> payTypes;

    public int getBusiness() { return business;}

    public void setBusiness(int business) { this.business = business;}

    public float getPrice() { return price;}

    public void setPrice(float price) { this.price = price;}

    public int getGoodsId() { return goodsId;}

    public void setGoodsId(int goodsId) { this.goodsId = goodsId;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description;}

    public List<PayTypesBean> getPayTypes() { return payTypes;}

    public void setPayTypes(List<PayTypesBean> payTypes) { this.payTypes = payTypes;}

}
