package com.zantong.mobilecttx.card.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.HandleCTCardApiClient;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.browser.BrowserHtmlActivity;
import com.zantong.mobilecttx.card.dto.BidCTCardDTO;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.bean.DriverOcrResult;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.bean.BankResponse;
import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.RegexUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.ViewUtils;
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
    @Bind(R.id.tv_toast)
    TextView mTvToast;

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

        noSpacesText();
    }

    protected void noSpacesText() {
        ViewUtils.editTextInputSpace(mDriverFileNum);
        ViewUtils.editTextInputSpace(mName);
        ViewUtils.editTextInputSpace(mIdCard);
    }

    @Override
    public void initData() {
        //测试数据下

        boolean positon = new Random().nextBoolean();
        if (BuildConfig.App_Url && positon) {
            mName.setText("遇紫紫");
            mIdCard.setText("301364198811040740");
            mDriverFileNum.setText("310002038631");
        } else if (BuildConfig.App_Url && !positon) {
            mName.setText("毛乾帅");
            mIdCard.setText("310109198503162039");
            mDriverFileNum.setText("310010007285");
        }
    }

    @OnClick({R.id.apply_card_first_img, R.id.apply_card_first_desc, R.id.apply_card_first_commit,
            R.id.apply_card_idcard_img, R.id.apply_card_first_camera, R.id.tv_toast})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_card_first_img:  //驾驶证问号
                new DialogMgr(this, R.mipmap.code_query_notice_iamge);
                break;
            case R.id.apply_card_first_desc:   //说明
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, "隐私声明");
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, "file:///android_asset/bindcard_agreement.html");
                Act.getInstance().gotoLoginByIntent(this, BrowserHtmlActivity.class, intent);
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
            case R.id.tv_toast:
                ToastUtils.toastLong("只要持外地驾驶证的客户到他现在居住所在地的车管所申请办理，办理时需提交以下资料，客户身份证，暂住证，驾驶证，还需要携带本人近期一寸彩照3张去所属车管所填写机动车驾驶证申请表就可以办理了，正常的话当天就可以换到证，最迟不会超过三个工作日");
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
        ToastUtils.toastShort("您已关闭内存卡读写权限");
    }

    /**
     * 表单校验值
     */
    private void valueFormValidation() {
        if (TextUtils.isEmpty(getUserName())) {
            ToastUtils.toastShort("姓名不可为空");
            return;
        }
        if (TextUtils.isEmpty(getUserIdCard())) {
            ToastUtils.toastShort("驾档编号不能为空");
            return;
        }
        if (TextUtils.isEmpty(getDriverFileNum())) {
            ToastUtils.toastShort("驾驶证号不能为空");
            return;
        }

        if (getDriverFileNum().length() != 12) {
            ToastUtils.toastShort("请输入12位驾档编号");
            return;
        }
        if (!RegexUtils.isIDCard15(getUserIdCard()) && !RegexUtils.isIDCard18(getUserIdCard())) {
            ToastUtils.toastShort("身份证号码不正确");
            return;
        }
        if (!getUserIdCard().startsWith("310")) {
            ToastUtils.toastShort("外地驾照如需办理请先换成上海驾照");
            return;
        }

        //TODO 什么贵
        LoginData.getInstance().filenum = getDriverFileNum();

        if (BuildConfig.App_Url) {//七天之内不能重复办卡 不用
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
        CarApiClient.checkCtk(this, checkCtkDTO, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                if (result.getResponseCode() == 2000 || result.getResponseCode() == 2001) {
                    checkApplyCardRecord();
                } else {
                    hideDialogLoading();
                    ToastUtils.toastShort("七天之内不能重复办卡");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                hideDialogLoading();
                ToastUtils.toastShort("请求失败，请再次点击");
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
        bidCTCardDTO.setUsrid(MainRouter.getUserID(true));
        bidCTCardDTO.setCtfnum(RSAUtils.strByEncryption(getUserIdCard(), true));
        bidCTCardDTO.setFilenum(RSAUtils.strByEncryption(getDriverFileNum(), true));
        bidCTCardDTO.setPhoenum(RSAUtils.strByEncryption(MainRouter.getUserPhoenum(), true));
        HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u006.01", bidCTCardDTO, this);
    }

    /**
     * 客户办卡记录校验 响应
     *
     * @param bankResponse
     */
    @Override
    public void resultSuccess(BankResponse bankResponse) {
        hideDialogLoading();
        if (bankResponse.getSYS_HEAD().getReturnCode().equals("1")) {//不可快捷办卡
            Intent intent = new Intent(this, ApplyCardSecondActivity.class);
            intent.putExtra("filenum", getDriverFileNum());
            intent.putExtra("name", getUserName());
            intent.putExtra("idCard", getUserIdCard());
            startActivity(intent);
        } else if (bankResponse.getSYS_HEAD().getReturnCode().equals("000000")) {
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
        ToastUtils.toastShort(Config.getErrMsg("1"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginData.getInstance().filenum = "";
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
