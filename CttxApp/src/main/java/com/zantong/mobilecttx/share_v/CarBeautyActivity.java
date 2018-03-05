package com.zantong.mobilecttx.share_v;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.router.MainRouter;

import com.tzly.ctcyh.router.util.MobUtils;

/**
 * Created by jianghw on 2017/7/7.
 * Description: 推荐 汽车美容
 * Update by:
 * Update day:
 */

public class CarBeautyActivity extends JxBaseActivity implements View.OnClickListener {
    private ImageView mImgViolation;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_car_beauty;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("汽车保养");

        initView(childView);
    }

    @Override
    protected void initContentData() {
        MobUtils.getInstance().eventIdByUMeng(24);
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
            //                break;
            case R.id.img_violation:
                MainRouter.gotoWebHtmlActivity(this,
                        "汽车冰蜡", "http://m.hiservice.com.cn/market/icbc58");
                break;
            default:
                break;
        }
    }

}
