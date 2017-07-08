package com.zantong.mobilecttx.car.dto;

/**
 * 车辆信息实体
 * <p>
 * 参数名	必选	类型	说明
 * usrid	是	string	用户ID
 * carnum	是	string	车牌号 加密
 * <p>
 * carmodel	否	string	车辆型号
 * <p>
 * ispaycar	是	string	是否可缴费车辆
 * <p>
 * inspectdate	否	string	年检日期
 * <p>
 * defaultflag	是	string	默认标识
 * inspectflag	是	string	年检提醒标识
 * carnumtype	是	string	车牌类型
 * violationflag	是	string	违章提醒标识
 * enginenum	是	string	发动机号 加密
 */
public class CarInfoDTO {

    private String carnum; //车牌
    private String enginenum;//发动机号
    private String carnumtype;//车辆类型
    private String inspectdate;//注册日期
    private String carmodel;
    private String usrid;
    private String ispaycar;
    private String defaultflag;
    private String inspectflag;
    private String violationflag;
    private String carimage;
    private String opertype;
    private String activityCar;

    public CarInfoDTO() {
    }

    public CarInfoDTO(String carnumtype, String inspectdate,
                      String carmodel, String usrid, String ispaycar, String defaultflag,
                      String inspectflag, String violationflag, String carimage,
                      String opertype, String activityCar) {

        this.carnumtype = carnumtype;
        this.inspectdate = inspectdate;
        this.carmodel = carmodel;
        this.usrid = usrid;
        this.ispaycar = ispaycar;
        this.defaultflag = defaultflag;
        this.inspectflag = inspectflag;
        this.violationflag = violationflag;
        this.carimage = carimage;
        this.opertype = opertype;
        this.activityCar = activityCar;
    }

    public String getActivityCar() {
        return activityCar;
    }

    public void setActivityCar(String activityCar) {
        this.activityCar = activityCar;
    }

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

    public String getCarnumtype() {
        return carnumtype;
    }

    public void setCarnumtype(String carnumtype) {
        this.carnumtype = carnumtype;
    }

    public String getInspectdate() {
        return inspectdate;
    }

    public void setInspectdate(String inspectdate) {
        this.inspectdate = inspectdate;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getDefaultflag() {
        return defaultflag;
    }

    public void setDefaultflag(String defaultflag) {
        this.defaultflag = defaultflag;
    }

    public String getInspectflag() {
        return inspectflag;
    }

    public void setInspectflag(String inspectflag) {
        this.inspectflag = inspectflag;
    }

    public String getViolationflag() {
        return violationflag;
    }

    public void setViolationflag(String violationflag) {
        this.violationflag = violationflag;
    }

    public String getCarimage() {
        return carimage;
    }

    public void setCarimage(String carimage) {
        this.carimage = carimage;
    }

    public String getOpertype() {
        return opertype;
    }

    public void setOpertype(String opertype) {
        this.opertype = opertype;
    }

    public void setIspaycar(String ispaycar) {
        this.ispaycar = ispaycar;
    }

    public String getIspaycar() {
        return ispaycar;
    }
}
