package com.zantong.mobilecttx.card.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.common.activity.CommonTwoLevelMenuActivity;
import com.zantong.mobilecttx.card.dto.ApplyCTCardDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.widght.SettingItemView;

import butterknife.Bind;
import butterknife.OnClick;

public class ApplyCardThirdStepActvity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    private ApplyCTCardDTO applyCTCardDTO;

    @Bind(R.id.activity_apply_third_name)
    EditText mName;
    @Bind(R.id.activity_apply_third_phone)
    EditText mPhone;
    @Bind(R.id.activity_apply_third_relationship)
    SettingItemView mRelationShip;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        setTitleText("申办畅通卡");
        applyCTCardDTO = (ApplyCTCardDTO) getIntent().getSerializableExtra("data");
        initValue();
    }

    /**
     *
     */
    private void initValue() {
        applyCTCardDTO.setGnd(String.valueOf("1"));
        mRelationShip.setRightText("朋友");
        mRelationShip.setRightTextColor(getResources().getColor(R.color.gray_25));
        applyCTCardDTO.setCtc1("8");
        applyCTCardDTO.setCtc2("8");
    }

    @OnClick({R.id.activity_apply_third_commit, R.id.activity_apply_third_relationship})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_apply_third_commit:
                checkData();
                break;
            case R.id.activity_apply_third_relationship:
                startActivityForResult(CommonTwoLevelMenuActivity.getIntent(this, 8), 10008);
                break;
        }

    }

    /**
     * 校验数据
     */
    private void checkData() {
//        Intent intent = new Intent(this, ApplyCardFourStepActvity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data", applyCTCardDTO);
//        intent.putExtras(bundle);
//        startActivity(intent);
        String name = mName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(this, "联系人姓名不可为空");
            return;
        }
        applyCTCardDTO.setCtcnm1(name);
        applyCTCardDTO.setCtcnm2(name);
        applyCTCardDTO.setCtcgnd1("1");
        applyCTCardDTO.setCtcgnd2("1");
        applyCTCardDTO.setCtcconm1("无");
        applyCTCardDTO.setCtcconm2("无");
        applyCTCardDTO.setCtccoareacode1("021");
        applyCTCardDTO.setCtccoareacode2("021");
        String phone = mPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(this, "联系人手机号不可为空");
            return;
        }
        if (!ValidateUtils.isMobile(phone)) {
            ToastUtils.showShort(this, "联系人手机号输入不正确");
            return;
        }
        applyCTCardDTO.setCtcphoenum1(phone);
        applyCTCardDTO.setCtcphoenum2(phone);
        applyCTCardDTO.setCtccophoe1(phone);
        applyCTCardDTO.setCtccophoe2(phone);
        applyCTCardDTO.setCtccophoeexn1("");
        applyCTCardDTO.setCtccophoeexn2("");
        applyCTCardDTO.setCtchmadr1("无");
        applyCTCardDTO.setCtchmadr2("无");
        applyCTCardDTO.setCtchmadrzip1("201900");
        applyCTCardDTO.setCtchmadrzip2("201900");

        Intent intent = new Intent(this, ApplyCardFourStepActvity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", applyCTCardDTO);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10008 && resultCode == 1008 && data != null) {
            CommonTwoLevelMenuBean commonTwoLevelMenuBean = (CommonTwoLevelMenuBean)
                    data.getSerializableExtra("data");
            applyCTCardDTO.setCtc1(String.valueOf(commonTwoLevelMenuBean.getId()));
            applyCTCardDTO.setCtc2(String.valueOf(commonTwoLevelMenuBean.getId()));
            mRelationShip.setRightText(commonTwoLevelMenuBean.getContext());
            mRelationShip.setRightTextColor(getResources().getColor(R.color.gray_25));
        }
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_apply_third;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PublicData.getInstance().filenum = "";
    }
}
