package com.zantong.mobilecttx.card.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.card.bean.ProvinceModel;
import com.zantong.mobilecttx.common.activity.CommonTwoLevelMenuActivity;
import com.zantong.mobilecttx.card.dto.ApplyCTCardDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.dialog.CityDialog;
import com.zantong.mobilecttx.utils.xmlparser.XmlParserHandler;
import com.zantong.mobilecttx.widght.SettingItemView;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.OnClick;

public class ApplyCardSecondStepActvity extends BaseMvpActivity<IBaseView, HelpPresenter> {


    @Bind(R.id.second_money)
    EditText mMoney;
    @Bind(R.id.second_ziyou)
    ImageView mZiyou;
    @Bind(R.id.second_ziyou_layout)
    RelativeLayout mZiyouLayout;
    @Bind(R.id.second_qiye)
    ImageView mQiye;
    @Bind(R.id.second_qiye_layout)
    RelativeLayout mQiyeLayout;
    @Bind(R.id.second_danwei_name)
    EditText mDanweiName;
    @Bind(R.id.second_danwei_xingzhi)
    SettingItemView mDanweiXingzhi;
    @Bind(R.id.second_suozai_bumen)
    EditText mSuozaiBumen;
    @Bind(R.id.second_zhiwu)
    SettingItemView mZhiwu;
    @Bind(R.id.second_zhiye)
    SettingItemView mZhiye;
    @Bind(R.id.second_danwei_quyu)
    SettingItemView mDanweiQuyu;
    @Bind(R.id.second_danwei_detail)
    EditText mDanweiDetail;
    @Bind(R.id.second_youbian)
    EditText mYoubian;
    @Bind(R.id.second_danwei_phone)
    EditText mDanweiPhone;
    @Bind(R.id.frist_next)
    Button mNext;
    private ApplyCTCardDTO applyCTCardDTO;

    private int mWorktype = 2;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        setTitleText("申办畅通卡");
        applyCTCardDTO = (ApplyCTCardDTO) getIntent().getSerializableExtra("data");
        initValue();
    }

    @OnClick({R.id.second_ziyou_layout, R.id.second_qiye_layout, R.id.second_danwei_xingzhi,
            R.id.second_zhiwu, R.id.second_zhiye, R.id.second_danwei_quyu, R.id.frist_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.second_ziyou_layout:
                mWorktype = 1;
                chooseZhiyeState(1);
                break;
            case R.id.second_qiye_layout:
                mWorktype = 2;
                chooseZhiyeState(2);
                break;
            case R.id.second_danwei_xingzhi:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 6), 10006);
                break;
            case R.id.second_zhiwu:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 5), 10005);
                break;
            case R.id.second_zhiye:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 4), 10004);
                break;
            case R.id.second_danwei_quyu:
                chooseAddress();
                break;
            case R.id.frist_next:
                checkData();
                break;
        }
    }

    /**
     * 选择就职情况
     */
    private void chooseZhiyeState(int from) {
        if (from == 1) {
            mQiye.setBackgroundResource(R.mipmap.checkbox_normal);
            mZiyou.setBackgroundResource(R.mipmap.checkbox_checked);
            initValue();
            chooseZiyouValue();
        } else if (from == 2) {
            mZiyou.setBackgroundResource(R.mipmap.checkbox_normal);
            mQiye.setBackgroundResource(R.mipmap.checkbox_checked);
            chooseQiyeValue();
        }
    }

    /**
     * 选择自由职业赋值
     */
    private void chooseZiyouValue() {
        mDanweiName.setText("无");
        applyCTCardDTO.setConm("无");
        mSuozaiBumen.setText("无");
        applyCTCardDTO.setCorpsecr("无");
        mDanweiPhone.setText("00000000");
        applyCTCardDTO.setCophoe("00000000");
        mYoubian.setText("201900");
        applyCTCardDTO.setCoadrzip("201900");
        applyCTCardDTO.setCoadrprov("无");
        applyCTCardDTO.setCoadrcity("无");
        applyCTCardDTO.setCoadrcnty("无");
        mDanweiDetail.setText("无");
        applyCTCardDTO.setCoadr("无");
        applyCTCardDTO.setOcp("5");
        mZhiye.setRightText("自由职业者");
        mZhiye.setRightTextColor(getResources().getColor(R.color.gray_25));
    }

    /**
     * 选择企业赋值
     */
    private void chooseQiyeValue() {
        mDanweiName.setText("");
        applyCTCardDTO.setConm("");
        mSuozaiBumen.setText("");
        applyCTCardDTO.setCorpsecr("");
        mDanweiPhone.setText("");
//        applyCTCardDTO.setCoadrprov("");
//        applyCTCardDTO.setCoadrcity("");
//        applyCTCardDTO.setCoadrcnty("");
        applyCTCardDTO.setCoadr("");
        mYoubian.setText("201900");
        applyCTCardDTO.setCoadrzip("201900");
        applyCTCardDTO.setOcp("30");
        mZhiye.setRightText("私人业主");
        mZhiye.setRightTextColor(getResources().getColor(R.color.gray_25));
    }

    /**
     * 赋初始值
     */
    private void initValue() {
        applyCTCardDTO.setOcp("1");
        mZhiye.setRightText("公务员");
        mZhiye.setRightTextColor(getResources().getColor(R.color.gray_25));
        applyCTCardDTO.setCottl("6");
        mZhiwu.setRightText("职员/科员级");
        mZhiwu.setRightTextColor(getResources().getColor(R.color.gray_25));
        applyCTCardDTO.setCorptp(String.valueOf("20"));
        mDanweiXingzhi.setRightText("集体");
        mDanweiXingzhi.setRightTextColor(getResources().getColor(R.color.gray_25));
        mDanweiQuyu.setRightText("无");
    }

    /**
     * 校验数据
     */
    private void checkData() {
//        Intent intent = new Intent(this, ApplyCardThirdStepActvity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data", applyCTCardDTO);
//        intent.putExtras(bundle);
//        startActivity(intent);
        applyCTCardDTO.setIdycgy("1");
        applyCTCardDTO.setJoindate("201501");
        applyCTCardDTO.setCophoeexn("");
        applyCTCardDTO.setCoareacode("021");
        String money = mMoney.getText().toString();
        if (TextUtils.isEmpty(money)) {
            ToastUtils.showShort(this, "本人年收入不可为空");
            return;
        }
        applyCTCardDTO.setAnulincm(money);

        String name = mDanweiName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(this, "单位名称不可为空");
            return;
        }
        applyCTCardDTO.setConm(name);

        String bumen = mSuozaiBumen.getText().toString();
        if (TextUtils.isEmpty(bumen)) {
            ToastUtils.showShort(this, "所在部门不可为空");
            return;
        }
        applyCTCardDTO.setCorpsecr(bumen);

        if (TextUtils.isEmpty(applyCTCardDTO.getCoadrprov())) {
            ToastUtils.showShort(this, "单位区域不可为空");
            return;
        }

        if (mWorktype == 2 && TextUtils.isEmpty(mDanweiPhone.getText().toString())) {
            ToastUtils.showShort(this, "单位电话不可为空");
            return;
        }

        String address = mDanweiDetail.getText().toString();
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShort(this, "详细地址不可为空");
            return;
        }
        applyCTCardDTO.setCoadr(address);

        String youbian = mYoubian.getText().toString();
        if (TextUtils.isEmpty(youbian)) {
            ToastUtils.showShort(this, "邮编不可为空");
            return;
        }
        applyCTCardDTO.setCoadrzip(youbian);

        String danPhone = mDanweiPhone.getText().toString();
        if (TextUtils.isEmpty(danPhone)) {
            ToastUtils.showShort(this, "单位电话不可为空");
            return;
        }
        applyCTCardDTO.setCophoe(danPhone);

        Intent intent = new Intent(this, ApplyCardThirdStepActvity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", applyCTCardDTO);
        intent.putExtras(bundle);
        startActivity(intent);
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
                applyCTCardDTO.setCoadrprov(data[0]);
                applyCTCardDTO.setCoadrcity(data[1]);
                applyCTCardDTO.setCoadrcnty(data[2]);
                mDanweiQuyu.setRightText(data[0] + "、" + data[1] + "、" + data[2]);
                mDanweiQuyu.setRightTextColor(getResources().getColor(R.color.gray_25));
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
        }
        if (quStr.contains("嘉定区")) {
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
        mYoubian.setText(zipCode);
        applyCTCardDTO.setCoadrzip(zipCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10006 && resultCode == 1006 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setCorptp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mDanweiXingzhi.setRightText(commonTwoLevelMenuBean.getContext());
            mDanweiXingzhi.setRightTextColor(getResources().getColor(R.color.gray_25));
        } else if (requestCode == 10005 && resultCode == 1005 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setCottl(String.valueOf(commonTwoLevelMenuBean.getId()));
            mZhiwu.setRightText(commonTwoLevelMenuBean.getContext());
            mZhiwu.setRightTextColor(getResources().getColor(R.color.gray_25));
        } else if (requestCode == 10004 && resultCode == 1004 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setOcp(String.valueOf(commonTwoLevelMenuBean.getId()));
            mZhiye.setRightText(commonTwoLevelMenuBean.getContext());
            mZhiye.setRightTextColor(getResources().getColor(R.color.gray_25));
        }
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_bid_second_step;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PublicData.getInstance().filenum = "";
    }
}
