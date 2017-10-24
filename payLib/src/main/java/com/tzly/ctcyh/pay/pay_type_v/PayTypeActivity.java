package com.tzly.ctcyh.pay.pay_type_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;


/**
 * 支付方式 选择页面
 */

public class PayTypeActivity extends JxBaseActivity implements IPayTypeUi{

    private int mCurPosition;
    private PayTypeFragment mPayTypeFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("选择优惠劵");
    }

    @Override
    protected void initContentData() {
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurPosition) {
            case 1:
                if (mPayTypeFragment == null) {
                    mPayTypeFragment = PayTypeFragment.newInstance();
                }
                FragmentUtils.add(fragmentManager, mPayTypeFragment, R.id.lay_base_frame, false, true);
                break;
            default:
                break;
        }
    }

    /**
     * 手动刷新
     */
    @Override
    protected void userRefreshData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
