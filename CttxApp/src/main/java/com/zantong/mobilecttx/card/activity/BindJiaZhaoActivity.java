package com.zantong.mobilecttx.card.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.tzly.ctcyh.service.IUserService;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.browser.BrowserHtmlActivity;
import com.zantong.mobilecttx.card.bean.BindCardResult;
import com.zantong.mobilecttx.card.dto.BindCardDTO;
import com.zantong.mobilecttx.card.dto.BindDrivingDTO;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

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

    final BindDrivingDTO params = new BindDrivingDTO();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1203 && resultCode == 1204) {
            if (data != null) {
                getJiaZhaoInfo();
            } else {
                mLicenseno.setText("数据为空");
            }
        }
    }

    @OnClick({R.id.bind_jia_zhao_file_num_img, R.id.bind_jia_zhao_idcard_img, R.id.bind_jia_zhao_phone_img, R.id.tv_toast,
            R.id.bind_jia_zhao_file_camera, R.id.bind_jia_zhao_commit, R.id.activity_bind_jia_zhao_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_jia_zhao_file_num_img:
                new DialogMgr(BindJiaZhaoActivity.this, R.mipmap.code_query_notice_iamge);
                break;
            case R.id.bind_jia_zhao_idcard_img:
                new DialogMgr(BindJiaZhaoActivity.this, R.mipmap.img_jiazhao_idcard);
                break;
            case R.id.bind_jia_zhao_phone_img:
                ToastUtils.toastShort("预留手机号是指在办理银行卡过程中，" +
                        "开通网上银行时需要在柜台提交的银行预留手机号，" +
                        "作为以后确认信息的凭证，能及时接受账户资金变动信息。");
                break;
            case R.id.bind_jia_zhao_file_camera:

                PermissionGen.needPermission(this, 100,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        });
                break;
            case R.id.activity_bind_jia_zhao_agreement://保密隐私条例
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, "《用户隐私保密协议》");
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, "file:///android_asset/www/bindcard_agreement.html");
                Act.getInstance().gotoLoginByIntent(this, BrowserHtmlActivity.class, intent);
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

        String licenseno = mLicenseno.getText().toString().trim();
        final String fileNum = mFileNum.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();

        if (TextUtils.isEmpty(licenseno)) {
            ToastUtils.toastShort("驾驶证号不可为空");
            return;
        }
        if (TextUtils.isEmpty(fileNum)) {
            ToastUtils.toastShort("驾驶证档案编号不可为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.toastShort("手机号码不可为空");
            return;
        }

        if (fileNum.length() != 12) {
            ToastUtils.toastShort("请输入12位正确驾档编号");
            return;
        }

        if (!ValidateUtils.isMobile(phone)) {
            ToastUtils.toastShort("手机号码格式不正确");
            return;
        }

        params.setUserId(LoginData.getInstance().userID);
        params.setLicenseno(licenseno);
        params.setFileNum(fileNum);

        BindCardDTO dto = new BindCardDTO();
        dto.setCtfnum(RSAUtils.strByEncryption(licenseno, true));
        dto.setCtftp("0");
        dto.setFilenum(RSAUtils.strByEncryption(fileNum, true));

        dto.setRelatedphone(RSAUtils.strByEncryption(phone, true));
        dto.setUsrid(LoginData.getInstance().userID);
        showDialogLoading();

        UserApiClient.bindCard(Utils.getContext(), dto, new CallBack<BindCardResult>() {
            @Override
            public void onSuccess(BindCardResult result) {
                hideDialogLoading();

                if (result.getSYS_HEAD().getReturnCode().equals("000000")) {

                    if (result.getRspInfo().getCardflag() != 0
                            && result.getRspInfo().getCustcodeflag() == 1
                            && result.getRspInfo().getMobileflag() == 1) {

                        if ("男".equals(params.getSex())) {
                            params.setSex("0");
                        } else {
                            params.setSex("1");
                        }

                        CarApiClient.commitDriving(Utils.getContext(), params, new CallBack<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse result) {

                            }
                        });
                        //更新驾挡编号
                        ServiceRouter serviceRouter = ServiceRouter.getInstance();
                        if (serviceRouter.getService(IUserService.class.getSimpleName()) != null) {
                            IUserService service = (IUserService) serviceRouter
                                    .getService(IUserService.class.getSimpleName());
                            service.saveUserFilenum(fileNum);
                        } else {
                            //注册机开始工作
                            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
                        }
                        Act.getInstance().gotoIntent(BindJiaZhaoActivity.this, BindCardSuccess.class);
                        BindJiaZhaoActivity.this.finish();

                    } else if (result.getRspInfo().getCardflag() == 0) {
                        ToastUtils.toastShort("您还没有畅通卡");
                    } else if (result.getRspInfo().getCustcodeflag() == 0) {
                        ToastUtils.toastShort("您的身份证号与驾照号码不一致");
                    } else if (result.getRspInfo().getMobileflag() == 0) {
                        ToastUtils.toastShort("您的预留手机号码不正确");
                    }
                } else {
                    ToastUtils.toastShort(result.getSYS_HEAD().getReturnMessage());
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                ToastUtils.toastShort(msg + "&&" + errorCode);
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
        ToastUtils.toastShort("您已关闭摄像头权限");
    }

}
