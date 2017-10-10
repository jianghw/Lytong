package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.order.bean.ReceiveInfoResponse;
import com.zantong.mobilecttx.order.dto.ExpressDTO;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * 快递会叫页面
 */

public interface IOrderExpressContract {

    interface IOrderExpressView extends IMvpView<IOrderExpressPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

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

    interface IOrderExpressPresenter extends IMvpPresenter {

        void getAllAreas();

        void addExpressInfo();

        ExpressDTO initExpressDTO();

        void getReceiveInfo();

    }

}
