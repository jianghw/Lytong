package com.zantong.mobilecttx.weizhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tzly.annual.base.util.StatusBarUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.presenter.LicenseGradeAtyPresenter;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.weizhang.fragment.LicenseDetailFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.tzly.annual.base.util.ui.FragmentUtils;

import static com.zantong.mobilecttx.weizhang.activity.LicenseCheckGradeActivity.KEY_BUNDLE;

/**
 * Created by jianghw on 2017/5/4.
 * 驾驶证查分
 */

public class LicenseDetailActivity extends BaseJxActivity {
    /**
     * 是否手动关闭当前页面
     */
    private boolean isClose;
    private LicenseFileNumDTO bean;

    /**
     * 状态栏颜色
     */
    protected void initStatusBarColor() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorTvRed_f33), 0);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            bean = bundle.getParcelable(KEY_BUNDLE);
            isClose = bundle.getBoolean(LicenseCheckGradeActivity.KEY_BUNDLE_FINISH, false);
        }
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("本计分周期累计扣分");

        setTvRightVisible("编辑");
        setTitleBackgroundRed();

        if (bean != null) {
            String beanStrtdt = bean.getStrtdt();
            String startDay = removeDateAcross(beanStrtdt);

            LicenseFileNumDTO newBean = new LicenseFileNumDTO();
            newBean.setFilenum(bean.getFilenum());
            newBean.setStrtdt(getStartDate(startDay));
            newBean.setEnddt(localDateFormat(getEndDate(startDay)));

            FragmentManager fragmentManager = getSupportFragmentManager();
            LicenseDetailFragment detailFragment =
                    (LicenseDetailFragment) getSupportFragmentManager().findFragmentById(R.id.lay_base_frame);
            if (detailFragment == null) {
                detailFragment = LicenseDetailFragment.newInstance(newBean);

                FragmentUtils.addFragment(fragmentManager, detailFragment, R.id.lay_base_frame);
            }
            LicenseGradeAtyPresenter mPresenter = new LicenseGradeAtyPresenter(
                    Injection.provideRepository(getApplicationContext()), detailFragment);
        }
    }

    protected void rightClickListener() {
        Intent intent = new Intent(this, LicenseCheckGradeActivity.class);
        startActivity(intent);
        if (isClose) finish();
    }

    @Override
    protected void DestroyViewAndThing() {

    }

    /**
     * 去除分隔符
     *
     * @param beanStrtdt
     * @return
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
        //去年的今天时间
//        Calendar today = Calendar.getInstance();
//        today.setTime(new Date());
//        today.add(Calendar.YEAR, -1);
//        long lastDate = today.getTime().getTime();
//        int yearLast = today.get(Calendar.YEAR);
//        //输入时间
//        String dateString = dateFormat(startDate);
//        Date date = null;
//        try {
//            date = simpleDateFormat.parse(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        long dateTime = calendar.getTime().getTime();
//        int day = calendar.get(Calendar.DATE);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        //判断逻辑
//        if (dateTime >= lastDate) return simpleDateFormat.format(new Date());
//        calendar.set(yearLast, month - 1, day);
//        if (calendar.getTime().getTime() >= lastDate) return simpleDateFormat.format(new Date());
//        calendar.add(Calendar.YEAR, 1);
//        return simpleDateFormat.format(calendar.getTime());
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