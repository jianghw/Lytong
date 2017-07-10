package com.zantong.mobilecttx.fahrschule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleApplyFragment;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleApplySucceedFragment;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleOrderNumFragment;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ToastUtils;
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
    private FahrschuleApplySucceedFragment mFahrschuleApplySucceedFragment = null;
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

        initFragment(mCurPosition);
    }

    @Override
    protected void DestroyViewAndThing() {
        mFahrschuleApplyFragment = null;
        mFahrschuleOrderNumFragment = null;
        mFahrschuleApplySucceedFragment = null;
    }

    private void initFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0://驾校报名页面
                if (mFahrschuleApplyFragment == null) {
                    mFahrschuleApplyFragment = FahrschuleApplyFragment.newInstance();
                    FragmentUtils.replaceFragment(fragmentManager, mFahrschuleApplyFragment, R.id.content, true);
                }
                mFahrschuleApplyFragment.setSwitcherListener(new SwitcherListener() {
                    @Override
                    public void setCurPosition(int position) {
                        initFragment(position);
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
                if (mFahrschuleApplySucceedFragment == null) {
                    mFahrschuleApplySucceedFragment = FahrschuleApplySucceedFragment.newInstance();
                    FragmentUtils.replaceFragment(fragmentManager, mFahrschuleApplySucceedFragment, R.id.content, true);
                }
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalConstant.requestCode.fahrschule_order_num_web
                && resultCode == GlobalConstant.resultCode.web_order_id_succeed) {
            initFragment(2);
        } else if (requestCode == GlobalConstant.requestCode.fahrschule_order_num_web
                && resultCode == GlobalConstant.resultCode.web_order_id_error) {
//TODO 订单详情
            ToastUtils.toastShort("去订单详情，未完成");
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
                finish();
                break;
        }
    }

    /**
     * 返回监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return false;
    }

    public interface SwitcherListener {
        void setCurPosition(int position);
    }
}
