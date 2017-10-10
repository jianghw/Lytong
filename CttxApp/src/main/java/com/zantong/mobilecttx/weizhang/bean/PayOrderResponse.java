package com.zantong.mobilecttx.weizhang.bean;

import cn.qqtheme.framework.bean.BaseResponse;

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
public class PayOrderResponse extends BaseResponse {

    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
