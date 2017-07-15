package com.zantong.mobilecttx.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.global.GlobalConstant;

/**
 * Created by jianghw on 2017/7/7.
 * Description: 推荐 汽车美容
 * Update by:
 * Update day:
 */

public class CarBeautyActivity extends BaseJxActivity implements View.OnClickListener {
    private ImageView mImgAdmissions;
    private ImageView mImgViolation;

    private int mCurPosition;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCurPosition = intent.getIntExtra(GlobalConstant.putExtra.share_position_extra, 0);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_car_beauty;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("汽车保养");

        initView(view);

        GlobalConfig.getInstance().eventIdByUMeng(24);
    }

    @Override
    protected void DestroyViewAndThing() {

    }

    public void initView(View view) {
        mImgAdmissions = (ImageView) view.findViewById(R.id.img_admissions);
        mImgAdmissions.setOnClickListener(this);
        mImgViolation = (ImageView) view.findViewById(R.id.img_violation);
        mImgViolation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_admissions:
                mCurPosition = 1;
                PublicData.getInstance().webviewUrl = "http://m.hiservice.com.cn/activity/freekongtiaoyanghu?source=gonghangcx";
                PublicData.getInstance().webviewTitle = "车内空调养护";
                PublicData.getInstance().isCheckLogin = true;
                Act.getInstance().gotoIntent(this, BrowserActivity.class);
                break;
            case R.id.img_violation:
                mCurPosition = 2;
                PublicData.getInstance().webviewUrl = "http://m.hiservice.com.cn/market/icbc58";
                PublicData.getInstance().webviewTitle = "汽车冰蜡";
                PublicData.getInstance().isCheckLogin = true;
                Act.getInstance().gotoIntent(this, BrowserActivity.class);
                break;
            default:
                break;
        }
    }

}
