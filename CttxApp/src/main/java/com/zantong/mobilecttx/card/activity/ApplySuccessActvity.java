package com.zantong.mobilecttx.card.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.home_v.HomeMainActivity;
import com.zantong.mobilecttx.presenter.HelpPresenter;

import butterknife.Bind;

public class ApplySuccessActvity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.apply_success_hint)
    TextView mSuccessHint;
    @Bind(R.id.activity_apply_success_wangdian)
    TextView mSuccessWangdian;

    public static Intent getIntent(Context context, String wangdian) {
        Intent intent = new Intent(context, ApplySuccessActvity.class);
        intent.putExtra("wangdian", wangdian);
        return intent;
    }

    @Override
    public void initView() {
        getBaseBack().setVisibility(View.GONE);
        mSuccessHint.setText(Html.fromHtml(getResources().getString(R.string.apply_success_hint)));
    }

    @Override
    public void initData() {
        setTitleText("申办畅通卡");
        setEnsureText("完成");
        String address = getIntent().getStringExtra("wangdian");
        if (address.length() > 20) {
            address = address.substring(0, 20) + "...";
        }
        mSuccessWangdian.setText(address);
    }

    @Override
    protected void baseGoEnsure() {
        startActivity(new Intent(this, HomeMainActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startActivity(new Intent(this, HomeMainActivity.class));
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_apply_success;
    }
}
