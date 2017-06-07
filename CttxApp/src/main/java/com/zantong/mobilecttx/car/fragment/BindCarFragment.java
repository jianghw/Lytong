package com.zantong.mobilecttx.car.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseExtraFragment;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.ViolationResultAcitvity;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import butterknife.Bind;
import butterknife.OnClick;


@SuppressLint("ValidFragment")
public class BindCarFragment extends BaseExtraFragment {

    String mCarNum;
    String mCarType;
    String mEngineNum;
    String times;
    String money;
    String points;

    public BindCarFragment(){

    }

    @SuppressLint("ValidFragment")
    public BindCarFragment(String carnum,String cartype,String enginenum,String times,String money,String points){
        this.mCarNum = carnum;
        this.mCarType = cartype;
        this.mEngineNum = enginenum;
        this.times = times;
        this.money = money;
        this.points = points;
    }

    @Bind(R.id.bindcar_card)
    TextView mCard;
    @Bind(R.id.fragment_bindercar_amt)
    TextView mAmt;
    @Bind(R.id.fragment_bindercar_cent)
    TextView mCent;
    @Bind(R.id.fragment_bindercar_count)
    TextView mCount;
    @Bind(R.id.fragment_bindcar_layout)
    View mFragmentLayout;
    @Override
    protected int getLayoutResId() {
        return R.layout.main_fragment_bindcar;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        mCard.setText(mCarNum);
        mCent.setText(points);
        mCount.setText(times);
        mAmt.setText(money);
//        if (!times.equals("0")){
//            mAmt.setTextColor(getResources().getColor(R.color.red));
//            mCent.setTextColor(getResources().getColor(R.color.red));
//            mCount.setTextColor(getResources().getColor(R.color.red));
//        }
    }

    @OnClick(R.id.fragment_bindcar_layout)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.fragment_bindcar_layout:
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(1));
                PublicData.getInstance().mHashMap.put("IllegalViolationName",mCarNum);
                PublicData.getInstance().mHashMap.put("carnum",mCarNum);
                PublicData.getInstance().mHashMap.put("enginenum", mEngineNum);
                PublicData.getInstance().mHashMap.put("carnumtype", mCarType);
                LogUtils.i(mCarNum+"<<<"+mEngineNum+"<<<"+mCarType);
//                Act.getInstance().lauchIntent(getActivity(), ViolationResultAcitvity.class);

                ViolationDTO dto = new ViolationDTO();
                dto.setCarnum(RSAUtils.strByEncryption(this.getActivity(),mCarNum,true));
                dto.setEnginenum(RSAUtils.strByEncryption(this.getActivity(),mEngineNum,true));
                dto.setCarnumtype(mCarType);
                Intent intent = new Intent(this.getActivity(),ViolationResultAcitvity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("params",dto);
                intent.putExtras(bundle);
                intent.putExtra("plateNum",mCarNum);
                startActivity(intent);
                break;
        }
    }
}
