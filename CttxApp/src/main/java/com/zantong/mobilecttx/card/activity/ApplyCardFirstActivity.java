package com.zantong.mobilecttx.card.activity;

import android.Manifest;
import android.content.Intent;
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
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.permission.PermissionFail;
import com.zantong.mobilecttx.utils.permission.PermissionGen;
import com.zantong.mobilecttx.utils.permission.PermissionSuccess;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class ApplyCardFirstActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements HandleCTCardApiClient.ResultInterface {

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

    private String driverFileNum;
    private String name;
    private String idCard;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;  //请求码，自己定义
    private int mPermissionFrom = 0;

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
    }

    @Override
    public void initData() {
        setTitleText("申办畅通卡");
    }

    @OnClick({R.id.apply_card_first_img, R.id.apply_card_first_desc, R.id.apply_card_first_commit,
            R.id.apply_card_idcard_img, R.id.apply_card_first_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_card_first_img:  //驾驶证问号
                new DialogMgr(ApplyCardFirstActivity.this, R.mipmap.code_query_notice_iamge);
                break;
            case R.id.apply_card_first_desc:   //说明
                PublicData.getInstance().webviewUrl = "file:///android_asset/bindcard_agreement.html";
                PublicData.getInstance().webviewTitle = "隐私声明";
                Act.getInstance().gotoIntent(this, BrowserActivity.class);
                break;
            case R.id.apply_card_first_commit:
                mPermissionFrom = 0;
                PermissionGen.needPermission(this, 100,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        });

                break;
            case R.id.apply_card_idcard_img:
                new DialogMgr(this, R.mipmap.img_jiazhao_idcard);
                break;
            case R.id.apply_card_first_camera:
                mPermissionFrom = 1;
                //检查权限
                PermissionGen.needPermission(this, 100,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        });
                break;
        }
    }

    /**
     * 校验值
     */
    private void valueCheck() {
        driverFileNum = mDriverFileNum.getText().toString();
        name = mName.getText().toString();
        idCard = mIdCard.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(this, "姓名不可为空");
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            ToastUtils.showShort(this, "驾驶证号不能为空");
            return;
        }
        if (TextUtils.isEmpty(driverFileNum)) {
            ToastUtils.showShort(this, "驾档编号不能为空");
            return;
        }

        if (driverFileNum.length() != 12) {
            ToastUtils.showShort(this, "驾档编号格式不正确");
            return;
        }
        if (!ValidateUtils.isIdCard(idCard)) {
            ToastUtils.showShort(this, "身份证号码不正确");
            return;
        }
        PublicData.getInstance().filenum = driverFileNum;

        if (BuildConfig.DEBUG) {//七天之内不能重复办卡 不用
            checkApplyCardRecord();
        } else {
            checkCtkDate();
        }
    }

    /**
     * 申办畅通卡时间校验接口
     */
    private void checkCtkDate() {
        showDialogLoading();
        CheckCtkDTO checkCtkDTO = new CheckCtkDTO();
        checkCtkDTO.setApplyCode(driverFileNum);
        checkCtkDTO.setApplyInterface("banka");
        checkCtkDTO.setFlag("0");
        CarApiClient.checkCtk(this, checkCtkDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                if (result.getResponseCode() == 2000 || result.getResponseCode() == 2001) {
                    checkApplyCardRecord();
                } else {
                    hideDialogLoading();
                    ToastUtils.showShort(ApplyCardFirstActivity.this, "七天之内不能重复办卡");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                ToastUtils.showShort(ApplyCardFirstActivity.this, "请求失败，请再次点击");
            }
        });
    }

    /**
     * 客户办卡记录校验
     */
    private void checkApplyCardRecord() {
        BidCTCardDTO bidCTCardDTO = new BidCTCardDTO();
        bidCTCardDTO.setCtftp("0");
        bidCTCardDTO.setUsrname(name);
        bidCTCardDTO.setUsrid(SPUtils.getInstance(this).getLoginInfoBean().getUsrid());
        bidCTCardDTO.setCtfnum(RSAUtils.strByEncryption(this, idCard, true));
        bidCTCardDTO.setFilenum(RSAUtils.strByEncryption(this, driverFileNum, true));
        bidCTCardDTO.setPhoenum(RSAUtils.strByEncryption(this, SPUtils.getInstance(this).getLoginInfoBean().getUsrid(), true));
        HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u006.01", bidCTCardDTO, this);
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
                if ("OK".equals(result.getStatus())) {
                    LogUtils.i(result.getContent().toString());
                    mName.setText(result.getContent().getName());
                    mIdCard.setText(result.getContent().getCardNo());
                } else {
                    ToastUtils.showShort(ApplyCardFirstActivity.this, "解析失败，请重试");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
            }
        });
    }

    @Override
    public void resultSuccess(Result result) {
        if (result.getSYS_HEAD().getReturnCode().equals("1")) {
            startActivity(ApplyCardSecondActivity.getIntent(this, driverFileNum, name, idCard));
        } else if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
            startActivity(ApplyCardQuickActivity.getIntent(this, driverFileNum, name, idCard));
        }
        hideDialogLoading();
    }

    @Override
    public void resultError(String mesage) {
        hideDialogLoading();
        Toast.makeText(ApplyCardFirstActivity.this, Config.getErrMsg("1"), Toast.LENGTH_SHORT).show();
    }


    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        if (mPermissionFrom == 0) {
            valueCheck();
        } else if (mPermissionFrom == 1) {
            Intent intentOcr = new Intent(this, OcrCameraActivity.class);
            intentOcr.putExtra("ocr_resource", 1);
            startActivityForResult(intentOcr, 1205);
        }
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        if (mPermissionFrom == 0) {
            ToastUtils.showShort(this, "您已关闭内存卡读写权限");
        } else if (mPermissionFrom == 1) {
            ToastUtils.showShort(this, "您已关闭摄像头或者内存卡读写权限");
        }

    }
}
