package com.zantong.mobilecttx.home_v;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.tablebottom.UiTableBottom;
import com.tzly.ctcyh.router.util.AppUtils;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.StatusBarUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.global.MainGlobal;
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

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.putExtra.home_position_extra))
                mCurBottomPosition = bundle.getInt(MainGlobal.putExtra.home_position_extra, 0);
        }
    }

    @Override
    protected void newIntent(Intent intent) {
        mCustomBottom.selectTab(mCurBottomPosition);

        if (mHomeUnimpededFragment != null) mHomeUnimpededFragment.loadingFirstData();
    }

    @Override
    protected void initStatusBarColor() {
        StatusBarUtils.setTranslucentForImageViewInFragment(
                this, StatusBarUtils.DEFAULT_BAR_ALPHA, null);
    }

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
    protected void bindFragment() {
        //推送广播核心部分代码：
        mBroadcastReceiver = new PushBroadcastReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(PushBroadcastReceiver.PUSH_TIP_ACTION);
        registerReceiver(mBroadcastReceiver, itFilter);
        mBroadcastReceiver.setCustomListener(this);

        mFrameLayout = (FrameLayout) findViewById(R.id.content);
        mCustomBottom = (UiTableBottom) findViewById(R.id.custom_bottom);

        initBottomTable();
    }

    /**
     * 底部小红点显示 和消息提示
     */
    @Override
    public void tipByNumber(int position, int number) {
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
        //更新检查
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        int appCode = AppUtils.getAppVersionCode();
        int mVersionCode = appCode;
        if (upgradeInfo != null) {
            mVersionCode = upgradeInfo.versionCode;
        }
        //  public int upgradeType = 1;//升级策略 1建议 2强制 3手工
        if (appCode < mVersionCode) {
            if (upgradeInfo.upgradeType == 2) {
                Beta.checkUpgrade();
            } else {
                DialogUtils.updateDialog(this,
                        upgradeInfo.title, upgradeInfo.newFeature,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Beta.checkUpgrade();
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareAppShop(Utils.getContext().getPackageName());
                            }
                        });
            }
        }
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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
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

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toastShort("请再点击一次,退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //实现Home键效果  super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
