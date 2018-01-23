package com.tzly.ctcyh.cargo.refuel_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;

/**
 * 97折扣 加油充值
 */
public class DiscountOilActivity extends AbstractBaseActivity
        implements View.OnClickListener, IRechargeAToF {

    private DiscountOilFragment mFragment;
    private Button btnCommit;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame_btn;
    }

    /**
     * 右击
     */
    @Override
    protected void rightClickListener() {
        CargoRouter.gotoBidOilActivity(this);
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
        }
    }

    @Override
    protected void bindFragment() {
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(this);

        titleContent("97折油卡充值");
        titleMore("申购97折油卡");
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        if (mFragment != null) mFragment.verificationSubmitData();
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = DiscountOilFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }

    @Override
    public void setCommitEnable(boolean b) {
        if (btnCommit != null) btnCommit.setEnabled(b);
    }


}