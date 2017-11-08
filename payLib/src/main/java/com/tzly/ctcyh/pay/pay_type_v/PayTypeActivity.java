package com.tzly.ctcyh.pay.pay_type_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;


/**
 * 支付方式 选择页面
 */

public class PayTypeActivity extends JxBaseActivity implements IPayTypeUi {

    private PayTypeFragment mPayTypeFragment;
    private String mExtraOrder;
    private String mCurHost;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(PayGlobal.putExtra.pay_type_order))
                mExtraOrder = bundle.getString(PayGlobal.putExtra.pay_type_order);
            if (intent.hasExtra(PayGlobal.Host.pay_type_host))
                mCurHost = bundle.getString(PayGlobal.Host.pay_type_host);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("支付");
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
            }
            FragmentUtils.add(fragmentManager, mPayTypeFragment, R.id.lay_base_frame, false, true);
        } else if (mCurHost.equals(PayGlobal.Host.pay_type_host)) {
            if (mPayTypeFragment == null) {
                mPayTypeFragment = PayTypeFragment.newInstance(mExtraOrder);
            }
            FragmentUtils.add(fragmentManager, mPayTypeFragment, R.id.lay_base_frame, false, true);
        }
    }

    /**
     * 手动刷新
     */
    @Override
    protected void userClickRefreshData() {
        if (mPayTypeFragment != null) mPayTypeFragment.onRefreshData();
    }

    /**
     * 页面回调code 注意
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        setResult(PayGlobal.resultCode.pay_type_back);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (TextUtils.isEmpty(mCurHost) || mCurHost.equals(PayGlobal.Host.pay_type_host)) {
            if (mPayTypeFragment != null) {
                mPayTypeFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
