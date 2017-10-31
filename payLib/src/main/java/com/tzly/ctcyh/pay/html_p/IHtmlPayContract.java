package com.tzly.ctcyh.pay.html_p;

import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
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

        void toastShort(String msg);

        boolean isLogin();

        void gotoLogin();
    }

    interface IHtmlPayPresenter extends IBasePresenter {
        void orderDetail();

        String getOrderId();
    }

}
