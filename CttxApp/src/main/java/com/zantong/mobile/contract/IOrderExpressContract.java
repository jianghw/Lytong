package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.order.bean.ReceiveInfoResult;
import com.zantong.mobile.order.dto.ExpressDTO;

import com.tzly.annual.base.bean.BaseResult;

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

        void addExpressInfoSucceed(BaseResult result);

        String getUserName();

        String getUserPhone();

        String getAddress();

        String getOrderId();

        String getProvince();

        void getReceiveInfoError(String message);

        void getReceiveInfoSucceed(ReceiveInfoResult result);
    }

    interface IOrderExpressPresenter extends IMvpPresenter {

        void getAllAreas();

        void addExpressInfo();

        ExpressDTO initExpressDTO();

        void getReceiveInfo();

    }

}
