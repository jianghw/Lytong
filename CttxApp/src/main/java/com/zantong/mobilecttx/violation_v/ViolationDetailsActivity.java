package com.zantong.mobilecttx.violation_v;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;

/**
 * 违章详情
 */
public class ViolationDetailsActivity extends AbstractBaseActivity {

    private ViolationDetailsFragment mDetailsFragment;
    private String mViolationnum;

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent.hasExtra(JxGlobal.putExtra.common_extra)) {
            mViolationnum = intent.getStringExtra(JxGlobal.putExtra.common_extra);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindFragment() {
        titleContent("违章详情");
    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mDetailsFragment == null) {
            mDetailsFragment = ViolationDetailsFragment.newInstance(mViolationnum);
        }
        FragmentUtils.add(fragmentManager, mDetailsFragment, R.id.lay_base_frame);
    }

}
