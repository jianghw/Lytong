package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;

/**
 * 科目强化订单页面
 */

public interface ISubjectCommitContract {

    interface ISubjectCommitView extends IMvpView<ISubjectCommitPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void createOrderError(String s);

        void createOrderSucceed(CreateOrderResponse result);

        String getGoodsId();

        String getPriceValue();

        String getEditName();

        String getEditPhone();

        void couponByTypeError(String message);

        void couponByTypeSucceed(RechargeCouponResponse result);

        String getCouponId();

        boolean getUseCoupon();
    }

    interface ISubjectCommitPresenter extends IMvpPresenter {

        void createOrder();

        CreateOrderDTO getCreateOrder();

        void getCouponByType();

        String initUserId();
    }

}