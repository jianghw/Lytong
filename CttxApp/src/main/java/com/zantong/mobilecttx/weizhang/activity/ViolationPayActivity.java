package com.zantong.mobilecttx.weizhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.fragment.ViolationPayFragment;

import cn.qqtheme.framework.global.JxGlobal;

/**
 * 违章支付页面
 */
public class ViolationPayActivity extends BaseJxActivity {

    private int mCurPosition;
    private ViolationPayFragment mViolationPayFragment = null;
    private ViolationBean violationBean;

    /**
     * 支付类型
     */
    private int mPayType = 1;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCurPosition = intent.getIntExtra(JxGlobal.putExtra.fahrschule_position_extra, 0);
            Bundle bundle = intent.getExtras();
            violationBean = bundle.getParcelable(JxGlobal.putExtra.violation_pay_bean_extra);
        }
    }

    protected boolean isNeedCustomTitle() {
        return true;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initFragment(mCurPosition);
    }

    @Override
    protected void DestroyViewAndThing() {
        mViolationPayFragment = null;
    }

    private void initFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0://科目强化选择页面
                if (mViolationPayFragment == null) {
                    mViolationPayFragment = ViolationPayFragment.newInstance(violationBean);
                }
                FragmentUtils.add(fragmentManager, mViolationPayFragment, R.id.lay_base_frame, false, true);
                break;
            default:
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

    public void showLoadingDialog() {
        showDialogLoading();
    }

    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    /**
     * 注意 支付类型
     */
    public int getPayType() {
        return mPayType;
    }

    public void setPayType(int payType) {
        mPayType = payType;
    }
}
