package com.zantong.mobilecttx.order_v;

import com.zantong.mobilecttx.order.bean.OrderListBean;

/**
 * 接口通讯
 */
public interface IOrderListsItem {

    void refreshListData();

    void loadMoreData();

    void doClickCancel(OrderListBean bean);

    void doClickPay(OrderListBean bean);

    void doClickDriving(OrderListBean bean);

    void doClickCourier(OrderListBean bean);

    void doClickSubscribe(OrderListBean bean);

    void doClickUnSubscribe(OrderListBean bean);
}
