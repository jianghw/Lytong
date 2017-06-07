package com.zantong.mobilecttx.card.bean;

/**
 * 
 * @author zyb
 *
 *  
 *    *  *   *  *     
 *  *      *      *
 *  *             *   
 *   *           *    
 *      *     *       
 *         *          
 * 
 *
 * create at 17/1/6 下午3:53
 */
public class XingShiZhengBean {

    private String Name;//姓名
    private String CardNo;//车牌号
    private String Addr;//地址
    private String IssueDate;//初次办理日期
    private String VehicleType;//车辆类型
    private String UseCharacte;//营运类型
    private String Model;//车型车系
    private String Vin;//车辆识别代号
    private String EnginePN;//发动机号
    private String RegisterDate;//注册日期

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getUseCharacte() {
        return UseCharacte;
    }

    public void setUseCharacte(String useCharacte) {
        UseCharacte = useCharacte;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getVin() {
        return Vin;
    }

    public void setVin(String vin) {
        Vin = vin;
    }

    public String getEnginePN() {
        return EnginePN;
    }

    public void setEnginePN(String enginePN) {
        EnginePN = enginePN;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getVehicleType() {
        return VehicleType;
    }
}
