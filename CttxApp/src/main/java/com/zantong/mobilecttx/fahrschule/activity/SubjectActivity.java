package com.zantong.mobilecttx.fahrschule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectCommitFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectIntensifyFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectOrderFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectSucceedFragment;
import com.zantong.mobilecttx.order.activity.OrderDetailActivity;

import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 科目强化页面
 */
public class SubjectActivity extends BaseJxActivity {

    private int mCurPosition;

    /**
     * 三个页面
     */
    private SubjectIntensifyFragment mSubjectIntensifyFragment = null;
    private SubjectCommitFragment mSubjectCommitFragment = null;
    private SubjectOrderFragment mSubjectOrderFragment = null;
    private SubjectSucceedFragment mSubjectSucceedFragment = null;

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
        initTitleContent("科目强化");
        setImageRightVisible(0);

        initFragment(mCurPosition);
    }

    @Override
    protected void DestroyViewAndThing() {
        mSubjectIntensifyFragment = null;
        mSubjectCommitFragment = null;
        mSubjectOrderFragment = null;
        mSubjectSucceedFragment = null;
    }

    private void initFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0://科目强化选择页面
                if (mSubjectIntensifyFragment == null) {
                    mSubjectIntensifyFragment = SubjectIntensifyFragment.newInstance();
                }
                FragmentUtils.addFragment(fragmentManager, mSubjectIntensifyFragment, R.id.lay_base_frame, false, true);
                mSubjectIntensifyFragment.setSwitcherListener(new ISubjectSwitcherListener() {
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
        if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
                && resultCode == JxGlobal.resultCode.web_order_id_succeed) {
            mCurPosition = 3;
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
