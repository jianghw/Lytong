package com.tzly.ctcyh.pay.coupon_v;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tzly.ctcyh.pay.BuildConfig;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.bean.response.CouponDetailBean;
import com.tzly.ctcyh.pay.bean.response.CouponDetailResponse;
import com.tzly.ctcyh.pay.coupon_p.CouponDetailPresenter;
import com.tzly.ctcyh.pay.coupon_p.ICouponDetailContract;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.image.ImageOptions;

import java.text.DecimalFormat;

/**
 * 优惠券详情页面
 */

public class CouponDetailFragment extends RefreshFragment
        implements ICouponDetailContract.ICouponDetailView {

    private ICouponDetailContract.ICouponDetailPresenter mPresenter;
    private ImageView mImgBrand;
    private TextView mTvBrand;
    private ImageView mImgInvalid;
    /**
     * 0
     */
    private TextView mTvPrice;
    private TextView mTvUnit;
    private TextView mTvCode;
    private TextView mTvDate;
    private TextView mTvContent;
    private TextView mTvUse;

    @Override
    protected int fragmentView() {
        return R.layout.pay_fragment_coupont_detail;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        CouponDetailPresenter mPresenter = new CouponDetailPresenter(
                InjectionRepository.provideRepository(getActivity()), this);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.couponDetail();
    }

    @Override
    public void setPresenter(ICouponDetailContract.ICouponDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String couponId() {
        return getArguments().getString(PayGlobal.putExtra.coupon_detail_id);
    }

    public static CouponDetailFragment newInstance(String couponId) {
        CouponDetailFragment f = new CouponDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.coupon_detail_id, couponId);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof CouponDetailResponse) {
            CouponDetailResponse couponResponse = (CouponDetailResponse) response;
            CouponDetailBean couponDetailBean = couponResponse.getData();
            setSimpleDataResult(couponDetailBean);
        } else
            responseError();
    }

    private void setSimpleDataResult(CouponDetailBean couponDetailBean) {
        CouponDetailBean.CouponBean couponBean = couponDetailBean.getCouponBean();
        if (couponBean == null)
            responseError();
        else {
            if (!BuildConfig.App_Url) {
                ImageLoader.getInstance().displayImage(
                        couponBean.getCouponImage(), mImgBrand,
                        ImageOptions.getMessageOptions());
            }

            mTvBrand.setText(couponBean.getCouponName());
            String type = couponBean.getCouponType();
            String value = couponBean.getCouponValue();
            if (type.equals("2")) {
                mTvPrice.setText(new DecimalFormat("#0.#").format(Float.valueOf(value) / 10));
                mTvUnit.setText("折");
            } else if (type.equals("3")) {
                mTvPrice.setText(value);
                mTvUnit.setText("元");
            } else if (type.equals("1")) {
                mTvPrice.setText("兑换码");
                mTvUnit.setVisibility(View.INVISIBLE);
            } else {
                mTvPrice.setText("未知状态");
                mTvUnit.setVisibility(View.INVISIBLE);
            }
            mImgInvalid.setVisibility(
                    couponBean.getCouponStatus().equals("2") ? View.VISIBLE : View.GONE);

            mTvCode.setText(couponBean.getCouponCode());
            mTvDate.setText("有效时间 " + couponBean.getCouponValidityEnd());
            mTvContent.setText(couponBean.getCouponContent());
            mTvUse.setText(couponBean.getCouponUse());
        }
    }

    public void initView(View view) {
        mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
        mTvBrand = (TextView) view.findViewById(R.id.tv_brand);
        mImgInvalid = (ImageView) view.findViewById(R.id.img_invalid);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvUnit = (TextView) view.findViewById(R.id.tv_unit);
        mTvCode = (TextView) view.findViewById(R.id.tv_code);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvUse = (TextView) view.findViewById(R.id.tv_use);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
    }
}