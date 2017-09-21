package com.zantong.mobilecttx.chongzhi.bean;

import com.tzly.annual.base.bean.BaseResult;

import java.util.List;

/**
 * 充值订单实体类
 * @author zyb
 *
 * ZhengYingBing LOVE ZhengLinLin FOREVER
 *
 *    *  *   *  *
 *  *      *      *
 *  *             *  猜猜我是怎么做到的
 *   *           *
 *      *     *
 *         *
 *
 *    是不是很厉害
 *
 * create at 16/12/30 下午4:15
 */
public class RechargeOrderResult  extends BaseResult {


    List<RechargeOrderBean> data;

    public List<RechargeOrderBean> getData() {
        return data;
    }

    public void setData(List<RechargeOrderBean> data) {
        this.data = data;
    }
}
