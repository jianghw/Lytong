package com.zantong.mobile.user.bean;

/**
 * 订单列表实体
 * @author Sandy
 * create at 16/6/12 上午11:40
 */
public class CheckOrder {
//    0 未支付
//    1 支付成功
//    2 未支付的已撤单
//    3 支付中
//    4 交易可疑
//    5 支付失败
//    6 支付成功且退款成功的已撤单
//    7 退款中(退款接收成功，退款处理中，退款可疑)
    private int pymtste;//订单状态

    public int getPymtste() {
        return pymtste;
    }

    public void setPymtste(int pymtste) {
        this.pymtste = pymtste;
    }
}
