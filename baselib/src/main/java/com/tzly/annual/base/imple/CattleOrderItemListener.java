package com.tzly.annual.base.imple;

import com.tzly.annual.base.bean.response.OrderListBean;

/**
 * Created by jianghw on 2017/9/20.
 * Description: 黄牛页 订单点击 监听
 * Update by:
 * Update day:
 */

public interface CattleOrderItemListener {

    void doClickHave(OrderListBean bean);

    void doClickAudit(OrderListBean bean);

    void doClickProcess(OrderListBean bean);

    void doClickCompleted(OrderListBean bean);

}
