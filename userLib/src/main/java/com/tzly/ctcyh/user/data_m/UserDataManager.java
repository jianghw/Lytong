package com.tzly.ctcyh.user.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.router.util.Des3;
import com.tzly.ctcyh.router.util.RSAUtils;
import com.tzly.ctcyh.service.MemoryData;
import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginBean;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.global.UserGlobal;

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
    public void initPhoneDeviceId() {
        mLocalData.initPhoneDeviceId();
    }

    /**
     * 获取设备device id
     */
    public String getPhoneDeviceId() {
        return mLocalData.getPhoneDeviceId();
    }

    /**
     * 获取推送 id
     */
    public String getPushDeviceId() {
        return mLocalData.getPushDeviceId();
    }

    /**
     * 获取用户 id
     */
    public String getRASUserID() {
        return RSAUtils.strByEncryption(getUserID(), true);
    }

    public String getUserID() {
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
            UiRouter.getInstance().openUriBundle(mLocalData.getWeakReference(),
                    UserGlobal.Scheme.user_scheme + "://" + UserGlobal.Host.login_host,
                    null);
            return false;
        }

        LoginBean loginBean = responseFromSp.getRspInfo();
        MemoryData.getInstance().setLogin(true);
        MemoryData.getInstance().setUsrid(loginBean.getUsrid());
        MemoryData.getInstance().setFilenum(Des3.decode(loginBean.getFilenum()));
        MemoryData.getInstance().setPhoenum(Des3.decode(loginBean.getPhoenum()));
        MemoryData.getInstance().setCtfnum(Des3.decode(loginBean.getCtfnum()));
        MemoryData.getInstance().setRecdphoe(Des3.decode(loginBean.getRecdphoe()));
        return true;
    }

    /**
     * 获取保存的用户数据
     */
    public LoginResponse getLoginResponseFromSp() {
        String response = mLocalData.getLoginResponseFromSp();
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
}
