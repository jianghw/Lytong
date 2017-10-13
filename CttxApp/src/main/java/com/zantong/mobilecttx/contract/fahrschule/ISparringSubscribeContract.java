package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * 驾校陪练订单页面
 */

public interface ISparringSubscribeContract {

    interface ISparringSubscribeView extends IMvpView<ISparringSubscribePresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void serviceAreaError(String message);

        void serviceAreaSucceed(SparringAreaResponse result);

        void goodsSucceed(SparringGoodsResponse result);

        void goodsError(String s);

        void serverTimeError(String message);

        void serverTimeSucceed(ArrayList<String> dateList, Date dateString);

        void couponByTypeSucceed(RechargeCouponResponse result);

        void couponByTypeError(String message);

        CreateOrderDTO getCreateOrderDTO();

        void createOrderError(String message);

        void createOrderSucceed(CreateOrderResponse result);

        boolean getUseCoupon();

        String getCouponId();

    }

    interface ISparringSubscribePresenter extends IMvpPresenter {

        void getServiceArea();

        void getGoods();

        void getServerTime();

        void getCouponByType();

        String initUserId();

        void createOrder();

        CreateOrderDTO getCreateOrder();
    }

}