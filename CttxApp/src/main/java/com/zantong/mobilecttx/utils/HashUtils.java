package com.zantong.mobilecttx.utils;

import com.zantong.mobilecttx.BuildConfig;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by zhoujie on 2017/2/23.
 */

public class HashUtils {
//    private static String saltRelease = "44536480189231b9c77ed03080d803b1";
    private static String saltRelease = "SCHJxN49XQvaLM3YSasKeMQd090803sa1ds5";

    private static String saltBug = "ND3xN49XQvaLM3YS91KeMQd030803sad2fy5";

    public static String getSignature(HashMap<String, String> params) {
        String strHash = "";
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getValue());
        }
        if (BuildConfig.DEBUG) {
            basestring.append(saltBug);
        } else {
            basestring.append(saltRelease);
        }
        LogUtils.i("hash", "===================>" + basestring.toString());
        // 使用MD5对待签名串求签
//        try {
//            // 生成一个MD5加密计算摘要
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            // 计算md5函数
//            md.update(basestring.toString().getBytes());
//            LogUtils.i("hash", "===================>" + basestring.toString().getBytes());
//            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
//            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
//            strHash = new BigInteger(1, md.digest()).toString(16);
//            LogUtils.i("hash", "===================>" + strHash);
//        } catch (Exception e) {
//            new Exception("MD5加密出错！");
//        }
//        return strHash;

        try {
            MessageDigest  digest = MessageDigest.getInstance("MD5");
            byte[]  bytes = digest.digest(basestring.toString().getBytes());
            StringBuffer sb = new  StringBuffer();
            for(int i = 0;i<bytes.length;i++){
                String s = Integer.toHexString(0xff&bytes[i]);

                if(s.length()==1){
                    sb.append("0"+s);
                }else{
                    sb.append(s);
                }
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("buhuifasheng");
        }
    }
}
