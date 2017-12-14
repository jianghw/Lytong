package com.tzly.ctcyh.user.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;
import com.tzly.ctcyh.user.bean.response.LoginBean;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.bean.response.VCodeResponse;
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
     * urlCode 构造器
     *
     * @param urlCode cip.cfc.v001.01
     */
    public RequestHeadDTO requestHeadDTO(String urlCode) {
        RequestHeadDTO dto = mLocalData.getRequestHeadDTO();
        dto.setTransServiceCode(urlCode);
        return dto;
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
        LogUtils.i("DeviceId=============:" + deviceId);
        return deviceId;
    }

    /**
     * 获取推送 id
     */
    public String getPushId() {
        String pushId = mLocalData.getPushId();
        if (TextUtils.isEmpty(pushId)) pushId = UserRouter.getPushId();
        LogUtils.i("PushId=============:" + pushId);
        return pushId;
    }

    public void savePushId(String pushId) {
        mLocalData.setPushId(pushId);
        LogUtils.i("PushId=============:" + pushId);
    }

    /**
     * 获取用户 id
     */
    public String getRASUserID() {
        return getRASByStr(getUserID());
    }

    /**
     * 这段加密封装
     */
    public String getRASByStr(String string) {
        return mLocalData.getRASByStr(string);
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
        String userId = mLocalData.getLoginBean().getUsrid();
        if (!TextUtils.isEmpty(userId)) return userId;

        if (isNeedLogin) isUserLogin();
        return mLocalData.getLoginBean().getUsrid();
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
        if (mLocalData.isLogin()) return true;
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
        if (mLocalData.isLogin()) return true;
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

        loginBean.setFilenum(Des3.decode(loginBean.getFilenum()));
        loginBean.setPhoenum(Des3.decode(loginBean.getPhoenum()));
        loginBean.setCtfnum(Des3.decode(loginBean.getCtfnum()));
        loginBean.setRecdphoe(Des3.decode(loginBean.getRecdphoe()));

        mLocalData.setLoginBean(loginBean);
        mLocalData.setLogin(true);
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
            loginBean.setFilenum(Des3.encode(filenum));
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
            loginBean.setPhoenum(Des3.encode(phoenum));
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
        String phoenum = mLocalData.getLoginBean().getPhoenum();
        if (!TextUtils.isEmpty(phoenum)) return phoenum;
        //false
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp == null) return "";
        initLoginBean(responseFromSp);
        return mLocalData.getLoginBean().getPhoenum();
    }

    public String getUserFilenum() {
        String filenum = mLocalData.getLoginBean().getFilenum();
        if (!TextUtils.isEmpty(filenum)) return filenum;

        if (saveUserMemory()) return "";
        return mLocalData.getLoginBean().getFilenum();
    }

    public String getUserGetdate() {
        String getdate = mLocalData.getLoginBean().getGetdate();
        if (!TextUtils.isEmpty(getdate)) return getdate;
        if (saveUserMemory()) return "";
        return mLocalData.getLoginBean().getGetdate();
    }

    public String getUserPortrait() {
        String getdate = mLocalData.getLoginBean().getPortrait();
        if (!TextUtils.isEmpty(getdate)) return getdate;

        if (saveUserMemory()) return "";
        return mLocalData.getLoginBean().getPortrait();
    }

    public String getUserNickname() {
        String getdate = mLocalData.getLoginBean().getNickname();
        if (!TextUtils.isEmpty(getdate)) return getdate;

        if (saveUserMemory()) return "";
        return mLocalData.getLoginBean().getNickname();
    }

    /**
     * 内存数据为空时查询缓存数据
     */
    protected boolean saveUserMemory() {
        LoginResponse responseFromSp = getLoginResponseFromSp();
        if (responseFromSp == null) return true;
        initLoginBean(responseFromSp);
        return false;
    }

    /**
     * 退出登录
     */
    public void getCleanUser() {
        mLocalData.getCleanUser();
    }

    /**
     * 安盛登录接口
     */
    public Observable<LoginResponse> bank_u011_01(String msg) {
        return mRemoteData.bank_u011_01(msg);
    }

    /**
     * 8.用户注册修改接口
     */
    public Observable<BaseResponse> register(RegisterDTO registerDTO) {
        return mRemoteData.register(registerDTO);
    }

    public Observable<BankResponse> bank_v004_01(String msg) {
        return mRemoteData.bank_v004_01(msg);
    }

    /**
     * 获取验证码
     */
    public Observable<VCodeResponse> bank_u015_01(String msg) {
        return mRemoteData.bank_u015_01(msg);
    }

    /**
     * 发送验证码
     */
    public Observable<BankResponse> bank_p002_01(String msg) {
        return mRemoteData.bank_p002_01(msg);
    }

    /**
     * 注册
     */
    public Observable<LoginResponse> bank_u001_01(String msg) {
        return mRemoteData.bank_u001_01(msg);
    }

    /**
     * 修改密码
     */
    public Observable<LoginResponse> bank_u013_01(String msg) {
        return mRemoteData.bank_u001_01(msg);
    }
}
