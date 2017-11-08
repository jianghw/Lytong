package com.zantong.mobilecttx.fahrschule_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.fahrschule_v.FahrschuleApplyFragment;
import com.zantong.mobilecttx.fahrschule_v.FahrschuleApplySucceedFragment;
import com.zantong.mobilecttx.fahrschule_v.FahrschuleOrderNumFragment;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;

/**
 * 驾校报名页面
 */
public class FahrschuleActivity extends JxBaseActivity {

    private int mCurPosition;

    /**
     * 三个页面
     */
    private FahrschuleApplyFragment mFahrschuleApplyFragment = null;
    private FahrschuleOrderNumFragment mFahrschuleOrderNumFragment = null;
    private FahrschuleApplySucceedFragment mFahrschuleApplySucceedFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.Host.fahrschule_host))
                mCurPosition = bundle.getInt(MainGlobal.Host.fahrschule_host, 0);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_fahrschule;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("驾校报名");
        rightImage(0);
    }

    @Override
    protected void initContentData() {
        initFragment(mCurPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                FragmentUtils.add(fragmentManager, mFahrschuleApplyFragment, R.id.content, false, true);
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
                FragmentUtils.add(
                        fragmentManager, mFahrschuleOrderNumFragment, R.id.content, false, true);
                break;
            case 2:
                if (mFahrschuleApplySucceedFragment == null) {
                    mFahrschuleApplySucceedFragment = FahrschuleApplySucceedFragment.newInstance();
                }
                FragmentUtils.add(
                        fragmentManager, mFahrschuleApplySucceedFragment, R.id.content, false, true);
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
        if (mCurPosition == 2)
            finish();
        else
            closeFragment();
    }

    @Override
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

    public interface SwitcherListener {
        void setCurPosition(int position);
    }
}
