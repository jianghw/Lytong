package com.zantong.mobilecttx.chongzhi.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

/**
 * 加油充值返回实体
 * @author zyb
 *
 *
 *    *  *   *  *
 *  *      *      *
 *  *             *
 *   *           *
 *      *     *
 *         *
 *
 *
 * create at 17/1/11 下午5:15
 */
public class RechargeResult extends BaseResult {

    private OrderBean data;

    public OrderBean getData() {
        return data;
    }

    public void setData(OrderBean data) {
        this.data = data;
    }

    public class OrderBean{
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}

