package com.tzly.ctcyh.cargo.bean.request;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class RefuelOilDTO {

    /**
     * methodType : rechargeOrder
     * oilCardNum : 4234324234234234
     * rechargeMoney : 500.00
     * goodsId : 7
     * userId : F7HOzMGquY8K38CewfURIjS9N73bw1u9WR7hz/NNuz61csdspwDDOuhSnHdx0EfYVNRl0dqrEt4ThJdBGxgXGgNX91w/4NErVah3JGfzRhOpoDqtoYY4UQ3NXh8wmfO2a7OvwrgWQwZkBP9RccDNXXh93bObAYOqiEtZJppSg1M=
     */

    private String methodType;
    private String oilCardNum;
    private String rechargeMoney;
    private String goodsId;
    private String userId;

    public String getMethodType() { return methodType;}

    public void setMethodType(String methodType) { this.methodType = methodType;}

    public String getOilCardNum() { return oilCardNum;}

    public void setOilCardNum(String oilCardNum) { this.oilCardNum = oilCardNum;}

    public String getRechargeMoney() { return rechargeMoney;}

    public void setRechargeMoney(String rechargeMoney) { this.rechargeMoney = rechargeMoney;}

    public String getGoodsId() { return goodsId;}

    public void setGoodsId(String goodsId) { this.goodsId = goodsId;}

    public String getUserId() { return userId;}

    public void setUserId(String userId) { this.userId = userId;}
}
