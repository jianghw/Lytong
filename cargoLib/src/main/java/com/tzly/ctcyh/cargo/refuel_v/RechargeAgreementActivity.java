package com.tzly.ctcyh.cargo.refuel_v;

import android.content.Intent;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;

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
public class RechargeAgreementActivity extends AbstractBaseActivity {

    @Override
    protected void bundleIntent(Intent intent) {}

    @Override
    protected void newIntent(Intent intent) {}

    @Override
    protected int initContentView() {
        return R.layout.cargo_activity_recharge_agree;
    }

    @Override
    protected void bindFragment() {
        titleContent("油卡充值服务协议");
    }

    @Override
    protected void initContentData() {}

}
