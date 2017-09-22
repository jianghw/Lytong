package com.zantong.mobilecttx.utils.RefreshNewTools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.encryption.AESEncryptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tzly.annual.base.util.ContextUtils;

/**
 * Created by Administrator on 2016/5/25.
 */
public class UserInfoRememberCtrl {

    public static String FILENAME = "CTTXINFO";
    public static String USERINFO = "userinfo";
    public static String USERPD = "userpd";
    public static String USERDEVICE = "userdevice";


    public static void saveObject(Object obj) {
        saveObject(USERINFO, obj);
    }

    public static Object readObject() {
        return readObject(USERINFO);
    }

    public static void clearObject(Context context, String key) {
        // 保存对象
        SharedPreferences.Editor sharedata = context.getSharedPreferences(FILENAME, 0).edit();
        sharedata.putString(key, "");
        sharedata.apply();
    }

    /**
     * desc:保存对象
     *
     * @param key
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     */
    public static void saveObject(String key, Object obj) {
        try {
            if (Tools.isStrEmpty(key)) {
                return;
            }
            // 保存对象
            SharedPreferences.Editor sharedata = ContextUtils.getContext().getSharedPreferences(FILENAME, 0).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            String bytesToHexString = "";
            //将对象序列化写入byte缓存
            if (obj != null) {
                os.writeObject(obj);
                //将序列化的数据转为16进制保存
                bytesToHexString = bytesToHexString(bos.toByteArray());
                //加密 保存该16进制数组
                try {
                    AESEncryptor.encrypt("19900506", bytesToHexString);
                } catch (Exception e) {
                    Log.e("why", e.toString());
                }
            }
            sharedata.putString(key, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:获取保存的Object对象
     */
    public static Object readObject(String key) {
        SharedPreferences sharedPreferences = ContextUtils.getContext().getSharedPreferences(FILENAME, 0);
        if (sharedPreferences.contains(key)) {
            String sharedPreferencesString = sharedPreferences.getString(key, "");
            try {
                AESEncryptor.decrypt("19900506", sharedPreferencesString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (TextUtils.isEmpty(sharedPreferencesString)) {
                return null;
            } else {
                //将16进制的数据转为数组，准备反序列化
                byte[] stringToBytes = StringToBytes(sharedPreferencesString);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stringToBytes);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
                    //返回反序列化得到的对象
                    return inputStream.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //所有异常返回null
        return null;
    }

    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

}
