package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;

/**
 * Created by jianghw on 2017/4/26.
 * Description: 加油充值p
 * Update by:
 * Update day:
 */

public interface IRechargeAtyContract {

    interface IRechargeAtyView extends IMvpView<IRechargeAtyPresenter> {
        /**
         * 加载提示框
         */
        void showLoadingDialog();

        void dismissLoadingDialog();

        /**
         * 获取指定类型优惠券响应
         */
        void onCouponByTypeError(String message);

        void onCouponByTypeSucceed(RechargeCouponResult result);

        /**
         * 10.创建加油订单
         */
        void addOilCreateOrderError(String s);

        void addOilCreateOrderSucceed(RechargeResult result);

        RechargeDTO initRechargeDTO();
    }

    interface IRechargeAtyPresenter extends IMvpPresenter {
        /**
         * 57.获取指定类型优惠券
         */
        void getCouponByType();

        /**
         * UserId
         */
        String initUserId();

        /**
         * 10.创建加油订单
         */
        void addOilCreateOrder();

        RechargeDTO initRechargeDTO();
    }

}
