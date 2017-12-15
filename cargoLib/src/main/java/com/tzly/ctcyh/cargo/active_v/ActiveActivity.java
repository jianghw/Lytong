package com.tzly.ctcyh.cargo.active_v;

import android.content.Intent;
import android.os.Bundle;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.active_p.ActivePresenter;
import com.tzly.ctcyh.cargo.active_p.IActiveContract;
import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.service.RouterGlobal;

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
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.unSubscribe();
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
     * 获取渠道信息
     */
    @Override
    public void responseError(String message) {

    }

    @Override
    public void responseSucceed(ReceiveCouponResponse response) {

    }
}