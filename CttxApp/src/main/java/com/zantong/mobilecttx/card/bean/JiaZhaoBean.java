package com.zantong.mobilecttx.card.bean;

import java.util.List;

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
public class JiaZhaoBean {

    private String Name;//姓名
    private String Num;//身份证
    private String Sex;//性别
    private String Addr;//地址
    private String Issue;//日期
    private String ValidPeriod;
    private String Nation;//国际
    private String drivingType;//驾照类型
    private String registerDate;//有效期

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getValidPeriod() {
        return ValidPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        ValidPeriod = validPeriod;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        Nation = nation;
    }

    public String getDrivingType() {
        return drivingType;
    }

    public void setDrivingType(String drivingType) {
        this.drivingType = drivingType;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
