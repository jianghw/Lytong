package com.zantong.mobilecttx.fahrschule.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleApplyFragment;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleOrderNumFragment;
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
    private FahrschuleApplyFragment mFahrschuleApplyFragment = null;
    private FahrschuleOrderNumFragment mFahrschuleOrderNumFragment = null;
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
            case 1://驾校报名页面
                if (mFahrschuleApplyFragment == null) {
                    mFahrschuleApplyFragment = FahrschuleApplyFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mFahrschuleApplyFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mFahrschuleApplyFragment);
                break;
            case 2://驾校订单页面
                if (mFahrschuleOrderNumFragment == null) {
                    mFahrschuleOrderNumFragment = FahrschuleOrderNumFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mFahrschuleOrderNumFragment, R.id.content);
                }
                FragmentUtils.hideAllShowFragment(mFahrschuleOrderNumFragment);
                break;
            case 3:
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
