package com.tzly.ctcyh.user.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.tzly.ctcyh.user.global.UserMemoryData;
import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginBean;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.router.UserRouter;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 仓库管理类
 */

public class UserDataManager {
    @Nullable
    private static UserDataManager INSTANCE = null;
    @NonNull
    private final RemoteData mRemoteData;
    @NonNull
    private final LocalData mLocalData;

    /**
     * 懒汉式，线程不安全
     */
    public static UserDataManager getInstance(RemoteData remoteData, LocalData localData) {
        if (INSTANCE == null) {
            INSTANCE = new UserDataManager(remoteData, localData);
        }
        return INSTANCE;
    }

    private UserDataManager(@NonNull RemoteData remoteData, @NonNull LocalData localData) {
        mRemoteData = remoteData;
        mLocalData = localData;
    }

    /**
     * 安盛登录接口
     */
    public Observable<LoginResponse> loadLoginPost(String msg) {
        return mRemoteData.loadLoginPost(msg);
    }

    /**
     * 8.用户注册修改接口
     */
    public Observable<BaseResponse> register(RegisterDTO registerDTO) {
        return mRemoteData.register(registerDTO);
    }

    /**
     * urlCode 构造器
     *
     * @param urlCode cip.cfc.v001.01
     */
    public RequestHeadDTO getRequestHeadDTO(String urlCode) {
        RequestHeadDTO dto = mLocalData.getRequestHeadDTO();
        dto.setTransServiceCode(urlCode);
        return dto;
    }

    public Observable<BankResponse> bank_V004_01(String msg) {
        return mRemoteData.bank_V004_01(msg);
    }

    /**
     * 设备保存device id
     */
    public void savePhoneDeviceId() {
        mLocalData.savePhoneDeviceId();
    }

    /**
     * 获取设备device id
     */
    public String getPhoneDeviceId() {
        String deviceId = mLocalData.getPhoneDeviceId();
        if (TextUtils.isEmpty(deviceId)) deviceId = getPushId();
        return deviceId;
    }

    /**
     * 获取推送 id
     */
    public String getPushId() {
        String pushId = mLocalData.getPushId();
        if (TextUtils.isEmpty(pushId)) pushId = UserRouter.getPushId();
        if (TextUtils.isEmpty(pushId)) pushId = getUserID(false);
        if (TextUtils.isEmpty(pushId)) pushId = "1234567890";
        LogUtils.i("PushId=============:" + pushId);
        return pushId;
    }

    public void savePushId(String pushId) {
        LogUtils.i("PushId=============:" + pushId);
        mLocalData.setPushId(pushId);
    }

    /**
     * 获取用户 id
     */
    public String getRASUserID() {
        return RSAUtils.strByEncryption(getUserID(), true);
    }

    /**
     * 统一下 这里获取数据
     */
    public String getUserID() {
        String userId = getUserID(true);
        LogUtils.i("userId=============:" + userId);
        return userId;
    }

    public String getUserID(boolean isNeedLogin) {
        String userId = mLocalData.getUserId();
        if (!TextUtils.isEmpty(userId)) return userId;

        if (isNeedLogin) isUserLogin();
        return mLocalData.getUserId();
    }

    /**
     * 用户是否登录
     * filenum	是	string	档案号 加密
     * ctfnum	是	string	证件号码 加密
     * usrid	是	string	用户ID
     * phoenum	是	string	手机号 加密
     * recdphoe	是	string	推荐人手机号 加密
     */
    public boolean isUserLogin() {
        boolean isLogin = UserMemoryData.getInstance().isLogin();
        if (isLogin) return true;
        //false
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp == null) {
            //登录页面启动
            UserRouter.gotoLoginActivity(mLocalData.getWeakReference());
            return false;
        }
        initLoginBean(responseFromSp);
        return true;
    }

    public boolean isLogin() {
        boolean isLogin = UserMemoryData.getInstance().isLogin();
        if (isLogin) return true;
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp == null) {
            return false;
        }
        initLoginBean(responseFromSp);
        return true;
    }

    /**
     * 包含解析过程
     */
    private void initLoginBean(LoginResponse responseFromSp) {
        if (responseFromSp == null) return;
        LoginBean loginBean = responseFromSp.getRspInfo();
        if (loginBean == null) return;
        UserMemoryData.getInstance().setLogin(true);
        UserMemoryData.getInstance().setUsrid(loginBean.getUsrid());
        UserMemoryData.getInstance().setGetdate(loginBean.getGetdate());
        UserMemoryData.getInstance().setNickname(loginBean.getNickname());
        UserMemoryData.getInstance().setPortrait(loginBean.getPortrait());

        UserMemoryData.getInstance().setFilenum(Des3.decode(loginBean.getFilenum()));
        UserMemoryData.getInstance().setPhoenum(Des3.decode(loginBean.getPhoenum()));
        UserMemoryData.getInstance().setCtfnum(Des3.decode(loginBean.getCtfnum()));
        UserMemoryData.getInstance().setRecdphoe(Des3.decode(loginBean.getRecdphoe()));
    }

    protected void saveUserMemoryAll(LoginResponse responseFromSp) {
        saveLoginResponseToSp(responseFromSp);
        initLoginBean(responseFromSp);
    }

    /**
     * 登录后保存数据 本地保存
     */
    public void saveLoginResponseToSp(LoginResponse loginResponse) {
        mLocalData.saveLoginResponseToSp(loginResponse);
    }

    /**
     * 获取保存的用户数据
     */
    public LoginResponse getLoginResponseFromSp() {
        String response = mLocalData.getLoginResponseNewOrOld();
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
            return gson.fromJson(response, LoginResponse.class);
        } else {
            return null;
        }
    }

    /**
     * 注册页面保存用户信息
     */
    public void saveLoginBean(String userString) {
        mLocalData.saveLoginBean(userString);
        saveUserMemory();
    }

    /**
     * 保存驾挡编号
     */
    public void saveUserFilenum(String filenum) {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp != null) {
            LoginBean loginBean = responseFromSp.getRspInfo();
            loginBean.setFilenum(filenum);
        }
        saveUserMemoryAll(responseFromSp);
    }

    /**
     * 保存手机号码
     */
    public void saveUserPhoenum(String phoenum) {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp != null) {
            LoginBean loginBean = responseFromSp.getRspInfo();
            loginBean.setPhoenum(phoenum);
        }
        saveUserMemoryAll(responseFromSp);
    }

    /**
     * 保存头像
     */
    public void saveUserPortrait(String portrait) {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp != null) {
            LoginBean loginBean = responseFromSp.getRspInfo();
            loginBean.setPortrait(portrait);
        }
        saveUserMemoryAll(responseFromSp);
    }

    /**
     * 保存注册时间
     */
    public void saveUserGetdate(String getdate) {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp != null) {
            LoginBean loginBean = responseFromSp.getRspInfo();
            loginBean.setGetdate(getdate);
        }
        saveUserMemoryAll(responseFromSp);
    }

    /**
     * 保存匿名
     */
    public void saveUserNickname(String nickname) {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp != null) {
            LoginBean loginBean = responseFromSp.getRspInfo();
            loginBean.setNickname(nickname);
        }
        saveUserMemoryAll(responseFromSp);
    }

    public String getUserPhoenum() {
        String phoenum = mLocalData.getPhoenum();
        if (!TextUtils.isEmpty(phoenum)) return phoenum;
        //false
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp == null) return "";
        initLoginBean(responseFromSp);
        return mLocalData.getPhoenum();
    }

    public String getUserFilenum() {
        String filenum = mLocalData.getFilenum();
        if (!TextUtils.isEmpty(filenum)) return filenum;

        if (saveUserMemory()) return "";
        return mLocalData.getFilenum();
    }

    protected boolean saveUserMemory() {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp == null) return true;
        initLoginBean(responseFromSp);
        return false;
    }

    public String getUserGetdate() {
        String getdate = mLocalData.getGetdate();
        if (!TextUtils.isEmpty(getdate)) return getdate;
        if (saveUserMemory()) return "";
        return mLocalData.getGetdate();
    }

    public String getUserPortrait() {
        String getdate = mLocalData.getPortrait();
        if (!TextUtils.isEmpty(getdate)) return getdate;

        if (saveUserMemory()) return "";
        return mLocalData.getPortrait();
    }

    public String getUserNickname() {
        String getdate = mLocalData.getNickname();
        if (!TextUtils.isEmpty(getdate)) return getdate;

        if (saveUserMemory()) return "";
        return mLocalData.getNickname();
    }

    /**
     * 退出登录
     */
    public void getCleanUser() {
        mLocalData.getCleanUser();
    }
}
