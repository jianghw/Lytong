package com.tzly.ctcyh.pay.pay_type_p;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.bean.response.PayWeixinResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description: 支付方式
 * Update by:
 * Update day:
 */

public interface IPayTypeContract {

    interface IPayTypeView extends IBaseView<IPayTypePresenter>,IResponseView {
        String getExtraOrderId();
        void getBankPayHtmlError(String message);

        void getBankPayHtmlSucceed(PayUrlResponse response);

        String getExtraType();

        void couponByTypeError(String message);

        void couponByTypeSucceed(CouponResponse response);

        int getPayType();

        void weChatPayError(String message);

        void weChatPaySucceed(PayWeixinResponse response);

        int getCouponUserId();
    }

    interface IPayTypePresenter extends IBasePresenter {
        void getOrderInfo();

        void getBankPayHtml(String orderId, String s, int couponBeanId);

        void getCouponByType();

        void weChatPay(String extraOrderId, String s);
    }

}
