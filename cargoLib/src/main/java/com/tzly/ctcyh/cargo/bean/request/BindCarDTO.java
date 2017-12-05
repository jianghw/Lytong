package com.tzly.ctcyh.cargo.bean.request;

/**
 * Created by zhengyingbing on 16/9/29.
 * 添加车辆新接口javabean
 */

public class BindCarDTO {

    private String plateNo;//号牌号码
    private String fileNum;//档案编号
    private String vehicleType;//车辆类型
    private String address;//住址
    private String useCharacter;//使用性质
    private String carModel;//品牌型号
    private String vin;//车辆识别代号
    private String engineNo;//发动机号码
    private String registerDate;//注册日期
    private String issueDate;//发证日期

    private String ownerName;//所有人
    private String usrnum;//用户ID

    public void setUsrnum(String usrnum) {
        this.usrnum = usrnum;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUseCharacter(String useCharacter) {
        this.useCharacter = useCharacter;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}
