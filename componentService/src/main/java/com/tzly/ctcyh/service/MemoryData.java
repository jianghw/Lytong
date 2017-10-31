package com.tzly.ctcyh.service;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;

/**
 * app内存数据清单 用于全局数据交互
 */
public class MemoryData {
    /**
     * 登录用户信息
     */
    private boolean isLogin;
    private String filenum;
    private String ctfnum;
    private String usrid;
    private String phoenum;
    private String recdphoe;
    private String getdate;
    private String nickname;
    private String portrait;

    private static class SingletonHolder {
        private static final MemoryData INSTANCE = new MemoryData();
    }

    public static MemoryData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getGetdate() {
        return getdate;
    }

    public void setGetdate(String getdate) {
        this.getdate = getdate;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    //TODO 暂时这么 要改！！！Main
    public boolean isMainLogin() {
        if (isLogin) return true;
        UiRouter.getInstance().openUriBundle(
                Utils.getContext(),
                "main_scheme" + "://" + "in_exist_host",
                null);
        return isLogin;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    /**
     * 获取用户id
     */
    public String getRASUserID() {
        return RSAUtils.strByEncryption(getGlobalUserID(), true);
    }

    /**
     * 只为用户模块使用 user 其他地方禁止使用
     */
    public String getUsrid() {
        return usrid;
    }

    /**
     * 朕 统一哈
     */
    public String getGlobalUserID() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        if (serviceRouter.getService(IUserService.class.getSimpleName()) != null) {
            IUserService service = (IUserService) serviceRouter
                    .getService(IUserService.class.getSimpleName());
            return service.getUserID();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "0123456789";
        }
    }

}
