package com.zantong.mobile.fahrschule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.fahrschule.fragment.FahrschuleApplyFragment;
import com.zantong.mobile.fahrschule.fragment.FahrschuleApplySucceedFragment;
import com.zantong.mobile.fahrschule.fragment.FahrschuleOrderNumFragment;
import com.zantong.mobile.order.activity.OrderDetailActivity;

import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ui.FragmentUtils;

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
        Intent intent = getIntent();
        if (intent != null)
            mCurPosition = intent.getIntExtra(JxGlobal.putExtra.fahrschule_position_extra, 0);
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
                }
                FragmentUtils.addFragment(
                        fragmentManager, mFahrschuleApplyFragment, R.id.content, false, true);
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
                }
                FragmentUtils.addFragment(
                        fragmentManager, mFahrschuleOrderNumFragment, R.id.content, false, true);
                break;
            case 2:
                if (mFahrschuleApplySucceedFragment == null) {
                    mFahrschuleApplySucceedFragment = FahrschuleApplySucceedFragment.newInstance();
                }
                FragmentUtils.addFragment(
                        fragmentManager, mFahrschuleApplySucceedFragment, R.id.content, false, true);
                break;
            default:
                break;
        }
    }

    /**
     * 页面回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
                && resultCode == JxGlobal.resultCode.web_order_id_succeed) {
            mCurPosition = 2;
            initFragment(mCurPosition);
        } else if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
                && resultCode == JxGlobal.resultCode.web_order_id_error && data != null) {
            //前往 订单详情页面
            String orderId = data.getStringExtra(JxGlobal.putExtra.web_order_id_extra);
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
            startActivity(intent);
            finish();
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
                if (mCurPosition == 2)
                    finish();
                else
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
            if (mCurPosition == 2)
                finish();
            else
                closeFragment();
        }
        return false;
    }

    public interface SwitcherListener {
        void setCurPosition(int position);
    }
}