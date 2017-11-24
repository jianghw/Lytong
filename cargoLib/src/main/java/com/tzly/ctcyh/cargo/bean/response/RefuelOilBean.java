package com.tzly.ctcyh.cargo.bean.response;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class RefuelOilBean {

    /**
     * goodsId : 1
     * price : 100
     * oilType : 1
     * name : 中石化100元加油充值卡
     * type : 1
     */

    private int goodsId;
    private float price;
    private int oilType;
    private String name;
    private int type;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getGoodsId() { return goodsId;}

    public void setGoodsId(int goodsId) { this.goodsId = goodsId;}

    public float getPrice() { return price;}

    public void setPrice(float price) { this.price = price;}

    public int getOilType() { return oilType;}

    public void setOilType(int oilType) { this.oilType = oilType;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public int getType() { return type;}

    public void setType(int type) { this.type = type;}
}
