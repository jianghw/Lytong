package com.zantong.mobile.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.browser.AdvBrowserActivity;
import com.zantong.mobile.utils.jumptools.Act;

import com.tzly.annual.base.global.JxConfig;
import com.tzly.annual.base.global.JxGlobal;

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
            mCurPosition = intent.getIntExtra(JxGlobal.putExtra.share_position_extra, 0);
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

        JxConfig.getInstance().eventIdByUMeng(24);
    }

    @Override
    protected void DestroyViewAndThing() {

    }

    public void initView(View view) {
//        mImgAdmissions = (ImageView) view.findViewById(R.id.img_admissions);
//        mImgAdmissions.setOnClickListener(this);
        mImgViolation = (ImageView) view.findViewById(R.id.img_violation);
        mImgViolation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.img_admissions:
//                mCurPosition = 1;
//                Intent i = new Intent();
//                i.putExtra(JxGlobal.putExtra.browser_title_extra, "车内空调养护");
//                i.putExtra(JxGlobal.putExtra.browser_url_extra, "http://m.hiservice.com.cn/activity/freekongtiaoyanghu?source=gonghangcx");
//                Act.getInstance().gotoLoginByIntent(this, AdvBrowserActivity.class, i);
//                break;
            case R.id.img_violation:
                mCurPosition = 2;
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, "汽车冰蜡");
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, "http://m.hiservice.com.cn/market/icbc58");
                Act.getInstance().gotoLoginByIntent(this, AdvBrowserActivity.class, intent);
                break;
            default:
                break;
        }
    }

}
