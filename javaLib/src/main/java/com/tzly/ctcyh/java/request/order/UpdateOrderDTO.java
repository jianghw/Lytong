package com.tzly.ctcyh.java.request.order;

/**
 * orderId	是	String	订单号
 * name	否	String	姓名
 * phone	否	String	电话
 * sheng	否	String	省
 * shi	否	String	市
 * xian	否	String	县
 * addressDetail	否	String	详细地址
 * supplement	否	String	备注
 * bespeakDate	否	String	预约时间(type=7必传)
 */

public class UpdateOrderDTO {
    private String orderId;
    private String name;
    private String phone;
    private String sheng;
    private String shi;
    private String xian;
    private String addressDetail;
    private String supplement;
    private String bespeakDate;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public void setBespeakDate(String bespeakDate) {
        this.bespeakDate = bespeakDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSheng() {
        return sheng;
    }

    public String getShi() {
        return shi;
    }

    public String getXian() {
        return xian;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getSupplement() {
        return supplement;
    }

    public String getBespeakDate() {
        return bespeakDate;
    }
}
