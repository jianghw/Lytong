package com.tzly.ctcyh.pay.response;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */
public class PayTypesBean {
    /**
     * id : 1
     * typeName : 畅通卡
     * effective : true
     */

    private int id;
    private String typeName;
    private int payId;
    private boolean effective;

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getTypeName() { return typeName;}

    public void setTypeName(String typeName) { this.typeName = typeName;}

    public boolean isEffective() { return effective;}

    public void setEffective(boolean effective) { this.effective = effective;}
}
