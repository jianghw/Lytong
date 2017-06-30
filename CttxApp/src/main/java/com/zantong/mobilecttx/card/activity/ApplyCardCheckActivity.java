package com.zantong.mobilecttx.card.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.HandleCTCardApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.dto.BidCTCardDTO;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.UiHelpers;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ToastUtils;

public class ApplyCardCheckActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements HandleCTCardApiClient.ResultInterface {

    @Bind(R.id.bid_ct_card_instruction)   //驾驶证问号
            ImageView mInstruction;
    @Bind(R.id.bid_ct_card_drive_num)    //驾驶证编号
            EditText mDriveNum;
    @Bind(R.id.bid_ct_card_name)     //姓名
            EditText mName;
    @Bind(R.id.bid_ct_card_identity_card)   //身份证
            EditText mIdentityCard;
    @Bind(R.id.bid_ct_card_img)    //长期有效img
            ImageView mImg;
    @Bind(R.id.bid_ct_card_img_layout)   //长期有效layout
            RelativeLayout mImgLayout;
    @Bind(R.id.bid_ct_card_year_month_day)   //年月日
            TextView mYearMonthDay;
    @Bind(R.id.bid_ct_card_year_month_day_layout)   //年月日layout
            LinearLayout mYearMonthDayLayout;
    @Bind(R.id.bid_ct_card_phone)   //手机号
            TextView mPhone;
    @Bind(R.id.bid_ct_cars_explain)//说明
            TextView mExplain;
    //下一步
    @Bind(R.id.bid_ct_card_next)
    Button mNext;

    private boolean isLongtermLimited = false; //是否长期有效
    private DatePicker picker;
    private String fileNum;
    private String name;
    private String licenseno;
    private String phone;
    private String youxiaoDate;  //有效时间

    @Override
    public void initView() {
        UiHelpers.setTextViewIcon(this, getEnsureView(), R.mipmap.icon_add_car_camera,
                R.dimen.ds_60,
                R.dimen.ds_48, UiHelpers.DRAWABLE_RIGHT);
    }

    @Override
    public void initData() {
        setTitleText("申办畅通卡");
        phone = PublicData.getInstance().mLoginInfoBean.getPhoenum();
        mPhone.setText(phone);
    }

    @OnClick({R.id.bid_ct_card_instruction, R.id.bid_ct_card_img_layout,
            R.id.bid_ct_card_year_month_day_layout, R.id.bid_ct_cars_explain,
            R.id.bid_ct_card_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bid_ct_card_instruction:  //驾驶证问号
                new DialogMgr(ApplyCardCheckActivity.this, R.mipmap.code_query_notice_iamge);
                break;
            case R.id.bid_ct_card_img_layout:  //长期有效layout
                longtermLimited();
                break;
            case R.id.bid_ct_card_year_month_day_layout:  //年月日layout
                chooseDate();
                break;
            case R.id.bid_ct_cars_explain:   //说明
                PublicData.getInstance().webviewUrl = "file:///android_asset/bindcard_agreement.html";
                PublicData.getInstance().webviewTitle = "隐私声明";
                Act.getInstance().gotoIntent(this, BrowserActivity.class);
                break;
            case R.id.bid_ct_card_next:
                valueCheck();
//                startActivity(ApplyCardFristStepActvity.getIntent(this, "111111111111", "周杰",
//                        "321321199303115493", "2099-10-31"));
                break;
        }
    }

    /**
     * 校验值
     */
    private void valueCheck() {
        fileNum = mDriveNum.getText().toString();
        name = mName.getText().toString();
        licenseno = mIdentityCard.getText().toString();
        if (TextUtils.isEmpty(fileNum)) {
            ToastUtils.showShort(this, "驾驶证档案编号不可为空");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(this, "姓名不可为空");
            return;
        }
        if (TextUtils.isEmpty(licenseno)) {
            ToastUtils.showShort(this, "身份证号码不可为空");
            return;
        }
        if (fileNum.length() < 12) {
            ToastUtils.showShort(this, "驾驶证档案编号不正确");
            return;
        }
        if (!ValidateUtils.isIdCard(licenseno)) {
            ToastUtils.showShort(this, "身份证号码不正确");
            return;
        }
        if (TextUtils.isEmpty(youxiaoDate)) {
            ToastUtils.showShort(this, "请选择有效期");
            return;
        }
        PublicData.getInstance().filenum = fileNum;
        checkCtkDate();
    }

    /**
     * 客户办卡记录校验
     */
    private void checkBidCard() {
        BidCTCardDTO bidCTCardDTO = new BidCTCardDTO();
        bidCTCardDTO.setCtftp("0");
        bidCTCardDTO.setUsrname(name);
        bidCTCardDTO.setUsrid(PublicData.getInstance().mLoginInfoBean.getUsrid());
        bidCTCardDTO.setCtfnum(RSAUtils.strByEncryption(this, licenseno, true));
        bidCTCardDTO.setFilenum(RSAUtils.strByEncryption(this, fileNum, true));
        bidCTCardDTO.setPhoenum(RSAUtils.strByEncryption(this, phone, true));
        HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u006.01", bidCTCardDTO, this);
    }

    /**
     * 申办畅通卡时间校验接口
     */
    private void checkCtkDate() {
        showDialogLoading();
        CheckCtkDTO checkCtkDTO = new CheckCtkDTO();
        checkCtkDTO.setApplyCode(fileNum);
        checkCtkDTO.setApplyInterface("banka");
        checkCtkDTO.setFlag("0");
        CarApiClient.checkCtk(this, checkCtkDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                if (result.getResponseCode() == 2000 || result.getResponseCode() == 2001) {
                    checkBidCard();
                } else {
                    hideDialogLoading();
                    ToastUtils.showShort(ApplyCardCheckActivity.this, "七天之内不能重复办卡");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                ToastUtils.showShort(ApplyCardCheckActivity.this, "请求失败，请再次点击");
            }
        });
    }

    @Override
    public void resultSuccess(Result result) {
        if (result.getSYS_HEAD().getReturnCode().equals("1")) {
            startActivity(ApplyCardFristStepActvity.getIntent(this, fileNum, name,
                    licenseno, youxiaoDate));
        } else if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
            startActivity(ApplyCardFourStepActvity.getIntent(this, fileNum, name,
                    licenseno, youxiaoDate));
        }
        hideDialogLoading();
//        ToastUtils.showShort(ApplyCardCheckActivity.this, result.getSYS_HEAD().getReturnMessage());
    }

    @Override
    public void resultError(String msg) {
        hideDialogLoading();
        Toast.makeText(ApplyCardCheckActivity.this, Config.getErrMsg("1"), Toast.LENGTH_SHORT).show();
    }

    /**
     * 选择时间
     */
    private void chooseDate() {
        picker = new DatePicker(ApplyCardCheckActivity.this);
        String temp = mYearMonthDay.getText().toString().trim();
        if (temp.contains("年")) {
            temp = "";
        }
        picker.setRangeEnd(DateUtils.getYear() + 100, DateUtils.getMonth(), DateUtils.getDay());
        picker.setRangeStart(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        try {
            String date = temp;
            if (!"".equals(date)) {
                date = date.replace("-", "");
                picker.setSelectedItem(Integer.valueOf(date.substring(0, 4)), Integer.valueOf(date.substring(4, 6)), Integer.valueOf(date.substring(6, 8)));
            } else {
                picker.setSelectedItem(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                mYearMonthDay.setText(year + "-" + month + "-" + day);
                youxiaoDate = year + "-" + month + "-" + day;
            }
        });
        picker.show();
    }

    /**
     * 长期有效
     */
    private void longtermLimited() {
        isLongtermLimited = isLongtermLimited == false ? true : false;
        if (isLongtermLimited) {
            mYearMonthDay.setText("年/月/日");
            mYearMonthDay.setTextColor(getResources().getColor(R.color.gray_b2));
            mYearMonthDayLayout.setClickable(false);
            mImg.setBackgroundResource(R.mipmap.checkbox_checked);
            youxiaoDate = "2099-10-31";
        } else {
            mYearMonthDay.setTextColor(getResources().getColor(R.color.gray_25));
            mYearMonthDayLayout.setClickable(true);
            mImg.setBackgroundResource(R.mipmap.checkbox_normal);
        }
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;  //请求码，自己定义

    @Override
    protected void baseGoEnsure() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);

        } else {

            //有授权，直接开启摄像头
            Intent intent = new Intent(this, OcrCameraActivity.class);
            intent.putExtra("ocr_resource", 1);
            startActivityForResult(intent, 1205);
        }
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_bid_ct_card;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PublicData.getInstance().filenum = "";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1205 && resultCode == 1206) {
            if (data != null) {
                getJiaZhaoInfo();
            }
        }
    }

    /**
     * ocr
     * 获取驾照信息
     */
    private void getJiaZhaoInfo() {
        showDialogLoading();
        CarApiClient.uploadDriverImg(this, OcrCameraActivity.file, new CallBack<DriverOcrResult>() {
            @Override
            public void onSuccess(DriverOcrResult result) {
                hideDialogLoading();
                if ("OK".equals(result.getStatus()) && result.getContent() != null) {
                    mName.setText(result.getContent().getName());
                    mIdentityCard.setText(result.getContent().getCardNo());
                } else {
                    ToastUtils.toastShort("解析失败，请重试");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
            }
        });
    }
}
