package com.zantong.mobilecttx.common.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.fragment.CommonProblemFragment;

import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 常见问题
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemActivity extends BaseJxActivity {


    private int mCurBottomPosition;
    private CommonProblemFragment mProblemFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        mCurBottomPosition = 1;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("常见问题");

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 1:
                if (mProblemFragment == null) {
                    mProblemFragment = CommonProblemFragment.newInstance();
                }
                FragmentUtils.replaceFragment(
                        fragmentManager, mProblemFragment, R.id.lay_base_frame, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        mProblemFragment = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return false;
    }
}
