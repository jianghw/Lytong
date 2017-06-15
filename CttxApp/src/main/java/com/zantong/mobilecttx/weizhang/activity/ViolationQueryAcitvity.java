package com.zantong.mobilecttx.weizhang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.car.adapter.MyCarAdapter;
import com.zantong.mobilecttx.car.bean.MyCarBean;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.CommonListActivity;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.AllCapTransformationMethod;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.VehicleTypeTools;
import com.zantong.mobilecttx.utils.popwindow.KeyWordPop;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.LogUtils;

public class ViolationQueryAcitvity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.violation_query_province_text)
    TextView mProvinceText;//车牌省份
    @Bind(R.id.violation_query_plate_edit)
    EditText mPlateEdit;//车牌号
    @Bind(R.id.violation_query_engine_edit)
    EditText mEngineEdit;//发动机号
    @Bind(R.id.violation_query_cartype_text)
    TextView mCarTypeText;//车辆类型
    @Bind(R.id.violation_query_xrecyclerview_title)
    TextView mRecyclerViewTitle;//列表标题
    @Bind(R.id.violation_query_xrecyclerview)
    XRecyclerView mRecyclerView;//车辆列表

    MyCarAdapter mAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_violation_query;
    }


    @Override
    public void initView() {
        setTitleText("违章查询");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCarTypeText.setText(PublicData.getInstance().commonListData);
    }

    @Override
    public void initData() {
        mPlateEdit.setTransformationMethod(new AllCapTransformationMethod());
        mPlateEdit.setSelection(mPlateEdit.getText().toString().length());
        mEngineEdit.setSelection(mEngineEdit.getText().toString().length());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setArrowImageView(R.mipmap.loading);
        mAdapter = new MyCarAdapter();
        mRecyclerView.setAdapter(mAdapter);
        List<MyCarBean> list = new ArrayList<>();
        list.clear();
        if (PublicData.getInstance().loginFlag) {
            for (int i = 0; i < PublicData.getInstance().mServerCars.size(); i++) {
                MyCarBean bean = new MyCarBean();
                bean.setCar_name(PublicData.getInstance().mServerCars.get(i).getCarnum());
                bean.setEngine_num(PublicData.getInstance().mServerCars.get(i).getEnginenum());
                bean.setCar_type(PublicData.getInstance().mServerCars.get(i).getCarnumtype());
                list.add(bean);
            }
        } else {
            for (int i = 0; i < PublicData.getInstance().mLocalCars.size(); i++) {
                MyCarBean bean = new MyCarBean();
                bean.setCar_name(PublicData.getInstance().mLocalCars.get(i).getCarnum());
                bean.setEngine_num(PublicData.getInstance().mLocalCars.get(i).getEnginenum());
                bean.setCar_type(PublicData.getInstance().mLocalCars.get(i).getCarnumtype());
                list.add(bean);
            }
        }
        if (list.size() > 0) {
            mRecyclerViewTitle.setVisibility(View.VISIBLE);
        } else {
            mRecyclerViewTitle.setVisibility(View.GONE);
        }
        mAdapter.append(list);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                MyCarBean bean = (MyCarBean) data;
//                PublicData.getInstance().mHashMap.put("carnum",bean.getCar_name());
//                PublicData.getInstance().mHashMap.put("enginenum",bean.getEngine_num());
//                PublicData.getInstance().mHashMap.put("carnumtype",bean.getCar_type());
//                PublicData.getInstance().mHashMap.put("IllegalViolationName",bean.getCar_name());//标题
//                Act.getInstance().gotoIntent(ViolationQueryAcitvity.this, QueryResultActivity.class);

                ViolationDTO dto = new ViolationDTO();
                dto.setCarnum(RSAUtils.strByEncryption(ViolationQueryAcitvity.this, bean.getCar_name(), true));
                dto.setEnginenum(RSAUtils.strByEncryption(ViolationQueryAcitvity.this, bean.getEngine_num(), true));
                dto.setCarnumtype(bean.getCar_type());
                Intent intent = new Intent(ViolationQueryAcitvity.this, ViolationResultAcitvity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("params", dto);
                intent.putExtras(bundle);
                intent.putExtra("plateNum", bean.getCar_name());
                startActivity(intent);
            }
        });
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }


    @OnClick({R.id.aviolation_query_btn, R.id.violation_query_cartype_desc,
            R.id.violation_query_province_layout, R.id.violation_query_cartype_layout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.violation_query_cartype_desc:
                new DialogMgr(this, R.mipmap.engine_number_image);
                break;
            case R.id.violation_query_province_layout:
                InputMethodManager imm = (InputMethodManager)
                        this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                KeyWordPop mKeyWordPop = new KeyWordPop(this, v, new KeyWordPop.KeyWordLintener() {
                    @Override
                    public void onKeyWordLintener(String cityStr) {
                        mProvinceText.setText(cityStr);
                    }
                });
                break;
            case R.id.violation_query_cartype_layout:
                PublicData.getInstance().commonListType = 3;
                startActivityForResult(new Intent(this, CommonListActivity.class), CommonListActivity.REQUEST_COMMON_TYPE);
                break;
            case R.id.aviolation_query_btn:
                ViolationDTO dto = new ViolationDTO();
                if (mPlateEdit.getText().toString().length() < 5) {
                    ToastUtils.showShort(this, "车牌号格式不正确");
                    return;
                }
                if (mEngineEdit.getText().toString().length() < 5) {
                    ToastUtils.showShort(this, "发动机号格式不正确");
                    return;
                }
                String plateNum = mProvinceText.getText().toString() + mPlateEdit.getTransformationMethod().getTransformation(mPlateEdit.getText(), mPlateEdit);
                dto.setCarnum(RSAUtils.strByEncryption(this, plateNum, true));
                dto.setEnginenum(RSAUtils.strByEncryption(this, mEngineEdit.getText().toString(), true));
                dto.setCarnumtype(VehicleTypeTools.switchVehicleCode(mCarTypeText.getText().toString()));
                LogUtils.i(plateNum + "---" + mEngineEdit.getText().toString() + "---" + mCarTypeText.getText().toString());
                Intent intent = new Intent(this, ViolationResultAcitvity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("params", dto);
                intent.putExtras(bundle);
                intent.putExtra("plateNum", plateNum);
                startActivity(intent);
                break;
        }

    }


}
