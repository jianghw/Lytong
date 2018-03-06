package com.tzly.ctcyh.pay.coupon_p;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface ICouponStatusContract {

    interface ICouponStatusView extends IBaseView<ICouponStatusPresenter>, IResponseView {

        void delUsrCouponError(String message);

        void delUsrCouponSucceed(BaseResponse response, int position);
    }

    interface ICouponStatusPresenter extends IBasePresenter {

        void couponUserList();

        void delUsrCoupon(String couponId, int position);
    }

}
