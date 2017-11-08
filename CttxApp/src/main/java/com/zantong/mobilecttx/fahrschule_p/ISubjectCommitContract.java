package com.zantong.mobilecttx.fahrschule_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;

/**
 * 科目强化订单页面
 */

public interface ISubjectCommitContract {

    interface ISubjectCommitView extends IBaseView<ISubjectCommitPresenter> {

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

    interface ISubjectCommitPresenter extends IBasePresenter {

        void createOrder();

        CreateOrderDTO getCreateOrder();

        void getCouponByType();
    }

}
