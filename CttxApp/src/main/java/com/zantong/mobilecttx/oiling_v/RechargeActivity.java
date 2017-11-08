package com.zantong.mobilecttx.oiling_v;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.oiling_p.RechargePresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;

import com.tzly.ctcyh.router.util.MobUtils;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 加油充值
 */
public class RechargeActivity extends JxBaseActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {}

    @Override
    protected void rightClickListener() {
        MobUtils.getInstance().eventIdByUMeng(3);

        showContacts();
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("油卡充值");
        titleMore("加油充值");
    }

    @Override
    protected void initContentData() {
        MobUtils.getInstance().eventIdByUMeng(22);

        initMvPresenter();
    }

    protected void initMvPresenter() {
        RechargeFragment fragment =
                (RechargeFragment) getSupportFragmentManager().findFragmentById(R.id.lay_base_frame);
        if (fragment == null) {
            fragment = RechargeFragment.newInstance();
            FragmentUtils.add(getSupportFragmentManager(), fragment, R.id.lay_base_frame, false, true);
        }
        RechargePresenter mPresenter = new RechargePresenter(
                Injection.provideRepository(getApplicationContext()), fragment);
    }

    public void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            Intent intent = new Intent();
            intent.putExtra(JxGlobal.putExtra.map_type_extra, JxGlobal.MapType.annual_oil_map);
            Act.getInstance().gotoLoginByIntent(this, BaiduMapParentActivity.class, intent);
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
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.map_type_extra, JxGlobal.MapType.annual_oil_map);
        Act.getInstance().gotoLoginByIntent(this, BaiduMapParentActivity.class, intent);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

}
