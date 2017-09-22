package com.zantong.mobile.weizhang.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * 支付订单返回实体类
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
 * create at 17/3/13 上午11:14
 */
public class PayOrderResult extends BaseResult {

    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
