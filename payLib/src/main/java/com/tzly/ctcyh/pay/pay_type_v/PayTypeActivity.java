package com.tzly.ctcyh.pay.pay_type_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;


/**
 * 支付方式 选择页面
 */

public class PayTypeActivity extends AbstractBaseActivity {

    private PayTypeFragment mPayTypeFragment;
    private String mExtraOrder;
    private String mCurHost;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(PayGlobal.putExtra.pay_type_order))
                    mExtraOrder = bundle.getString(PayGlobal.putExtra.pay_type_order);
                if (intent.hasExtra(PayGlobal.Host.pay_type_host))
                    mCurHost = bundle.getString(PayGlobal.Host.pay_type_host);
            }
        }
        mExtraOrder = "17121817400291";

    }

    @Override
    protected void bindFragment() {
        titleContent("选择支付");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (TextUtils.isEmpty(mCurHost)) {
            if (mPayTypeFragment == null) {
                mPayTypeFragment = PayTypeFragment.newInstance(mExtraOrder);
                FragmentUtils.add(fragmentManager, mPayTypeFragment, R.id.lay_base_frame);
            }
        } else if (mCurHost.equals(PayGlobal.Host.pay_type_host)) {
            if (mPayTypeFragment == null) {
                mPayTypeFragment = PayTypeFragment.newInstance(mExtraOrder);
                FragmentUtils.add(fragmentManager, mPayTypeFragment, R.id.lay_base_frame);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        setResult(PayGlobal.resultCode.pay_type_back);
    }

    /**
     * 页面回调code 注意
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPayTypeFragment = null;
    }

}
