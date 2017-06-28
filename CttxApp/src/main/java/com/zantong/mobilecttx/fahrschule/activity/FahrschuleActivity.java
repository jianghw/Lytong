package com.zantong.mobilecttx.fahrschule.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.fahrschule.fragment.ApplyFahrschuleFragment;
import com.zantong.mobilecttx.home.fragment.FavorableFragment;
import com.zantong.mobilecttx.home.fragment.MeFragment;

import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 驾校报名页面
 */
public class FahrschuleActivity extends AppCompatActivity implements View.OnClickListener {

    private int mCurPosition;

    /**
     * 三个页面
     */
    private ApplyFahrschuleFragment mApplyFahrschuleFragment = null;
    private FavorableFragment mFavorableFragment = null;
    private MeFragment mMeFragment = null;
    private ImageView mImgBack;
    private ImageView mImgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fahrschule);
        initView();
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurPosition) {
            case 0:
                if (mApplyFahrschuleFragment == null) {
                    mApplyFahrschuleFragment = ApplyFahrschuleFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mApplyFahrschuleFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mApplyFahrschuleFragment);
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
                    mMeFragment = MeFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mMeFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mMeFragment);
                break;
            default:
                break;
        }
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mImgHome = (ImageView) findViewById(R.id.img_home);
        mImgHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back://上一级
                break;
            case R.id.img_home:
                break;
        }
    }
}
