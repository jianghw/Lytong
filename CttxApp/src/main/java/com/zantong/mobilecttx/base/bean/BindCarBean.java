package com.zantong.mobilecttx.base.bean;

/**
 * 号牌号码： getCardNo()
 * 车辆类型： getVehicleType()
 * 所有人：   getName())
 * 住址：     getAddress()
 * 使用性质： getUseCharace()
 * 品牌型号： getModel()
 * 车辆识别代码： getVin()
 * 发动机号码 getEngine_pn()
 * 注册日期： getRegisterdate()
 * 发证日期： getIssuaedate()
 */

public class BindCarBean {

    private String CardNo;
    private String VehicleType;
    private String Name;
    private String Addr;
    private String UseCharace;
    private String Model;
    private String Vin;
    private String EnginePN;
    private String RegisterDate;
    private String IssueDate;

    public String getCardNo() {
        return CardNo;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public String getName() {
        return Name;
    }

    public String getAddr() {
        return Addr;
    }

    public String getUseCharace() {
        return UseCharace;
    }

    public String getModel() {
        return Model;
    }

    public String getVin() {
        return Vin;
    }

    public String getEnginePN() {
        return EnginePN;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public String getIssueDate() {
        return IssueDate;
    }
}
