package com.zantong.mobile.user.dto;

/**
 * 修改密码实体
 * @author Sandy
 * create at 16/6/22 下午12:03
 */
public class ChangePwdDTO {

    private String usrid; //ID
    private String oldpswd; //老密码
    private String newpswd;//新密码

    public String getOldpswd() {
        return oldpswd;
    }

    public void setOldpswd(String oldpswd) {
        this.oldpswd = oldpswd;
    }

    public String getNewpswd() {
        return newpswd;
    }

    public void setNewpswd(String newpswd) {
        this.newpswd = newpswd;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }
}
