package cn.qqtheme.framework.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jianghw on 2017/5/31.
 * Description: 用于提交日期或显示日期 工具
 * Update by:
 * Update day:
 */

public class DateTools {
    /**
     * 时间戳格式 转
     *
     * @param stringDate time=1496198289
     * @return 05/12 11:33
     */
    public static String displayFormatDate(String stringDate) {
        if (TextUtils.isEmpty(stringDate)) return "未知时间";
        long longDate = 0;
        try {
            longDate = Long.valueOf(stringDate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (longDate == 0) return "错误时间";
        Date date = new Date(longDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(date);
    }
}
