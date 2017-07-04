package com.zantong.mobilecttx.car.activity;

import android.support.v4.app.FragmentTransaction;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.car.fragment.CarChooseXiFragment;
import com.zantong.mobilecttx.car.fragment.CarChooseXingFragment;

/**
 * 车辆选择
 */
public class CarChooseActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    public static final int REQUEST_L_CODE = 10003;
    public static final int RESULT_L_CODE = 10004;
    public static final int REQUEST_X_CODE = 10005;
    public static final int RESULT_X_CODE = 10006;
    public static final String CAR_LINE_BEAN = "CarLineBean";
    public static final String CAR_XING_BEAN = "CarXingBean";

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int type = getIntent().getIntExtra("type", 1);
        int id = getIntent().getIntExtra("id", 0);
        if (type == 1) {
            setTitleText("车系");
            transaction.replace(R.id.lay_base_frame, CarChooseXiFragment.newInstance(id));
        } else {
            setTitleText("车型");
            int idB = getIntent().getIntExtra("idB", 0);
            transaction.replace(R.id.lay_base_frame, CarChooseXingFragment.newInstance(id, idB));
        }
        transaction.commit();

    }

}
