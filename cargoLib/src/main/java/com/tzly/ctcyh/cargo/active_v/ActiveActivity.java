package com.tzly.ctcyh.cargo.active_v;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.active_p.ActivePresenter;
import com.tzly.ctcyh.cargo.active_p.IActiveContract;
import com.tzly.ctcyh.cargo.bean.response.ActiveConfigBean;
import com.tzly.ctcyh.cargo.bean.response.ActiveConfigResponse;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.cargo.router.CargoUiRouter;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.dialog.CouponDialogFragment;
import com.tzly.ctcyh.router.custom.dialog.DateDialogFragment;
import com.tzly.ctcyh.router.custom.dialog.IOnCouponSubmitListener;
import com.tzly.ctcyh.router.custom.dialog.IOnDateSetListener;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.service.RouterGlobal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 提供活动显示
 */
public class ActiveActivity extends AbstractBaseActivity implements IActiveContract.IActiveView {

    private String mExtraChannel;
    private String mExtraRegisterDate;
    private IActiveContract.IActivePresenter mPresenter;

    /**
     * 是否要子布局定义title
     */
    @Override
    protected boolean isCustomTitle() {
        return true;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //渠道
                if (intent.hasExtra(RouterGlobal.putExtra.channel_active))
                    mExtraChannel = bundle.getString(RouterGlobal.putExtra.channel_active);
                //日期
                if (intent.hasExtra(RouterGlobal.putExtra.channel_register_date))
                    mExtraRegisterDate = bundle.getString(RouterGlobal.putExtra.channel_register_date);
            }
        }

        mExtraChannel = "1";
        mExtraRegisterDate = "2017-12-12";
    }

    @Override
    protected void bindFragment() {
        ActivePresenter presenter = new ActivePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void initContentData() {
        if (mPresenter != null) mPresenter.getConfig();
    }

    @Override
    public void setPresenter(IActiveContract.IActivePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getChannel() {
        return mExtraChannel;
    }

    /**
     * 渠道
     */
    @Override
    public void configError(String message) {
        shortToast(message);
    }

    @Override
    public void configSucceed(ActiveConfigResponse response) {
        ActiveConfigBean bean = response.getData();
        String configType = bean.getConfigType();

        if (configType.equals("1")) {
            List<ActiveConfigBean.CouponInfoBean> infoBeanList = bean.getCouponInfo();
            if (!infoBeanList.isEmpty()) {
                ActiveConfigBean.CouponInfoBean infoBean = infoBeanList.get(0);

                CouponDialogFragment dialogFragment = CouponDialogFragment.newInstance(
                        infoBean.getCouponId(), infoBean.getCouponName(), infoBean.getCouponBusiness(),
                        infoBean.getCouponType(), infoBean.getCouponValue());
                dialogFragment.setClickListener(new IOnCouponSubmitListener() {
                    @Override
                    public void submit(String couponId) {
                        if (mPresenter != null) mPresenter.receiveCoupon(couponId);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "coupon_dialog");
            }
        } else if (configType.equals("2")) {//消息

        } else if (configType.equals("3")) {//网页
        }
    }

    /**
     * 获取渠道信息
     */
    @Override
    public void responseError(String message) {
        shortToast(message);
    }

    @Override
    public void responseSucceed(ReceiveCouponResponse response) {

    }

    @Override
    public String getResisterDate() {
        return mExtraRegisterDate;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 飞翔吧少年
     */
    @Override
    protected void onDestroy() {
        CargoRouter.gotoMainActivity(this, 1);

        super.onDestroy();

        if (mPresenter != null) mPresenter.unSubscribe();
    }
}