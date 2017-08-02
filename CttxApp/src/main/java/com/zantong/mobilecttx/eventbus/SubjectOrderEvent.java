package com.zantong.mobilecttx.eventbus;

import com.zantong.mobilecttx.fahrschule.bean.SubjectGoodsBean;

public class SubjectOrderEvent {

    private final String mOrderId;
    private final SubjectGoodsBean mGoodsBean;
    private final String mEditName;
    private final String mPhone;

    public SubjectOrderEvent(String orderId, SubjectGoodsBean goodsBean, String editName, String phone) {
        mOrderId = orderId;
        mGoodsBean = goodsBean;
        mEditName = editName;
        mPhone = phone;
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
}
