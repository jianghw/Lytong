package com.zantong.mobilecttx.car.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.fragment.SetPayCarFragment;
import com.zantong.mobilecttx.global.MainGlobal;

public class SetPayCarActivity extends JxBaseActivity {

    private SetPayCarFragment mSetPayCarFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {}

    @Override
    protected int initContentView() {
        return R.layout.activity_set_paycar;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("更改已绑定车辆");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mSetPayCarFragment == null) {
            mSetPayCarFragment = SetPayCarFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mSetPayCarFragment, R.id.activity_set_paycar_layout, false, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSetPayCarFragment = null;
    }

    public void setSucceedResult() {
        setResult(MainGlobal.resultCode.set_pay_car_succeed);
    }

}
