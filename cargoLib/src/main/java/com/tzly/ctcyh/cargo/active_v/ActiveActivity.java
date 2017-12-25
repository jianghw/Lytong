package com.tzly.ctcyh.cargo.active_v;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.active_p.ActivePresenter;
import com.tzly.ctcyh.cargo.active_p.IActiveContract;
import com.tzly.ctcyh.cargo.bean.response.ActiveBean_1;
import com.tzly.ctcyh.cargo.bean.response.ActiveConfigBean;
import com.tzly.ctcyh.cargo.bean.response.ActiveConfigResponse;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.dialog.CouponDialogFragment;
import com.tzly.ctcyh.router.custom.dialog.IOnCouponSubmitListener;
import com.tzly.ctcyh.router.custom.dialog.MessageDialogFragment;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.service.RouterGlobal;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 提供活动显示
 */
public class ActiveActivity extends AbstractBaseActivity implements IActiveContract.IActiveView {

    private String mExtraChannel;
    private String mExtraRegisterDate;
    private IActiveContract.IActivePresenter mPresenter;
    private String mId_Only;

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
        finish();
    }

    @Override
    public void configSucceed(ActiveConfigResponse response) {
        ActiveConfigBean bean = response.getData();
        //标记id
        mId_Only = bean.getId();
        boolean chancleID = SPUtils.getInstance(SPUtils.FILENAME).getBoolean(mId_Only, false);
        if (chancleID) {
            finish();
            return;
        }

        String configType = bean.getConfigType();
        final String beanExtra = bean.getExtra();
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

                        gotoHtmlByExtra(beanExtra);
                    }

                    @Override
                    public void cancel() {
                        finish();
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "coupon_dialog");
            } else {
                finish();
            }
        } else if (configType.equals("2")) {//消息
            MessageDialogFragment dialogFragment = MessageDialogFragment.newInstance(beanExtra);
            dialogFragment.setClickListener(new IOnCouponSubmitListener() {
                @Override
                public void submit(String couponId) {
                    saveSubmit();
                    finish();
                }

                @Override
                public void cancel() {
                }
            });
            dialogFragment.show(getSupportFragmentManager(), "message_dialog");
        } else if (configType.equals("3")) {//网页
            if (!TextUtils.isEmpty(beanExtra)) gotoHtml(beanExtra);
            finish();
        }
    }

    private void gotoHtmlByExtra(String beanExtra) {
        Type type = new TypeToken<List<ActiveBean_1>>() {
        }.getType();
        List<ActiveBean_1> beanList = new Gson().fromJson(beanExtra, type);
        if (beanList != null && !beanList.isEmpty()) {
            ActiveBean_1 bean_1 = beanList.get(0);
            if (!TextUtils.isEmpty(bean_1.getUrl())) gotoHtml(bean_1.getUrl());
        }
    }

    private void gotoHtml(String beanExtra) {
        CargoRouter.gotoHtmlActivity(this, "Html页面", beanExtra);
    }

    /**
     * 获取渠道信息
     */
    @Override
    public void responseError(String message) {
        shortToast(message);
        saveSubmit();
        finish();
    }

    /**
     * 本地标记
     */
    @Override
    public void responseSucceed(ReceiveCouponResponse response) {
        saveSubmit();
        finish();
    }

    private void saveSubmit() {
        SPUtils.getInstance(SPUtils.FILENAME).put(mId_Only, true);
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
        super.onDestroy();

        if (mPresenter != null) mPresenter.unSubscribe();
    }
}