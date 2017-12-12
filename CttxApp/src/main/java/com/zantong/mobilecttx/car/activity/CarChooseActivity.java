package com.zantong.mobilecttx.car.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.fragment.CarChooseXiFragment;
import com.zantong.mobilecttx.car.fragment.CarChooseXingFragment;

/**
 * 车辆选择
 */
public class CarChooseActivity extends AbstractBaseActivity {

    public static final int REQUEST_L_CODE = 10003;
    public static final int RESULT_L_CODE = 10004;
    public static final int REQUEST_X_CODE = 10005;
    public static final int RESULT_X_CODE = 10006;
    public static final String CAR_LINE_BEAN = "CarLineBean";
    public static final String CAR_XING_BEAN = "CarXingBean";

    private int mType;
    private int mId;
    private int mIdB;
    private Fragment mFragment;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("type")) mType = getIntent().getIntExtra("type", 1);
            if (intent.hasExtra("id")) mId = getIntent().getIntExtra("id", 0);
            if (intent.hasExtra("idB")) mIdB = getIntent().getIntExtra("idB", 0);
        }
    }

    @Override
    protected void bindFragment() {
        if (mType == 1) {
            titleContent("车系");
        } else {
            titleContent("车型");
        }

    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mType == 1) {
            mFragment = CarChooseXiFragment.newInstance(mId);
        } else {
            mFragment = CarChooseXingFragment.newInstance(mId, mIdB);
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }
}
