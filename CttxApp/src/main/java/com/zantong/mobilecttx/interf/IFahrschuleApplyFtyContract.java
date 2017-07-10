package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResult;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;

/**
 * 畅通页面
 */

public interface IFahrschuleApplyFtyContract {

    interface IFahrschuleApplyFtyView extends IMvpView<IFahrschuleApplyFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getMerchantAreaError(String message);

        void getMerchantAreaSucceed(MerchantAresResult result);

        int getAreaCode();

        void getAreaGoodsError(String message);

        void getAreaGoodsSucceed(AresGoodsResult result);

        String getGoodsId();

        String getEditName();

        String getEditPhone();

        String getEditIdentityCard();

        void createOrderError(String message);

        void createOrderSucceed(CreateOrderResult result);

        String getPriceValue();

    }

    interface IFahrschuleApplyFtyPresenter extends IMvpPresenter {
        /**
         * 3.获取商户区域列表
         */
        void getMerchantArea();

        void getAreaGoods();

        int getAreaCode();

        void createOrder();

        CreateOrderDTO getCreateOrder();
    }

}
