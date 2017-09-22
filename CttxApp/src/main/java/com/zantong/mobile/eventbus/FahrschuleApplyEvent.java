package com.zantong.mobile.eventbus;

import com.zantong.mobile.fahrschule.dto.CreateOrderDTO;

public class FahrschuleApplyEvent {

    private final String mOrderId;
    private final CreateOrderDTO mCreateOrder;
    private final String mCourseSel;

    public FahrschuleApplyEvent(String orderId, CreateOrderDTO createOrder, String tvCourseSel) {
        mOrderId = orderId;
        mCreateOrder = createOrder;
        mCourseSel = tvCourseSel;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public CreateOrderDTO getCreateOrder() {
        return mCreateOrder;
    }

    public String getCourseSel() {
        return mCourseSel;
    }
}
