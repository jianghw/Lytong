package com.zantong.mobilecttx.fahrschule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.fahrschule.fragment.SparringOrderFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SparringSubscribeFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SparringSucceedFragment;
import com.zantong.mobilecttx.order.activity.OrderDetailActivity;

import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 陪练页面
 */
public class SparringActivity extends BaseJxActivity {

    private int mCurPosition;

    /**
     * 三个页面
     */
    private SparringSubscribeFragment mSparringSubscribeFragment = null;
    private SparringOrderFragment mSparringOrderFragment = null;
    private SparringSucceedFragment mSparringSucceedFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null)
            mCurPosition = intent.getIntExtra(JxGlobal.putExtra.fahrschule_position_extra, 0);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("陪练预约");
        setImageRightVisible(0);

        initFragment(mCurPosition);
    }

    @Override
    protected void DestroyViewAndThing() {
        mSparringSubscribeFragment = null;
        mSparringOrderFragment = null;
        mSparringSucceedFragment = null;
    }

    private void initFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0://陪练预约页面
                if (mSparringSubscribeFragment == null) {
                    mSparringSubscribeFragment = SparringSubscribeFragment.newInstance();
                }
                FragmentUtils.addFragment(fragmentManager, mSparringSubscribeFragment, R.id.lay_base_frame, false, true);
                mSparringSubscribeFragment.setSwitcherListener(new ISubjectSwitcherListener() {
                    @Override
                    public void setCurPosition(int position) {
                        initFragment(position);
                    }
                });
                break;
            case 1://陪练预约页面订单
                if (mSparringOrderFragment == null) {
                    mSparringOrderFragment = SparringOrderFragment.newInstance();
                }
                FragmentUtils.addFragment(fragmentManager, mSparringOrderFragment, R.id.lay_base_frame, false, true);
                break;
            case 2://陪练预约成功
                if (mSparringSucceedFragment == null) {
                    mSparringSucceedFragment = SparringSucceedFragment.newInstance();
                }
                FragmentUtils.addFragment(fragmentManager, mSparringSucceedFragment, R.id.lay_base_frame, false, true);
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

    /**
     * 回退监听功能
     */
    protected void backClickListener() {
        if (mCurPosition == 2)
            finish();
        else
            closeFragment();
    }

    protected void imageClickListener() {
        finish();
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

}
