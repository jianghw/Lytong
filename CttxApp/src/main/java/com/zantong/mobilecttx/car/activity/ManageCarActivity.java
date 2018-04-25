package com.zantong.mobilecttx.car.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.car.fragment.ManageCarListFragment;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;

/**
 * 车辆管理母页面
 */
public class ManageCarActivity extends AbstractBaseActivity {

    private ManageCarListFragment mCarListFragment;
    private int resultMain;

    /**
     * >=3 不添加
     */
    @SuppressLint("ResourceType")
    public void isAddCarTitle(int count) {
        titleMore(count >= 3 ? 0 : R.mipmap.ic_title_add);
    }

    /**
     * 添加车辆
     */
    protected void imageClickListener() {
        //TODO 确保每次能添加
        MainRouter.gotoViolationActivity(this);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_manage_car;
    }

    @Override
    protected void bundleIntent(Intent intent) {
    }

    @Override
    protected void bindFragment() {
        titleContent("车辆管理");

        TextView tvCar = (TextView) findViewById(R.id.tv_bind_car);
        tvCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for (UserCarInfoBean bean : LoginData.getInstance().mServerCars) {
                    if (bean.getIspaycar().equals("1")) count = count + 1;
                }
                if (count < 2) {
                    ToastUtils.toastShort("你绑定车辆小于2辆,现任意车辆均可缴费无需改邦操作");
                } else if (count == 2 && count == LoginData.getInstance().mServerCars.size()) {
                    ToastUtils.toastShort("你已绑定2辆车辆,请再去添加添加新未绑定车辆");
                } else {
                    MainRouter.gotoSetPayCarActivity(ManageCarActivity.this);
                }
            }
        });
    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (mCarListFragment == null) {
            mCarListFragment = ManageCarListFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mCarListFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        resultMain = resultCode;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCarListFragment = null;
        //车辆同步
        if (resultMain == MainGlobal.resultCode.violation_query_del
                || resultMain == MainGlobal.resultCode.violation_query_submit) {

            MainRouter.gotoMainActivity(this, 2);
        }
    }
}
