package com.tzly.annual.base.imple;

import com.tzly.annual.base.bean.response.CattleOrderBean;

/**
 * Created by jianghw on 2017/9/20.
 * Description: 黄牛页 订单点击 监听
 * Update by:
 * Update day:
 */

public interface CattleOrderItemListener {

    void doClickHave(CattleOrderBean bean);

    void doClickAudit(CattleOrderBean bean);

    void doClickProcess(CattleOrderBean bean);

    void doClickCompleted(CattleOrderBean bean);

}
