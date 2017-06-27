package com.zantong.mobilecttx.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home.fragment.FavorableFragment;
import com.zantong.mobilecttx.home.fragment.MeFragment;
import com.zantong.mobilecttx.home.fragment.UnimpededFragment;

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
    private UnimpededFragment mUnimpededFragment = null;
    private FavorableFragment mFavorableFragment = null;
    private MeFragment mMeFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_immersion);
        initView();
        initBottomTable();
    }

    private void initView() {
        mCustomBottom = (UiTableBottom) findViewById(R.id.custom_bottom);
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
                if (mUnimpededFragment == null) {
                    mUnimpededFragment = UnimpededFragment.newInstance("123", "456");
                    FragmentUtils.addFragment(fragmentManager, mUnimpededFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mUnimpededFragment);
                break;
            case 1:
                if (mFavorableFragment == null) {
                    mFavorableFragment = FavorableFragment.newInstance("456", "456");
                    FragmentUtils.addFragment(fragmentManager, mFavorableFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mFavorableFragment);
                break;
            case 2:
                if (mMeFragment == null) {
                    mMeFragment = MeFragment.newInstance("789", "789");
                    FragmentUtils.addFragment(fragmentManager, mMeFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mMeFragment);
                break;
            default:
                break;
        }
    }

}
