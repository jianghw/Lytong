package com.tzly.ctcyh.pay.pay_type_p;

import com.tzly.ctcyh.java.response.coupon.CouponInfoResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description: 支付方式
 * Update by:
 * Update day:
 */

public interface IPaySucceedContract {

    interface IPaySucceedView extends IBaseView<IPaySucceedPresenter>, IResponseView {
        String getGoodsType();

        void couponInfoError(String message);

        void couponInfoSucceed(CouponInfoResponse response);
    }

    interface IPaySucceedPresenter extends IBasePresenter {
        void getCouponInfo();
    }

}
