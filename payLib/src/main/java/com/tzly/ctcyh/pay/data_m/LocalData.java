package com.tzly.ctcyh.pay.data_m;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.zxing.common.StringUtils;
import com.tzly.ctcyh.java.request.RequestHeadDTO;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.util.AppUtils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;

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

    @Override
    public Context getWeakReference() {
        return weakReference.get();
    }

    @Override
    public String getUserID() {
        return PayRouter.getUserID();
    }

    @Override
    public RequestHeadDTO requestHeadDTO() {
        RequestHeadDTO dto = new RequestHeadDTO();
        dto.setConsumerId("04");
        dto.setRequestDate(getDate());
        dto.setRequestTime(getTime());
        dto.setConsumerSeqNo(getRandomStr());
        dto.setDvcToken(PayRouter.getDeviceId());
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

    @Override
    public String rasByStr(String str) {
        return RSAUtils.strByEncryption(str, true);
    }
}
