package com.zantong.mobilecttx.card.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.custom.primission.PermissionFail;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.primission.PermissionSuccess;
import com.tzly.ctcyh.router.custom.rea.RSAUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.base.bean.BindDrivingBean;
import com.zantong.mobilecttx.card.bean.BindCardResult;
import com.zantong.mobilecttx.card.dto.BindCardDTO;
import com.zantong.mobilecttx.card.dto.BindDrivingDTO;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.router.MainRouter;
import com.tzly.ctcyh.router.custom.dialog.DialogMgr;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.Bind;
import butterknife.OnClick;

import static com.tzly.ctcyh.router.util.ToastUtils.toastShort;
import static com.tzly.ctcyh.router.custom.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 绑定畅通卡
 */
public class BindJiaZhaoActivity extends BaseJxActivity {

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
    @Bind(R.id.tv_toast)
    TextView mTvToast;

    BindDrivingDTO bindDrivingDTO = new BindDrivingDTO();

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {}

    @Override
    protected int getContentResId() {
        return R.layout.activity_bind_jia_zhao;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("绑定畅通卡");
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void DestroyViewAndThing() {}

    @OnClick({R.id.bind_jia_zhao_file_num_img, R.id.bind_jia_zhao_idcard_img,
            R.id.bind_jia_zhao_phone_img, R.id.tv_toast, R.id.bind_jia_zhao_file_camera,
            R.id.bind_jia_zhao_commit, R.id.activity_bind_jia_zhao_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_jia_zhao_file_num_img:
                new DialogMgr(this, R.mipmap.ic_mark_driving_license);
                break;
            case R.id.bind_jia_zhao_idcard_img:
                new DialogMgr(this, R.mipmap.ic_mark_jiazhao_idcard);
                break;
            case R.id.bind_jia_zhao_phone_img:
                toastShort("预留手机号是指在办理银行卡过程中，" +
                        "开通网上银行时需要在柜台提交的银行预留手机号，" +
                        "作为以后确认信息的凭证，能及时接受账户资金变动信息。");
                break;
            case R.id.bind_jia_zhao_file_camera:
                takePhoto();
                break;
            case R.id.activity_bind_jia_zhao_agreement://保密隐私条例

                MainRouter.gotoWebHtmlActivity(this, "《用户隐私保密协议》", "file:///android_asset/www/bindcard_agreement.html");
                break;
            case R.id.bind_jia_zhao_commit://提交信息
                bindChangTongKa();
                break;
            case R.id.tv_toast://信息
                ToastUtils.toastLong("畅通卡开卡半年内如无消费，会被自动冻结 请去银行柜面办理解冻后才能正常绑卡");
                break;
            default:
                break;
        }
    }

    /**
     * 得到控件上的值
     * 提交绑卡信息接口和驾驶证信息接口
     */
    private void bindChangTongKa() {
        String licenseno = getLicenseno();
        String fileNum = getFileNum();
        String phone = getUserPhone();

        if (TextUtils.isEmpty(licenseno)) {
            toastShort("驾驶证号不可为空");
            return;
        }
        if (TextUtils.isEmpty(fileNum)) {
            toastShort("驾驶证档案编号不可为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            toastShort("手机号码不可为空");
            return;
        }

        if (!ValidateUtils.isMobile(phone)) {
            toastShort("手机号码格式不正确");
            return;
        }
        BindCardDTO cardDTO = new BindCardDTO();
        cardDTO.setCtfnum(RSAUtils.strByEncryption(licenseno, true));
        cardDTO.setCtftp("0");
        cardDTO.setFilenum(RSAUtils.strByEncryption(fileNum, true));
        cardDTO.setRelatedphone(RSAUtils.strByEncryption(phone, true));
        cardDTO.setUsrid(MainRouter.getUserID());
        showDialogLoading();

        UserApiClient.bindCard(Utils.getContext(), cardDTO, new CallBack<BindCardResult>() {
            @Override
            public void onSuccess(BindCardResult result) {
                hideDialogLoading();

                if (result.getSYS_HEAD().getReturnCode().equals("000000")) {

                    if (result.getRspInfo().getCardflag() != 0
                            && result.getRspInfo().getCustcodeflag() == 1
                            && result.getRspInfo().getMobileflag() == 1) {
                        succeedData();
                    } else if (result.getRspInfo().getCardflag() == 0) {
                        toastShort("您还没有畅通卡");
                    } else if (result.getRspInfo().getCustcodeflag() == 0) {
                        toastShort("您的身份证号与驾照号码不一致");
                    } else if (result.getRspInfo().getMobileflag() == 0) {
                        toastShort("您的预留手机号码不正确");
                    }
                } else {
                    toastShort(result.getSYS_HEAD().getReturnMessage());
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                toastShort(msg + "&&" + errorCode);
                hideDialogLoading();
            }
        });
    }

    @NonNull
    private String getUserPhone() {return mPhone.getText().toString().trim();}

    private String getLicenseno() {
        return mLicenseno.getText().toString().trim();
    }

    @NonNull
    private String getFileNum() {return mFileNum.getText().toString().trim();}

    private void succeedData() {
        bindDrivingDTO.setUserId(MainRouter.getUserID());
        bindDrivingDTO.setLicenseno(getLicenseno());
        bindDrivingDTO.setFileNum(getFileNum());

        CarApiClient.commitDriving(Utils.getContext(), bindDrivingDTO, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {}
        });
        //更新驾挡编号
        MainRouter.saveUserFilenum(getFileNum());

        Act.getInstance().gotoIntent(this, BindCardSuccess.class);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == 200 && data != null) {
            String drivingDTO = data.getStringExtra("bankcardinfo");
            BindDrivingBean drivingBean = new Gson().fromJson(drivingDTO, BindDrivingBean.class);

            mLicenseno.setText(drivingBean.getNum());

            bindDrivingDTO.setName(drivingBean.getName());
            bindDrivingDTO.setLicenseno(drivingBean.getNum());
            bindDrivingDTO.setSex(drivingBean.getSex().contains("男") ? "0" : "1");
            bindDrivingDTO.setDateOfBirth(drivingBean.getBirt());
            bindDrivingDTO.setAddress(drivingBean.getAddr());
            bindDrivingDTO.setDateOfFirstIssue(drivingBean.getIssue());
            bindDrivingDTO.setValidPeriodEnd(drivingBean.getValidPeriod());
            bindDrivingDTO.setNationality(drivingBean.getNation().contains("中国") ? "1" : "2");
            bindDrivingDTO.setAllowType(drivingBean.getDrivingType());
            bindDrivingDTO.setValidPeriodStart(drivingBean.getRegisterDate());
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
                    mLicenseno.setText(result.getContent().getCardNo());
                } else {
                    toastShort("解析失败，请重试");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                hideDialogLoading();
            }
        });
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
        } else {
            goToCamera();
        }
    }

    /**
     * 驾驶证
     */
    private void goToCamera() {
        MainRouter.gotoDrivingCameraActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 拍照前权限调用
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        goToCamera();
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        toastShort("您已关闭摄像头权限,请设置中打开");
    }

}
