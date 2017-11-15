package com.zantong.mobilecttx.eventbus;

import com.tzly.ctcyh.router.bean.response.SubjectGoodsBean;

public class SubjectOrderEvent {

    private final String mOrderId;
    private final SubjectGoodsBean mGoodsBean;
    private final String mEditName;
    private final String mPhone;
    private final String mPrice;

    public SubjectOrderEvent(String orderId, SubjectGoodsBean goodsBean,
                             String editName, String editPhone, String priceValue) {
        mOrderId = orderId;
        mGoodsBean = goodsBean;
        mEditName = editName;
        mPhone = editPhone;
        mPrice = priceValue;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public SubjectGoodsBean getGoodsBean() {
        return mGoodsBean;
    }

    public String getEditName() {
        return mEditName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getPrice() {
        return mPrice;
    }
}
