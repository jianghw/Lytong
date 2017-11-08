package com.tzly.ctcyh.pay.html_p;

import android.location.Location;

import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description: 支付方式
 * Update by:
 * Update day:
 */

public interface IHtmlPayContract {

    interface IHtmlPayView extends IBaseView<IHtmlPayPresenter> {
        void orderDetailCompleted();

        void intervalError(String s);

        String getOrderId();

        void getOrderDetailSucceed(OrderDetailResponse response);

        void getOrderDetailError(String s);

        void ToastMsg(String msg);

        boolean isLogin();

        void gotoLogin();

        void payMOTOrder(String coupon, String orderId, String amount);

        void callCamera();

        void searchViolationList(String carnum, String enginenum, String carnumtype);

        void goNianjianMap();

        void popAttention();

        String getEncreptUserId();

        void queryViolations();

        int getBindCardStatus();

        String getUserId();

        void chaser();

        void addOil();

        void bindCard();

        Location getLocaltion();

        void getBankPayHtmlError(String message);

        void getBankPayHtmlSucceed(PayUrlResponse response);
    }

    interface IHtmlPayPresenter extends IBasePresenter {
        void orderDetail();

        String getOrderId();

        void getBankPayHtml(String orderId, String s, String coupon);
    }

}
