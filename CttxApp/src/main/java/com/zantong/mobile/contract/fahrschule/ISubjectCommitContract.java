package com.zantong.mobile.contract.fahrschule;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobile.fahrschule.bean.CreateOrderResult;
import com.zantong.mobile.fahrschule.dto.CreateOrderDTO;

/**
 * 科目强化订单页面
 */

public interface ISubjectCommitContract {

    interface ISubjectCommitView extends IMvpView<ISubjectCommitPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void createOrderError(String s);

        void createOrderSucceed(CreateOrderResult result);

        String getGoodsId();

        String getPriceValue();

        String getEditName();

        String getEditPhone();

        void couponByTypeError(String message);

        void couponByTypeSucceed(RechargeCouponResult result);

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
