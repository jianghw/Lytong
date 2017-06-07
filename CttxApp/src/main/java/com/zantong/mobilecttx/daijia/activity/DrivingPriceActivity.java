package com.zantong.mobilecttx.daijia.activity;

import android.text.Html;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;

import butterknife.Bind;

/**
 * Created by zhoujie on 2017/2/16.
 */

public class DrivingPriceActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.driving_price_desc)
    TextView mDesc;

    @Override
    public void initView() {
        setTitleText("服务代驾");
        mDesc.setText(Html.fromHtml(getString(R.string.driving_price_desc)));
    }

    @Override
    public void initData() {
    }

    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_driving_price;
    }

}

