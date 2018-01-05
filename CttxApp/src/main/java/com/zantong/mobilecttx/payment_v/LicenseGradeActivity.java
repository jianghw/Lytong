package com.zantong.mobilecttx.payment_v;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.dialog.DateDialogFragment;
import com.tzly.ctcyh.router.custom.dialog.IOnDateSetListener;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.DialogMgr;

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

public class LicenseGradeActivity extends AbstractBaseActivity implements View.OnClickListener {

    private EditText mEditArchivesNumber;
    private TextView mTvDate;
    private ImageView mTvImage;
    private Button mBtnCommit;
    private RelativeLayout mLyData;

    private LicenseFileNumDTO fromJson;
    private int position;

    @Override
    protected int initContentView() {
        return R.layout.activity_license_check_grade;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            position = bundle.getInt(MainGlobal.putExtra.license_position_extra, 1);
        }

        String grade = SPUtils.instance().getString(SPUtils.USER_GRADE);
        if (!TextUtils.isEmpty(grade)) {
            fromJson = new Gson().fromJson(grade, LicenseFileNumDTO.class);
        } else {
            fromJson = new LicenseFileNumDTO();
            if (!TextUtils.isEmpty(MainRouter.getUserFilenum()))
                fromJson.setFilenum(MainRouter.getUserFilenum());
            if (!TextUtils.isEmpty(MainRouter.getUserGetdate()))
                fromJson.setStrtdt(MainRouter.getUserGetdate());
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("违章缴费查询");
        assignViews();

        mBtnCommit.setOnClickListener(this);
        mLyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog();
            }
        });
        mTvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void assignViews() {
        mEditArchivesNumber = (EditText) findViewById(R.id.edit_archives_number);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvImage = (ImageView) findViewById(R.id.img_cartype_desc);
        mLyData = (RelativeLayout) findViewById(R.id.lay_date);
        mBtnCommit = (Button) findViewById(R.id.btn_commit);
    }

    private void showDialog() {
        new DialogMgr(this, R.mipmap.code_query_notice_iamge);
    }

    private void showDataDialog() {
        DateDialogFragment dialogFragment = DateDialogFragment.newInstance();
        dialogFragment.setClickListener(new IOnDateSetListener() {
            @Override
            public void onDateSet(
                    DatePicker view, Date date, boolean usable) {

                if (!usable) return;
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                mTvDate.setText(format.format(calendar.getTime()));
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "register_date");
    }

    @Override
    protected void initContentData() {
        if (fromJson != null) {
            if (!TextUtils.isEmpty(fromJson.getFilenum()))
                mEditArchivesNumber.setText(fromJson.getFilenum());
            if (!TextUtils.isEmpty(fromJson.getStrtdt()))
                mTvDate.setText(dateFormat(fromJson.getStrtdt()));
        }
    }

    /**
     * yyyyMMdd-->yyyy-MM-dd
     */
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

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(getArchivesNumber()) || getArchivesNumber().length() != 12) {
            ToastUtils.toastShort("请输入正确12位驾驶证档案编号");
        } else if (TextUtils.isEmpty(getTvDate())) {
            ToastUtils.toastShort("请选择初次领证日期");
        } else {
            MobUtils.getInstance().eventIdByUMeng(8);

            LicenseFileNumDTO dto = new LicenseFileNumDTO();
            dto.setFilenum(getArchivesNumber());
            dto.setStrtdt(localDateFormat(getTvDate()));//真时的时间

            String gson = new Gson().toJson(dto);
            SPUtils.instance().put(SPUtils.USER_GRADE, gson);
            if (position == 2) {
                MainRouter.gotoPaymentActivity(this, gson);
            } else if (position == 1) {
                MainRouter.gotoLicenseDetailActivity(this, gson);
            }
            finish();
        }
    }

    public String getArchivesNumber() {
        return mEditArchivesNumber.getText().toString().trim();
    }

    public String getTvDate() {
        return mTvDate.getText().toString().trim();
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

    public LicenseFileNumDTO initLicenseFileNumDTO() {
        LicenseFileNumDTO dto = new LicenseFileNumDTO();
        dto.setFilenum(getArchivesNumber());
        dto.setStrtdt(localDateFormat(getTvDate()));//真时的时间
        return dto;
    }

    /**
     * 复杂逻辑的 初始时间
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

}