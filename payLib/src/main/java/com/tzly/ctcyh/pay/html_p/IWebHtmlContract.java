package com.tzly.ctcyh.pay.html_p;

import com.tzly.ctcyh.java.request.card.ApplyCTCardDTO;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.tzly.ctcyh.pay.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.response.PayUrlResponse;
import com.tzly.ctcyh.pay.response.PayWeixinResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description: 支付方式
 * Update by:
 * Update day:
 */

public interface IWebHtmlContract {

    interface IWebHtmlView extends IBaseView<IWebHtmlPresenter> {

        String getOrderId();

        void orderDetailCompleted();

        void intervalError(String s);

        void orderDetailError(String s);

        void orderDetailSucceed(OrderDetailResponse result);

        void bankPayHtmlError(String message);

        void bankPayHtmlSucceed(PayUrlResponse response, String orderId);

        void weChatPayError(String message);

        void weChatPaySucceed(PayWeixinResponse response, String orderId);

        void bank_v003_01Error(String s);

        void updateStateError(String s);

        void updateStateSucceed(ViolationNumBean result);

        void customerServiceMain(String url);

        void titleShare();

        void gotoTargetPath(String url);
    }

    interface IWebHtmlPresenter extends IBasePresenter {
        public void intervalOrder(String orderId);

        public void orderDetail(String orderId);

        public void bankPayHtml(String orderId, String amount, String coupon);

        public void weChatPay(String couponUserId, final String orderId, String amount);

        void bank_v003_01(String violationNum);

        void applyRecord(ApplyCTCardDTO applyCTCardDTO);

        void customerService(String url, int i);
    }

}
