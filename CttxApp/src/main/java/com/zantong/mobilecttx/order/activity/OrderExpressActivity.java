package com.zantong.mobilecttx.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.order.fragment.OrderExpressFragment;

import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ui.FragmentUtils;

/**
 * 呼叫快递
 */
public class OrderExpressActivity extends BaseJxActivity {
    /**
     * 订单号
     */
    private String mOrderId;
    private int mCurPosition;
    private OrderExpressFragment mExpressFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(JxGlobal.putExtra.common_extra)) {
            mOrderId = intent.getStringExtra(JxGlobal.putExtra.common_extra);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("呼叫快递");

        initFragment(mCurPosition);
    }

    @Override
    protected void DestroyViewAndThing() {
        mExpressFragment = null;
    }

    private void initFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0://科目强化选择页面
                if (mExpressFragment == null) {
                    mExpressFragment = OrderExpressFragment.newInstance(mOrderId);
                }
                FragmentUtils.addFragment(fragmentManager, mExpressFragment, R.id.lay_base_frame, false, true);
                break;
            default:
                break;
        }
    }

    /**
     * 返回监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return false;
    }
}
