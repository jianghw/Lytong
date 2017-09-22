package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.fahrschule.bean.AresGoodsResult;
import com.zantong.mobile.fahrschule.bean.CreateOrderResult;
import com.zantong.mobile.fahrschule.bean.GoodsDetailResult;
import com.zantong.mobile.fahrschule.bean.MerchantAresResult;
import com.zantong.mobile.fahrschule.dto.CreateOrderDTO;

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

        void getGoodsDetailError(String message);

        void getGoodsDetailSucceed(GoodsDetailResult result);
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
