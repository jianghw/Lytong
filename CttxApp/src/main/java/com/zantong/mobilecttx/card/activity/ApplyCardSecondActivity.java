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
import com.zantong.mobilecttx.card.bean.ProvinceModel;
import com.zantong.mobilecttx.card.bean.YingXiaoResult;
import com.zantong.mobilecttx.card.dto.ApplyCTCardDTO;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.card.dto.YingXiaoDataDTO;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.CommonTwoLevelMenuActivity;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.map.bean.NetLocationBean;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.ReadFfile;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.dialog.CityDialog;
import com.zantong.mobilecttx.utils.dialog.NetLocationDialog;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.xmlparser.XmlParserHandler;
import com.zantong.mobilecttx.widght.CttxEditText;
import com.zantong.mobilecttx.widght.SettingItemView;
import com.zantong.mobilecttx.widght.UISwitchButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ApplyCardSecondActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements HandleCTCardApiClient.ResultInterface {

    @Bind(R.id.applycard_normal_filenum)
    CttxEditText mFileNum;
    @Bind(R.id.applycard_normal_username)
    CttxEditText mUserName;
    @Bind(R.id.applycard_normal_username_pinyin)
    CttxEditText mUserNamePinYin;
    @Bind(R.id.applycard_normal_idcard_num)
    CttxEditText mIdCardNum;
    @Bind(R.id.applycard_normal_detail_addr)
    CttxEditText mDetailAddr;
    @Bind(R.id.applycard_normal_tel)
    CttxEditText mTel;

    @Bind(R.id.applycard_normal_income)
    CttxEditText mIncome;
    @Bind(R.id.applycard_normal_company_name)
    CttxEditText mCompanyName;
    @Bind(R.id.applycard_normal_department_name)
    CttxEditText mDepartName;
    @Bind(R.id.applycard_normal_compay_detailaddr)
    CttxEditText mCompanyDetailAddr;
    @Bind(R.id.applycard_normal_compay_zipcode)
    CttxEditText mZipCode;
    @Bind(R.id.applycard_normal_compay_tel)
    CttxEditText mCompanyTel;

    @Bind(R.id.applycard_normal_contact_name)
    CttxEditText mContactName;
    @Bind(R.id.applycard_normal_contact_mobile)
    CttxEditText mContactMobile;
    @Bind(R.id.applycard_normal_zhuanchukahao)
    CttxEditText mZhuanChuKaHao;
    @Bind(R.id.applycard_normal_email)
    CttxEditText mEmail;
    @Bind(R.id.applycard_normal_yingxiaocode)
    CttxEditText mYingXiaoCode;


    @Bind(R.id.applycard_normal_idcard_layout1)
    View mIdCardLayout1;
    @Bind(R.id.applycard_normal_idcard_flag)
    ImageView mIdCardForeverImg;
    @Bind(R.id.applycard_normal_idcard_layout2)
    SettingItemView mIdCardLayout2;
    @Bind(R.id.applycard_normal_marital_status)//婚姻状况
    SettingItemView mMaritalStatus;
    @Bind(R.id.applycard_normal_education_status)
    SettingItemView mEducationStatus;
    @Bind(R.id.applycard_normal_house_status)
    SettingItemView mHouseStatus;
    @Bind(R.id.applycard_normal_addr)
    SettingItemView mAddr;

    @Bind(R.id.applycard_normal_ziyou_img)
    ImageView mZiyouImg;
    @Bind(R.id.applycard_normal_company_img)
    ImageView mCompanyImg;
    @Bind(R.id.applycard_normal_company_type)
    SettingItemView mCompanyType;
    @Bind(R.id.applycard_normal_zhiwu)
    SettingItemView mZhiWu;
    @Bind(R.id.applycard_normal_zhiye)
    SettingItemView mZhiYe;
    @Bind(R.id.applycard_normal_compay_addr)
    SettingItemView mCompanyAddr;

    @Bind(R.id.applycard_normal_contact_relationship)
    SettingItemView mRelationShip;
    @Bind(R.id.applycard_normal_huankuantype)
    SettingItemView mHuanKuanType;
    @Bind(R.id.applycard_normal_wangdian)
    SettingItemView mLingKaWangDian;

    @Bind(R.id.activity_apply_four_duizhangdan)
    UISwitchButton mDuiZhangTiXing;
    @Bind(R.id.applycard_normal_tixing)
    UISwitchButton mHuanKuanTiXing;
    
    @Bind(R.id.applycard_normal_commit)
    Button mCommit;
    @Bind(R.id.applycard_normal_hint)
    TextView mNormalHint;


    private ApplyCTCardDTO applyCTCardDTO;

    private InputStream is;
    private FileOutputStream fos;

    private String wangdianAdress;//网点地址
    private String mEmpNum;//获取的营销代码

    public static Intent getIntent(Context context, String fileNum, String name,
                                   String idCard) {
        Intent intent = new Intent(context, ApplyCardSecondActivity.class);
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
        applyCTCardDTO = new ApplyCTCardDTO();
        setTitleText("申办畅通卡");
        mNormalHint.setText(Html.fromHtml(getResources().getString(R.string.apply_four_hint)));

        getYingXiaoCode();
        downloadTxt();
        String idCard = getIntent().getStringExtra("idCard");
        if (idCard.length() == 15){
            int temp = Integer.valueOf(idCard.lastIndexOf(0)) % 2;
            applyCTCardDTO.setGnd(temp == 0 ? "2" : "1"); // 1男  2女
            applyCTCardDTO.setDtofbrth(idCard.substring(6,8) + "-" + idCard.substring(8,10) + "-" + idCard.substring(10,12));
        }else if (idCard.length() == 18){
            int temp = Integer.valueOf(idCard.lastIndexOf(1)) % 2;
            applyCTCardDTO.setGnd(temp == 0 ? "2" : "1");
            applyCTCardDTO.setDtofbrth(idCard.substring(6,10) + "-" + idCard.substring(10,12) + "-" + idCard.substring(12,14));
        }else {
            applyCTCardDTO.setGnd("1");
        }
        LogUtils.i("=============================="+applyCTCardDTO.getDtofbrth());

        applyCTCardDTO.setCtftp("0");
        applyCTCardDTO.setUsrname(getIntent().getStringExtra("name"));
        applyCTCardDTO.setUsrid(SPUtils.getInstance(this).getLoginInfoBean().getUsrid());
        applyCTCardDTO.setCtfnum(RSAUtils.strByEncryption(this, idCard , true));
        applyCTCardDTO.setFilenum(RSAUtils.strByEncryption(this, getIntent().getStringExtra("filenum"), true));
        applyCTCardDTO.setActnotf("1");
        applyCTCardDTO.setElecbillsign("0");
        applyCTCardDTO.setAutcrepymtmth("9");
        applyCTCardDTO.setIssuoffic("0");
        applyCTCardDTO.setHmareacode("021");
        applyCTCardDTO.setIndate("2015-01-01");
        applyCTCardDTO.setHmphoeexn("");
        mCompanyAddr.setRightText("无");
        applyCTCardDTO.setIdycgy("1");
        applyCTCardDTO.setJoindate("201501");
        applyCTCardDTO.setCophoeexn("");
        applyCTCardDTO.setCoareacode("021");
        applyCTCardDTO.setCtc1("8");
        applyCTCardDTO.setCtc2("8");
        applyCTCardDTO.setCtccophoeexn1("");
        applyCTCardDTO.setCtccophoeexn2("");
        applyCTCardDTO.setCtchmadr1("无");
        applyCTCardDTO.setCtchmadr2("无");
        applyCTCardDTO.setCtchmadrzip1("201900");
        applyCTCardDTO.setCtchmadrzip2("201900");

        applyCTCardDTO.setCtcgnd1("1");
        applyCTCardDTO.setCtcgnd2("1");
        applyCTCardDTO.setCtcconm1("无");
        applyCTCardDTO.setCtcconm2("无");
        applyCTCardDTO.setCtccoareacode1("021");
        applyCTCardDTO.setCtccoareacode2("021");
        mHuanKuanType.setRightText("不开通");
        mHuanKuanType.setRightTextColor(getResources().getColor(R.color.gray_33));
        mDuiZhangTiXing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    applyCTCardDTO.setActnotf("1");
                } else {
                    applyCTCardDTO.setActnotf("0");
                }
            }
        });
        mHuanKuanTiXing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_applycard_second;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PublicData.getInstance().filenum = "";
    }
    @OnClick({R.id.applycard_normal_idcard_layout1,R.id.applycard_normal_idcard_layout2,R.id.applycard_normal_marital_status,
            R.id.applycard_normal_education_status,R.id.applycard_normal_house_status,R.id.applycard_normal_addr,
            R.id.applycard_normal_ziyou_layout,R.id.applycard_normal_company_layout,R.id.applycard_normal_company_type,
            R.id.applycard_normal_zhiwu,R.id.applycard_normal_zhiye,R.id.applycard_normal_compay_addr,
            R.id.applycard_normal_contact_relationship,R.id.applycard_normal_huankuantype, R.id.applycard_normal_wangdian,
            R.id.applycard_normal_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.applycard_normal_idcard_layout1:
                mIdCardForeverImg.setBackgroundResource(R.mipmap.checkbox_checked);
                mIdCardLayout2.setRightText("年/月/日");
                mIdCardLayout2.setRightTextColor(getResources().getColor(R.color.gray_99));
                applyCTCardDTO.setCtfvldprd("9999-12-30");
                break;
            case R.id.applycard_normal_idcard_layout2:
                mIdCardForeverImg.setBackgroundResource(R.mipmap.checkbox_normal);
                chooseDate();
                break;
            case R.id.applycard_normal_marital_status:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 0), 10000);
                break;
            case R.id.applycard_normal_education_status:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 1), 10001);
                break;
            case R.id.applycard_normal_house_status:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 2), 10002);
                break;
            case R.id.applycard_normal_addr:
                chooseAddress();
                break;
            case R.id.applycard_normal_ziyou_layout:
                mWorktype = 1;
                mZiyouImg.setBackgroundResource(R.mipmap.checkbox_checked);
                mCompanyImg.setBackgroundResource(R.mipmap.checkbox_normal);
                initValue();
                chooseZiyouValue();
                break;
            case R.id.applycard_normal_company_layout:
                mWorktype = 2;
                mZiyouImg.setBackgroundResource(R.mipmap.checkbox_normal);
                mCompanyImg.setBackgroundResource(R.mipmap.checkbox_checked);
                chooseQiyeValue();
                break;
            case R.id.applycard_normal_company_type:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 6), 10006);
                break;
            case R.id.applycard_normal_zhiwu:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 5), 10005);
                break;
            case R.id.applycard_normal_zhiye:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 4), 10004);
                break;
            case R.id.applycard_normal_compay_addr:
                chooseAddress();
                break;
            case R.id.applycard_normal_contact_relationship:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 8), 10008);
                break;
            case R.id.applycard_normal_huankuantype:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 9), 10009);
                break;
            case R.id.applycard_normal_wangdian:
                lingQuWangDianDialog();
                break;
            case R.id.applycard_normal_commit:
                checkData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == 1000 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setMarlst(String.valueOf(commonTwoLevelMenuBean.getId()));
            mMaritalStatus.setRightText(commonTwoLevelMenuBean.getContext());
            mMaritalStatus.setRightTextColor(getResources().getColor(R.color.gray_33));
        } else if (requestCode == 10001 && resultCode == 1001 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setEdunlvl(String.valueOf(commonTwoLevelMenuBean.getId()));
            mEducationStatus.setRightText(commonTwoLevelMenuBean.getContext());
            mEducationStatus.setRightTextColor(getResources().getColor(R.color.gray_33));
        } else if (requestCode == 10002 && resultCode == 1002 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setResltp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mHouseStatus.setRightText(commonTwoLevelMenuBean.getContext());
            mHouseStatus.setRightTextColor(getResources().getColor(R.color.gray_33));
        } else if (requestCode == 10004 && resultCode == 1004 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setOcp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mZhiYe.setRightText(commonTwoLevelMenuBean.getContext());
            mZhiYe.setRightTextColor(getResources().getColor(R.color.gray_33));
        } else if (requestCode == 10005 && resultCode == 1005 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setCottl(String.valueOf(commonTwoLevelMenuBean.getId()));
            mZhiWu.setRightText(commonTwoLevelMenuBean.getContext());
            mZhiWu.setRightTextColor(getResources().getColor(R.color.gray_33));
        } else if (requestCode == 10006 && resultCode == 1006 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setCorptp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mCompanyType.setRightText(commonTwoLevelMenuBean.getContext());
            mCompanyType.setRightTextColor(getResources().getColor(R.color.gray_33));
        } else if (requestCode == 10007 && resultCode == 1007 && data != null) {
             CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                     data.getSerializableExtra("data");
             applyCTCardDTO.setGnd(String.valueOf(commonTwoLevelMenuBean.getId()));
        } else if (requestCode == 10008 && resultCode == 1008 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setCtc1(String.valueOf(commonTwoLevelMenuBean.getId()));
            applyCTCardDTO.setCtc2(String.valueOf(commonTwoLevelMenuBean.getId()));
            mRelationShip.setRightText(commonTwoLevelMenuBean.getContext());
            mRelationShip.setRightTextColor(getResources().getColor(R.color.gray_33));
        } else if (requestCode == 10009 && resultCode == 1009 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
//            ToastUtils.showShort(this,"选择了"+commonTwoLevelMenuBean.getId());
            applyCTCardDTO.setAutcrepymtmth(String.valueOf(commonTwoLevelMenuBean.getId()));

            mHuanKuanType.setRightText(commonTwoLevelMenuBean.getContext());
            mHuanKuanType.setRightTextColor(getResources().getColor(R.color.gray_33));
        }
    }

    /**
     * 赋初始值
     */
    private void initValue() {
        applyCTCardDTO.setOcp("1");
        mZhiYe.setRightText("公务员");
        mZhiYe.setRightTextColor(getResources().getColor(R.color.gray_33));
        applyCTCardDTO.setCottl("6");
        mZhiWu.setRightText("职员/科员级");
        mZhiWu.setRightTextColor(getResources().getColor(R.color.gray_33));
        applyCTCardDTO.setCorptp(String.valueOf("20"));
        mCompanyType.setRightText("集体");
        mCompanyType.setRightTextColor(getResources().getColor(R.color.gray_33));
        mCompanyAddr.setRightText("无");
    }
    /**
     * 选择自由职业赋值
     */
    private void chooseZiyouValue() {
        mCompanyName.setContentText("无");
        applyCTCardDTO.setConm("无");
        mDepartName.setContentText("无");
        applyCTCardDTO.setCorpsecr("无");
        mCompanyTel.setContentText("00000000");
        applyCTCardDTO.setCophoe("00000000");
        mZipCode.setContentText("201900");
        applyCTCardDTO.setCoadrzip("201900");
        applyCTCardDTO.setCoadrprov("无");
        applyCTCardDTO.setCoadrcity("无");
        applyCTCardDTO.setCoadrcnty("无");
        mCompanyDetailAddr.setContentText("无");
        applyCTCardDTO.setCoadr("无");
        applyCTCardDTO.setOcp("5");
        mZhiYe.setRightText("自由职业者");
        mZhiYe.setRightTextColor(getResources().getColor(R.color.gray_33));
    }

    /**
     * 选择企业赋值
     */
    private void chooseQiyeValue() {
        mCompanyName.setContentText("");
        applyCTCardDTO.setConm("");
        mDepartName.setContentText("");
        applyCTCardDTO.setCorpsecr("");
        mCompanyTel.setContentText("");
//        applyCTCardDTO.setCoadrprov("");
//        applyCTCardDTO.setCoadrcity("");
//        applyCTCardDTO.setCoadrcnty("");
        applyCTCardDTO.setCoadr("");
        mZipCode.setContentText("201900");
        applyCTCardDTO.setCoadrzip("201900");
        applyCTCardDTO.setOcp("30");
        mZhiYe.setRightText("私人业主");
        mZhiYe.setRightTextColor(getResources().getColor(R.color.gray_33));
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
                    applyCTCardDTO.setGetbrno(data[1]);
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
     * 选择地址
     */
    private void chooseAddress() {
        List<ProvinceModel> provinceList = null;
        try {
            InputStream input = getAssets().open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            PublicData.getInstance().provinceModel = provinceList;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        CityDialog dialog = new CityDialog(this, null, new CityDialog.OnChooseDialogListener() {
            @Override
            public void back(String[] data) {
                applyCTCardDTO.setHmadrprov(data[0]);
                applyCTCardDTO.setHmadrcity(data[1]);
                applyCTCardDTO.setHmadrcnty(data[2]);
                mAddr.setRightText(data[0] + "、" + data[1] + "、" + data[2]);
                mAddr.setRightTextColor(getResources().getColor(R.color.gray_33));
                matchingZipCode(data[2]);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }
    /**
     * 匹配邮编
     */
    private void matchingZipCode(String quStr) {
        String zipCode = "201900";
        if (quStr.contains("卢湾区")) {
            zipCode = "200020";
        } else if (quStr.contains("徐汇区")) {
            zipCode = "200030";
        } else if (quStr.contains("长宁区")) {
            zipCode = "200050";
        } else if (quStr.contains("静安区")) {
            zipCode = "200040";
        } else if (quStr.contains("普陀区")) {
            zipCode = "200333";
        } else if (quStr.contains("闸北区")) {
            zipCode = "200070";
        } else if (quStr.contains("虹口区")) {
            zipCode = "200080";
        } else if (quStr.contains("杨浦区")) {
            zipCode = "200082";
        } else if (quStr.contains("闵行区")) {
            zipCode = "201100";
        } else if (quStr.contains("宝山区")) {
            zipCode = "201900";
        } else if (quStr.contains("嘉定区")) {
            zipCode = "201800";
        } else if (quStr.contains("金山区")) {
            zipCode = "200540";
        } else if (quStr.contains("松江区")) {
            zipCode = "201600";
        } else if (quStr.contains("青浦区")) {
            zipCode = "201700";
        } else if (quStr.contains("南汇区")) {
            zipCode = "201300";
        } else if (quStr.contains("奉贤区")) {
            zipCode = "201400";
        } else if (quStr.contains("崇明县")) {
            zipCode = "202150";
        } else if (quStr.contains("黄浦区")) {
            zipCode = "200001";
        } else if (quStr.contains("浦东新区")) {
            zipCode = "200135";
        }
        applyCTCardDTO.setHmadrzip(zipCode);
    }
    private int mWorktype = 2;
    /**
     * 检测数据
     */
    private void checkData() {
        String pinyin = mUserNamePinYin.getContentText();
        String detailAddr = mDetailAddr.getContentText();
        String tel = mTel.getContentText();
        String contactName = mContactName.getContentText();
        String contactMobile = mContactMobile.getContentText();
        String kahao = mZhuanChuKaHao.getContentText();
        String email = mEmail.getContentText();
        if (TextUtils.isEmpty(pinyin)){
            ToastUtils.showShort(this,"姓名拼音不可为空");
            return;
        }
        if (TextUtils.isEmpty(detailAddr)){
            ToastUtils.showShort(this,"详细地址不可为空");
            return;
        }
        if (mWorktype == 2 && TextUtils.isEmpty(tel)){
            ToastUtils.showShort(this,"座机号不可为空");
            return;
        }
        if (TextUtils.isEmpty(contactName)){
            ToastUtils.showShort(this,"联系人姓名不可为空");
            return;
        }
        if (TextUtils.isEmpty(contactMobile)){
            ToastUtils.showShort(this,"联系人手机号不可为空");
            return;
        }
        if (TextUtils.isEmpty(kahao)){
            ToastUtils.showShort(this,"转出卡号不可为空");
            return;
        }
        if ("".equals(email)){
            email = "690635872@qq.com";
        }
        String bankData = mZhuanChuKaHao.getContentText();
        //autcrepymtmth 0是开通提醒 9是不开通
        if ("0".equals(applyCTCardDTO.getAutcrepymtmth()) && TextUtils.isEmpty(bankData)) {
            ToastUtils.showShort(this, "自动还款转出卡号不可为空");
            return;
        }
        if (TextUtils.isEmpty(applyCTCardDTO.getGetbrno())) {
            ToastUtils.showShort(this, "请选择领卡网点");
            return;
        }
        applyCTCardDTO.setAnulincm(mIncome.getContentText());
        applyCTCardDTO.setConm(mCompanyName.getContentText());
        applyCTCardDTO.setCorpsecr(mDepartName.getContentText());
        applyCTCardDTO.setCoadr(mCompanyDetailAddr.getContentText());
        applyCTCardDTO.setCoadrzip(mZipCode.getContentText());
        applyCTCardDTO.setCophoe(mCompanyTel.getContentText());
        applyCTCardDTO.setCtcnm1(contactName);
        applyCTCardDTO.setCtcnm2(contactName);
        applyCTCardDTO.setCtcphoenum1(contactMobile);
        applyCTCardDTO.setCtcphoenum2(contactMobile);
        applyCTCardDTO.setCtccophoe1(contactMobile);
        applyCTCardDTO.setCtccophoe2(contactMobile);

        applyCTCardDTO.setPhoenum(RSAUtils.strByEncryption(this, PublicData.getInstance().mLoginInfoBean.getPhoenum(), true));
        applyCTCardDTO.setBrthcty(applyCTCardDTO.getHmadrcity());
        applyCTCardDTO.setHmphoe(tel);
        applyCTCardDTO.setHmadr(mDetailAddr.getContentText());
        applyCTCardDTO.setEnghnm(pinyin);
        applyCTCardDTO.setTurnoutacnum(bankData);
        applyCTCardDTO.setElecmail(email);
        if (TextUtils.isEmpty(applyCTCardDTO.getDscode())
                && TextUtils.isEmpty(applyCTCardDTO.getDscodegs())) {
            applyCTCardDTO.setDscode("TZ666666");
            applyCTCardDTO.setDscodegs("TZ666666");
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
        Toast.makeText(ApplyCardSecondActivity.this, Config.getErrMsg("1"), Toast.LENGTH_SHORT).show();
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
                    ToastUtils.showShort(ApplyCardSecondActivity.this, "七天之内不能重复办卡");
                }
            }
        });
    }
    /**
     * 提交办卡信息
     */
    private void commitInfo() {
        showDialogLoading();
        HandleCTCardApiClient.htmlLocal(this, "cip.cfc.u007.01", applyCTCardDTO, this);
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
                    ToastUtils.showShort(ApplyCardSecondActivity.this,"已提交营销代码");
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
                    applyCTCardDTO.setDscode(result.getData().getEmpNum());
                    applyCTCardDTO.setDscodegs(result.getData().getEmpNum());
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
        picker = new DatePicker(ApplyCardSecondActivity.this);
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
                applyCTCardDTO.setCtfvldprd(mIdCardLayout2.getRightText());
                mIdCardLayout2.setRightTextColor(getResources().getColor(R.color.gray_33));

            }
        });
        picker.show();
    }
}
