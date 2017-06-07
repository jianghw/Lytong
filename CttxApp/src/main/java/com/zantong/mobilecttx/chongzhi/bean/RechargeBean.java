package com.zantong.mobilecttx.chongzhi.bean;

/**
 * 油卡充值实体类
 *
 * @author zyb
 *         <p>
 *         ZhengYingBing LOVE ZhengLinLin FOREVER
 *         <p>
 *         *  *   *  *
 *         *      *      *
 *         *             *  猜猜我是怎么做到的
 *         *           *
 *         *     *
 *         *
 *         <p>
 *         是不是很厉害
 *         <p>
 *         create at 16/12/30 下午4:15
 */
public class RechargeBean {

    String amount;//金额
    String template;//充值代号 20001-中石化100
    String discount;//折扣
    boolean isCheckd;//选中状态


    public RechargeBean(String amount, String template, String discount, boolean isCheckd) {
        this.amount = amount;
        this.template = template;
        this.discount = discount;
        this.isCheckd = isCheckd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setCheckd(boolean checkd) {
        isCheckd = checkd;
    }

    public boolean isCheckd() {
        return isCheckd;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
