package com.zantong.mobilecttx.fahrschule_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.fahrschule_p.ISubjectSwitcherListener;
import com.zantong.mobilecttx.fahrschule_v.SparringOrderFragment;
import com.zantong.mobilecttx.fahrschule_v.SparringSubscribeFragment;
import com.zantong.mobilecttx.fahrschule_v.SparringSucceedFragment;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;

/**
 * 陪练页面
 */
public class SparringActivity extends JxBaseActivity {

    private int mCurPosition;

    /**
     * 三个页面
     */
    private SparringSubscribeFragment mSparringSubscribeFragment = null;
    private SparringOrderFragment mSparringOrderFragment = null;
    private SparringSucceedFragment mSparringSucceedFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.Host.sparring_host))
                mCurPosition = bundle.getInt(MainGlobal.Host.sparring_host, 0);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("陪练预约");
        rightImage(0);
    }

    @Override
    protected void initContentData() {
        initFragment(mCurPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                FragmentUtils.add(fragmentManager, mSparringSubscribeFragment, R.id.lay_base_frame, false, true);
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
                FragmentUtils.add(fragmentManager, mSparringOrderFragment, R.id.lay_base_frame, false, true);
                break;
            case 2://陪练预约成功
                if (mSparringSucceedFragment == null) {
                    mSparringSucceedFragment = SparringSucceedFragment.newInstance();
                }
                FragmentUtils.add(fragmentManager, mSparringSucceedFragment, R.id.lay_base_frame, false, true);
                break;
            default:
                break;
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
        MainRouter.gotoMainActivity(this, 1);
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
