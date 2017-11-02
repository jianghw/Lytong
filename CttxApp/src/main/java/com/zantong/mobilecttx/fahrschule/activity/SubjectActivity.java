package com.zantong.mobilecttx.fahrschule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectCommitFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectIntensifyFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectOrderFragment;
import com.zantong.mobilecttx.fahrschule.fragment.SubjectSucceedFragment;
import com.zantong.mobilecttx.global.MainGlobal;

/**
 * 科目强化页面
 */
public class SubjectActivity extends JxBaseActivity {

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
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.Host.subject_host))
                mCurPosition = bundle.getInt(MainGlobal.Host.subject_host, 0);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("科目强化");
        rightImage(0);
    }

    @Override
    protected void initContentData() {
        initFragment(mCurPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                FragmentUtils.add(fragmentManager, mSubjectIntensifyFragment, R.id.lay_base_frame, false, true);
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
                FragmentUtils.add(fragmentManager, mSubjectCommitFragment, R.id.lay_base_frame, false, true);
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
                FragmentUtils.add(fragmentManager, mSubjectOrderFragment, R.id.lay_base_frame, false, true);
                break;
            case 3://科目强化去支付页面
                if (mSubjectSucceedFragment == null) {
                    mSubjectSucceedFragment = SubjectSucceedFragment.newInstance();
                }
                FragmentUtils.add(fragmentManager, mSubjectSucceedFragment, R.id.lay_base_frame, false, true);
                break;
            default:
                break;
        }
    }

    /**
     * 回退监听功能
     */
    @Override
    protected void backClickListener() {
        if (mCurPosition == 3)
            finish();
        else
            closeFragment();
    }

    @Override
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
