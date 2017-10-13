package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * 订单页面
 */

public interface IOrderParentFtyContract {

    interface IOrderParentFtyView extends IMvpView<IOrderParentFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getOrderListError(String message);

        void dataDistribution(String message, int orderStatus);

        void nonPaymentData(List<OrderListBean> orderList);

        void havePaymentData(List<OrderListBean> orderList);

        void cancelPaymentData(List<OrderListBean> orderList);

        void allPaymentData(List<OrderListBean> data);

        void updateOrderStatusError(String message);

        void updateOrderStatusSucceed(BaseResponse result);

        void onPayOrderByCouponError(String message);

        void onPayOrderByCouponSucceed(PayOrderResponse result);

        void getBankPayHtmlSucceed(PayOrderResponse result, String orderId);
    }

    interface IOrderParentFtyPresenter extends IMvpPresenter {
        void getOrderList();

        String initUserId();

        void updateOrderStatus(OrderListBean bean);

        void onPayOrderByCoupon(String orderId, String orderPrice, String payType);

        void getBankPayHtml(String orderId, String orderPrice);

        void cancelOrder(OrderListBean bean);

        void aliPayHtml(String orderId);
    }
}