package com.zantong.mobilecttx.card.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.card.bean.ProvinceModel;
import com.zantong.mobilecttx.common.activity.CommonTwoLevelMenuActivity;
import com.zantong.mobilecttx.card.dto.ApplyCTCardDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.ChineseToPinYin;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.DialogMgr;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.dialog.CityDialog;
import com.zantong.mobilecttx.utils.xmlparser.XmlParserHandler;
import com.zantong.mobilecttx.widght.SettingItemView;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

public class ApplyCardFristStepActvity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.frist_filenum_img)
    ImageView mFilenumImg;   //档案编号提示
    @Bind(R.id.frist_filenum)
    EditText mFilenum;
    @Bind(R.id.frist_name)
    EditText mName;
    @Bind(R.id.frist_name_pinyin)
    EditText mNamePinyin;
    @Bind(R.id.frist_gender)
    SettingItemView mGender;   //性别
    @Bind(R.id.frist_identity_card)
    EditText mIdentityCard;   //身份证
    @Bind(R.id.frist_youxiaoqi)
    SettingItemView mYouxiaoqi;  //身份证有效期
    @Bind(R.id.frist_birth_date)
    SettingItemView mBirthDate;  //出生日期
    @Bind(R.id.frist_guoji_img)
    ImageView mGuojiImg;   //国籍提示
    @Bind(R.id.frist_phone)
    EditText mPhone;
    @Bind(R.id.frist_hunyin_state)
    SettingItemView mHunyinState;   //婚姻状态
    @Bind(R.id.frist_jiaoyu_state)
    SettingItemView mJiaoyuState;   //受教育程度
    @Bind(R.id.frist_zhuzhai_state)
    SettingItemView mZhuzhaiState;   //住宅情况
    @Bind(R.id.frist_zhuzhai_quyu)
    SettingItemView mZhuzhaiQuyu;   //住宅区域
    @Bind(R.id.frist_address)
    EditText mAddress;   //详细地址
    @Bind(R.id.frist_zhuzhai_phone)
    EditText mZhuzhaiPhone;     //住宅电话
    @Bind(R.id.frist_next)
    Button mNext;

    private DatePicker picker;
    private ApplyCTCardDTO applyCTCardDTO;

    public static Intent getIntent(Context context, String fileNum, String name,
                                   String idCard, String yXdate) {
        Intent intent = new Intent(context, ApplyCardFristStepActvity.class);
        intent.putExtra("filenum", fileNum);
        intent.putExtra("name", name);
        intent.putExtra("idCard", idCard);
        intent.putExtra("date", yXdate);
        return intent;
    }

    @Override
    public void initView() {
        mPhone.setText(PublicData.getInstance().mLoginInfoBean.getPhoenum());
    }

    @Override
    public void initData() {
        setTitleText("申办畅通卡");
        applyCTCardDTO = new ApplyCTCardDTO();
        initValue();
    }

    /**
     * 初始赋值
     */
    private void initValue() {
        mFilenum.setText(getIntent().getStringExtra("filenum"));
        mFilenum.setFocusable(false);
        mName.setText(getIntent().getStringExtra("name"));
        mNamePinyin.setText(ChineseToPinYin.getPingYin(mName.getText().toString()));
        mName.setFocusable(false);
        mIdentityCard.setText(getIntent().getStringExtra("idCard"));
        mIdentityCard.setFocusable(false);
        if (getIntent().getStringExtra("date").equals("2099-10-31")) {
            mYouxiaoqi.setRightText("长期有效");
        } else {
            mYouxiaoqi.setRightText(getIntent().getStringExtra("date"));
        }
        applyCTCardDTO.setCtfvldprd(getIntent().getStringExtra("date"));
        mYouxiaoqi.setRightTextColor(getResources().getColor(R.color.gray_25));
        mYouxiaoqi.setClickable(false);
        applyCTCardDTO.setGnd(String.valueOf("1"));
        mGender.setRightText("男");
        mGender.setRightTextColor(getResources().getColor(R.color.gray_25));
        applyCTCardDTO.setMarlst(String.valueOf("1"));
        mHunyinState.setRightText("未婚");
        mHunyinState.setRightTextColor(getResources().getColor(R.color.gray_25));
        applyCTCardDTO.setEdunlvl(String.valueOf("1"));
        mJiaoyuState.setRightText("博士及以上");
        mJiaoyuState.setRightTextColor(getResources().getColor(R.color.gray_25));
        applyCTCardDTO.setResltp(String.valueOf("1"));
        mZhuzhaiState.setRightText("自有住房");
        mZhuzhaiState.setRightTextColor(getResources().getColor(R.color.gray_25));
    }

    @OnClick({R.id.frist_filenum_img, R.id.frist_gender, R.id.frist_youxiaoqi,
            R.id.frist_birth_date, R.id.frist_guoji_img, R.id.frist_hunyin_state,
            R.id.frist_jiaoyu_state, R.id.frist_zhuzhai_state,
            R.id.frist_zhuzhai_quyu, R.id.frist_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frist_filenum_img: //档案编号提示
                new DialogMgr(ApplyCardFristStepActvity.this, R.mipmap.code_query_notice_iamge);
                break;
            case R.id.frist_gender://性别
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 7), 10007);
                break;
            case R.id.frist_youxiaoqi://身份证有效期
                chooseDate(1);
                break;
            case R.id.frist_birth_date://出生日期
                chooseDate(2);
                break;
            case R.id.frist_guoji_img://国籍提示
                ToastUtils.showShort(this, "暂时只支持中国籍人士在线申办畅通卡");
                break;
            case R.id.frist_hunyin_state://婚姻状态
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 0), 10000);
                break;
            case R.id.frist_jiaoyu_state://受教育程度
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 1), 10001);
                break;
            case R.id.frist_zhuzhai_state://住宅情况
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 2), 10002);
                break;
            case R.id.frist_zhuzhai_quyu://住宅区域
                chooseAddress();
                break;
            case R.id.frist_next:
                checkData();
                break;
        }
    }

    /**
     * 校验数据
     */
    private void checkData() {
//        Intent intent = new Intent(this, ApplyCardSecondStepActvity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data", applyCTCardDTO);
//        intent.putExtras(bundle);
//        startActivity(intent);
        applyCTCardDTO.setUsrid(PublicData.getInstance().mLoginInfoBean.getUsrid());

        String fileNum = mFilenum.getText().toString();
        if (TextUtils.isEmpty(fileNum)) {
            ToastUtils.showShort(this, "驾驶证档案编号不可为空");
            return;
        }
        if (fileNum.length() < 12) {
            ToastUtils.showShort(this, "驾驶证档案编号不正确");
            return;
        }
        applyCTCardDTO.setFilenum(RSAUtils.strByEncryption(fileNum, true));

        String name = mName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(this, "姓名不可为空");
            return;
        }
        applyCTCardDTO.setUsrname(name);

        String pingYin = mNamePinyin.getText().toString();
        if (TextUtils.isEmpty(pingYin)) {
            ToastUtils.showShort(this, "姓名拼音不可为空");
            return;
        }
        applyCTCardDTO.setEnghnm(pingYin);

        if (TextUtils.isEmpty(applyCTCardDTO.getGnd())) {
            ToastUtils.showShort(this, "请选择性别");
            return;
        }

        String licenseno = mIdentityCard.getText().toString();
        if (TextUtils.isEmpty(licenseno)) {
            ToastUtils.showShort(this, "身份证号码不可为空");
            return;
        }
        if (!ValidateUtils.isIdCard(licenseno)) {
            ToastUtils.showShort(this, "身份证号码不正确");
            return;
        }
        applyCTCardDTO.setIssuoffic("0");
        applyCTCardDTO.setCtftp("0");
        applyCTCardDTO.setCtfnum(RSAUtils.strByEncryption(licenseno, true));

        if (TextUtils.isEmpty(applyCTCardDTO.getCtfvldprd())) {
            ToastUtils.showShort(this, "请选择证件有效期");
            return;
        }

        if (TextUtils.isEmpty(applyCTCardDTO.getDtofbrth())) {
            ToastUtils.showShort(this, "请选择出生日期");
            return;
        }

        String phone = mPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(this, "手机号码不可为空");
            return;
        }
        if (phone.length() != 11) {
            ToastUtils.showShort(this, "手机号码输入有误");
            return;
        }
        if (!ValidateUtils.isMobile(phone)) {
            ToastUtils.showShort(this, "手机号码不正确");
            return;
        }
        applyCTCardDTO.setPhoenum(RSAUtils.strByEncryption(phone, true));

        if (TextUtils.isEmpty(applyCTCardDTO.getMarlst())) {
            ToastUtils.showShort(this, "请选择婚姻状况");
            return;
        }

        if (TextUtils.isEmpty(applyCTCardDTO.getEdunlvl())) {
            ToastUtils.showShort(this, "请选择教育程度");
            return;
        }

        if (TextUtils.isEmpty(applyCTCardDTO.getResltp())) {
            ToastUtils.showShort(this, "请选择住宅类型");
            return;
        }

        if (TextUtils.isEmpty(applyCTCardDTO.getHmadrprov())) {
            ToastUtils.showShort(this, "请选择所在区域");
            return;
        }
        applyCTCardDTO.setBrthcty(applyCTCardDTO.getHmadrcity());

        String address = mAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShort(this, "详细地址不可为空");
            return;
        }
        applyCTCardDTO.setHmadr(address);
        applyCTCardDTO.setHmareacode("021");

        String zhuPhone = mZhuzhaiPhone.getText().toString();
        if (TextUtils.isEmpty(zhuPhone)) {
            ToastUtils.showShort(this, "住宅电话不可为空");
            return;
        }
        if (zhuPhone.length() != 8) {
            ToastUtils.showShort(this, "住宅电话输入有误");
            return;
        }
        applyCTCardDTO.setHmphoe(zhuPhone);
        applyCTCardDTO.setHmphoeexn("");
        applyCTCardDTO.setIndate("2015-01-01");

        Intent intent = new Intent(this, ApplyCardSecondStepActvity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", applyCTCardDTO);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10007 && resultCode == 1007 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setGnd(String.valueOf(commonTwoLevelMenuBean.getId()));
            mGender.setRightText(commonTwoLevelMenuBean.getContext());
            mGender.setRightTextColor(getResources().getColor(R.color.gray_25));
        } else if (requestCode == 10000 && resultCode == 1000 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setMarlst(String.valueOf(commonTwoLevelMenuBean.getId()));
            mHunyinState.setRightText(commonTwoLevelMenuBean.getContext());
            mHunyinState.setRightTextColor(getResources().getColor(R.color.gray_25));
        } else if (requestCode == 10001 && resultCode == 1001 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setEdunlvl(String.valueOf(commonTwoLevelMenuBean.getId()));
            mJiaoyuState.setRightText(commonTwoLevelMenuBean.getContext());
            mJiaoyuState.setRightTextColor(getResources().getColor(R.color.gray_25));
        } else if (requestCode == 10002 && resultCode == 1002 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setResltp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mZhuzhaiState.setRightText(commonTwoLevelMenuBean.getContext());
            mZhuzhaiState.setRightTextColor(getResources().getColor(R.color.gray_25));
        }
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
                mZhuzhaiQuyu.setRightText(data[0] + "、" + data[1] + "、" + data[2]);
                mZhuzhaiQuyu.setRightTextColor(getResources().getColor(R.color.gray_25));
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

    /**
     * 选择时间
     * 1.身份证有效期   2.出生日期
     */
    private void chooseDate(final int form) {
        picker = new DatePicker(ApplyCardFristStepActvity.this);
        String temp = "";
        if (form == 1) {
            temp = mYouxiaoqi.getRightText();
            picker.setRangeEnd(DateUtils.getYear() + 70, DateUtils.getMonth(), DateUtils.getDay());
            picker.setRangeStart(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        } else if (form == 2) {
            temp = mBirthDate.getRightText();
            picker.setRangeStart(DateUtils.getYear() - 70, DateUtils.getMonth(), DateUtils.getDay());
            picker.setRangeEnd(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        }
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
                if (form == 1) {
                    mYouxiaoqi.setRightText(year + "-" + month + "-" + day);
                    applyCTCardDTO.setCtfvldprd(mYouxiaoqi.getRightText());
                    mYouxiaoqi.setRightTextColor(getResources().getColor(R.color.gray_25));
                } else if (form == 2) {
                    mBirthDate.setRightText(year + "-" + month + "-" + day);
                    applyCTCardDTO.setDtofbrth(mBirthDate.getRightText());
                    mBirthDate.setRightTextColor(getResources().getColor(R.color.gray_25));
                }

            }
        });
        picker.show();
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_bid_frist_step;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PublicData.getInstance().filenum = "";
    }

}
