package com.zantong.mobilecttx.card.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.fragment.BindCarSuccessFragment;
import com.zantong.mobilecttx.eventbus.ChangTongEvent;

import org.greenrobot.eventbus.EventBus;

public class BindCardSuccess extends BaseJxActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("绑定畅通卡");
        setTvRightVisible("完成");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BindCarSuccessFragment mBindCarSuccessFragment = new BindCarSuccessFragment();
        transaction.replace(R.id.lay_base_frame, mBindCarSuccessFragment);
        transaction.commit();
    }

    protected void rightClickListener() {
        EventBus.getDefault().post(new ChangTongEvent(true, "成功"));
        finish();
    }

    @Override
    protected void DestroyViewAndThing() {
    }

}
