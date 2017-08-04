package com.zantong.mobilecttx.eventbus;

import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;

public class SparringOrderEvent {

    private final String mOrderId;
    private final CreateOrderDTO mGoodsBean;
    private final String mTextTime;
    private final String mCarType;

    public SparringOrderEvent(String orderId,
                              CreateOrderDTO createOrderDTO, String time, String carType) {
        mOrderId = orderId;
        mGoodsBean = createOrderDTO;
        mTextTime = time;
        mCarType = carType;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public CreateOrderDTO getGoodsBean() {
        return mGoodsBean;
    }

    public String getTextTime() {
        return mTextTime;
    }

    public String getCarType() {
        return mCarType;
    }
}
