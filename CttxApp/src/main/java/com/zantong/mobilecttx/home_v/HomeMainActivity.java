package com.zantong.mobilecttx.home_v;

import android.content.Intent;
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
import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.StatusBarUtils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;

import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.custom.tablebottom.UiTableBottom;
import cn.qqtheme.framework.global.JxConfig;
import cn.qqtheme.framework.util.AppUtils;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionGen;


/**
 * 新的主页面
 */
public class HomeMainActivity extends JxBaseActivity {

    private FrameLayout mFrameLayout;
    private UiTableBottom mCustomBottom;
    /**
     * 初始化当期页面
     */
    private int mCurBottomPosition = 0;

    HomeUnimpededFragment mHomeUnimpededFragment;
    HomeDiscountsFragment mHomeDiscountsFragment;
    HomeMeFragment mHomeMeFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.putExtra.home_position_extra))
                mCurBottomPosition = bundle.getInt(MainGlobal.putExtra.home_position_extra, 0);
        }
    }

    @Override
    protected void initStatusBarColor() {
        StatusBarUtils.setTranslucentForImageViewInFragment(this, 38, null);
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
    protected void bindContentView(View view) {
        mFrameLayout = (FrameLayout) view.findViewById(R.id.content);
        mCustomBottom = (UiTableBottom) view.findViewById(R.id.custom_bottom);

        initBottomTable();
    }

    @Override
    protected void initContentData() {
        //更新检查
        Beta.checkUpgrade(false, true);
        Beta.init(getApplicationContext(), false);
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
                DialogUtils.updateDialog(this, upgradeInfo.title, upgradeInfo.newFeature,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Beta.checkUpgrade();
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareAppShop(ContextUtils.getContext().getPackageName());
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
            ToastUtils.toastShort("您没有安装应用市场,请点击立即更新");
        }
    }

    /**
     * 登陆信息
     */
    public void initLoginInfo() {
        RspInfoBean user = (RspInfoBean) UserInfoRememberCtrl.readObject();
        if (null != user) {
            LoginData.getInstance().userID = user.getUsrid();
            LoginData.getInstance().loginFlag = true;
            LoginData.getInstance().filenum = user.getFilenum();
            LoginData.getInstance().getdate = user.getGetdate();
            LoginData.getInstance().mLoginInfoBean = user;
            if (UserInfoRememberCtrl.readObject(LoginData.getInstance().NOTICE_STATE) != null) {
                LoginData.getInstance().updateMsg =
                        (boolean) UserInfoRememberCtrl.readObject(LoginData.getInstance().NOTICE_STATE);
            }
            if (!Tools.isStrEmpty(AccountRememberCtrl.getDefaultNumber(getApplicationContext()))) {
                LoginData.getInstance().defaultCar = true;
                LoginData.getInstance().defaultCarNumber =
                        AccountRememberCtrl.getDefaultNumber(getApplicationContext());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        initStatusBarColor();

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (mCurBottomPosition) {
            case 0:
                if (mHomeUnimpededFragment == null) {
                    mHomeUnimpededFragment = HomeUnimpededFragment.newInstance();
                    FragmentUtils.add(fragmentManager, mHomeUnimpededFragment, R.id.content, false, true);
                }
                mHomeUnimpededFragment.setMessageListener(new MessageListener() {
                    @Override
                    public void setTipOfNumber(int position, int number) {
                        mCustomBottom.setTipOfNumber(position, number);
                    }
                });
                FragmentUtils.showHideNull(mHomeUnimpededFragment, mHomeDiscountsFragment, mHomeMeFragment);
                JxConfig.getInstance().eventIdByUMeng(18);
                break;
            case 1:
                if (mHomeDiscountsFragment == null) {
                    mHomeDiscountsFragment = HomeDiscountsFragment.newInstance();
                    FragmentUtils.add(fragmentManager, mHomeDiscountsFragment, R.id.content, false, true);
                }
                FragmentUtils.showHideNull(mHomeDiscountsFragment, mHomeUnimpededFragment, mHomeMeFragment);

                JxConfig.getInstance().eventIdByUMeng(19);
                break;
            case 2:
                if (mHomeMeFragment == null) {
                    mHomeMeFragment = HomeMeFragment.newInstance();
                    FragmentUtils.add(fragmentManager, mHomeMeFragment, R.id.content, false, true);
                }
                mHomeMeFragment.setMessageListener(new MessageListener() {
                    @Override
                    public void setTipOfNumber(int position, int number) {
                        mCustomBottom.setTipOfNumber(position, number);
                    }
                });
                FragmentUtils.showHideNull(mHomeMeFragment, mHomeUnimpededFragment, mHomeDiscountsFragment);
                break;
            default:
                break;
        }
    }

    /**
     * 信息备份
     */
    private void liyingreg() {
        LiYingRegDTO liYingRegDTO = new LiYingRegDTO();
        try {
            String phone = RSAUtils.strByEncryptionLiYing(
                    LoginData.getInstance().mLoginInfoBean.getPhoenum(), true);
            SHATools sha = new SHATools();
            String pwd = RSAUtils.strByEncryptionLiYing(SHATools.hexString(
                    sha.eccryptSHA1(SPUtils.getInstance().getUserPwd())), true);
            liYingRegDTO.setPhoenum(phone);
            liYingRegDTO.setPswd(pwd);
            liYingRegDTO.setUsrid(RSAUtils.strByEncryptionLiYing(
                    LoginData.getInstance().mLoginInfoBean.getUsrid(), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {

            }
        });
    }

    public interface MessageListener {
        void setTipOfNumber(int position, int number);
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.toastShort("请再点击一次,退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                return super.onKeyDown(keyCode, event);
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
