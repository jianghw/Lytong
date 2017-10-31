package com.tzly.ctcyh.user.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.tzly.ctcyh.service.MemoryData;
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
     *
     * @param msg
     * @return
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
        if (TextUtils.isEmpty(pushId)) pushId = getUserID();
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
        String userId = mLocalData.getUserId();
        if (!TextUtils.isEmpty(userId)) return userId;

        isUserLogin();
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
        boolean isLogin = MemoryData.getInstance().isLogin();
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

    private void initLoginBean(LoginResponse responseFromSp) {
        if (responseFromSp == null) return;
        LoginBean loginBean = responseFromSp.getRspInfo();
        if (loginBean == null) return;
        MemoryData.getInstance().setLogin(true);
        MemoryData.getInstance().setUsrid(loginBean.getUsrid());
        MemoryData.getInstance().setGetdate(loginBean.getGetdate());
        MemoryData.getInstance().setNickname(loginBean.getNickname());
        MemoryData.getInstance().setPortrait(loginBean.getPortrait());
        MemoryData.getInstance().setFilenum(Des3.decode(loginBean.getFilenum()));
        MemoryData.getInstance().setPhoenum(Des3.decode(loginBean.getPhoenum()));
        MemoryData.getInstance().setCtfnum(Des3.decode(loginBean.getCtfnum()));
        MemoryData.getInstance().setRecdphoe(Des3.decode(loginBean.getRecdphoe()));
    }


    public void initLoginData() {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        initLoginBean(responseFromSp);
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
     * 登录后保存数据 本地保存
     */
    public void saveLoginResponseToSp(LoginResponse loginResponse) {
        mLocalData.saveLoginResponseToSp(loginResponse);
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
        saveLoginResponseToSp(responseFromSp);
        initLoginBean(responseFromSp);
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
        saveLoginResponseToSp(responseFromSp);
        initLoginBean(responseFromSp);
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
        saveLoginResponseToSp(responseFromSp);
        initLoginBean(responseFromSp);
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
        saveLoginResponseToSp(responseFromSp);
        initLoginBean(responseFromSp);
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
        saveLoginResponseToSp(responseFromSp);
        initLoginBean(responseFromSp);
    }
}
