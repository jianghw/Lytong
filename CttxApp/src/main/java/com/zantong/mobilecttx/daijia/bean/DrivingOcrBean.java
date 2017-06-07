package com.zantong.mobilecttx.daijia.bean;

/**
 * Created by zhengyingbing on 16/9/13.
 */
public class DrivingOcrBean {

    private String Address;
    private String CardNo;
    private String IssueDate;
    private String Name;
    private String enginePN;
    private String model;
    private String registerDate;
    private String useCharacte;
    private String vehicleType;
    private String vin;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEnginePN() {
        return enginePN;
    }

    public void setEnginePN(String enginePN) {
        this.enginePN = enginePN;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getUseCharacte() {
        return useCharacte;
    }

    public void setUseCharacte(String useCharacte) {
        this.useCharacte = useCharacte;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
