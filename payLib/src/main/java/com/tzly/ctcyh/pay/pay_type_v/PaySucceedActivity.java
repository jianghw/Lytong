package com.tzly.ctcyh.pay.pay_type_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;


/**
 * 支付成功页面
 */

public class PaySucceedActivity extends AbstractBaseActivity {

    private PaySucceedFragment mPaySucceedFragment;
    private String mGoodsType;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(PayGlobal.putExtra.web_goods_type_extra))
                    mGoodsType = bundle.getString(PayGlobal.putExtra.web_goods_type_extra);
                else
                    mGoodsType = "";
            }
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("支付成功");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mPaySucceedFragment == null) {
            mPaySucceedFragment = PaySucceedFragment.newInstance(mGoodsType);
            FragmentUtils.add(fragmentManager, mPaySucceedFragment, R.id.lay_base_frame);
        }
    }

    /**
     * 页面回调code 注意
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPaySucceedFragment = null;
    }

    @Override
    public void closeFragment() {
        PayRouter.gotoMainActivity(this, 1);
    }
}
