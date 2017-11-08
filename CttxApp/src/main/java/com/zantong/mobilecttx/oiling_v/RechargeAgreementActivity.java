package com.zantong.mobilecttx.oiling_v;

import android.os.Bundle;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.zantong.mobilecttx.R;

/**
 * 充值协议
 *
 * @author zyb
 *         <p>
 *         <p>
 *         *  *   *  *
 *         *      *      *
 *         *             *
 *         *           *
 *         *     *
 *         *
 *         <p>
 *         <p>
 *         create at 17/1/11 下午4:10
 */
public class RechargeAgreementActivity extends JxBaseActivity {


    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int initContentView() {
        return R.layout.recharge_agreement_activity;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("油卡充值服务协议");
    }

    @Override
    protected void initContentData() {}

}
