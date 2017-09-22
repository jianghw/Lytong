package com.zantong.mobile.weizhang.dto;

/**
 * 违章单号
 */

public class ViolationOrderDTO {
    /**
     * carnum	是	string	车牌号
     * usernum	是	string	安盛用户ID(rsa加密）
     * peccancynum	是	string	违章单号
     * peccancydate	是	string	违章日期
     * orderprice	是	string	罚款金额
     * enginenum	是	string	发动机引擎号
     */
    private String carnum;
    private String enginenum;
    private String usernum;
    private String peccancynum;
    private String peccancydate;
    private String orderprice;

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getEnginenum() {
        return enginenum;
    }

    public void setEnginenum(String enginenum) {
        this.enginenum = enginenum;
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getPeccancynum() {
        return peccancynum;
    }

    public void setPeccancynum(String peccancynum) {
        this.peccancynum = peccancynum;
    }

    public String getPeccancydate() {
        return peccancydate;
    }

    public void setPeccancydate(String peccancydate) {
        this.peccancydate = peccancydate;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice;
    }
}
