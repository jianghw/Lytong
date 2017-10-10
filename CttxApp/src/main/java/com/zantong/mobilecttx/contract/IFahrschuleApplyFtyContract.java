package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResponse;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.bean.GoodsDetailResponse;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;

/**
 * 畅通页面
 */

public interface IFahrschuleApplyFtyContract {

    interface IFahrschuleApplyFtyView extends IMvpView<IFahrschuleApplyFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getMerchantAreaError(String message);

        void getMerchantAreaSucceed(MerchantAresResponse result);

        int getAreaCode();

        void getAreaGoodsError(String message);

        void getAreaGoodsSucceed(AresGoodsResponse result);

        String getGoodsId();

        String getEditName();

        String getEditPhone();

        String getEditIdentityCard();

        void createOrderError(String message);

        void createOrderSucceed(CreateOrderResponse result);

        String getPriceValue();

        void getGoodsDetailError(String message);

        void getGoodsDetailSucceed(GoodsDetailResponse result);
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

        void getGoodsDetail(String goodsId);
    }

}
