package com.zantong.mobilecttx.home.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.fragment.HomeMvpFragment;
import com.zantong.mobilecttx.user.activity.AddrActivity;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.permission.PermissionFail;
import com.zantong.mobilecttx.utils.permission.PermissionGen;
import com.zantong.mobilecttx.utils.permission.PermissionSuccess;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.AtyUtils;

/**
 * Created by zhengyingbing on 16/9/8.
 * Description:
 * Update by:
 * Update day:
 */
public class HomeActivity extends BaseActivity {

    @Bind(R.id.home_sweep)
    View mSweep;
    @Bind(R.id.activity_home_title)
    RelativeLayout mLayout;

    public boolean mStatusBarHeight = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarSpace();
    }

    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    private void setStatusBarSpace() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusBar = findViewById(R.id.activity_home_title);
            if (mStatusBarHeight) {
                statusBar.setPadding(0, tintManager.getConfig().getStatusBarHeight(), 0, 0);
            }
        }
    }

    @Override
    public void initView() {
        HomeMvpFragment homeFragment =
                (HomeMvpFragment) getSupportFragmentManager().findFragmentById(R.id.home_content);
        if (homeFragment == null) {
            homeFragment = HomeMvpFragment.newInstance();

            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), homeFragment, R.id.home_content);
        }

//        HomePresenter mPresenter = new HomePresenter(
//                Injection.provideRepository(getApplicationContext()), homeFragment);
    }

    @Override
    public void initData() {
        LoginInfoBean.RspInfoBean user = (LoginInfoBean.RspInfoBean) UserInfoRememberCtrl.readObject(HomeActivity.this);
        if (null != user) {
            PublicData.getInstance().userID = user.getUsrid();
            PublicData.getInstance().loginFlag = true;
            PublicData.getInstance().filenum = user.getFilenum();
            PublicData.getInstance().getdate = user.getGetdate();
            PublicData.getInstance().mLoginInfoBean = user;
            if (UserInfoRememberCtrl.readObject(this, PublicData.getInstance().NOTICE_STATE) != null) {
                PublicData.getInstance().updateMsg = (boolean) UserInfoRememberCtrl.readObject(this, PublicData.getInstance().NOTICE_STATE);
            }
            if (!Tools.isStrEmpty(AccountRememberCtrl.getDefaultNumber(this))) {
                PublicData.getInstance().defaultCar = true;
                PublicData.getInstance().defaultCarNumber = AccountRememberCtrl.getDefaultNumber(this);
            }
        }
        if (Build.VERSION.SDK_INT <= 23) {
            PublicData.getInstance().imei = Tools.getIMEI(this);
        }
    }

    public View getSweepView() {
        return mSweep;
    }

    @OnClick({R.id.home_addr, R.id.home_sweep})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.home_addr:
                Act.getInstance().lauchIntent(this, AddrActivity.class);
                break;
            case R.id.home_sweep:
                PermissionGen.needPermission(this, 100,
                        new String[]{
                                Manifest.permission.CAMERA,
//                                Manifest.permission.RECEIVE_SMS,
//                                Manifest.permission.WRITE_CONTACTS
                        }
                );
                break;
        }
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        Act.getInstance().lauchIntent(this, CaptureActivity.class);
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        ToastUtils.showShort(this, "您已关闭摄像头权限");
    }

}
