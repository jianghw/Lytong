package com.tzly.ctcyh.user.data_m;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.custom.rea.RSAUtils;
import com.tzly.ctcyh.router.util.AppUtils;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.response.LoginBean;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by jianghw on 2017/4/26.
 * 本地数据处理
 */

public class LocalData implements ILocalSource {
    @Nullable
    private static LocalData INSTANCE = null;
    private final WeakReference<Context> weakReference;

    /**
     * 登录用户信息
     */
    private boolean isLogin = false;
    private LoginBean mLoginBean;

    /**
     * 懒汉式，线程不安全
     */
    public static LocalData getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalData(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private LocalData(Context context) {
        weakReference = new WeakReference<>(context);

        if (BuildConfig.isDeta) {
            isLogin = true;
            mLoginBean = new LoginBean();
            int positon = new Random().nextInt(4);
            if (positon == 0) {
                mLoginBean.setUsrid("00031813622244433");
                mLoginBean.setPhoenum("13622244433");
            } else if (positon == 1) {
                mLoginBean.setUsrid("00033315700010099");
                mLoginBean.setPhoenum("15700010099");
            } else if (positon == 2) {
                mLoginBean.setUsrid("00033217317111560");
                mLoginBean.setPhoenum("17317111560");
            } else if (positon == 3) {
                mLoginBean.setUsrid("00146618811025031");
                mLoginBean.setPhoenum("18811025031");
            } else if (positon == 4) {
            }
        }
    }

    @Override
    public Context getWeakReference() {
        return weakReference.get();
    }

    /**
     * 安盛请求必须构建体
     */
    @Override
    public RequestHeadDTO getRequestHeadDTO() {
        RequestHeadDTO dto = new RequestHeadDTO();
        dto.setConsumerId("04");
        dto.setRequestDate(getDate());
        dto.setRequestTime(getTime());
        dto.setConsumerSeqNo(getRandomStr());
        dto.setDvcToken(getPhoneDeviceId());
        return dto;
    }

    public String getDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE)
                .format(Calendar.getInstance().getTime());
    }

    public String getTime() {
        SimpleDateFormat timeDate = new SimpleDateFormat("hhmmss", Locale.SIMPLIFIED_CHINESE);
        return timeDate.format(new Date());
    }

    public String getRandomStr() {
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[14];
        StringBuffer sb = new StringBuffer(14);
        for (int i = 0; i < rands.length; i++) {
            int rand = (int) (Math.random() * a.length());
            rands[i] = a.charAt(rand);
            sb.append(rands[i]);
        }
        return sb.toString();
    }

    /**
     * 设备保存device id
     */
    @Override
    public void savePhoneDeviceId() {
        String deviceId = AppUtils.getDeviceId();
        SPUtils.instance().put(SPUtils.USER_DEVICE_ID, deviceId);
    }

    /**
     * 获取设备device id
     */
    @Override
    public String getPhoneDeviceId() {
        String deviceId = SPUtils.instance().getString(SPUtils.USER_DEVICE_ID);
        if (TextUtils.isEmpty(deviceId)) deviceId = AppUtils.getDeviceId();
        return deviceId;
    }

    /**
     * 数据为网络原始 数据 加密的
     */
    @Override
    public void saveLoginResponseToSp(LoginResponse loginResponse) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        String user = gson.toJson(loginResponse);
        SPUtils.instance().put(SPUtils.USER_INFO, user);
    }

    public String getLoginResponseFromSp() {
        return SPUtils.instance().getString(SPUtils.USER_INFO);
    }

    /**
     * 获取保存的用户数据
     */
    @Override
    public String getLoginResponseNewOrOld() {
        String user = getLoginResponseFromSp();
        if (!TextUtils.isEmpty(user)) return user;
        //获取旧数据，走旧业务逻辑
        Object oldLoginBean = SPUtils.instance().readObject(SPUtils.OLD_USER_INFO);
        if (oldLoginBean == null) return null;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        String userString = gson.toJson(oldLoginBean);
        LoginBean loginBean = gson.fromJson(userString, LoginBean.class);

        LoginResponse responseFromSp = new LoginResponse();
        responseFromSp.setRspInfo(loginBean);
        saveLoginResponseToSp(responseFromSp);
        SPUtils.instance().remove(SPUtils.OLD_USER_INFO);
        return getLoginResponseFromSp();
    }

    /**
     * 保存用户信息
     */
    @Override
    public void saveLoginBean(String userString) {
        if (TextUtils.isEmpty(userString)) return;
        getCleanUser();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        LoginBean loginBean = gson.fromJson(userString, LoginBean.class);
        LoginResponse responseFromSp = new LoginResponse();
        responseFromSp.setRspInfo(loginBean);
        saveLoginResponseToSp(responseFromSp);
    }

    /**
     * 获取用户数据bean
     */
    @Override
    public LoginBean getLoginBean() {
        return mLoginBean != null ? mLoginBean : new LoginBean();
    }

    @Override
    public String getRASByStr(String string) {
        return RSAUtils.strByEncryption(string, true);
    }

    public void setLoginBean(LoginBean loginBean) {
        mLoginBean = loginBean;
    }

    /**
     * 设置PushId
     */
    @Override
    public void setPushId(String pushId) {
        SPUtils.instance().put(SPUtils.USER_PUSH_ID, pushId);
    }

    @Override
    public String getPushId() {
        return SPUtils.instance().getString(SPUtils.USER_PUSH_ID);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    /**
     * 数据清理中~
     */
    @Override
    public void getCleanUser() {
        isLogin = false;
        mLoginBean = null;

        SPUtils.instance().clear();
    }

}
