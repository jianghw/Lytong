package com.zantong.mobilecttx.home.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.DensityUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.SystemBarTintManager;

import cn.qqtheme.framework.util.LogUtils;

/**
 * 关于我们
 *
 * @author Sandy
 *         create at 16/6/12 下午5:10
 */
public class GuideActivity extends FragmentActivity {

    RelativeLayout mLayout;
    SystemBarTintManager tintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tintManager = new SystemBarTintManager(this);

        setContentView(R.layout.activity_guide);

        mLayout = (RelativeLayout) findViewById(R.id.activity_guide_layout);
        mLayout.setPadding(0, tintManager.getConfig().getStatusBarHeight(), 0, 0);
        if (PublicData.getInstance().GUIDE_TYPE == 0) {
            guidePaiZhao();
        } else if (PublicData.getInstance().GUIDE_TYPE == 1) {
            guideXingShiZheng();
        } else if (PublicData.getInstance().GUIDE_TYPE == 2) {
            guideJiaZhao();
        } else if (PublicData.getInstance().GUIDE_TYPE == 3) {
            guideJiaZhao();
        }

    }

    @SuppressLint("SetTextI18n")
    private void guidePaiZhao() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.view_guide_paizhao, mLayout, false);
        TextView content = (TextView) view.findViewById(R.id.view_guide_paizhao_content);
        TextView title = (TextView) view.findViewById(R.id.view_guide_paizhao_tv);
        ImageView imageView = (ImageView) view.findViewById(R.id.view_guide_paizhao_iv);
        imageView.setBackgroundResource(R.drawable.common_sweep_icon_selecter);
        title.setText("扫罚单");
        content.setText("违章条码“扫一扫”,\n" + "查看方便又快捷。");
        TextView btn = (TextView) view.findViewById(R.id.view_guide_paizhao_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
                SPUtils.getInstance(GuideActivity.this).setGuideSaoFaDan(true);
                guideWeiZhang();
            }
        });
        mLayout.addView(view);
    }

    private void guideWeiZhang() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.view_guide_weizhang, mLayout, false);
        View layout = view.findViewById(R.id.view_guide_weizhang_layout);
        View mImgLayout = view.findViewById(R.id.view_guide_weizhang_layout);
        TextView content = (TextView) view.findViewById(R.id.view_guide_weizhang_content);
        TextView btn = (TextView) view.findViewById(R.id.view_guide_weizhang_ok);
        LogUtils.i("分辨率--------" + DensityUtils.getScreenWidth(this));
        if (DensityUtils.getScreenWidth(this) == 720) {
            mImgLayout.setPadding(40, 0, 0, 66);
        }
        if (DensityUtils.getScreenWidth(this) == 768) {
            mImgLayout.setPadding(43, 0, 0, 0);
        }
        if (DensityUtils.getScreenWidth(this) == 1080) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mImgLayout.setPadding(42, 0, 0, 24);
            } else {

                mImgLayout.setPadding(42, 0, 0, 99);
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance(GuideActivity.this).setGuideWeiZhang(true);
                view.setVisibility(View.INVISIBLE);
                finish();
            }
        });
        mLayout.addView(view);
    }


    protected void guideXingShiZheng() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.view_guide_paizhao, mLayout, false);
        TextView content = (TextView) view.findViewById(R.id.view_guide_paizhao_content);
        TextView title = (TextView) view.findViewById(R.id.view_guide_paizhao_tv);
        ImageView imageView = (ImageView) view.findViewById(R.id.view_guide_paizhao_iv);
        imageView.setBackgroundResource(R.mipmap.icon_add_car_camera);
        title.setText("");
        content.setText("扫描机动车行驶证，添加车辆更省心");
        TextView btn = (TextView) view.findViewById(R.id.view_guide_paizhao_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
                SPUtils.getInstance(GuideActivity.this).setGuideXingShiZheng(true);
                finish();
            }
        });
        mLayout.addView(view);
    }

    protected void guideJiaZhao() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.view_guide_paizhao, mLayout, false);
        TextView content = (TextView) view.findViewById(R.id.view_guide_paizhao_content);
        TextView title = (TextView) view.findViewById(R.id.view_guide_paizhao_tv);
        ImageView imageView = (ImageView) view.findViewById(R.id.view_guide_paizhao_iv);
        title.setText("");
        imageView.setBackgroundResource(R.mipmap.icon_add_car_camera);
        if (PublicData.getInstance().GUIDE_TYPE == 2) {
            content.setText("绑卡无需填卡号，驾驶证扫一扫，资料填写更简单。");
        } else {
            content.setText("填写信息很麻烦，扫描驾驶证，我们帮您填好啦。");
        }

        TextView btn = (TextView) view.findViewById(R.id.view_guide_paizhao_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
                if (PublicData.getInstance().GUIDE_TYPE == 2) {
                    SPUtils.getInstance(GuideActivity.this).setGuideJiaShiZheng(true);
                } else {
                    SPUtils.getInstance(GuideActivity.this).setGuideBanKa(true);
                }
                finish();
            }
        });
        mLayout.addView(view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return false;
    }
}
