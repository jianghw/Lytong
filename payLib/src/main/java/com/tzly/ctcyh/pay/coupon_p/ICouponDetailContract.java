package com.tzly.ctcyh.pay.coupon_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵详情
 * Update by:
 * Update day:
 */

public interface ICouponDetailContract {

    interface ICouponDetailView extends IBaseView<ICouponDetailPresenter>, IResponseView {
        String couponId();
    }

    interface ICouponDetailPresenter extends IBasePresenter {
        void couponDetail();

        void getCodeDetail();
    }

}
