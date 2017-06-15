package com.zantong.mobilecttx.car.fragment;

import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseExtraFragment;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.car.activity.AddCarActivity;

import butterknife.OnClick;

/**
 * 添加车辆页面
 */
public class AddCarFragment extends BaseExtraFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.main_fragment_addcar;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.addcar_btn)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.addcar_btn:
                AddCarActivity.isFrom = false;
                Act.getInstance().lauchIntent(getContext(), AddCarActivity.class);
                break;
        }
    }
}
