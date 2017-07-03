package com.zantong.mobilecttx.home.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.fragment.HomeFavorableFragment;
import com.zantong.mobilecttx.home.fragment.HomeMeFragment;
import com.zantong.mobilecttx.home.fragment.HomeUnimpededFragment;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;

import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;
import cn.qqtheme.framework.util.ui.FragmentUtils;
import cn.qqtheme.framework.widght.tablebottom.UiTableBottom;


/**
 * 新的主页面
 */
public class ImmersionMainActivity extends AppCompatActivity {

    private UiTableBottom mCustomBottom;

    /**
     * 初始化当期页面
     */
    private int mCurBottomPosition = 0;
    /**
     * 三个页面
     */
    private HomeUnimpededFragment mHomeUnimpededFragment = null;
    private HomeFavorableFragment mHomeFavorableFragment = null;
    private HomeMeFragment mHomeMeFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_immersion);

        initView();
        initLoginInfo();
        initBottomTable();

        //是否显示引导页面
        if (!SPUtils.getInstance(getApplicationContext()).getGuideSaoFaDan()) {
            PublicData.getInstance().GUIDE_TYPE = 0;
            Act.getInstance().gotoIntent(this, GuideActivity.class);
        }

        //登录信息
        if (PublicData.getInstance().loginFlag) liyingreg();
    }

    private void initView() {
        mCustomBottom = (UiTableBottom) findViewById(R.id.custom_bottom);
    }

    /**
     * 登陆信息
     */
    public void initLoginInfo() {
        LoginInfoBean.RspInfoBean user = (LoginInfoBean.RspInfoBean) UserInfoRememberCtrl.readObject(getApplicationContext());
        if (null != user) {
            PublicData.getInstance().userID = user.getUsrid();
            PublicData.getInstance().loginFlag = true;
            PublicData.getInstance().filenum = user.getFilenum();
            PublicData.getInstance().getdate = user.getGetdate();
            PublicData.getInstance().mLoginInfoBean = user;
            if (UserInfoRememberCtrl.readObject(getApplicationContext(), PublicData.getInstance().NOTICE_STATE) != null) {
                PublicData.getInstance().updateMsg = (boolean) UserInfoRememberCtrl.readObject(getApplicationContext(), PublicData.getInstance().NOTICE_STATE);
            }
            if (!Tools.isStrEmpty(AccountRememberCtrl.getDefaultNumber(this))) {
                PublicData.getInstance().defaultCar = true;
                PublicData.getInstance().defaultCarNumber = AccountRememberCtrl.getDefaultNumber(getApplicationContext());
            }
        }

        takePhoneIMEI();
    }

    public void takePhoneIMEI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 100,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE}
            );
        } else {
            PublicData.getInstance().imei = Tools.getIMEI();
        }
    }

    @PermissionSuccess(requestCode = 100)
    public void doPermissionIMEISuccess() {
        PublicData.getInstance().imei = Tools.getIMEI();
    }

    @PermissionFail(requestCode = 100)
    public void doPermissionIMEIFail() {

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 0:
                if (mHomeUnimpededFragment == null) {
                    mHomeUnimpededFragment = HomeUnimpededFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mHomeUnimpededFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mHomeUnimpededFragment);
                break;
            case 1:
                if (mHomeFavorableFragment == null) {
                    mHomeFavorableFragment = HomeFavorableFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mHomeFavorableFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mHomeFavorableFragment);
                break;
            case 2:
                if (mHomeMeFragment == null) {
                    mHomeMeFragment = HomeMeFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mHomeMeFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mHomeMeFragment);
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
            String phone = RSAUtils.strByEncryptionLiYing(getApplicationContext(), PublicData.getInstance().mLoginInfoBean.getPhoenum(), true);
            SHATools sha = new SHATools();
            String pwd = RSAUtils.strByEncryptionLiYing(getApplicationContext(),
                    SHATools.hexString(
                            sha.eccryptSHA1(SPUtils.getInstance(getApplicationContext()).getUserPwd())), true);
            liYingRegDTO.setPhoenum(phone);
            liYingRegDTO.setPswd(pwd);
            liYingRegDTO.setUsrid(
                    RSAUtils.strByEncryptionLiYing(getApplicationContext(), PublicData.getInstance().mLoginInfoBean.getUsrid(), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {

            }
        });
    }
}
