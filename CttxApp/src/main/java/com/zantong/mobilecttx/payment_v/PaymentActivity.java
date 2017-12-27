package com.zantong.mobilecttx.payment_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.zantong.mobilecttx.payment_v.LicenseGradeActivity.KEY_BUNDLE;

/**
 * 缴费记录
 */
public class PaymentActivity extends AbstractBaseActivity {

    private PaymentFragment fragment;
    private LicenseFileNumDTO fileNumDTO;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void rightClickListener() {

    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                fileNumDTO = bundle.getParcelable(KEY_BUNDLE);
            }
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("违章缴费记录");
        titleMore("编辑");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        String beanStrtdt = fileNumDTO.getStrtdt();
        String startDay = removeDateAcross(beanStrtdt);

        LicenseFileNumDTO newBean = new LicenseFileNumDTO();
        newBean.setFilenum(fileNumDTO.getFilenum());
        newBean.setStrtdt(getStartDate(startDay));
        newBean.setEnddt(localDateFormat(getEndDate(startDay)));

        //默认页面显示
        if (fragment == null) {
            fragment = PaymentFragment.newInstance(newBean);
        }
        FragmentUtils.add(fragmentManager, fragment, R.id.lay_base_frame);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fragment != null) fragment = null;
    }

    /**
     * 日期格式标准化
     */
    private String removeDateAcross(String beanStrtdt) {
        if (beanStrtdt.contains("-")) {
            String[] days = beanStrtdt.split("-");
            return days[0] + days[1] + days[2];
        } else if (beanStrtdt.contains("/")) {
            String[] days = beanStrtdt.split("/");
            return days[0] + days[1] + days[2];
        }
        return beanStrtdt;
    }

    /**
     * 复杂逻辑的 初始时间
     *
     * @return
     */
    public String getStartDate(String startDate) {
        //去年的今天时间
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        long todayDate = today.getTime().getTime();
        int todayYear = today.get(Calendar.YEAR);
        //输入时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        String dateString = dateFormat(startDate);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        //输入转为今年日期
        calendar.set(todayYear, month - 1, day);
        long dateTime = calendar.getTime().getTime();
        if (dateTime <= todayDate)
            return localDateFormat(simpleDateFormat.format(calendar.getTime()));

        calendar.add(Calendar.YEAR, -1);
        return localDateFormat(simpleDateFormat.format(calendar.getTime()));
    }

    /**
     * 1、去年的今天时间 lastDate
     * 2、选择时间大于lastDate 则返回当前时间
     * 3、比lastDate小时，就是去年的年份+填写的月、日
     */
    public String getEndDate(String startDate) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        return simpleDateFormat.format(new Date());
    }

    public String localDateFormat(String oldDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        Date date = null;
        try {
            date = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
        return simpleDateFormat.format(date);
    }

    public String dateFormat(String oldDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
        Date date = null;
        try {
            date = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        return simpleDateFormat.format(date);
    }

}