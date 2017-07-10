package com.zantong.mobilecttx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据验证工具类
 *
 * @author Sandy
 *         create at 16/6/14 下午5:18
 */
public class ValidateUtils {
    /**
     * 手否为手机号码
     *
     * @param mobile 手机号
     * @return true 为手机号
     */
    public static boolean isMobile(String mobile) {
        return match("^(1[3|4|5|7|8][0-9])\\d{8}$", mobile);
    }

    /**
     * 是否为身份证号
     *
     * @param idcard
     * @return
     */
    public static boolean isIdCard(String idcard) {
        return match("^(\\d{15}|\\d{18}|\\d{17}(\\d|X|x))$", idcard);
//		return match("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])", idcard);
    }

    /**
     * 密码规则验证
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        return match("^[A-Za-z0-9]{6,20}$", pwd);
    }

    /**
     * 密码规则验证
     * 只能输入字母和数字混合
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd2(String pwd) {
        return match(" /(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{4,23}", pwd);
    }

    /**
     * 是否纯数字
     *
     * @param pwd
     * @return
     */
    public static boolean checkNum(String pwd) {
        return match("[0-9]*", pwd);
    }

    /**
     * 是否纯字母
     *
     * @param pwd
     * @return
     */
    public static boolean checkChar(String pwd) {
        return match("[A-Za-z]*", pwd);
    }

    /**
     * 匹配验证
     *
     * @param regex 正则表达式
     * @param str   需要验证的字符串
     * @return true 匹配 ，false 不匹配
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     * @author syf
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     * @author syf
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}

