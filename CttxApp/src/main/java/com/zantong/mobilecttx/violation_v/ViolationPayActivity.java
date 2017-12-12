package com.zantong.mobilecttx.violation_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

/**
 * 违章支付页面
 */
public class ViolationPayActivity extends AbstractBaseActivity implements IViolationPayUi {

    private ViolationPayFragment mViolationPayFragment = null;
    private ViolationBean violationBean;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViolationPayFragment = null;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            violationBean = bundle.getParcelable(JxGlobal.putExtra.violation_pay_bean_extra);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindFragment() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (mViolationPayFragment == null) {
            mViolationPayFragment = ViolationPayFragment.newInstance(violationBean);
        }
        FragmentUtils.add(fragmentManager,
                mViolationPayFragment, R.id.lay_base_frame, false, true);

    }

    @Override
    protected void initContentData() {}

    /**
     * 注意 支付类型
     */
    public int getPayType() {
        return mViolationPayFragment != null ? mViolationPayFragment.getPayType() : 4;
    }

    public void setPayType(int payType) {
        if (mViolationPayFragment != null) {
            mViolationPayFragment.setPayType(payType);
        }
    }
}
