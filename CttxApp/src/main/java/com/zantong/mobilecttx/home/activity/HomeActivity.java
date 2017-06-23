package com.zantong.mobilecttx.home.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.fragment.HomeMvpFragment;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.AtyUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 主页面
 */
public class HomeActivity extends BaseActivity {

    @Bind(R.id.home_sweep)
    View mSweep;
    @Bind(R.id.home_addr_text)
    TextView mTvMsgCount;
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
        LoginInfoBean.RspInfoBean user = (LoginInfoBean.RspInfoBean) UserInfoRememberCtrl.readObject(getApplicationContext());
        if (null != user) {
            PublicData.getInstance().userID = user.getUsrid();
            PublicData.getInstance().loginFlag = true;
            PublicData.getInstance().filenum = user.getFilenum();
            PublicData.getInstance().getdate = user.getGetdate();
            PublicData.getInstance().mLoginInfoBean = user;
            if (UserInfoRememberCtrl.readObject(this, PublicData.getInstance().NOTICE_STATE) != null) {
                PublicData.getInstance().updateMsg = (boolean) UserInfoRememberCtrl.readObject(getApplicationContext(), PublicData.getInstance().NOTICE_STATE);
            }
            if (!Tools.isStrEmpty(AccountRememberCtrl.getDefaultNumber(this))) {
                PublicData.getInstance().defaultCar = true;
                PublicData.getInstance().defaultCarNumber = AccountRememberCtrl.getDefaultNumber(getApplicationContext());
            }
        }
        if (Build.VERSION.SDK_INT <= 23) {
            PublicData.getInstance().imei = Tools.getIMEI(this);
        }

    }

    @Override
    public void initData() {
        HomeMvpFragment homeFragment =
                (HomeMvpFragment) getSupportFragmentManager().findFragmentById(R.id.home_content);
        if (homeFragment == null) {
            homeFragment = HomeMvpFragment.newInstance();

            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), homeFragment, R.id.home_content);
        }
    }

    @OnClick({R.id.home_addr, R.id.home_sweep})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_addr://消息页面
//                Act.getInstance().lauchIntent(this, AddrActivity.class);
                MobclickAgent.onEvent(this.getApplicationContext(), Config.getUMengID(24));
                Act.getInstance().lauchIntentToLogin(this, MegTypeActivity.class);
                break;
            case R.id.home_sweep:
                takeCapture();
                break;
            default:
                break;
        }
    }

    /**
     * 违章单扫描
     */
    public void takeCapture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}
            );
        } else {
            Act.getInstance().lauchIntent(this, CaptureActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        Act.getInstance().lauchIntent(this, CaptureActivity.class);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {

    }

    /**
     * 未读消息
     */
    public void messageCount(int i) {
        if (mTvMsgCount != null) {
            mTvMsgCount.setVisibility(i > 0 ? View.VISIBLE : View.INVISIBLE);
            mTvMsgCount.setText(String.valueOf(i));
        }
    }

}
