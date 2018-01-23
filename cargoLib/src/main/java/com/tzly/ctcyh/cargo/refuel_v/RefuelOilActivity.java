package com.tzly.ctcyh.cargo.refuel_v;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;

import static com.tzly.ctcyh.router.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 加油充值
 */
public class RefuelOilActivity extends AbstractBaseActivity
        implements View.OnClickListener, IRechargeAToF {

    private RefuelOilFragment mFragment;
    private Button btnCommit;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame_btn;
    }

    /**
     * 右击
     */
    @Override
    protected void rightClickListener() {
        showContacts();
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
        }
    }

    @Override
    protected void bindFragment() {
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(this);

        titleContent("加油充值");
        titleMore("优惠加油站");
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        if (mFragment != null) mFragment.verificationSubmitData();
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = RefuelOilFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }

    public void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请拍照运行时权限
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        gotoMap();
    }

    private void gotoMap() {
        CargoRouter.gotoBaiduMapParentActivity(this);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

    @Override
    public void setCommitEnable(boolean b) {
        if (btnCommit != null) btnCommit.setEnabled(b);
    }


}