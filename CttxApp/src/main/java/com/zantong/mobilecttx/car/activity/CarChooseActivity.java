package com.zantong.mobilecttx.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.fragment.CarChooseXiFragment;
import com.zantong.mobilecttx.car.fragment.CarChooseXingFragment;

/**
 * 车辆选择
 */
public class CarChooseActivity extends BaseJxActivity {

    public static final int REQUEST_L_CODE = 10003;
    public static final int RESULT_L_CODE = 10004;
    public static final int REQUEST_X_CODE = 10005;
    public static final int RESULT_X_CODE = 10006;
    public static final String CAR_LINE_BEAN = "CarLineBean";
    public static final String CAR_XING_BEAN = "CarXingBean";

    private int mType;
    private int mId;
    private int mIdB;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("type")) mType = getIntent().getIntExtra("type", 1);
            if (intent.hasExtra("id")) mId = getIntent().getIntExtra("id", 0);
            if (intent.hasExtra("idB")) mIdB = getIntent().getIntExtra("idB", 0);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mType == 1) {
            initTitleContent("车系");
            transaction.replace(R.id.lay_base_frame, CarChooseXiFragment.newInstance(mId));
        } else {
            initTitleContent("车型");
            transaction.replace(R.id.lay_base_frame, CarChooseXingFragment.newInstance(mId, mIdB));
        }
        transaction.commit();
    }

    @Override
    protected void DestroyViewAndThing() {
    }

}
