package com.zantong.mobilecttx.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tzly.ctcyh.router.util.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by zhengyingbing on 16/6/8.
 */
public class DateUtils {

    public static final String START_TIME = "20170417000001";//20170417000001

    public static String getDate() {
//        SimpleDateFormat yearDate = new SimpleDateFormat("yyyymmdd");
//        String str = yearDate.format(new Date());
        return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    }

    public static String getTime() {
        SimpleDateFormat timeDate = new SimpleDateFormat("hhmmss");
        String str = timeDate.format(new Date());
        return str;
    }

    public static String getYearMonthDay() {
        Calendar a = Calendar.getInstance();
        int y = a.get(Calendar.YEAR);
        int m = a.get(Calendar.MONTH) + 1;
        int d = a.get(Calendar.DATE);
        if (m < 10) {
            return y + "0" + m + d;
        } else {
            return "" + y + m + d;
        }
    }

    public static int getYear() {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.DATE);
    }

    /**
     * 获取开始时间  20160607
     *
     * @author Sandy
     * create at / s
     */
    public static String getStartDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        long tempTm = (currentTm - time);
        LogUtils.i("currentTm" + currentTm);
        LogUtils.i("time" + time);
        LogUtils.i("tempTm" + tempTm);
        LogUtils.i("startDate:" + format.format(tempTm));
        return format.format(tempTm);
    }

    /**
     * 获取结束时间 格式:20160606
     *
     * @author Sandy
     * create at 16/6/8 下午5:00
     */
    public static String getEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        currentTm = System.currentTimeMillis();
        LogUtils.i("endDate:" + format.format(currentTm));
        return format.format(currentTm);
    }

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String getStringByFormat2(String strDate, String format)
            throws Exception {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = mSimpleDateFormat.parse(strDate);
        SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
        return mSimpleDateFormat2.format(date);
    }

    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format)
            throws Exception {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        return mSimpleDateFormat.format(date);
    }

    static long currentTm = 0L;


    /**
     * 获取时间  20160607
     *
     * @author Sandy
     * create at / s
     */
    public static String getFormatTime() {
        SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = timeDateFormat.format(new Date());
        return str;
    }

    public static String getDateFormat() {
//        String str = yearDate.format(new Date());
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }


    /**
     * 根据URL和格式化类型获取时间
     *
     * @return
     */
    public static boolean isCanSign() {
        try {

            URL url = new URL("http://www.bjtime.cn");// 获取url对象
            URLConnection uc = url.openConnection();// 获取生成连接对象
            uc.connect();// 发出连接请求
            long currentTm = uc.getDate();// 读取网站当前时间

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            //报名结束时间
            long endTm = sdf.parse(START_TIME).getTime();//毫秒
            LogUtils.i("当前时间：" + currentTm + "，报名截止时间：" + endTm);
            if (currentTm > endTm) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据URL和格式化类型获取时间
     *
     * @return
     */
    public static long getWebTm() throws IOException {
        long time = -1;
//            URL url = new URL("http://www.baidu.com");// 获取url对象
//            URLConnection uc = url.openConnection();// 获取生成连接对象
//            uc.connect();// 发出连接请求
//            LogUtils.i("当前网络时间："+uc.getDate());
        // 取得资源对象
        URL url = new URL("http://www.baidu.com");
        // 生成连接对象
        URLConnection uc = url.openConnection();
        uc.setConnectTimeout(1000);
        // 发出连接
        uc.connect();
        LogUtils.i("当前网络时间：" + uc.getDate());
        time = uc.getDate();
        //获取服务器时间
        return time;
    }

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            // TODO
            // UI界面的更新等相关操作
        }
    };
}
