package com.zantong.mobilecttx.fahrschule.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleApplyFragment;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleOrderNumFragment;
import com.zantong.mobilecttx.home.fragment.MeFragment;

import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 驾校报名页面
 */
public class FahrschuleActivity extends BaseJxActivity implements View.OnClickListener {

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
    protected void bundleIntent(Bundle savedInstanceState) {

    }

    /**
     * 不要基础title栏
     */
    protected boolean isNeedCustomTitle() {
        return true;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_fahrschule;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        initFragment();
    }

    @Override
    protected void DestroyViewAndThing() {

    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurPosition) {
            case 0://驾校报名页面
                if (mFahrschuleApplyFragment == null) {
                    mFahrschuleApplyFragment = FahrschuleApplyFragment.newInstance();
                    FragmentUtils.replaceFragment(fragmentManager, mFahrschuleApplyFragment, R.id.content, true);
                }
                mFahrschuleApplyFragment.setSwitcherListener(new SwitcherListener() {
                    @Override
                    public void setCurPosition(int position) {
                        mCurPosition = position;
                        initFragment();
                    }
                });
                break;
            case 1://驾校订单页面
                if (mFahrschuleOrderNumFragment == null) {
                    mFahrschuleOrderNumFragment = FahrschuleOrderNumFragment.newInstance();
                    FragmentUtils.replaceFragment(fragmentManager, mFahrschuleOrderNumFragment, R.id.content, true);
                }
                break;
            case 2:
                if (mMeFragment == null) {
                    mMeFragment = MeFragment.newInstance();
                    FragmentUtils.addFragment(fragmentManager, mMeFragment, R.id.content);
                }
                break;
            default:
                break;
        }
    }

    private void initView(View view) {
        mImgBack = (ImageView) view.findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mImgHome = (ImageView) view.findViewById(R.id.img_home);
        mImgHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back://上一级
                closeFragment();
                break;
            case R.id.img_home:
                break;
        }
    }

    public interface SwitcherListener {
        void setCurPosition(int position);
    }
}
