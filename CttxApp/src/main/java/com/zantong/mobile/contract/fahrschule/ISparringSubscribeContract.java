package com.zantong.mobile.contract.fahrschule;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobile.fahrschule.bean.CreateOrderResult;
import com.zantong.mobile.fahrschule.bean.SparringAreaResult;
import com.zantong.mobile.fahrschule.bean.SparringGoodsResult;
import com.zantong.mobile.fahrschule.dto.CreateOrderDTO;

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

        void serviceAreaSucceed(SparringAreaResult result);

        void goodsSucceed(SparringGoodsResult result);

        void goodsError(String s);

        void serverTimeError(String message);

        void serverTimeSucceed(ArrayList<String> dateList, Date dateString);

        void couponByTypeSucceed(RechargeCouponResult result);

        void couponByTypeError(String message);

        CreateOrderDTO getCreateOrderDTO();

        void createOrderError(String message);

        void createOrderSucceed(CreateOrderResult result);

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
