package com.zantong.mobilecttx.chongzhi.activity;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.chongzhi.fragment.RechargeFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.map.activity.BaiduMapActivity;
import com.zantong.mobilecttx.presenter.chongzhi.RechargePresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;

import cn.qqtheme.framework.util.AtyUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            Act.getInstance().gotoIntent(this, BaiduMapActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请拍照运行时权限
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        Act.getInstance().gotoIntent(this, BaiduMapActivity.class);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
    }

}
