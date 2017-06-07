package com.zantong.mobilecttx.chongzhi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 订单列表实体
 *
 * @author Sandy
 *         create at 16/6/12 上午11:40
 */
public class OilPriceItem implements Serializable {

    private static final long serialVersionUID = -7620435178023928252L;

    private String amount;
    private String discount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
