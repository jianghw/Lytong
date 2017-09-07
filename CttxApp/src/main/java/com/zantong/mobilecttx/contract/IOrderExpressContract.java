package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.order.dto.ExpressDTO;

import cn.qqtheme.framework.contract.bean.BaseResult;

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
    }

    interface IOrderExpressPresenter extends IMvpPresenter {

        void getAllAreas();

        void addExpressInfo();

        ExpressDTO initExpressDTO();
    }

}
