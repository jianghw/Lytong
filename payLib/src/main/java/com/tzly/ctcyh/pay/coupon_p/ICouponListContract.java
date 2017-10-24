package com.tzly.ctcyh.pay.coupon_p;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface ICouponListContract {

    interface ICouponListView extends IBaseView<ICouponListPresenter> {
        String getExtraType();

        void couponByTypeError(String message);

        void couponByTypeSucceed(CouponResponse response);
    }

    interface ICouponListPresenter extends IBasePresenter {
        void getCouponByType();

        String getUserId();

    }

}
