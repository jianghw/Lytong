package com.zantong.mobilecttx.card.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.FileDownloadApi;
import com.zantong.mobilecttx.api.HandleCTCardApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.bean.YingXiaoResult;
import com.zantong.mobilecttx.card.dto.ApplyCTCardDTO;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.card.dto.QuickApplyCardDTO;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.CommonTwoLevelMenuActivity;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.utils.ReadFfile;
import com.zantong.mobilecttx.utils.dialog.NetLocationDialog;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.widght.SettingItemView;
import com.zantong.mobilecttx.widght.UISwitchButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.log.LogUtils;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApplyCardFourStepActvity extends BaseMvpActivity<IBaseView, HelpPresenter>
        implements HandleCTCardApiClient.ResultInterface {

    @Bind(R.id.activity_apply_four_huaikuan_type)
    SettingItemView mHuaikuanType;    //自动还款
    @Bind(R.id.activity_apply_four_bank_card)
    EditText mBankCard;  //银行卡号
    @Bind(R.id.activity_apply_four_open_email)
    UISwitchButton mOpenEmail;   //是否开启邮件对账
    @Bind(R.id.activity_apply_four_mail)
    EditText mMail;   //邮箱
    @Bind(R.id.activity_apply_four_open_balance)
    UISwitchButton mOpenBalance;   //是否开启余额变动提醒
    @Bind(R.id.activity_apply_four_lingka_wangdian)
    SettingItemView mLingkaWangdian;   //领卡网点
    @Bind(R.id.activity_apply_four_fangchan_info)
    EditText mFangchanInfo;   //房产信息
    @Bind(R.id.activity_apply_four_car_num)
    EditText mCarNum;   //车牌号
    @Bind(R.id.apply_four_hint)
    TextView mHint;   //车牌号
    @Bind(R.id.four_next)
    Button mNext;
    @Bind(R.id.activity_apply_four_yingxiao)
    EditText mYingxiao;   //营销代码

    private ApplyCTCardDTO applyCTCardDTO;
    private QuickApplyCardDTO quickApplyCardDTO; //快捷
    private int form;//1 快捷办卡
    private String wangdianAdress;
    private InputStream is;
    private FileOutputStream fos;
    private String mEmpNum;//获取的营销代码

    public static Intent getIntent(Context context, String fileNum, String name,
                                   String idCard, String yXdate) {
        Intent intent = new Intent(context, ApplyCardFourStepActvity.class);
        intent.putExtra("filenum", fileNum);
        intent.putExtra("name", name);
        intent.putExtra("idCard", idCard);
        intent.putExtra("date", yXdate);
        intent.putExtra("form", 1);
        return intent;
    }

    @Override
    public void initView() {
        mOpenBalance.setClickable(true);
    }

    @Override
    public void initData() {
        setTitleText("申办畅通卡");
        mHint.setText(Html.fromHtml(getResources().getString(R.string.apply_four_hint)));
        form = getIntent().getIntExtra("form", 0);
        if (form == 1) {
            quickApplyCardDTO = new QuickApplyCardDTO();
            mHuaikuanType.setFocusable(false);
            mHuaikuanType.setFocusableInTouchMode(false);
            mHuaikuanType.setClickable(false);
            initQuickValue();
        } else {
            applyCTCardDTO = (ApplyCTCardDTO) getIntent().getSerializableExtra("data");
            mHuaikuanType.setFocusable(true);
            mHuaikuanType.setFocusableInTouchMode(true);
            mHuaikuanType.setClickable(true);
            initValue();
        }
        getYingCode();
    }

    /**
     * 快捷办卡赋初始值
     */
    private void initQuickValue() {
        quickApplyCardDTO.setCtftp("0");
        quickApplyCardDTO.setUsrname(getIntent().getStringExtra("name"));
        quickApplyCardDTO.setUsrid(PublicData.getInstance().mLoginInfoBean.getUsrid());
        quickApplyCardDTO.setCtfnum(RSAUtils.strByEncryption(getIntent().getStringExtra("idCard"), true));
        quickApplyCardDTO.setFilenum(RSAUtils.strByEncryption(getIntent().getStringExtra("filenum"), true));
        quickApplyCardDTO.setPhoenum(RSAUtils.strByEncryption(PublicData.getInstance().mLoginInfoBean.getPhoenum(), true));
        quickApplyCardDTO.setCtfvldprd(getIntent().getStringExtra("date"));
        quickApplyCardDTO.setActnotf("0");//默认不开启自动还款
        quickApplyCardDTO.setElecbillsign("0");
        quickApplyCardDTO.setAutcrepymtmth("0");//9
        mHuaikuanType.setRightText("人民币自动转存");
        mHuaikuanType.setRightTextColor(getResources().getColor(R.color.gray_25));
        mOpenBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    quickApplyCardDTO.setActnotf("1");
                } else {
                    quickApplyCardDTO.setActnotf("0");
                }
            }
        });
        mOpenEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    quickApplyCardDTO.setElecbillsign("1");
                } else {
                    quickApplyCardDTO.setElecbillsign("0");
                }
            }
        });
    }

    /**
     * 赋初始值
     */
    private void initValue() {
        applyCTCardDTO.setActnotf("1");
        applyCTCardDTO.setElecbillsign("0");
        applyCTCardDTO.setAutcrepymtmth("9");
        mHuaikuanType.setRightText("不开通");
        mHuaikuanType.setRightTextColor(getResources().getColor(R.color.gray_25));
        mOpenBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    applyCTCardDTO.setActnotf("1");
                } else {
                    applyCTCardDTO.setActnotf("0");
                }
            }
        });
        mOpenEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    applyCTCardDTO.setElecbillsign("1");
                } else {
                    applyCTCardDTO.setElecbillsign("0");
                }
            }
        });
    }

    @OnClick({R.id.activity_apply_four_huaikuan_type, R.id.activity_apply_four_lingka_wangdian, R.id.four_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_apply_four_huaikuan_type:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 9), 10009);
                break;
            case R.id.activity_apply_four_lingka_wangdian:
                lingQuWangDianDialog();
                break;
            case R.id.four_next:
                checkData();
                break;
        }
    }

    /**
     * 检测数据
     */
    private void checkData() {


//        if (applyCTCardDTO.getElecbillsign().equals("1")) {
//            ToastUtils.showShort(this, "您已开通Email对账单请填写邮箱地址");
//            return;
//        }

        String email = mMail.getText().toString();
        if ("".equals(email)){
            email = "690635872@qq.com";
        }
//        if (TextUtils.isEmpty(email)) {
//            ToastUtils.showShort(this, "邮箱不可为空");
//            return;
//        }
        String bankData = mBankCard.getText().toString();
        //autcrepymtmth 0是开通提醒 9是不开通
        if (form == 1) {
            if (TextUtils.isEmpty(bankData)) {
                ToastUtils.showShort(this, "自动还款转出卡号不可为空");
                return;
            }
            if (TextUtils.isEmpty(quickApplyCardDTO.getGetbrno())) {
                ToastUtils.showShort(this, "请选择领卡网点");
                return;
            }

            quickApplyCardDTO.setTurnoutacnum(bankData);
            quickApplyCardDTO.setElecmail(email);
            if (TextUtils.isEmpty(quickApplyCardDTO.getDscode())
                    && TextUtils.isEmpty(quickApplyCardDTO.getDscodegs())) {
                quickApplyCardDTO.setDscode("TZ666666");
                quickApplyCardDTO.setDscodegs("TZ666666");
            }
        } else {
            if ("0".equals(applyCTCardDTO.getAutcrepymtmth()) && TextUtils.isEmpty(bankData)) {
                ToastUtils.showShort(this, "自动还款转出卡号不可为空");
                return;
            }
            if (TextUtils.isEmpty(applyCTCardDTO.getGetbrno())) {
                ToastUtils.showShort(this, "请选择领卡网点");
                return;
            }

            applyCTCardDTO.setTurnoutacnum(bankData);
            applyCTCardDTO.setElecmail(email);
            if (TextUtils.isEmpty(applyCTCardDTO.getDscode())
                    && TextUtils.isEmpty(applyCTCardDTO.getDscodegs())) {
                applyCTCardDTO.setDscode("TZ666666");
                applyCTCardDTO.setDscodegs("TZ666666");
            }
        }
        commitInfo();
    }

    /**
     * 得到营销代码
     */
    private void getYingCode() {
        CancelRechargeOrderDTO dto = new CancelRechargeOrderDTO();
        CarApiClient.getYingXiaoCode(this, dto, new CallBack<YingXiaoResult>() {
            @Override
            public void onSuccess(YingXiaoResult result) {
                if (result.getResponseCode() == 2000) {
                    mEmpNum = result.getData().getEmpNum();
                    mYingxiao.setText(mEmpNum);
                    if (form == 1){//快捷办卡
                        quickApplyCardDTO.setDscode(result.getData().getEmpNum());
                        quickApplyCardDTO.setDscodegs(result.getData().getEmpNum());
                    }else{
                        applyCTCardDTO.setDscode(result.getData().getEmpNum());
                        applyCTCardDTO.setDscodegs(result.getData().getEmpNum());
                    }
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }

    /**
     * 领取网点dialog
     */
    private void lingQuWangDianDialog() {
        try {
            NetLocationDialog dialog = new NetLocationDialog(this, null, new NetLocationDialog.OnChooseDialogListener() {

                @Override
                public void back(String[] data) {
                    String address = data[0] + data[2];
                    wangdianAdress = address;
                    if (address.length() > 20) {
                        address = address.substring(0, 20) + "...";
                    }
                    mLingkaWangdian.setRightText(address);
                    mLingkaWangdian.setRightTextColor(getResources().getColor(R.color.gray_25));
                    if (form == 1) {
                        quickApplyCardDTO.setGetbrno(data[1]);
                    } else {
                        applyCTCardDTO.setGetbrno(data[1]);
                    }
                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            downloadTxt();
            ToastUtils.showShort(this, "获取网点失败,正在为你重新获取");
        }
    }

    /**
     * 提交办卡信息
     */
    private void commitInfo() {
        showDialogLoading();
        if (form == 1) {
            HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u010.01", quickApplyCardDTO, this);
        } else {
            HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u007.01", applyCTCardDTO, this);
        }

        commitYingXiaoDataForLYT(applyCTCardDTO);
    }

    /**
     * 申办畅通卡时间校验接口
     */
    private void checkCtkDate() {
        CheckCtkDTO checkCtkDTO = new CheckCtkDTO();
        if (form == 1) {
            //checkCtkDTO.setApplyCode(quickApplyCardDTO.getFilenum()); //已经加密
            checkCtkDTO.setApplyCode(PublicData.getInstance().filenum);
        } else {
            //checkCtkDTO.setApplyCode(applyCTCardDTO.getFilenum()); //已经加密
            checkCtkDTO.setApplyCode(PublicData.getInstance().filenum);
        }
        checkCtkDTO.setApplyInterface("banka");
        checkCtkDTO.setFlag("1");
        CarApiClient.checkCtk(this, checkCtkDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                hideDialogLoading();
                if (result.getResponseCode() == 2000) {
                    commitInfo();
                } else {
                    ToastUtils.showShort(ApplyCardFourStepActvity.this, "七天之内不能重复办卡");
                }
            }
        });
    }

    @Override
    public void resultSuccess(Result result) {
        hideDialogLoading();
        if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
            checkCtkDate();
            startActivity(ApplySuccessActvity.getIntent(this, wangdianAdress));
        }
    }

    @Override
    public void resultError(String msg) {
        hideDialogLoading();
        Toast.makeText(ApplyCardFourStepActvity.this, Config.getErrMsg("1"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10009 && resultCode == 1009 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            ToastUtils.showShort(this,"选择了"+commonTwoLevelMenuBean.getId());
            if (form == 1){
                quickApplyCardDTO.setAutcrepymtmth(String.valueOf(commonTwoLevelMenuBean.getId()));
            }else{
                applyCTCardDTO.setAutcrepymtmth(String.valueOf(commonTwoLevelMenuBean.getId()));
            }

            mHuaikuanType.setRightText(commonTwoLevelMenuBean.getContext());
            mHuaikuanType.setRightTextColor(getResources().getColor(R.color.gray_25));
        }
    }

    /**
     * 办卡申请成功后，提交营销代码
     * @param applyCTCardDTO
     */
    private void commitYingXiaoDataForLYT(ApplyCTCardDTO applyCTCardDTO){

        CarApiClient.commitYingXiaoData(this, applyCTCardDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                if (result.getResponseCode() == 2000){
                    ToastUtils.showShort(ApplyCardFourStepActvity.this,"已提交营销代码");
                }
            }
        });
    }
    /**
     * 下载文件txt
     */
    public void downloadTxt() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        downloadFile(subscriber);
                    }
                })
                .subscribeOn(Schedulers.io())
//                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        PublicData.getInstance().mNetLocationBean
                                = ReadFfile.readNetLocationFile(getApplicationContext());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                    }
                });
    }

    private void downloadFile(Subscriber<? super String> subscriber) {
        FileDownloadApi api = new Retrofit2Utils().getRetrofitHttps(BuildConfig.APP_URL).create(FileDownloadApi.class);
        Response<ResponseBody> response = null;
        try {
            response = api.downloadFileWithFixedUrl("download/icbcorg.txt").execute();
        } catch (IOException e) {
            subscriber.onError(e);
        }

        if (response != null && response.isSuccessful()) {
            InputStream inputStream = response.body().byteStream();
            String filePath = FileUtils.icbTxtFilePath(getApplicationContext(), FileUtils.DOWNLOAD_DIR);
            File txtFile = new File(filePath);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(txtFile);
                int count;
                byte[] buffer = new byte[1024 * 8];
                while ((count = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.flush();
                subscriber.onCompleted();
            } catch (IOException e) {
                subscriber.onError(e);
            } finally {
                try {
                    inputStream.close();
                    if (fileOutputStream != null) fileOutputStream.close();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        } else {
            subscriber.onError(new Exception("银行网点接口请求异常,请退出页面稍后重试"));
        }
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_bid_four_step;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PublicData.getInstance().filenum = "";
    }
}
