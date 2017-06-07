package com.zantong.mobilecttx.card.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.card.dto.QuickApplyCardDTO;
import com.zantong.mobilecttx.card.dto.YingXiaoDataDTO;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.map.bean.NetLocationBean;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.ReadFfile;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
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
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApplyCardQuickActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements HandleCTCardApiClient.ResultInterface {

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


    private QuickApplyCardDTO quickApplyCardDTO; //快捷

    private InputStream is;
    private FileOutputStream fos;

    private String wangdianAdress;//网点地址
    private String mEmpNum;//获取的营销代码

    public static Intent getIntent(Context context, String fileNum, String name,
                                   String idCard) {
        Intent intent = new Intent(context, ApplyCardQuickActivity.class);
        intent.putExtra("filenum", fileNum);
        intent.putExtra("name", name);
        intent.putExtra("idCard", idCard);
        return intent;
    }


    @Override
    public void initView() {
        Intent intent = getIntent();
        String idCard = intent.getStringExtra("idCard");
        mIdCardNum.setContentText(idCard);
    }

    @Override
    public void initData() {
        quickApplyCardDTO = new QuickApplyCardDTO();
        setTitleText("申办畅通卡");
        mNormalHint.setText(Html.fromHtml(getResources().getString(R.string.apply_four_hint)));

        getYingXiaoCode();
        downloadTxt();
        quickApplyCardDTO.setCtftp("0");
        quickApplyCardDTO.setUsrname(getIntent().getStringExtra("name"));
        quickApplyCardDTO.setUsrid(SPUtils.getInstance(this).getLoginInfoBean().getUsrid());
        quickApplyCardDTO.setCtfnum(RSAUtils.strByEncryption(this, getIntent().getStringExtra("idCard"), true));
        quickApplyCardDTO.setFilenum(RSAUtils.strByEncryption(this, getIntent().getStringExtra("filenum"), true));
        quickApplyCardDTO.setPhoenum(RSAUtils.strByEncryption(this, PublicData.getInstance().mLoginInfoBean.getPhoenum(), true));
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
        PublicData.getInstance().filenum = "";
    }
    @OnClick({R.id.applycard_qucik_idcard_layout1,R.id.applycard_qucik_idcard_layout2,
            R.id.applycard_qucik_wangdian,R.id.applycard_qucik_commit})
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
            ToastUtils.showShort(this, "获取网点失败,正在为你重新获取");
        }
    }

    public void downloadTxt() {
        Retrofit2Utils retrofit2Utils = new Retrofit2Utils();
        final FileDownloadApi api = retrofit2Utils.getRetrofitHttps(BuildConfig.APP_URL).create(FileDownloadApi.class);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    Response<ResponseBody> response = api.downloadFileWithFixedUrl("download/icbcorg.txt").execute();
                    try {

                        if (response != null && response.isSuccessful()) {
                            //文件总长度
                            long fileSize = response.body().contentLength();
                            long fileSizeDownloaded = 0;
                            is = response.body().byteStream();
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "networktable.txt");
                            if (file.exists()) {
                                file.delete();
                            } else {
                                file.createNewFile();
                            }
                            fos = new FileOutputStream(file);
                            int count = 0;
                            byte[] buffer = new byte[1024];
                            while ((count = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, count);
                                fileSizeDownloaded += count;
                                subscriber.onNext("file download: " + fileSizeDownloaded + " of " + fileSize);
                            }
                            fos.flush();
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new Exception("接口请求异常"));
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.e("why", e.toString());
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i("文件下载完成");
                        NetLocationBean bean = ReadFfile.readNetLocationFile();
//                        PublicData.getInstance().mNetLocationBean.setNetLocationlist(bean.getNetLocationlist());
                        PublicData.getInstance().mNetLocationBean = bean;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("MainActivity", s);
                    }
                });
    }


    /**
     * 检测数据
     */
    private void checkData() {
        String kahao = mZhuanChuKaHao.getContentText();
        String email = mEmail.getContentText();
        if (TextUtils.isEmpty(kahao)){
            ToastUtils.showShort(this,"转出卡号不可为空");
            return;
        }
        if ("".equals(email)){
            email = "690635872@qq.com";
        }
        String bankData = mZhuanChuKaHao.getContentText();
        //autcrepymtmth 0是开通提醒 9是不开通
        if ("0".equals(quickApplyCardDTO.getAutcrepymtmth()) && TextUtils.isEmpty(bankData)) {
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
        commitInfo();
    }

    @Override
    public void resultSuccess(Result result) {
        hideDialogLoading();
        if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
            commitYingXiaoDataForLYT();
            checkCtkDate();
            startActivity(ApplySuccessActvity.getIntent(this, wangdianAdress));
        }
    }

    @Override
    public void resultError(String mesage) {
        hideDialogLoading();
        Toast.makeText(ApplyCardQuickActivity.this, Config.getErrMsg("1"), Toast.LENGTH_SHORT).show();
    }

    /**
     * 申办畅通卡时间校验接口
     */
    private void checkCtkDate() {
        CheckCtkDTO checkCtkDTO = new CheckCtkDTO();
        checkCtkDTO.setApplyCode(PublicData.getInstance().filenum);
        checkCtkDTO.setApplyInterface("banka");
        checkCtkDTO.setFlag("1");
        CarApiClient.checkCtk(this, checkCtkDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                hideDialogLoading();
                if (result.getResponseCode() == 2000) {
                    commitInfo();
                } else {
                    ToastUtils.showShort(ApplyCardQuickActivity.this, "七天之内不能重复办卡");
                }
            }
        });
    }
    /**
     * 提交办卡信息
     */
    private void commitInfo() {
        showDialogLoading();
        HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u010.01", quickApplyCardDTO, this);
    }
    /**
     * 办卡申请成功后，提交营销代码
     */
    private void commitYingXiaoDataForLYT(){

        YingXiaoDataDTO dto = new YingXiaoDataDTO();
        dto.setUsrnum(RSAUtils.strByEncryption(this,PublicData.getInstance().userID,true));
        if (TextUtils.isEmpty(mYingXiaoCode.getContentText())){
            dto.setEmpNum(mEmpNum);
        }else{
            dto.setEmpNum(mYingXiaoCode.getContentText());
        }

        CarApiClient.commitYingXiaoData(this, dto, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                if (result.getResponseCode() == 2000){
                    ToastUtils.showShort(ApplyCardQuickActivity.this,"已提交营销代码");
                }
            }
        });
    }

    /**
     * 得到营销代码
     */
    private void getYingXiaoCode() {
        CancelRechargeOrderDTO dto = new CancelRechargeOrderDTO();
        CarApiClient.getYingXiaoCode(this, dto, new CallBack<YingXiaoResult>() {
            @Override
            public void onSuccess(YingXiaoResult result) {
                if (result.getResponseCode() == 2000) {
                    mEmpNum = result.getData().getEmpNum();
//                    mYingXiaoCode.setContentText(mEmpNum);
                    quickApplyCardDTO.setDscode(result.getData().getEmpNum());
                    quickApplyCardDTO.setDscodegs(result.getData().getEmpNum());
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }
    private DatePicker picker;
    /**
     * 选择时间
     * 1.身份证有效期
     */
    private void chooseDate() {
        picker = new DatePicker(ApplyCardQuickActivity.this);
        String temp = "";
        temp = mIdCardLayout2.getRightText();
        picker.setRangeEnd(DateUtils.getYear() + 70, DateUtils.getMonth(), DateUtils.getDay());
        picker.setRangeStart(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        if (temp.contains("请输入")) {
            temp = "";
        }
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
                mIdCardLayout2.setRightText(year + "-" + month + "-" + day);
                quickApplyCardDTO.setCtfvldprd(mIdCardLayout2.getRightText());
                mIdCardLayout2.setRightTextColor(getResources().getColor(R.color.gray_33));

            }
        });
        picker.show();
    }
}
