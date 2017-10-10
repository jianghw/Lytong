package com.zantong.mobilecttx.card.activity;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.FileDownloadApi;
import com.zantong.mobilecttx.api.HandleCTCardApiClient;
import com.zantong.mobilecttx.application.MemoryData;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import cn.qqtheme.framework.bean.BankResponse;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.bean.YingXiaoResponse;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.card.dto.QuickApplyCardDTO;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.ReadFfile;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.dialog.NetLocationDialog;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.widght.CttxEditText;
import com.zantong.mobilecttx.widght.CttxTextView;
import com.zantong.mobilecttx.widght.SettingItemView;
import com.zantong.mobilecttx.widght.UISwitchButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.custom.picker.DatePicker;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.log.LogUtils;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 快捷办卡
 */
public class ApplyCardQuickActivity extends BaseMvpActivity<IBaseView, HelpPresenter>
        implements HandleCTCardApiClient.ResultInterface {

    @Bind(R.id.applycard_qucik_idcard_num)
    CttxTextView mIdCardNum;

    @Bind(R.id.applycard_qucik_zhuanchukahao)
    CttxEditText mZhuanChuKaHao;
    @Bind(R.id.applycard_qucik_email)
    CttxEditText mEmail;
    @Bind(R.id.applycard_qucik_yingxiaocode)
    CttxEditText mYingXiaoCode;


    @Bind(R.id.applycard_qucik_idcard_layout1)
    View mIdCardLayout1;
    @Bind(R.id.applycard_qucik_idcard_flag)
    ImageView mIdCardForeverImg;
    @Bind(R.id.applycard_qucik_idcard_layout2)
    SettingItemView mIdCardLayout2;


    @Bind(R.id.applycard_qucik_huankuantype)
    CttxTextView mHuanKuanType;
    @Bind(R.id.applycard_qucik_wangdian)
    SettingItemView mLingKaWangDian;

    @Bind(R.id.activity_qucik_duizhangdan)
    UISwitchButton mDuiZhangTiXing;
    @Bind(R.id.applycard_qucik_tixing)
    UISwitchButton mHuanKuanTiXing;

    @Bind(R.id.applycard_qucik_commit)
    Button mCommit;
    @Bind(R.id.applycard_qucik_hint)
    TextView mNormalHint;


    private QuickApplyCardDTO quickApplyCardDTO = new QuickApplyCardDTO();
    private String wangdianAdress;//网点地址
    private String mEmpNum;//获取的营销代码

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_applycard_quick;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MemoryData.getInstance().filenum = "";
    }

    @Override
    public void initView() {
        setTitleText("快捷申办畅通卡");
        mNormalHint.setText(Html.fromHtml(getResources().getString(R.string.apply_four_hint)));

        getYingXiaoCode();
        downloadTxt();
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String idCard = intent.getStringExtra("idCard");
        mIdCardNum.setContentText(idCard);

        quickApplyCardDTO.setCtftp("0");
        quickApplyCardDTO.setUsrname(getIntent().getStringExtra("name"));
        quickApplyCardDTO.setUsrid(SPUtils.getInstance().getLoginInfoBean().getUsrid());
        quickApplyCardDTO.setCtfnum(RSAUtils.strByEncryption(getIntent().getStringExtra("idCard"), true));
        quickApplyCardDTO.setFilenum(RSAUtils.strByEncryption(getIntent().getStringExtra("filenum"), true));
        quickApplyCardDTO.setPhoenum(RSAUtils.strByEncryption(MemoryData.getInstance().mLoginInfoBean.getPhoenum(), true));
        quickApplyCardDTO.setCtfvldprd(getIntent().getStringExtra("date"));
        quickApplyCardDTO.setActnotf("0");//默认不开启自动还款
        quickApplyCardDTO.setElecbillsign("0");
        quickApplyCardDTO.setAutcrepymtmth("0");//9

        mDuiZhangTiXing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    quickApplyCardDTO.setActnotf("1");
                } else {
                    quickApplyCardDTO.setActnotf("0");
                }
            }
        });
        mHuanKuanTiXing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    @OnClick({R.id.applycard_qucik_idcard_layout1, R.id.applycard_qucik_idcard_layout2,
            R.id.applycard_qucik_wangdian, R.id.applycard_qucik_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.applycard_qucik_idcard_layout1:
                mIdCardForeverImg.setBackgroundResource(R.mipmap.checkbox_checked);
                mIdCardLayout2.setRightText("年/月/日");
                mIdCardLayout2.setRightTextColor(getResources().getColor(R.color.gray_99));
                quickApplyCardDTO.setCtfvldprd("9999-12-30");
                break;
            case R.id.applycard_qucik_idcard_layout2:
                mIdCardForeverImg.setBackgroundResource(R.mipmap.checkbox_normal);
                chooseDate();
                break;
            case R.id.applycard_qucik_wangdian:
                lingQuWangDianDialog();
                break;
            case R.id.applycard_qucik_commit:
                checkData();
                break;
        }
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
                    mLingKaWangDian.setRightText(address);
                    mLingKaWangDian.setRightTextColor(getResources().getColor(R.color.gray_33));
                    quickApplyCardDTO.setGetbrno(data[1]);
                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            downloadTxt();
            ToastUtils.toastShort("获取网点失败,正在为你重新获取");
        }
    }

    /**
     * 检测数据
     */
    private void checkData() {
        String kahao = mZhuanChuKaHao.getContentText();
        String email = mEmail.getContentText();
        if (TextUtils.isEmpty(kahao)) {
            ToastUtils.toastShort("转出卡号不可为空");
            return;
        }
        if ("".equals(email)) {
            email = "690635872@qq.com";
        }
        String bankData = mZhuanChuKaHao.getContentText();
        //autcrepymtmth 0是开通提醒 9是不开通
        if ("0".equals(quickApplyCardDTO.getAutcrepymtmth()) && TextUtils.isEmpty(bankData)) {
            ToastUtils.toastShort("自动还款转出卡号不可为空");
            return;
        }
        if (TextUtils.isEmpty(quickApplyCardDTO.getGetbrno())) {
            ToastUtils.toastShort("请选择领卡网点");
            return;
        }

        quickApplyCardDTO.setTurnoutacnum(bankData);
        quickApplyCardDTO.setElecmail(email);
        if (TextUtils.isEmpty(quickApplyCardDTO.getDscode())
                && TextUtils.isEmpty(quickApplyCardDTO.getDscodegs())) {
            quickApplyCardDTO.setDscode("TZ666666");
            quickApplyCardDTO.setDscodegs("TZ666666");
        }
        commitInfo();
    }

    /**
     * 提交办卡信息
     */
    private void commitInfo() {
        showDialogLoading();
        HandleCTCardApiClient.htmlLocal(ContextUtils.getContext(), "cip.cfc.u010.01", quickApplyCardDTO, this);
    }

    @Override
    public void resultSuccess(BankResponse bankResponse) {
        hideDialogLoading();
        if (bankResponse.getSYS_HEAD().getReturnCode().equals("000000")) {

            commitYingXiaoDataForLYT(quickApplyCardDTO);
        } else {
            ToastUtils.toastShort(bankResponse.getSYS_HEAD().getReturnMessage());
        }
    }

    @Override
    public void resultError(String msg) {
        hideDialogLoading();
        ToastUtils.toastShort(Config.getErrMsg("1"));
    }

    /**
     * 申办畅通卡时间校验接口
     */
    private void checkCtkDate() {
        CheckCtkDTO checkCtkDTO = new CheckCtkDTO();
        checkCtkDTO.setApplyCode(MemoryData.getInstance().filenum);
        checkCtkDTO.setApplyInterface("banka");
        checkCtkDTO.setFlag("1");
        CarApiClient.checkCtk(this, checkCtkDTO, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                hideDialogLoading();
                if (result.getResponseCode() == 2000) {
                    commitInfo();
                } else {
                    ToastUtils.toastShort("七天之内不能重复办卡");
                }
            }
        });
    }

    /**
     * 办卡申请成功后，提交营销代码
     *
     * @param quickApplyCardDTO
     */
    private void commitYingXiaoDataForLYT(QuickApplyCardDTO quickApplyCardDTO) {
        CarApiClient.commitYingXiaoData(this, quickApplyCardDTO, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                startActivity(ApplySuccessActvity.getIntent(ApplyCardQuickActivity.this, wangdianAdress));
            }

            @Override
            public void onError(String errorCode, String msg) {
                startActivity(ApplySuccessActvity.getIntent(ApplyCardQuickActivity.this, wangdianAdress));
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
                        MemoryData.getInstance().mNetLocationBean
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

    /**
     * 得到营销代码
     */
    private void getYingXiaoCode() {
        CancelRechargeOrderDTO dto = new CancelRechargeOrderDTO();
        CarApiClient.getYingXiaoCode(this, dto, new CallBack<YingXiaoResponse>() {
            @Override
            public void onSuccess(YingXiaoResponse result) {
                if (result.getResponseCode() == 2000 && result.getData() != null) {
                    mEmpNum = result.getData().getEmpNum();
                    //TODO 手动不显示
//                    mYingXiaoCode.setContentText(mEmpNum);
                    quickApplyCardDTO.setDscode(mEmpNum);
                    quickApplyCardDTO.setDscodegs(mEmpNum);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
            }
        });
    }


    /**
     * 选择时间
     * 1.身份证有效期
     */
    private void chooseDate() {
        DatePicker picker = new DatePicker(ApplyCardQuickActivity.this);

        String temp = mIdCardLayout2.getRightText();
        picker.setRangeEnd(DateUtils.getYear() + 70, DateUtils.getMonth(), DateUtils.getDay());
        picker.setRangeStart(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        if (temp.contains("请输入")) {
            temp = "";
        }
        try {
            String date = temp;
            if (!"".equals(date)) {
                date = date.replace("-", "");
                picker.setSelectedItem(
                        Integer.valueOf(date.substring(0, 4)),
                        Integer.valueOf(date.substring(4, 6)),
                        Integer.valueOf(date.substring(6, 8)));
            } else {
                picker.setSelectedItem(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                mIdCardLayout2.setRightText(year + "-" + month + "-" + day);
                quickApplyCardDTO.setCtfvldprd(mIdCardLayout2.getRightText());
                mIdCardLayout2.setRightTextColor(getResources().getColor(R.color.gray_33));

            }
        });
        picker.show();
    }
}
