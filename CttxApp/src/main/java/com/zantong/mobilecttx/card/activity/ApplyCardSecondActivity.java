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

import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.FileDownloadApi;
import com.zantong.mobilecttx.api.HandleCTCardApiClient;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.bean.YingXiaoResponse;
import com.zantong.mobilecttx.card.dto.ApplyCTCardDTO;
import com.zantong.mobilecttx.card.dto.CheckCtkDTO;
import com.zantong.mobilecttx.common.activity.CommonTwoLevelMenuActivity;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.utils.ChineseToPinYin;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.ReadFfile;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.dialog.CityDialog;
import com.zantong.mobilecttx.utils.dialog.NetLocationDialog;
import com.zantong.mobilecttx.utils.xmlparser.XmlParserHandler;
import com.zantong.mobilecttx.widght.CttxEditText;
import com.zantong.mobilecttx.widght.SettingItemView;
import com.zantong.mobilecttx.widght.UISwitchButton;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.bean.BankResponse;
import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.custom.picker.DatePicker;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.RegexUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.log.LogUtils;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 正常办卡信息
 */
public class ApplyCardSecondActivity extends BaseMvpActivity<IBaseView, HelpPresenter>
        implements HandleCTCardApiClient.ResultInterface {

    @Bind(R.id.applycard_normal_filenum)
    CttxEditText mFileNum;
    @Bind(R.id.applycard_normal_username)
    CttxEditText mUserName;
    @Bind(R.id.applycard_normal_username_pinyin)
    CttxEditText mUserNamePinYin;
    @Bind(R.id.cedit_gender)
    SettingItemView mUserGender;
    @Bind(R.id.sivTv_birth)
    SettingItemView mUserBirth;

    @Bind(R.id.applycard_normal_idcard_num)
    CttxEditText mIdCardNum;
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
    @Bind(R.id.user_phone)
    CttxEditText mUserPhone;
    @Bind(R.id.applycard_normal_idcard_layout1)
    View mIdCardLayout1;
    @Bind(R.id.applycard_normal_idcard_flag)
    ImageView mIdCardForeverImg;

    @Bind(R.id.applycard_normal_idcard_layout2)
    SettingItemView mIdCardLayout2;
    //婚姻状况
    @Bind(R.id.applycard_normal_marital_status)
    SettingItemView mMaritalStatus;
    @Bind(R.id.applycard_normal_education_status)
    SettingItemView mEducationStatus;
    @Bind(R.id.applycard_normal_house_status)
    SettingItemView mHouseStatus;
    //住宅区域
    @Bind(R.id.applycard_normal_addr)
    SettingItemView mAddr;
    @Bind(R.id.applycard_normal_detail_addr)
    CttxEditText mDetailAddr;
    @Bind(R.id.applycard_normal_tel)
    CttxEditText mTel;

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
    //声明
    @Bind(R.id.applycard_normal_hint)
    TextView mNormalHint;

    /**
     * 请求beanDTO
     */
    private ApplyCTCardDTO applyCTCardDTO = new ApplyCTCardDTO();

    private String mMarketingCode;//获取的营销代码
    /**
     * 自由职业==1
     */
    private int mWorkType = 2;
    /**
     * 领卡网点地址
     */
    private String wangdianAdress;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginData.getInstance().filenum = "";
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
    public void initView() {
        setTitleText("申办畅通卡");
        mNormalHint.setText(Html.fromHtml(getResources().getString(R.string.apply_four_hint)));

        getYingXiaoCode();
        downloadTxt();

        mFileNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogMgr(ApplyCardSecondActivity.this, R.mipmap.code_query_notice_iamge);
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String userName = intent.getStringExtra("name");
        String idCard = intent.getStringExtra("idCard");
        String drivingNum = intent.getStringExtra("filenum");
//驾档号
        mFileNum.setContentText(drivingNum);
        applyCTCardDTO.setFilenum(RSAUtils.strByEncryption(drivingNum, true));
//姓名
        mUserName.setContentText(userName);
        applyCTCardDTO.setUsrname(userName);

        String pinyin = ChineseToPinYin.getPingYin(userName);
        mUserNamePinYin.setContentText(pinyin);
        applyCTCardDTO.setEnghnm(pinyin);
//性别
        String mYear;
        String mMonth;
        String mDay;
        if (idCard.length() == 15) {
            int temp = Integer.valueOf(idCard.substring(14, 15)) % 2;
            applyCTCardDTO.setGnd(temp == 0 ? "2" : "1"); // 1男  2女
            mYear = idCard.substring(6, 8);
            mMonth = idCard.substring(8, 10);
            mDay = idCard.substring(10, 12);
            applyCTCardDTO.setDtofbrth(mYear + "-" + mMonth + "-" + mDay);
            mUserBirth.setRightText(mYear + "-" + mMonth + "-" + mDay);
        } else if (idCard.length() == 18) {
            int temp = Integer.valueOf(idCard.substring(16, 17)) % 2;
            applyCTCardDTO.setGnd(temp == 0 ? "2" : "1");
            mYear = idCard.substring(6, 10);
            mMonth = idCard.substring(10, 12);
            mDay = idCard.substring(12, 14);
            applyCTCardDTO.setDtofbrth(mYear + "-" + mMonth + "-" + mDay);
            mUserBirth.setRightText(mYear + "-" + mMonth + "-" + mDay);
        } else {
            applyCTCardDTO.setGnd("1");
        }
        mUserGender.setRightText(applyCTCardDTO.getGnd().equals("1") ? "男" : "女");
//身份证
        initIDNumber();

        mIdCardNum.setContentText(idCard);
        applyCTCardDTO.setCtfnum(RSAUtils.strByEncryption(idCard, true));
//手机号
        mUserPhone.setContentText(LoginData.getInstance().mLoginInfoBean != null ?
                LoginData.getInstance().mLoginInfoBean.getPhoenum() : "");

//婚姻状况
        applyCTCardDTO.setMarlst("1");
        mMaritalStatus.setRightText("未婚");
        mMaritalStatus.setRightTextColor(getResources().getColor(R.color.gray_33));
//受教育程度
        applyCTCardDTO.setEdunlvl("3");
        mEducationStatus.setRightText("大学本科");
        mEducationStatus.setRightTextColor(getResources().getColor(R.color.gray_33));
//请选择住宅情况
        applyCTCardDTO.setResltp("1");
        mHouseStatus.setRightText("自有住房");
        mHouseStatus.setRightTextColor(getResources().getColor(R.color.gray_33));

        chooseQiyeValue();
//朋友
        applyCTCardDTO.setCtc1("8");
        applyCTCardDTO.setCtc2("8");
        mRelationShip.setRightText("朋友");
        mRelationShip.setRightTextColor(getResources().getColor(R.color.gray_33));

//证件类型
        applyCTCardDTO.setCtftp("0");

        applyCTCardDTO.setUsrid(SPUtils.getInstance().getLoginInfoBean().getUsrid());

        applyCTCardDTO.setActnotf("1");
        applyCTCardDTO.setElecbillsign("0");
        applyCTCardDTO.setIssuoffic("0");
        applyCTCardDTO.setHmareacode("021");
        applyCTCardDTO.setIndate("2015-01-01");
        applyCTCardDTO.setHmphoeexn("");

        applyCTCardDTO.setIdycgy("1");
        applyCTCardDTO.setJoindate("201501");
        applyCTCardDTO.setCophoeexn("");
        applyCTCardDTO.setCoareacode("021");
        applyCTCardDTO.setCtc1("8");
        applyCTCardDTO.setCtc2("8");
//联系人1单位电话分机号
        applyCTCardDTO.setCtccophoeexn1("");
        applyCTCardDTO.setCtccophoeexn2("");
        applyCTCardDTO.setCtchmadr1("无");
        applyCTCardDTO.setCtchmadr2("无");
//联系人1住宅邮编
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
        applyCTCardDTO.setAutcrepymtmth("9");

        mDuiZhangTiXing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                applyCTCardDTO.setActnotf(isChecked ? "1" : "0");
            }
        });
        mHuanKuanTiXing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                applyCTCardDTO.setElecbillsign(isChecked ? "1" : "0");
            }
        });
    }

    @OnClick({R.id.applycard_normal_idcard_layout1, R.id.applycard_normal_idcard_layout2, R.id.applycard_normal_marital_status,
            R.id.applycard_normal_education_status, R.id.applycard_normal_house_status, R.id.applycard_normal_addr,
            R.id.applycard_normal_ziyou_layout, R.id.applycard_normal_company_layout, R.id.applycard_normal_company_type,
            R.id.applycard_normal_zhiwu, R.id.applycard_normal_zhiye, R.id.applycard_normal_compay_addr,
            R.id.applycard_normal_contact_relationship, R.id.applycard_normal_huankuantype, R.id.applycard_normal_wangdian,
            R.id.applycard_normal_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.applycard_normal_idcard_layout1://身份证有效期
                initIDNumber();
                break;
            case R.id.applycard_normal_idcard_layout2://选择有效日期
                mIdCardForeverImg.setBackgroundResource(R.mipmap.checkbox_normal);
                identityCardDate();
                break;
            case R.id.applycard_normal_marital_status://请选择婚姻状况
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 0), 10000);
                break;
            case R.id.applycard_normal_education_status://请选择受教育程度
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 1), 10001);
                break;
            case R.id.applycard_normal_house_status://请选择住宅情况
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 2), 10002);
                break;
            case R.id.applycard_normal_addr://请选择住宅所在区域
                chooseAddress(1);
                break;
            case R.id.applycard_normal_ziyou_layout://自由职业
                mWorkType = 1;
                mZiyouImg.setBackgroundResource(R.mipmap.checkbox_checked);
                mCompanyImg.setBackgroundResource(R.mipmap.checkbox_normal);
                initCivilInfo();
                initFreelancerValue();
                break;
            case R.id.applycard_normal_company_layout://企事业单位
                mWorkType = 2;
                mZiyouImg.setBackgroundResource(R.mipmap.checkbox_normal);
                mCompanyImg.setBackgroundResource(R.mipmap.checkbox_checked);
                chooseQiyeValue();
                break;
            case R.id.applycard_normal_company_type://单位性质
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 6), 10006);
                break;
            case R.id.applycard_normal_zhiwu://职务
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 5), 10005);
                break;
            case R.id.applycard_normal_zhiye://职业
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 4), 10004);
                break;
            case R.id.applycard_normal_compay_addr://单位地址区域
                chooseAddress(2);
                break;
            case R.id.applycard_normal_contact_relationship://朋友
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 8), 10008);
                break;
            case R.id.applycard_normal_huankuantype://自动还款类型
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 9), 10009);
                break;
            case R.id.applycard_normal_wangdian://领卡网点
                lingQuWangDianDialog();
                break;
            case R.id.applycard_normal_commit:
                checkData();
                break;
            default:
                break;
        }
    }

    /**
     * 身份证长期有效
     */
    private void initIDNumber() {
        mIdCardForeverImg.setBackgroundResource(R.mipmap.checkbox_checked);
        mIdCardLayout2.setRightText("年/月/日");
        mIdCardLayout2.setRightTextColor(getResources().getColor(R.color.gray_99));

        applyCTCardDTO.setCtfvldprd("9999-12-30");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == 1000 && data != null) {//请选择婚姻状况
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setMarlst(String.valueOf(commonTwoLevelMenuBean.getId()));
            mMaritalStatus.setRightText(commonTwoLevelMenuBean.getContext());
            mMaritalStatus.setRightTextColor(getResources().getColor(R.color.gray_33));

        } else if (requestCode == 10001 && resultCode == 1001 && data != null) {//受教育程度
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setEdunlvl(String.valueOf(commonTwoLevelMenuBean.getId()));
            mEducationStatus.setRightText(commonTwoLevelMenuBean.getContext());
            mEducationStatus.setRightTextColor(getResources().getColor(R.color.gray_33));

        } else if (requestCode == 10002 && resultCode == 1002 && data != null) {//请选择住宅情况
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setResltp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mHouseStatus.setRightText(commonTwoLevelMenuBean.getContext());
            mHouseStatus.setRightTextColor(getResources().getColor(R.color.gray_33));

        } else if (requestCode == 10004 && resultCode == 1004 && data != null) {//职业
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setOcp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mZhiYe.setRightText(commonTwoLevelMenuBean.getContext());
            mZhiYe.setRightTextColor(getResources().getColor(R.color.gray_33));

        } else if (requestCode == 10005 && resultCode == 1005 && data != null) {//职务
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setCottl(String.valueOf(commonTwoLevelMenuBean.getId()));
            mZhiWu.setRightText(commonTwoLevelMenuBean.getContext());
            mZhiWu.setRightTextColor(getResources().getColor(R.color.gray_33));

        } else if (requestCode == 10006 && resultCode == 1006 && data != null) {//单位性质
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setCorptp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mCompanyType.setRightText(commonTwoLevelMenuBean.getContext());
            mCompanyType.setRightTextColor(getResources().getColor(R.color.gray_33));

        } else if (requestCode == 10007 && resultCode == 1007 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setGnd(String.valueOf(commonTwoLevelMenuBean.getId()));
        } else if (requestCode == 10008 && resultCode == 1008 && data != null) {////朋友
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setCtc1(String.valueOf(commonTwoLevelMenuBean.getId()));
            applyCTCardDTO.setCtc2(String.valueOf(commonTwoLevelMenuBean.getId()));
            mRelationShip.setRightText(commonTwoLevelMenuBean.getContext());
            mRelationShip.setRightTextColor(getResources().getColor(R.color.gray_33));

        } else if (requestCode == 10009 && resultCode == 1009 && data != null) {////自动还款类型
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean) data.getSerializableExtra("data");
            applyCTCardDTO.setAutcrepymtmth(String.valueOf(commonTwoLevelMenuBean.getId()));
            mHuanKuanType.setRightText(commonTwoLevelMenuBean.getContext());
            mHuanKuanType.setRightTextColor(getResources().getColor(R.color.gray_33));
        }
    }

    /**
     * 赋初始值 公务员信息
     */
    private void initCivilInfo() {
        mZhiYe.setRightText("公务员");
        mZhiYe.setRightTextColor(getResources().getColor(R.color.gray_33));
        applyCTCardDTO.setOcp("1");

        mZhiWu.setRightText("职员/科员级");
        mZhiWu.setRightTextColor(getResources().getColor(R.color.gray_33));
        applyCTCardDTO.setCottl("6");

        mCompanyAddr.setRightText("无");

        mCompanyType.setRightText("集体");
        mCompanyType.setRightTextColor(getResources().getColor(R.color.gray_33));
        applyCTCardDTO.setCorptp(String.valueOf("20"));
    }

    /**
     * 选择自由职业赋值
     */
    private void initFreelancerValue() {
        mCompanyName.setContentText("无");
        applyCTCardDTO.setConm("无");

        mDepartName.setContentText("无");
        applyCTCardDTO.setCorpsecr("无");
//公司电话
        mCompanyTel.setContentText("00000000");
        applyCTCardDTO.setCophoe("00000000");
//公司邮编
        mZipCode.setContentText("201900");
        applyCTCardDTO.setCoadrzip("201900");
//公司地址区域
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
        applyCTCardDTO.setCoadr("");

        mZipCode.setContentText("201900");
        applyCTCardDTO.setCoadrzip("201900");

        //单位性质
        applyCTCardDTO.setCorptp("20");
        mCompanyType.setRightText("集体");
        mCompanyType.setRightTextColor(getResources().getColor(R.color.gray_33));
//职务
        applyCTCardDTO.setCottl("6");
        mZhiWu.setRightText("职员/科员级");
        mZhiWu.setRightTextColor(getResources().getColor(R.color.gray_33));
//职业
        applyCTCardDTO.setOcp("1");
        mZhiYe.setRightText("公务员");
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
//领卡网点
                    applyCTCardDTO.setGetbrno(data[1]);
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
     * 选择地址 1==住宅  2==单位
     */
    private void chooseAddress(final int mType) {
        // 创建一个解析xml的工厂对象
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        XmlParserHandler parserHandler = new XmlParserHandler();
        try {
            InputStream inputStream = getAssets().open("province_data.xml");
            // 解析xml
            SAXParser saxParser = parserFactory.newSAXParser();
            saxParser.parse(inputStream, parserHandler);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        // 获取解析出来的数据
        LoginData.getInstance().provinceModel = parserHandler.getDataList();

        CityDialog dialog = new CityDialog(this, null, new CityDialog.OnChooseDialogListener() {
            @Override
            public void back(String[] data) {
                if (mType == 1) {
                    applyCTCardDTO.setHmadrprov(data[0]);
                    applyCTCardDTO.setHmadrcity(data[1]);
                    applyCTCardDTO.setHmadrcnty(data[2]);
                    mAddr.setRightText(data[0] + "、" + data[1] + "、" + data[2]);
                    mAddr.setRightTextColor(getResources().getColor(R.color.gray_33));
                } else {
                    applyCTCardDTO.setCoadrprov(data[0]);
                    applyCTCardDTO.setCoadrcity(data[1]);
                    applyCTCardDTO.setCoadrcnty(data[2]);

                    mCompanyAddr.setRightText(data[0] + "、" + data[1] + "、" + data[2]);
                    mCompanyAddr.setRightTextColor(getResources().getColor(R.color.gray_33));
                }

                matchingZipCode(data[2], mType);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    /**
     * 匹配邮编
     */
    private void matchingZipCode(String quStr, int mType) {
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
//住宅邮编
        if (mType == 1) {
            applyCTCardDTO.setHmadrzip(zipCode);
        } else {
            mZipCode.setContentText(zipCode);
            applyCTCardDTO.setCoadrzip(zipCode);
        }
    }

    /**
     * 检测数据
     */
    private void checkData() {
        String pinyin = mUserNamePinYin.getContentText();
        if (checkIsEmpty(pinyin, "姓名拼音不可为空")) return;
        applyCTCardDTO.setEnghnm(pinyin);
//手机号码
        String userPhone = mUserPhone.getContentText();
        if (checkIsEmpty(userPhone, "手机号不可为空")) return;
        if (!RegexUtils.isMobileSimple(userPhone)) {
            ToastUtils.toastShort("请确保手机号真实准确");
            return;
        }
        applyCTCardDTO.setPhoenum(RSAUtils.strByEncryption(userPhone, true));

//住宅地址
        String addr = mAddr.getRightText();
        if (checkIsEmpty(addr, "住宅区域不可为空")) return;

        String detailAddr = mDetailAddr.getContentText();
        if (checkIsEmpty(detailAddr, "住宅详细地址不可为空")) return;
        applyCTCardDTO.setHmadr(detailAddr);

        String tel = mTel.getContentText();
        if (checkIsEmpty(tel, "固定电话不可为空")) return;
        if (tel.length() != 8) {
            ToastUtils.toastShort("请确保固定座机8位有效");
            return;
        }
        applyCTCardDTO.setHmphoe(tel);

        String income = mIncome.getContentText();
        if (checkIsEmpty(income, "年收入不可为空")) return;
        applyCTCardDTO.setAnulincm(income);

        String companyName = mCompanyName.getContentText();
        if (checkIsEmpty(companyName, "单位名称不可为空")) return;
        applyCTCardDTO.setConm(companyName);

        String departName = mDepartName.getContentText();
        if (checkIsEmpty(companyName, "部门名称不可为空")) return;
        applyCTCardDTO.setCorpsecr(departName);

        String companyAddr = mCompanyAddr.getRightText();
        if (checkIsEmpty(companyAddr, "单位区域不可为空")) return;

        String companyDetailAddr = mCompanyDetailAddr.getContentText();
        if (checkIsEmpty(companyDetailAddr, "单位详细地址不可为空")) return;
        applyCTCardDTO.setCoadr(companyDetailAddr);

        String zipCode = mZipCode.getContentText();
        if (checkIsEmpty(zipCode, "单位邮编不可为空")) return;
        applyCTCardDTO.setCoadrzip(zipCode);

        String companyTel = mCompanyTel.getContentText();
        if (checkIsEmpty(companyTel, "单位电话不可为空")) return;
        if (companyTel.length() != 8) {
            ToastUtils.toastShort("请确保单位固定座机8位有效");
            return;
        }
        applyCTCardDTO.setCophoe(companyTel);
//联系人
        String contactName = mContactName.getContentText();
        if (checkIsEmpty(contactName, "联系人姓名不可为空")) return;
        applyCTCardDTO.setCtcnm1(contactName);
        applyCTCardDTO.setCtcnm2(contactName);

        String contactMobile = mContactMobile.getContentText();
        if (checkIsEmpty(contactMobile, "联系人手机号不可为空")) return;
        if (!RegexUtils.isMobileSimple(contactMobile)) {
            ToastUtils.toastShort("请确保联系人手机号真实准确");
            return;
        }
        applyCTCardDTO.setCtcphoenum1(contactMobile);
        applyCTCardDTO.setCtcphoenum2(contactMobile);
        applyCTCardDTO.setCtccophoe1(contactMobile);
        applyCTCardDTO.setCtccophoe2(contactMobile);

        String email = mEmail.getContentText();
        if ("".equals(email)) email = "690635872@qq.com";
        applyCTCardDTO.setElecmail(email);

        String bankData = mZhuanChuKaHao.getContentText();
        //autcrepymtmth 0是开通提醒 9是不开通
        if ("0".equals(applyCTCardDTO.getAutcrepymtmth()) && TextUtils.isEmpty(bankData)) {
            ToastUtils.toastShort("自动还款转出卡号不可为空");
            return;
        }
        applyCTCardDTO.setTurnoutacnum(bankData);

        String brno = applyCTCardDTO.getGetbrno();
        if (checkIsEmpty(brno, "请选择领卡网点")) return;
        applyCTCardDTO.setGetbrno(brno);

        applyCTCardDTO.setBrthcty(applyCTCardDTO.getHmadrcity());

        if (TextUtils.isEmpty(applyCTCardDTO.getDscode()) && TextUtils.isEmpty(applyCTCardDTO.getDscodegs())) {
            applyCTCardDTO.setDscode("TZ666666");
            applyCTCardDTO.setDscodegs("TZ666666");
        }

        commitInfo();
    }

    /**
     * 检查是否为空
     */
    private boolean checkIsEmpty(String msg, String toast) {
        if (TextUtils.isEmpty(msg) || msg.length() <= 0) {
            ToastUtils.toastShort(toast);
            return true;
        }
        return false;
    }

    /**
     * 提交办卡信息
     */
    private void commitInfo() {
        showDialogLoading();

        HandleCTCardApiClient.htmlLocal(ContextUtils.getContext(), "cip.cfc.u007.01", applyCTCardDTO, this);
    }

    /**
     * 办卡成功返回
     */
    @Override
    public void resultSuccess(BankResponse bankResponse) {
        hideDialogLoading();
        if (bankResponse.getSYS_HEAD().getReturnCode().equals("000000")) {

            if (TextUtils.isEmpty(wangdianAdress)) {
                ToastUtils.toastShort("请选择领卡网点");
            } else {
                commitYingXiaoDataForLYT(applyCTCardDTO);
            }
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
     * 办卡申请成功后，提交营销代码
     *
     * @param applyCTCardDTO
     */
    private void commitYingXiaoDataForLYT(ApplyCTCardDTO applyCTCardDTO) {
        CarApiClient.commitYingXiaoData(ContextUtils.getContext(), applyCTCardDTO,
                new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                        startActivity(ApplySuccessActvity.getIntent(ApplyCardSecondActivity.this, wangdianAdress));
                    }

                    @Override
                    public void onError(String errorCode, String msg) {
                        startActivity(ApplySuccessActvity.getIntent(ApplyCardSecondActivity.this, wangdianAdress));
                    }
                });
    }

    /**
     * 申办畅通卡时间校验接口
     */
    private void checkCtkDate() {
        CheckCtkDTO checkCtkDTO = new CheckCtkDTO();
        checkCtkDTO.setApplyCode(LoginData.getInstance().filenum);
        checkCtkDTO.setApplyInterface("banka");
        checkCtkDTO.setFlag("1");
        CarApiClient.checkCtk(getApplicationContext(), checkCtkDTO, new CallBack<BaseResponse>() {
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LoginData.getInstance().mNetLocationBean
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
        CarApiClient.getYingXiaoCode(getApplicationContext(), dto, new CallBack<YingXiaoResponse>() {
            @Override
            public void onSuccess(YingXiaoResponse result) {
                if (result.getResponseCode() == 2000 && result.getData() != null) {
                    mMarketingCode = result.getData().getEmpNum();
                    //TODO 手动不显示
//                    mYingXiaoCode.setContentText(mMarketingCode);

                    applyCTCardDTO.setDscode(mMarketingCode);
                    applyCTCardDTO.setDscodegs(mMarketingCode);
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
    private void identityCardDate() {
        DatePicker datePicker = new DatePicker(this);
        String dateString = mIdCardLayout2.getRightText();
        datePicker.setRangeEnd(DateUtils.getYear() + 70, DateUtils.getMonth(), DateUtils.getDay());
        datePicker.setRangeStart(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());

        if (dateString.contains("请输入")) dateString = "";

        if (!TextUtils.isEmpty(dateString)) {
            String date = dateString.replace("-", "");
            if (!TextUtils.isEmpty(date) && date.length() >= 8) {
                int year = Integer.valueOf(date.substring(0, 4));
                int month = Integer.valueOf(date.substring(4, 6));
                int day = Integer.valueOf(date.substring(6, 8));
                datePicker.setSelectedItem(year, month, day);
            } else {
                datePicker.setSelectedItem(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
            }
        } else {
            datePicker.setSelectedItem(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        }

        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                mIdCardLayout2.setRightText(year + "-" + month + "-" + day);
                mIdCardLayout2.setRightTextColor(getResources().getColor(R.color.gray_33));
//证件有效期
                applyCTCardDTO.setCtfvldprd(mIdCardLayout2.getRightText());

            }
        });
        datePicker.show();
    }

}
