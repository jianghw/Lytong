package com.tzly.ctcyh.service;

import android.text.TextUtils;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.util.RSAUtils;

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
    /**
     * 推送id
     */
    private String pushId;

    public String getPushId() {
        return pushId;
    }

    private static class SingletonHolder {
        private static final MemoryData INSTANCE = new MemoryData();
    }

    public static MemoryData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
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
        return RSAUtils.strByEncryption(getUserID(), true);
    }

    public String getUserID() {
        if (!TextUtils.isEmpty(usrid)) return usrid;

        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        if (serviceRouter.getService(IUserService.class.getSimpleName()) != null) {
            IUserService service = (IUserService) serviceRouter
                    .getService(IUserService.class.getSimpleName());
            boolean userLogin = service.isUserLogin();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
        return usrid;
    }

}
