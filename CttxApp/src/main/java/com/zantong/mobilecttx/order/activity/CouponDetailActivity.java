package com.zantong.mobilecttx.order.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.order.bean.CouponFragmentBean;
import com.zantong.mobilecttx.utils.ImageOptions;

/**
 * 优惠券详情页面
 */

public class CouponDetailActivity extends BaseJxActivity {

    private CouponFragmentBean mCouponFragmentBean;

    private ImageView mImgBrand;
    private TextView mTvBrand;
    private TextView mTvPrice;
    private TextView mTvCode;
    private TextView mTvDate;
    private TextView mTvContent;
    private ImageView mImgInvalid;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            mCouponFragmentBean = bundle.getParcelable("CouponFragmentBean");
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.coupon_detail_activity;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("优惠券详情");
        initView();

        initCouponData(mCouponFragmentBean);
    }

    @Override
    protected void DestroyViewAndThing() {
    }

    public void initView() {
        mImgBrand = (ImageView) findViewById(R.id.img_brand);
        mTvBrand = (TextView) findViewById(R.id.tv_brand);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvCode = (TextView) findViewById(R.id.tv_code);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mImgInvalid = (ImageView) findViewById(R.id.coupon_invalid);
    }

    @SuppressLint("SetTextI18n")
    private void initCouponData(CouponFragmentBean fragmentBean) {
        if (fragmentBean == null) return;
        ImageLoader.getInstance().displayImage(
                fragmentBean.getCouponImage(),
                mImgBrand,
                ImageOptions.getMessageOptions());

        mImgInvalid.setVisibility(
                fragmentBean.getCouponStatus().equals("1") ? View.VISIBLE : View.GONE);

        mTvBrand.setText(fragmentBean.getCouponName());
        mTvCode.setText(fragmentBean.getCouponCode() == null
                ? "暂时无优惠码" : fragmentBean.getCouponCode().equals("null")
                ? "暂时无优惠码" : fragmentBean.getCouponCode());

        mTvDate.setText("到期时间：" + fragmentBean.getCouponValidityEnd());

        mTvPrice.setText(fragmentBean.getCouponContent());

        mTvContent.setText(fragmentBean.getCouponUse());
    }
}