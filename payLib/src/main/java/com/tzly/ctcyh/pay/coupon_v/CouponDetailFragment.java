package com.tzly.ctcyh.pay.coupon_v;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.response.CodeDetailBean;
import com.tzly.ctcyh.pay.response.CodeDetailResponse;
import com.tzly.ctcyh.pay.coupon_p.CouponDetailPresenter;
import com.tzly.ctcyh.pay.coupon_p.ICouponDetailContract;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.htmltxt.HtmlHttpImageGetter;
import com.tzly.ctcyh.router.custom.htmltxt.HtmlTextView;
import com.tzly.ctcyh.router.custom.htmltxt.IHtmlTextClick;

/**
 * 优惠券详情页面
 */

public class CouponDetailFragment extends RefreshFragment
        implements ICouponDetailContract.ICouponDetailView {

    private ICouponDetailContract.ICouponDetailPresenter mPresenter;
    private TextView mTvBrand;
    private ImageView mImgInvalid;

    private TextView mTvCode;
    private TextView mTvDate;
    private HtmlTextView mTvUse;

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
        if (mPresenter != null) mPresenter.getCodeDetail();
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
        if (response instanceof CodeDetailResponse) {
            CodeDetailResponse detailResponse = (CodeDetailResponse) response;
            CodeDetailBean couponDetailBean = detailResponse.getData();
            setSimpleDataResult(couponDetailBean);
        } else
            responseError();
    }

    private void setSimpleDataResult(CodeDetailBean couponDetailBean) {
        if (couponDetailBean == null)
            responseError();
        else {
            mTvBrand.setText(couponDetailBean.getChannel());
            mTvCode.setText(couponDetailBean.getCode());
            mTvDate.setText("有效时间 " + couponDetailBean.getStartTime() + "-" + couponDetailBean.getEndTime());

            mTvUse.setClickHtml(couponDetailBean.getRegulation(),
                    new HtmlHttpImageGetter(mTvUse),
                    new IHtmlTextClick() {
                        @Override
                        public void clickLine(String url) {
                            gotoHtml(url);
                        }
                    });
        }
    }

    private void gotoHtml(String tableHtml) {
        PayRouter.gotoHtmlActivity(getActivity(), "Web页面", tableHtml);
    }

    public void initView(View view) {
        mTvBrand = (TextView) view.findViewById(R.id.tv_brand);
        mTvCode = (TextView) view.findViewById(R.id.tv_code);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvUse = (HtmlTextView) view.findViewById(R.id.tv_use);
    }
}