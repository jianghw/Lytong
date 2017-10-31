package com.tzly.ctcyh.user.data_m;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzly.ctcyh.router.util.AppUtils;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.service.MemoryData;
import com.tzly.ctcyh.user.bean.RequestHeadDTO;
import com.tzly.ctcyh.user.bean.response.LoginBean;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jianghw on 2017/4/26.
 * 本地数据处理
 */

public class LocalData implements ILocalSource {
    @Nullable
    private static LocalData INSTANCE = null;
    private final WeakReference<Context> weakReference;

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
        dto.setDvcToken(getTelDeviceId());
        return dto;
    }

    public String getDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE).format(Calendar.getInstance().getTime());
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

    @SuppressLint("HardwareIds")
    public static String getTelDeviceId() {
        String telDeviceId = "";
        TelephonyManager telephonyManager =
                (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            telDeviceId = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return telDeviceId;
    }

    /**
     * 设备保存device id
     */
    @Override
    public void savePhoneDeviceId() {
        String deviceId = AppUtils.getDeviceId();
        SPUtils.getInstance(SPUtils.FILENAME).put(SPUtils.USER_DEVICE_ID, deviceId);
    }

    /**
     * 获取设备device id
     */
    @Override
    public String getPhoneDeviceId() {
        return SPUtils.getInstance(SPUtils.FILENAME).getString(SPUtils.USER_DEVICE_ID);
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
        SPUtils.getInstance(SPUtils.FILENAME).put(SPUtils.USER_INFO, user);
    }

    /**
     * 获取保存的用户数据
     */
    @Override
    public String getLoginResponseNewOrOld() {
        String user = getLoginResponseFromSp();
        if (!TextUtils.isEmpty(user)) return user;
        //获取旧数据，走旧业务逻辑
        LoginBean oldLoginBean = (LoginBean)
                SPUtils.getInstance(SPUtils.FILENAME).readObject(SPUtils.OLD_USER_INFO);
        if (oldLoginBean == null) return null;
        LoginResponse responseFromSp = new LoginResponse();
        responseFromSp.setRspInfo(oldLoginBean);
        saveLoginResponseToSp(responseFromSp);
        SPUtils.getInstance(SPUtils.FILENAME).remove(SPUtils.OLD_USER_INFO);
        return getLoginResponseFromSp();
    }

    public String getLoginResponseFromSp() {
        return SPUtils.getInstance(SPUtils.FILENAME).getString(SPUtils.USER_INFO);
    }

    @Override
    public Context getWeakReference() {
        return weakReference.get();
    }

    @Override
    public String getUserId() {
        return MemoryData.getInstance().getUsrid();
    }

    /**
     * 设置PushId
     */
    @Override
    public void setPushId(String pushId) {
        SPUtils.getInstance(SPUtils.FILENAME).put(SPUtils.USER_PUSH_ID, pushId);
    }

    @Override
    public String getPushId() {
        return SPUtils.getInstance(SPUtils.FILENAME).getString(SPUtils.USER_PUSH_ID);
    }

}
