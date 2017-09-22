package com.zantong.mobile.chongzhi.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * 充值订单详情实体
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
 * create at 17/1/17 下午5:37
 */
public class RechargeOrderDetailResult extends BaseResult {

    private RechargeOrderDetailBean data;

    public void setData(RechargeOrderDetailBean data) {
        this.data = data;
    }

    public RechargeOrderDetailBean getData() {
        return data;
    }
}