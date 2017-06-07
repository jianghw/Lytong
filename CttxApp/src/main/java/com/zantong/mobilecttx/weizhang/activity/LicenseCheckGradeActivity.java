package com.zantong.mobilecttx.weizhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.dialog.MyChooseDialog;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jianghw on 2017/5/4.
 * 驾驶证查分
 */

public class LicenseCheckGradeActivity extends MvpBaseActivity implements View.OnClickListener {

    private EditText mEditArchivesNumber;
    private TextView mTvDate;
    private Button mBtnCommit;
    private RelativeLayout mLyData;

    public static final String KEY_BUNDLE = "LicenseFileNumDTO";
    public static final String KEY_BUNDLE_FINISH = "Close_Activity";

    @Override
    protected int getContentResId() {
        return R.layout.activity_license_check_grade;
    }

    @Override
    protected void setTitleView() {
        setTitleText("驾驶证查分");

        assignViews();

        initIntentData();
    }

    /**
     * 初始化填入数据
     */
    private void initIntentData() {
        String fileNum = PublicData.getInstance().filenum;
        mEditArchivesNumber.setText(fileNum);

        String startDate = PublicData.getInstance().getdate;
        String beanStrtdt = removeDateAcross(startDate);
        if (!TextUtils.isEmpty(beanStrtdt)) mTvDate.setText(dateFormat(beanStrtdt));

        LicenseFileNumDTO bean = SPUtils.getInstance(this).getLicenseFileNumDTO();
        if (bean != null) {
            mEditArchivesNumber.setText(bean.getFilenum());
            mTvDate.setText(dateFormat(bean.getStrtdt()));
        }
    }

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

    private void assignViews() {
//        mEditDrivingNumber = (EditText) findViewById(R.id.edit_driving_number);
        mEditArchivesNumber = (EditText) findViewById(R.id.edit_archives_number);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mLyData = (RelativeLayout) findViewById(R.id.rl_date);
        mBtnCommit = (Button) findViewById(R.id.btn_commit);
    }

    @Override
    protected void initMvPresenter() {
        mBtnCommit.setOnClickListener(this);

        mLyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog();
            }
        });
    }

    private void showDataDialog() {
        String[] temps = null;
        String temp = getTvDate();
        if (!TextUtils.isEmpty(temp) && temp.contains("-")) {
            temps = temp.split("-");
        }
        MyChooseDialog dialog = new MyChooseDialog(this, temps, new MyChooseDialog.OnChooseDialogListener() {
            @Override
            public void back(String name) {
                mTvDate.setText(name);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(getArchivesNumber()) || getArchivesNumber().length() != 12) {
            ToastUtils.showShort(getApplicationContext(), "请输入正确12位驾驶证档案编号");
        } else if (TextUtils.isEmpty(getTvDate())) {
            ToastUtils.showShort(getApplicationContext(), "请选择初次领证日期");
        } else {
            LicenseFileNumDTO dto = new LicenseFileNumDTO();
            dto.setFilenum(getArchivesNumber());
            dto.setStrtdt(localDateFormat(getTvDate()));//真时的时间
            SPUtils.getInstance(this).saveLicenseFileNumDTO(dto);

            startIntentToAty();
        }
    }

    private void startIntentToAty() {
        Intent intent = new Intent(this, LicenseDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_BUNDLE, initLicenseFileNumDTO());
        bundle.putBoolean(KEY_BUNDLE_FINISH, false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public LicenseFileNumDTO initLicenseFileNumDTO() {
        LicenseFileNumDTO dto = new LicenseFileNumDTO();
        dto.setFilenum(getArchivesNumber());
        dto.setStrtdt(localDateFormat(getTvDate()));//真时的时间
        return dto;
    }

    public String getArchivesNumber() {
        return mEditArchivesNumber.getText().toString().trim();
    }

    public String getTvDate() {
        return mTvDate.getText().toString().trim();
    }

    /**
     * 复杂逻辑的 初始时间
     *
     * @return
     */
    public String getStartDate() {
        //去年的今天时间
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.add(Calendar.YEAR, -1);
        long lastDate = today.getTime().getTime();
        int yearLast = today.get(Calendar.YEAR);
        //输入时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        String dateString = getTvDate();
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long dateTime = calendar.getTime().getTime();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;

        if (dateTime >= lastDate) return localDateFormat(getTvDate());
        calendar.set(yearLast, month - 1, day);
        return localDateFormat(simpleDateFormat.format(calendar.getTime()));
    }

    public String getEndDate() {
        //去年的今天时间
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.add(Calendar.YEAR, -1);
        long lastDate = today.getTime().getTime();
        int yearLast = today.get(Calendar.YEAR);
        //输入时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        String dateString = getTvDate();
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long dateTime = calendar.getTime().getTime();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        //判断逻辑
        if (dateTime >= lastDate) return simpleDateFormat.format(new Date());
        calendar.set(yearLast, month - 1, day);
        if (calendar.getTime().getTime() >= lastDate) simpleDateFormat.format(new Date());
        calendar.add(Calendar.YEAR, 1);
        return simpleDateFormat.format(calendar.getTime());
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