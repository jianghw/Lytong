package com.zantong.mobilecttx.card.dto;

/**
 * 营销代码请求实体类
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
 * create at 17/3/25 上午11:01
 */
public class YingXiaoDataDTO {

    private String usrnum; //用户id
    private String empNum; //用户id

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public void setUsrnum(String usrnum) {
        this.usrnum = usrnum;
    }

    public String getEmpNum() {
        return empNum;
    }

    public String getUsrnum() {
        return usrnum;
    }
}
