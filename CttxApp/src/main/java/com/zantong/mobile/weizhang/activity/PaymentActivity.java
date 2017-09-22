package com.zantong.mobile.weizhang.activity;

import android.os.Bundle;
import android.view.View;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;

/**
 * 缴费须知
 */
public class PaymentActivity extends BaseJxActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.pay_ment_activity;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("缴费须知");
    }

    @Override
    protected void DestroyViewAndThing() {
    }

}
