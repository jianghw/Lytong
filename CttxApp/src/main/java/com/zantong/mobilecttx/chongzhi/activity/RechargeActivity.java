package com.zantong.mobilecttx.chongzhi.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.chongzhi.fragment.RechargeFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.presenter.chongzhi.RechargePresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;

import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.AtyUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 加油充值
 */
public class RechargeActivity extends BaseJxActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("油卡充值");
        setTvRightVisible("加油充值");

        GlobalConfig.getInstance().eventIdByUMeng(22);

        initMvPresenter();
    }

    protected void rightClickListener() {
        GlobalConfig.getInstance().eventIdByUMeng(3);

        showContacts();
    }

    @Override
    protected void DestroyViewAndThing() {
    }

    protected void initMvPresenter() {
        RechargeFragment fragment =
                (RechargeFragment) getSupportFragmentManager().findFragmentById(R.id.lay_base_frame);
        if (fragment == null) {
            fragment = RechargeFragment.newInstance();
            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.lay_base_frame);
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
            intent.putExtra(GlobalConstant.putExtra.map_type_extra, GlobalConstant.MapType.annual_oil_map);
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
        intent.putExtra(GlobalConstant.putExtra.map_type_extra, GlobalConstant.MapType.annual_oil_map);
        Act.getInstance().gotoLoginByIntent(this, BaiduMapParentActivity.class, intent);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

}
