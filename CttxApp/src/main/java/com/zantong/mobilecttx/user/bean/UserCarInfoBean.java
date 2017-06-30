package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.weizhang.bean.ViolationInfoBean;

import java.util.List;

/**
 * 我的车辆返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class UserCarInfoBean {

    private String mileage;//行驶里程
    private String carnumtype;//车牌类型
    private String inspectdate;//年检日期
    private String ispaycar;//是否可缴费车辆
    private String carmodel;//车辆型号
    private String maintaindate;//上次保养日期
    private String defaultflag;//默认标识
    private String carcolour;//颜色
    private String maintaininterval;//保养间隔
    private String carframenum;//车架号
    private String inspectflag;//年检提醒标识
    private String totcent;//总分值
    private String carimage;//图片
    private String totcount;//总笔数
    private String enginenum;//发动机号
    private String buydate;//购买日期
    private String carnum;//车牌
    private String violationflag;//违章提醒标识
    private String totamt;//总金额
    private String untreatcount;//未处理总笔数
    private String untreatcent;//未处理总分值
    private String untreatamt;//未处理总金额
    private List<ViolationInfoBean> ViolationInfo;//

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
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

    public String getIspaycar() {
        return ispaycar;
    }

    public void setIspaycar(String ispaycar) {
        this.ispaycar = ispaycar;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getMaintaindate() {
        return maintaindate;
    }

    public void setMaintaindate(String maintaindate) {
        this.maintaindate = maintaindate;
    }

    public String getDefaultflag() {
        return defaultflag;
    }

    public void setDefaultflag(String defaultflag) {
        this.defaultflag = defaultflag;
    }

    public String getCarcolour() {
        return carcolour;
    }

    public void setCarcolour(String carcolour) {
        this.carcolour = carcolour;
    }

    public String getMaintaininterval() {
        return maintaininterval;
    }

    public void setMaintaininterval(String maintaininterval) {
        this.maintaininterval = maintaininterval;
    }

    public String getCarframenum() {
        return carframenum;
    }

    public void setCarframenum(String carframenum) {
        this.carframenum = carframenum;
    }

    public String getInspectflag() {
        return inspectflag;
    }

    public void setInspectflag(String inspectflag) {
        this.inspectflag = inspectflag;
    }

    public String getTotcent() {
        return totcent;
    }

    public void setTotcent(String totcent) {
        this.totcent = totcent;
    }

    public String getCarimage() {
        return carimage;
    }

    public void setCarimage(String carimage) {
        this.carimage = carimage;
    }

    public String getTotcount() {
        return totcount;
    }

    public void setTotcount(String totcount) {
        this.totcount = totcount;
    }

    public String getEnginenum() {
        return enginenum;
    }

    public void setEnginenum(String enginenum) {
        this.enginenum = enginenum;
    }

    public String getBuydate() {
        return buydate;
    }

    public void setBuydate(String buydate) {
        this.buydate = buydate;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getViolationflag() {
        return violationflag;
    }

    public void setViolationflag(String violationflag) {
        this.violationflag = violationflag;
    }

    public String getTotamt() {
        return totamt;
    }

    public void setTotamt(String totamt) {
        this.totamt = totamt;
    }

    public String getUntreatcount() {
        return untreatcount;
    }

    public void setUntreatcount(String untreatcount) {
        this.untreatcount = untreatcount;
    }

    public String getUntreatcent() {
        return untreatcent;
    }

    public void setUntreatcent(String untreatcent) {
        this.untreatcent = untreatcent;
    }

    public String getUntreatamt() {
        return untreatamt;
    }

    public void setUntreatamt(String untreatamt) {
        this.untreatamt = untreatamt;
    }

    public List<ViolationInfoBean> getViolationInfo() {
        return ViolationInfo;
    }

    public void setViolationInfo(List<ViolationInfoBean> violationInfo) {
        ViolationInfo = violationInfo;
    }
}
