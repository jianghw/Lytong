package com.zantong.mobilecttx.order_p;

import com.tzly.ctcyh.java.response.order.UpdateOrderResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.order.bean.OrderDetailResponse;

/**
 * 修改订单信息
 */

public interface IAmendOrderContract {

    interface IAmendOrderView extends IBaseView<IAmendOrderPresenter> {
        String getName();

        String getPhone();

        String getAddress();

        String getAddressDetail();

        String getBespeakDate();

        String getSupplement();

        void allAreasSucceed(Object[] objects);

        void allAreasError(String message);

        void updateOrderError(String message);

        void updateOrderSucceed(UpdateOrderResponse result);
    }

    interface IAmendOrderPresenter extends IBasePresenter {
        void updateOrderDetail();

        void getAllAreas();
    }

}
