package com.tzly.ctcyh.cargo.active_p;

import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

import java.util.List;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IActiveContract {

    interface IActiveView extends IBaseView<IActivePresenter> {

        String getChannel();

        void responseError(String message);

        void responseSucceed(ReceiveCouponResponse response);
    }

    interface IActivePresenter extends IBasePresenter {
        void receiveCoupon(String couponId);

        void getConfig();
    }

}
