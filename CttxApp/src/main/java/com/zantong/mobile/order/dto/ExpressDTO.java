package com.zantong.mobile.order.dto;

/**
 * 快递信息
 */
public class ExpressDTO {

    /**
     * 参数名	必选	类型	说明
     * userNum	是	string	安盛id
     * orderId	是	string	订单号
     * sendName	是	string	发件人姓名
     * sendPhone	是	string	发件人手机号
     * sendProvince	是	string	发件人省份
     * sendCity	是	string	发件人城市
     * sendAddress	是	string	发件人详细地址
     * expressId	是	string	快递服务方(1-顺丰)
     */

    private String userNum;
    private String orderId;
    private String sendName;
    private String sendPhone;
    private String sendProvince;
    private String sendCity;
    private String sendAddress;
    private String expressId;

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public void setSendProvince(String sendProvince) {
        this.sendProvince = sendProvince;
    }

    public void setSendCity(String sendCity) {
        this.sendCity = sendCity;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }
}
