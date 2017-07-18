package com.zantong.mobilecttx.card.activity;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.bean.BindCardResult;
import com.zantong.mobilecttx.card.dto.BindCardDTO;
import com.zantong.mobilecttx.card.dto.BindDrivingDTO;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

/**
 * 绑定畅通卡
 */
public class BindJiaZhaoActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements IBaseView {

    @Bind(R.id.bind_jia_zhao_file_num_img)
    ImageView mFileNumImg;
    @Bind(R.id.bind_jia_zhao_idcard_img)
    ImageView mIdCardImg;
    @Bind(R.id.bind_jia_zhao_phone_img)
    ImageView mPhoneImg;
    @Bind(R.id.bind_jia_zhao_file_camera)
    ImageView mCamera;
    @Bind(R.id.bind_jia_zhao_file_num)
    EditText mFileNum;//档案编号
    @Bind(R.id.bind_jia_zhao_licenseno)
    EditText mLicenseno;
    @Bind(R.id.bind_jia_zhao_phone)
    EditText mPhone;
    @Bind(R.id.bind_jia_zhao_commit)
    Button mDownStep;

    private static final String SHOWCASE_ID = "cttx_bindjiazhaoactivity";
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;//请求码，自己定义
    final BindDrivingDTO params = new BindDrivingDTO();


    @Override
    protected int getContentResId() {
        return R.layout.activity_bind_jia_zhao;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setTitleText("绑定畅通卡");
//        UiHelpers.setTextViewIcon(this, getEnsureView(), R.mipmap.icon_add_car_camera,
//                R.dimen.ds_60,
//                R.dimen.ds_48, UiHelpers.DRAWABLE_RIGHT);
//        if (!SPUtils.getInstance(this).getGuideJiaShiZheng()) {
//            PublicData.getInstance().GUIDE_TYPE = 2;
//            Act.getInstance().gotoIntent(this, GuideActivity.class);
//        }
    }


    @Override
    protected void baseGoEnsure() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1203 && resultCode == 1204) {
            if (data != null) {
                getJiaZhaoInfo();
            } else {
                mLicenseno.setText("数据为空");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.bind_jia_zhao_file_num_img, R.id.bind_jia_zhao_idcard_img, R.id.bind_jia_zhao_phone_img,
            R.id.bind_jia_zhao_file_camera, R.id.bind_jia_zhao_commit, R.id.activity_bind_jia_zhao_agreement,
            R.id.bind_jia_zhao_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_jia_zhao_file_num_img:
                new DialogMgr(BindJiaZhaoActivity.this, R.mipmap.code_query_notice_iamge);
                break;
            case R.id.bind_jia_zhao_idcard_img:
                new DialogMgr(BindJiaZhaoActivity.this, R.mipmap.img_jiazhao_idcard);
                break;
            case R.id.bind_jia_zhao_phone_img:
                ToastUtils.showShort(this, "预留手机号是指在办理银行卡过程中，开通网上银行时需要在柜台提交的银行预留手机号，作为以后确认信息的凭证，能及时接受账户资金变动信息。");
                break;
            case R.id.bind_jia_zhao_file_camera:

                PermissionGen.needPermission(this, 100,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        });
                break;
            case R.id.activity_bind_jia_zhao_agreement:
                PublicData.getInstance().webviewUrl = "file:///android_asset/www/bindcard_agreement.html";
                PublicData.getInstance().webviewTitle = "《用户隐私保密协议》";
                PublicData.getInstance().isCheckLogin = false;
                Act.getInstance().gotoIntent(this, BrowserActivity.class);
                break;
            case R.id.bind_jia_zhao_commit:
                bindChangTongKa();
                break;
            case R.id.bind_jia_zhao_apply:
                Act.getInstance().gotoIntent(this, ApplyCardFirstActivity.class);
                finish();
                break;
        }
    }

    /**
     * 得到控件上的值
     * 提交绑卡信息接口和驾驶证信息接口
     */
    private void bindChangTongKa() {

//        userId;//关联用户ID
//        name;//驾驶证姓名
//        licenseno;//证号
//        sex;//性别
//        nationality;//国籍
//        address;//住址
//        dateOfBirth;//出生日期
//        dateOfFirstIssue;//初次领
//        allowType;//准驾车型
//        validPeriodStart;//有效期
//        validPeriodEnd;//有效期限截
//        fileNum;//档案编号
//        record;//记录
//        memo;//备注
//        params.setAllowType("5");
//        String[] strs = this.getResources().getStringArray(R.array.driving_type);
//        for (int i = 0; i < strs.length; i++) {
//            if (strs[i].equals(mAllowType.getText().toString())) {
//                params.setAllowType(String.valueOf(i));
//            }
//
//        }

        final String fileNum = mFileNum.getText().toString();
        String licenseno = mLicenseno.getText().toString();
        String phone = mPhone.getText().toString();
        if (TextUtils.isEmpty(fileNum)) {
            ToastUtils.showShort(this, "档案编号不可为空");
            return;
        }
        if (TextUtils.isEmpty(licenseno)) {
            ToastUtils.showShort(this, "身份证号码不可为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(this, "手机号码不可为空");
            return;
        }
        if (fileNum.length() != 12) {
            ToastUtils.showShort(this, "驾档编号格式不正确");
            return;
        }
        if (!ValidateUtils.isIdCard(licenseno)) {
            ToastUtils.showShort(this, "身份证号码格式不正确");
            return;
        }
        if (!ValidateUtils.isMobile(phone)) {
            ToastUtils.showShort(this, "手机号码格式不正确");
            return;
        }
        params.setUserId(PublicData.getInstance().userID);
        params.setLicenseno(licenseno);
        params.setFileNum(fileNum);
//        params.setName(mName.getText().toString());
//        params.setDateOfBirth(mBirthDateText.getText().toString());
//        params.setAddress(mAddress.getText().toString());
//        params.setDateOfFirstIssue(dateOfFirstIssue);
//
//        params.setValidPeriodStart(mStartDateText.getText().toString());
        BindCardDTO dto = new BindCardDTO();
        dto.setCtfnum(RSAUtils.strByEncryption(licenseno, true));
        dto.setCtftp("0");
        dto.setFilenum(RSAUtils.strByEncryption(fileNum, true));
//        dto.setGetdate(dateOfFirstIssue);
        dto.setRelatedphone(RSAUtils.strByEncryption(phone, true));
        dto.setUsrid(PublicData.getInstance().userID);
        showDialogLoading();
        UserApiClient.bindCard(this, dto, new CallBack<BindCardResult>() {
            @Override
            public void onSuccess(BindCardResult result) {
                hideDialogLoading();
                if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
                    if (result.getRspInfo().getCardflag() != 0 && result.getRspInfo().getCustcodeflag() == 1 && result.getRspInfo().getMobileflag() == 1) {
                        if ("男".equals(params.getSex())) {
                            params.setSex("0");
                        } else {
                            params.setSex("1");
                        }
                        CarApiClient.commitDriving(BindJiaZhaoActivity.this, params, new CallBack<BaseResult>() {
                            @Override
                            public void onSuccess(BaseResult result) {

                            }
                        });
                        PublicData.getInstance().filenum = fileNum;
                        LoginInfoBean.RspInfoBean user = (LoginInfoBean.RspInfoBean) UserInfoRememberCtrl.readObject();
                        user.setFilenum(fileNum);
//                        user.setGetdate(dateOfFirstIssue);
                        UserInfoRememberCtrl.saveObject(user);
                        PublicData.getInstance().mLoginInfoBean.setFilenum(fileNum);
//                        PublicData.getInstance().mLoginInfoBean.setGetdate(dateOfFirstIssue);
                        Act.getInstance().gotoIntent(BindJiaZhaoActivity.this, BindCardSuccess.class);
                        BindJiaZhaoActivity.this.finish();
                    } else if (result.getRspInfo().getCardflag() == 0) {
                        ToastUtils.showShort(BindJiaZhaoActivity.this, "您还没有畅通卡");
                    } else if (result.getRspInfo().getCustcodeflag() == 0) {
                        ToastUtils.showShort(BindJiaZhaoActivity.this, "您的身份证号与驾照号码不一致");
                    } else if (result.getRspInfo().getMobileflag() == 0) {
                        ToastUtils.showShort(BindJiaZhaoActivity.this, "您的预留手机号码不正确");
                    }
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
            }
        });
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
                    mLicenseno.setText(result.getContent().getCardNo());
                } else {
                    ToastUtils.toastShort("解析失败，请重试");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                hideDialogLoading();
            }
        });
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        //有授权，直接开启摄像头
        Intent intent = new Intent(this, OcrCameraActivity.class);
        intent.putExtra("ocr_resource", 2);
        startActivityForResult(intent, 1203);
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        ToastUtils.showShort(this, "您已关闭摄像头权限");
    }
}
