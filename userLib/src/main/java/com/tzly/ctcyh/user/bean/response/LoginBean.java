package com.tzly.ctcyh.user.bean.response;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class LoginBean {
    /**
     * lastlogindate	是	string	最近登录日期
     * remindflag	否	string	销分提醒
     * getdate	是	string	首次领证日期
     * nickname	是	string	用户昵称
     * relatedcard	是	string	关联畅通卡号
     * pswderrnum	是	string	密码失败次数
     * ctftp	是	string	证件类型
     * portrait	是	string	用户头像
     * recddt	是	string	推荐日期
     * lastlogintime	是	string	最近登录时间
     * upddate	是	string	更新日期
     * regdate	是	string	注册日期
     * filenum	是	string	档案号 加密
     * ctfnum	是	string	证件号码 加密
     * usrid	是	string	用户ID
     * phoenum	是	string	手机号 加密
     * recdphoe	是	string	推荐人手机号 加密
     */

    private String lastlogindate;
    private String remindflag;
    private String getdate;
    private String nickname;
    private String relatedcard;
    private String pswderrnum;
    private String ctftp;
    private String portrait;
    private String recddt;
    private String lastlogintime;
    private String upddate;
    private String regdate;
    private String filenum;
    private String ctfnum;
    private String usrid;
    private String phoenum;
    private String recdphoe;

    public String getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(String lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public String getRemindflag() {
        return remindflag;
    }

    public void setRemindflag(String remindflag) {
        this.remindflag = remindflag;
    }

    public String getGetdate() {
        return getdate;
    }

    public void setGetdate(String getdate) {
        this.getdate = getdate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRelatedcard() {
        return relatedcard;
    }

    public void setRelatedcard(String relatedcard) {
        this.relatedcard = relatedcard;
    }

    public String getCtftp() {
        return ctftp;
    }

    public void setCtftp(String ctftp) {
        this.ctftp = ctftp;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getUpddate() {
        return upddate;
    }

    public void setUpddate(String upddate) {
        this.upddate = upddate;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getFilenum() {
        return filenum;
    }

    public void setFilenum(String filenum) {
        this.filenum = filenum;
    }

    public String getCtfnum() {
        return ctfnum;
    }

    public void setCtfnum(String ctfnum) {
        this.ctfnum = ctfnum;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getPhoenum() {
        return phoenum;
    }

    public void setPhoenum(String phoenum) {
        this.phoenum = phoenum;
    }

    public String getRecdphoe() {
        return recdphoe;
    }

    public void setRecdphoe(String recdphoe) {
        this.recdphoe = recdphoe;
    }

    public String getRecddt() {
        return recddt;
    }

    public void setRecddt(String recddt) {
        this.recddt = recddt;
    }

    public String getPswderrnum() {
        return pswderrnum;
    }

    public void setPswderrnum(String pswderrnum) {
        this.pswderrnum = pswderrnum;
    }
}
