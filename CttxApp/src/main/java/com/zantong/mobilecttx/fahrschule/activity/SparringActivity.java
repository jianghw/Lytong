package com.zantong.mobilecttx.fahrschule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.fahrschule.fragment.SparringSubscribeFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectCommitFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectOrderFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectSucceedFragment;
import com.zantong.mobilecttx.order.activity.OrderDetailActivity;

import cn.qqtheme.framework.global.GlobalConstant;
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
    private SubjectCommitFragment mSubjectCommitFragment = null;
    private SubjectOrderFragment mSubjectOrderFragment = null;
    private SubjectSucceedFragment mSubjectSucceedFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null)
            mCurPosition = intent.getIntExtra(GlobalConstant.putExtra.fahrschule_position_extra, 0);
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
        mSubjectCommitFragment = null;
        mSubjectOrderFragment = null;
        mSubjectSucceedFragment = null;
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
            case 1://科目强化生成订单
                if (mSubjectCommitFragment == null) {
                    mSubjectCommitFragment = SubjectCommitFragment.newInstance();
                }
                FragmentUtils.addFragment(fragmentManager, mSubjectCommitFragment, R.id.lay_base_frame, false, true);
                mSubjectCommitFragment.setSwitcherListener(new ISubjectSwitcherListener() {
                    @Override
                    public void setCurPosition(int position) {
                        initFragment(position);
                    }
                });
                break;
            case 2://科目强化去支付页面
                if (mSubjectOrderFragment == null) {
                    mSubjectOrderFragment = SubjectOrderFragment.newInstance();
                }
                FragmentUtils.addFragment(fragmentManager, mSubjectOrderFragment, R.id.lay_base_frame, false, true);
                break;
            case 3://科目强化去支付页面
                if (mSubjectSucceedFragment == null) {
                    mSubjectSucceedFragment = SubjectSucceedFragment.newInstance();
                }
                FragmentUtils.addFragment(fragmentManager, mSubjectSucceedFragment, R.id.lay_base_frame, false, true);
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
        if (requestCode == GlobalConstant.requestCode.fahrschule_order_num_web
                && resultCode == GlobalConstant.resultCode.web_order_id_succeed) {
            mCurPosition = 3;
            initFragment(mCurPosition);
        } else if (requestCode == GlobalConstant.requestCode.fahrschule_order_num_web
                && resultCode == GlobalConstant.resultCode.web_order_id_error && data != null) {
            //前往 订单详情页面
            String orderId = data.getStringExtra(GlobalConstant.putExtra.web_order_id_extra);
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra(GlobalConstant.putExtra.web_order_id_extra, orderId);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 回退监听功能
     */
    protected void backClickListener() {
        if (mCurPosition == 3)
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
            if (mCurPosition == 3)
                finish();
            else
                closeFragment();
        }
        return false;
    }

}
