package com.zantong.mobilecttx.card.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zantong.mobilecttx.BuildConfig;
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
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.RegexUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 申办畅通卡
 */
public class ApplyCardFirstActivity extends BaseMvpActivity<IBaseView, HelpPresenter>
        implements HandleCTCardApiClient.ResultInterface {

    @Bind(R.id.apply_card_idcard_img)
    ImageView mIdCardImg; //驾驶证档案编号
    @Bind(R.id.apply_card_first_camera)
    ImageView mCameraImg; //驾驶证档案编号
    @Bind(R.id.apply_card_first_filenum)
    EditText mDriverFileNum; //驾驶证档案编号
    @Bind(R.id.apply_card_first_name)
    EditText mName;//姓名
    @Bind(R.id.apply_card_first_idcard)
    EditText mIdCard;//身份证

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_apply_card_first;
    }

    @Override
    public void initView() {
        setTitleText("申办畅通卡");
    }

    @Override
    public void initData() {
        //测试数据下
        if (BuildConfig.DEBUG) {
            mName.setText("遇紫紫");
            mIdCard.setText("301364198811040740");
            mDriverFileNum.setText("310002038631");
        }
    }

    @OnClick({R.id.apply_card_first_img, R.id.apply_card_first_desc, R.id.apply_card_first_commit,
            R.id.apply_card_idcard_img, R.id.apply_card_first_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_card_first_img:  //驾驶证问号
                new DialogMgr(this, R.mipmap.code_query_notice_iamge);
                break;
            case R.id.apply_card_first_desc:   //说明
                PublicData.getInstance().webviewUrl = "file:///android_asset/bindcard_agreement.html";
                PublicData.getInstance().webviewTitle = "隐私声明";
                Act.getInstance().gotoIntent(this, BrowserActivity.class);
                break;
            case R.id.apply_card_first_commit://下一步
                commitValue();
                break;
            case R.id.apply_card_idcard_img://提示框
                new DialogMgr(this, R.mipmap.img_jiazhao_idcard);
                break;
            case R.id.apply_card_first_camera://扫描驾驶证
                takePhoto();
                break;
            default:
                break;
        }
    }

    /**
     * 下一步
     */
    private void commitValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 100,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    });
        } else {
            valueFormValidation();
        }
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent intentOcr = new Intent(this, OcrCameraActivity.class);
        intentOcr.putExtra("ocr_resource", 1);
        startActivityForResult(intentOcr, 1205);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请拍照运行时权限
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        openCamera();
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        valueFormValidation();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        ToastUtils.showShort(getApplicationContext(), "您已关闭内存卡读写权限");
    }

    /**
     * 表单校验值
     */
    private void valueFormValidation() {
        if (TextUtils.isEmpty(getUserName())) {
            ToastUtils.showShort(getApplicationContext(), "姓名不可为空");
            return;
        }
        if (TextUtils.isEmpty(getUserIdCard())) {
            ToastUtils.showShort(getApplicationContext(), "驾档编号不能为空");
            return;
        }
        if (TextUtils.isEmpty(getDriverFileNum())) {
            ToastUtils.showShort(getApplicationContext(), "驾驶证号不能为空");
            return;
        }

        if (getDriverFileNum().length() != 12) {
            ToastUtils.showShort(getApplicationContext(), "请输入12位驾档编号");
            return;
        }
        if (!RegexUtils.isIDCard15(getUserIdCard()) && !RegexUtils.isIDCard18(getUserIdCard())) {
            ToastUtils.showShort(this, "身份证号码不正确");
            return;
        }
//TODO 什么贵
        PublicData.getInstance().filenum = getDriverFileNum();

        if (BuildConfig.DEBUG) {//七天之内不能重复办卡 不用
            showDialogLoading();
            checkApplyCardRecord();
        } else {
            checkCtkDate();
        }
    }

    public String getUserName() {
        return mName.getText().toString().trim();
    }

    public String getUserIdCard() {
        return mIdCard.getText().toString().trim();
    }

    public String getDriverFileNum() {
        return mDriverFileNum.getText().toString().trim();
    }

    /**
     * 申办畅通卡时间校验接口
     */
    private void checkCtkDate() {
        showDialogLoading();

        CheckCtkDTO checkCtkDTO = new CheckCtkDTO();
        checkCtkDTO.setApplyCode(getDriverFileNum());
        checkCtkDTO.setApplyInterface("banka");
        checkCtkDTO.setFlag("0");
        CarApiClient.checkCtk(this, checkCtkDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                if (result.getResponseCode() == 2000 || result.getResponseCode() == 2001) {
                    checkApplyCardRecord();
                } else {
                    hideDialogLoading();
                    ToastUtils.showShort(getApplicationContext(), "七天之内不能重复办卡");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                ToastUtils.showShort(getApplicationContext(), "请求失败，请再次点击");
            }
        });
    }


    /**
     * 客户办卡记录校验
     */
    private void checkApplyCardRecord() {
        BidCTCardDTO bidCTCardDTO = new BidCTCardDTO();
        bidCTCardDTO.setCtftp("0");
        bidCTCardDTO.setUsrname(getUserName());
        bidCTCardDTO.setUsrid(SPUtils.getInstance(getApplicationContext()).getLoginInfoBean().getUsrid());
        bidCTCardDTO.setCtfnum(RSAUtils.strByEncryption(getApplicationContext(), getUserIdCard(), true));
        bidCTCardDTO.setFilenum(RSAUtils.strByEncryption(getApplicationContext(), getDriverFileNum(), true));
        bidCTCardDTO.setPhoenum(RSAUtils.strByEncryption(getApplicationContext(), SPUtils.getInstance(this).getLoginInfoBean().getUsrid(), true));
        HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u006.01", bidCTCardDTO, this);
    }

    /**
     * 客户办卡记录校验 响应
     *
     * @param result
     */
    @Override
    public void resultSuccess(Result result) {
        hideDialogLoading();
        if (result.getSYS_HEAD().getReturnCode().equals("1")) {//不可快捷办卡
            Intent intent = new Intent(this, ApplyCardSecondActivity.class);
            intent.putExtra("filenum", getDriverFileNum());
            intent.putExtra("name", getUserName());
            intent.putExtra("idCard", getUserIdCard());
            startActivity(intent);

        } else if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
            Intent intent = new Intent(this, ApplyCardQuickActivity.class);
            intent.putExtra("filenum", getDriverFileNum());
            intent.putExtra("name", getUserName());
            intent.putExtra("idCard", getUserIdCard());
            startActivity(intent);
        }
    }

    @Override
    public void resultError(String msg) {
        hideDialogLoading();
        Toast.makeText(getApplicationContext(), Config.getErrMsg("1"), Toast.LENGTH_SHORT).show();
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
                    mIdCard.setText(result.getContent().getCardNo());
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
}
