package com.zantong.mobilecttx.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengyingbing on 16/6/8.
 */
public class StringUtils {

    public static String getRandomStr() {

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
     * 获取两位小数的金钱
     *
     * @param price
     * @return
     */
    public static String getPriceDouble(double price) {
        DecimalFormat df = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return df.format(price);//format 返回的是字符串
    }

    /**
     * 除以100 获取两位小数的金钱
     *
     * @param price
     * @return
     */
    public static String getPriceString(String price) {
        String money = "0.00";
        if (TextUtils.isEmpty(price)) return money;
        double amount = 0.00;
        try {
            amount = Double.parseDouble(price) / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        money = decimalFormat.format(amount);//format 返回的是字符串
        return money;
    }

    /**
     * 除以100 获取两位小数的金钱
     *
     * @param price
     * @return
     */
    public static String getPriceDoubleFormat(double price) {
        double amount = 0.00;
        try {
            amount = price / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return df.format(amount);//format 返回的是字符串
    }

    public static String getEncrypPhone(String phone) {
        String str = phone.substring(0, phone.length() - (phone.substring(3)).length()) + "****" + phone.substring(7);
        return str;
    }

    public static String getTimeToStr() throws ParseException {
        long day = 1488253689;
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = myFormatter.parse(DateUtils.getDateFormat());
            Date mydate = myFormatter.parse("1970-01-01");
            day = (date.getTime() - mydate.getTime()) / 1000;
        } catch (Exception e) {

        }
        return String.valueOf(day);
    }
}
