package com.tzly.ctcyh.cargo.bean.response;

import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class BidOilData {

    private String img;
    private List<BidOilBean> goods;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<BidOilBean> getGoods() {
        return goods;
    }

    public void setGoods(List<BidOilBean> goods) {
        this.goods = goods;
    }
}
