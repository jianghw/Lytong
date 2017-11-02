package com.tzly.ctcyh.router.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by jianghw on 2017/6/21.
 * Description:初始化工具类
 * Update by:
 * Update day:
 */

public final class FormatUtils {

    private FormatUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String textForNull(String msg) {
        return TextUtils.isEmpty(msg) ? "-无数据-" : msg;
    }

    /**
     * 价格 0.00
     *
     * @param price
     * @return
     */
    public static String submitPrice(float price) {
        return new DecimalFormat("#0.00").format(floatValue(price));
    }

    public static String submitPrice(String price) {
        Float p = Float.valueOf(price);
        return new DecimalFormat("#0.00").format(floatValue(p));
    }

    /**
     * 向上取值
     */
    public static float floatValue(float price) {
        BigDecimal newPrice = new BigDecimal(price).setScale(2, BigDecimal.ROUND_UP);
        return newPrice.floatValue();
    }

}
