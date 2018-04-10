package com.zantong.mobilecttx.home_v;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.tablebottom.UiTableBottom;
import com.tzly.ctcyh.router.util.AppUtils;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.StatusBarUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.service.RouterGlobal;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.home.bean.VersionResponse;
import com.zantong.mobilecttx.push_v.IPushBroadcastReceiver;
import com.zantong.mobilecttx.push_v.PushBroadcastReceiver;
import com.zantong.mobilecttx.utils.DialogUtils;

import static com.tzly.ctcyh.router.util.ToastUtils.toastShort;


/**
 * 新的主页面
 */
public class HomeMainActivity extends AbstractBaseActivity
        implements IPushBroadcastReceiver {

    private FrameLayout mFrameLayout;
    private UiTableBottom mCustomBottom;
    /**
     * 初始化当期页面
     */
    private int mCurBottomPosition = 0;

    HomeUnimpededFragment mHomeUnimpededFragment;
    HomeDiscountsFragment mHomeDiscountsFragment;
    HomeMeFragment mHomeMeFragment;
    /**
     * 广播
     */
    private PushBroadcastReceiver mBroadcastReceiver;

    /**
     * 不要基础title栏
     */
    protected boolean isCustomTitle() {
        return true;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_main_immersion;
    }

    @Override
    protected void initStatusBarColor() {
        StatusBarUtils.setTranslucentForImageViewInFragment(
                this, StatusBarUtils.DEFAULT_BAR_ALPHA, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (mCustomBottom != null) mCustomBottom.selectTab(mCurBottomPosition);
        if (mHomeUnimpededFragment != null) mHomeUnimpededFragment.carLoadData();
    }

    /**
     * 再次启动时调用
     */
    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(MainGlobal.putExtra.home_position_extra)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mCurBottomPosition = bundle.getInt(
                        MainGlobal.putExtra.home_position_extra, 0);

                //渠道
                String channel = null;
                if (intent.hasExtra(RouterGlobal.putExtra.channel_active))
                    channel = bundle.getString(RouterGlobal.putExtra.channel_active);
                //日期
                String registerDate = null;
                if (intent.hasExtra(RouterGlobal.putExtra.channel_register_date))
                    registerDate = bundle.getString(RouterGlobal.putExtra.channel_register_date);

                if (mHomeDiscountsFragment != null && !TextUtils.isEmpty(channel))
                    mHomeDiscountsFragment.activeToShow(channel, registerDate);
            }
        }
    }

    @Override
    protected void bindFragment() {
        //推送广播核心部分代码：
        mBroadcastReceiver = new PushBroadcastReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(PushBroadcastReceiver.PUSH_TIP_ACTION);
        registerReceiver(mBroadcastReceiver, itFilter);
        mBroadcastReceiver.setCustomListener(this);

        mFrameLayout = (FrameLayout) findViewById(R.id.content);
        mCustomBottom = (UiTableBottom) findViewById(R.id.custom_bottom);
    }

    /**
     * 底部小红点显示 和消息提示
     */
    @Override
    public void tipByNumber(int position, int number) {
        LoginData.getInstance().tipCount = number;

        if (mCustomBottom != null)
            mCustomBottom.setTipOfNumber(position, number);
        if (mHomeUnimpededFragment != null)
            mHomeUnimpededFragment.unMessageCount(position, number);
        if (mHomeMeFragment != null)
            mHomeMeFragment.unMessageCount(position, number);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void initContentData() {
        initBottomTable();
    }

    public void versionInfoError() {
        oldVersionInfo();
    }

    /**
     * 1、强制 官方包
     * 2、
     * 3、强制 马甲包
     * 4、
     */
    public void versionInfoSucceed(VersionResponse.Version version) {
        int update = version.getIsUpdate();
        String ver = version.getVersion();

        String verName = AppUtils.getAppVersionName();
        if (ver.equals(verName)) return;

        if (update == 1) {//强制更新
            constraintUpate(ver);
        } else if (update == 2) {//原推荐更新
            recommendUpdate(ver);
        } else if (update == 3) {//强制更新官方包
            constraintUpate(ver, Utils.getContext().getPackageName());
        } else if (update == 4) {//推荐更新官方包
            recommendUpdate(ver, Utils.getContext().getPackageName());
        } else if (update == 5) {//bugly
            oldVersionInfo();
        } else {
            ToastUtils.toastShort("请点击'个人'-->'版本更新',获取应用最新版本信息");
        }
    }

    // 推荐更新
    private void recommendUpdate(String version) {
        recommendUpdate(version, "com.zantong.mobilecttx");
    }

    private void recommendUpdate(String version, final String packageName) {
        DialogUtils.createDialog(this, "更新提示",
                "官方最新版本" + version + "已发布，为了完整体验App功能请应用市场更新！",
                "更新", "取消",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareAppShop(packageName);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }

    //强制更新
    private void constraintUpate(String version) {
        constraintUpate(version, "com.zantong.mobilecttx");
    }

    private void constraintUpate(String version, final String packageName) {
        DialogUtils.createDialogNoDismiss(this,
                "官方最新版本" + version + "已发布，为了完整体验App功能请应用市场更新！",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareAppShop(packageName);
                    }
                });
    }

    protected void oldVersionInfo() {
        //更新检查
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        int appCode = AppUtils.getAppVersionCode();
        int mVersionCode = appCode;
        if (upgradeInfo != null) {
            mVersionCode = upgradeInfo.versionCode;
        }
        if (appCode < mVersionCode) Beta.checkUpgrade();
    }

    /**
     * 根据应用包名，跳转到应用市场
     *
     * @param packageName 所需下载（评论）的应用包名
     */
    public void shareAppShop(String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            toastShort("您没有安装应用市场,请点击立即更新");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 底部数据初始化
     */
    private void initBottomTable() {
        ArrayMap<Integer, Integer[]> hashMap = new ArrayMap<>();
        hashMap.put(0, new Integer[]{R.mipmap.tab_homepage_s, R.mipmap.tab_homepage});
        hashMap.put(1, new Integer[]{R.mipmap.tab_discount_s, R.mipmap.tab_discount});
        hashMap.put(2, new Integer[]{R.mipmap.tab_person_s, R.mipmap.tab_person});

        mCustomBottom.setUiViewPager(new UiTableBottom.OnUITabChangListener() {
            @Override
            public void onTabChang(int index) {//初始化时，监听就会被调用
                mCurBottomPosition = index;
                initFragment();
            }
        }, mCurBottomPosition, hashMap);
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 0:
                if (mHomeUnimpededFragment == null) {
                    mHomeUnimpededFragment = HomeUnimpededFragment.newInstance();
                    FragmentUtils.add(fragmentManager, mHomeUnimpededFragment, R.id.content);
                }
                FragmentUtils.showHideNull(mHomeUnimpededFragment, mHomeDiscountsFragment, mHomeMeFragment);
                MobUtils.getInstance().eventIdByUMeng(18);
                break;
            case 1:
                if (mHomeDiscountsFragment == null) {
                    mHomeDiscountsFragment = HomeDiscountsFragment.newInstance();
                    FragmentUtils.add(fragmentManager, mHomeDiscountsFragment, R.id.content);
                }
                FragmentUtils.showHideNull(mHomeDiscountsFragment, mHomeUnimpededFragment, mHomeMeFragment);
                MobUtils.getInstance().eventIdByUMeng(19);
                break;
            case 2:
                if (mHomeMeFragment == null) {
                    mHomeMeFragment = HomeMeFragment.newInstance();
                    FragmentUtils.add(fragmentManager, mHomeMeFragment, R.id.content);
                }
                FragmentUtils.showHideNull(mHomeMeFragment, mHomeUnimpededFragment, mHomeDiscountsFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        //        Intent intent = new Intent(Intent.ACTION_MAIN);
        //        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        //                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        //        intent.addCategory(Intent.CATEGORY_HOME);
        //        startActivity(intent);

        //按返回键返回桌面
        moveTaskToBack(true);
    }
}
