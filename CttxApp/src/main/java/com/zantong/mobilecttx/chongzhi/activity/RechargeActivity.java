package com.zantong.mobilecttx.chongzhi.activity;

import android.Manifest;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.chongzhi.fragment.RechargeFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.map.activity.BaiduMapActivity;
import com.zantong.mobilecttx.presenter.chongzhi.RechargePresenter;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.permission.PermissionFail;
import com.zantong.mobilecttx.utils.permission.PermissionGen;
import com.zantong.mobilecttx.utils.permission.PermissionSuccess;

import cn.qqtheme.framework.util.AtyUtils;

/**
 * 加油充值
 */
public class RechargeActivity extends MvpBaseActivity {

    @Override
    protected int getContentResId() {
        return R.layout.mine_msg_activity;
    }

    @Override
    protected void setTitleView() {
        setTitleText("油卡充值");
        setEnsureText("加油充值");
    }

    @Override
    protected void initMvPresenter() {
        RechargeFragment fragment =
                (RechargeFragment) getSupportFragmentManager().findFragmentById(R.id.mine_msg_layout);
        if (fragment == null) {
            fragment = RechargeFragment.newInstance();
            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.mine_msg_layout);
        }

        RechargePresenter mPresenter = new RechargePresenter(
                Injection.provideRepository(getApplicationContext()), fragment);
    }

    @Override
    protected void baseGoEnsure() {
        super.baseGoEnsure();
        PublicData.getInstance().mapType = BaiduMapActivity.TYPE_JIAYOU;
        showContacts();
    }

    public void showContacts() {
        PermissionGen.needPermission(this, 100,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                }
        );
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        Act.getInstance().gotoIntent(this, BaiduMapActivity.class);
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        ToastUtils.showShort(this, "您已关闭定位权限");
    }

}
