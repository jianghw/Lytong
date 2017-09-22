package com.zantong.mobile.huodong.dto;

/**
 * 活动报名请求实体
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
 * create at 17/2/17 下午5:11
 */
public class HundredPlanDTO {

    private String usrnum; //用户ID
    private String plateNo; //车牌号
    private String phoneNum; //手机号

    public String getUsrnum() {
        return usrnum;
    }

    public void setUsrnum(String usrnum) {
        this.usrnum = usrnum;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
