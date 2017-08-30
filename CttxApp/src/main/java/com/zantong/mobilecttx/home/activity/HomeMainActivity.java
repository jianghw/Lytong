package com.zantong.mobilecttx.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.fragment.HomeDiscountsFragment;
import com.zantong.mobilecttx.home.fragment.HomeMeFragment;
import com.zantong.mobilecttx.home.fragment.HomeUnimpededFragment;
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;

import cn.qqtheme.framework.contract.bean.BaseResult;
import cn.qqtheme.framework.global.JxConfig;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.ui.FragmentUtils;
import cn.qqtheme.framework.util.ui.StatusBarUtils;
import cn.qqtheme.framework.widght.tablebottom.UiTableBottom;


/**
 * 新的主页面
 */
public class HomeMainActivity extends BaseJxActivity {

    private FrameLayout mFrameLayout;
    private UiTableBottom mCustomBottom;
    /**
     * 初始化当期页面
     */
    private int mCurBottomPosition = 0;
    /**
     * 三个页面
     */
    private HomeUnimpededFragment mHomeUnimpededFragment = null;
    private HomeDiscountsFragment mHomeDiscountsFragment = null;
    private HomeMeFragment mHomeMeFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected void initStatusBarColor() {
        StatusBarUtils.setTranslucentForImageViewInFragment(this, 38, null);
    }

    /**
     * 不要基础title栏
     */
    protected boolean isNeedCustomTitle() {
        return true;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_main_immersion;
    }

    @Override
    protected void initFragmentView(View view) {
        mFrameLayout = (FrameLayout) view.findViewById(R.id.content);
        mCustomBottom = (UiTableBottom) view.findViewById(R.id.custom_bottom);

        initLoginInfo();
        initBottomTable();
        //登录信息
        if (PublicData.getInstance().loginFlag) liyingreg();
    }

    /**
     * 登陆信息
     */
    public void initLoginInfo() {
        RspInfoBean user = (RspInfoBean) UserInfoRememberCtrl.readObject();
        if (null != user) {
            PublicData.getInstance().userID = user.getUsrid();
            PublicData.getInstance().loginFlag = true;
            PublicData.getInstance().filenum = user.getFilenum();
            PublicData.getInstance().getdate = user.getGetdate();
            PublicData.getInstance().mLoginInfoBean = user;
            if (UserInfoRememberCtrl.readObject(PublicData.getInstance().NOTICE_STATE) != null) {
                PublicData.getInstance().updateMsg =
                        (boolean) UserInfoRememberCtrl.readObject(PublicData.getInstance().NOTICE_STATE);
            }
            if (!Tools.isStrEmpty(AccountRememberCtrl.getDefaultNumber(getApplicationContext()))) {
                PublicData.getInstance().defaultCar = true;
                PublicData.getInstance().defaultCarNumber =
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
                    FragmentUtils.addFragment(fragmentManager, mHomeUnimpededFragment, R.id.content, false, true);
                }
                mHomeUnimpededFragment.setMessageListener(new MessageListener() {
                    @Override
                    public void setTipOfNumber(int position, int number) {
                        mCustomBottom.setTipOfNumber(position, number);
                    }
                });

                if (mHomeUnimpededFragment != null)
                    FragmentUtils.hideAllShowFragment(fragmentManager, mHomeUnimpededFragment);

                JxConfig.getInstance().eventIdByUMeng(18);
                break;
            case 1:
                if (mHomeDiscountsFragment == null) {
                    mHomeDiscountsFragment = HomeDiscountsFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mHomeDiscountsFragment, R.id.content, false, true);
                }
                if (mHomeDiscountsFragment != null)
                    FragmentUtils.hideAllShowFragment(fragmentManager, mHomeDiscountsFragment);

                JxConfig.getInstance().eventIdByUMeng(19);
                break;
            case 2:
                if (mHomeMeFragment == null) {
                    mHomeMeFragment = HomeMeFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mHomeMeFragment, R.id.content, false, true);
                }
                mHomeMeFragment.setMessageListener(new MessageListener() {
                    @Override
                    public void setTipOfNumber(int position, int number) {
                        mCustomBottom.setTipOfNumber(position, number);
                    }
                });
                if (mHomeMeFragment != null)
                    FragmentUtils.hideAllShowFragment(fragmentManager, mHomeMeFragment);
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
                    PublicData.getInstance().mLoginInfoBean.getPhoenum(), true);
            SHATools sha = new SHATools();
            String pwd = RSAUtils.strByEncryptionLiYing(SHATools.hexString(
                    sha.eccryptSHA1(SPUtils.getInstance().getUserPwd())), true);
            liYingRegDTO.setPhoenum(phone);
            liYingRegDTO.setPswd(pwd);
            liYingRegDTO.setUsrid(RSAUtils.strByEncryptionLiYing(
                    PublicData.getInstance().mLoginInfoBean.getUsrid(), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {

            }
        });
    }

    @Override
    protected void DestroyViewAndThing() {
        mHomeUnimpededFragment = null;
        mHomeDiscountsFragment = null;
        mHomeMeFragment = null;
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
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
