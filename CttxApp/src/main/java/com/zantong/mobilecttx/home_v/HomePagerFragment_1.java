package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.jumptools.Act;

/**
 * 页面
 */
public class HomePagerFragment_1 extends RefreshFragment implements View.OnClickListener {


    private TextView mTvOil;
    /**
     * 年检地图
     */
    private TextView mTvMap;

    /**
     * 是否可刷新
     */
    protected boolean isRefresh() {
        return false;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int fragmentView() {
        return R.layout.fragment_home_pager_1;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);
    }

    public static HomePagerFragment_1 newInstance() {
        return new HomePagerFragment_1();
    }

    public void initView(View view) {
        mTvOil = (TextView) view.findViewById(R.id.tv_oil);
        mTvOil.setOnClickListener(this);
        mTvMap = (TextView) view.findViewById(R.id.tv_map);
        mTvMap.setOnClickListener(this);
    }

    @Override
    protected void loadingFirstData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_oil://优惠加油
                showContacts();
                break;
            case R.id.tv_map:
                enterDrivingActivity();
                break;
        }
    }

    /**
     * 加油地图
     */
    public void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 3000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoOilMap();
        }
    }

    private void gotoOilMap() {
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.map_type_extra, JxGlobal.MapType.annual_oil_map);
        Act.getInstance().gotoLoginByIntent(getActivity(), BaiduMapParentActivity.class, intent);
    }

    /**
     * 进入地图年检页面
     */
    public void enterDrivingActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 2000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoMap();
        }
    }

    private void gotoMap() {
        MainRouter.gotoMapActivity(getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请拍照运行时权限
     */
    @PermissionSuccess(requestCode = 3000)
    public void doMapPermissionSuccess() {
        gotoOilMap();
    }


    @PermissionFail(requestCode = 3000)
    public void doMapPermissionFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

    @PermissionSuccess(requestCode = 2000)
    public void doDrivingSuccess() {
        gotoMap();
    }

    @PermissionFail(requestCode = 2000)
    public void doDrivingFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }
}
