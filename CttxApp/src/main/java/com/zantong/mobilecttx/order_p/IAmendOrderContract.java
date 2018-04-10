package com.zantong.mobilecttx.order_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.order.bean.ReceiveInfoResponse;
import com.zantong.mobilecttx.order.dto.ExpressDTO;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 快递会叫页面
 */

public interface IOrderExpressContract {

    interface IOrderExpressView extends IBaseView<IOrderExpressPresenter> {

        void allAreasError(String message);

        void allAreasSucceed(Object[] result);

        void addExpressInfoError(String message);

        void addExpressInfoSucceed(BaseResponse result);

        String getUserName();

        String getUserPhone();

        String getAddress();

        String getOrderId();

        String getProvince();

        void getReceiveInfoError(String message);

        void getReceiveInfoSucceed(ReceiveInfoResponse result);
    }

    interface IOrderExpressPresenter extends IBasePresenter {

        void getAllAreas();

        void addExpressInfo();

        ExpressDTO initExpressDTO();

        void getReceiveInfo();

    }

}
