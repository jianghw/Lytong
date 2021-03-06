package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;

import java.util.List;

/**
 * 订单页面
 */

public interface IOrderParentFtyContract {

    interface IOrderParentFtyView extends IMvpView<IOrderParentFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getOrderListError(String message);

        void dataDistribution(String message);

        void nonPaymentData(List<OrderListBean> orderList);

        void havePaymentData(List<OrderListBean> orderList);

        void cancelPaymentData(List<OrderListBean> orderList);

        void allPaymentData(List<OrderListBean> data);

        void updateOrderStatusError(String message);

        void updateOrderStatusSucceed(BaseResult result);

        void onPayOrderByCouponError(String message);

        void onPayOrderByCouponSucceed(PayOrderResult result);

        void getBankPayHtmlSucceed(PayOrderResult result, String orderId);
    }

    interface IOrderParentFtyPresenter extends IMvpPresenter {
        void getOrderList();

        String initUserId();

        void updateOrderStatus(OrderListBean bean);

        void onPayOrderByCoupon(String orderId, String orderPrice, String payType);

        void getBankPayHtml(String orderId, String orderPrice);
    }
}
