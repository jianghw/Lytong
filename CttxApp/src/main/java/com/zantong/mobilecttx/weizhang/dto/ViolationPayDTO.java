package com.zantong.mobilecttx.weizhang.dto;

/**
 * 43.生成违章缴费订单
 */
public class ViolationPayDTO {
    private String carnum;//车牌号
    private String usernum;//安盛用户ID(rsa加密）
    private String peccancynum;//违章单号
    private String peccancydate;//违章日期
    private String orderprice;//罚款金额
    private String enginenum;//发动机引擎号

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
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

    public String getEnginenum() {
        return enginenum;
    }

    public void setEnginenum(String enginenum) {
        this.enginenum = enginenum;
    }
}
