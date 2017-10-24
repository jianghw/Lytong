package com.tzly.ctcyh.pay.pay_type_v;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;

/**
 * 选择支付方式
 */
public class PayTypeFragment extends JxBaseRefreshFragment implements View.OnClickListener {

    private TextView mTvOrder;
    private LinearLayout mLayLinearOrder;
    private TextView mCombo;
    private LinearLayout mLayLinearCombo;
    private TextView mTvName;
    private LinearLayout mLayLinearName;
    private TextView mTvPhone;
    private LinearLayout mLayLinearPhone;
    private TextView mTvPrice;
    private LinearLayout mLayLinearPrice;
    /**
     * 畅通卡支付
     */
    private RadioButton mRbCarpay;
    private RelativeLayout mLineCarpay;
    /**
     * 银联卡支付
     */
    private RadioButton mRbUnionpay;
    private RelativeLayout mLineUnionpay;
    /**
     * 支付宝
     */
    private RadioButton mRbAlipay;
    private RelativeLayout mLineAlipay;
    /**
     * 微信支付
     */
    private RadioButton mRbWeixinpay;
    private ImageView mImgRight;
    private TextView mTvCoupon;
    private RelativeLayout mLayReCoupon;
    /**
     * 金额:
     */
    private TextView mPriceTitle;
    /**
     * ￥
     */
    private TextView mPriceUnit;
    /**
     * 0.00
     */
    private TextView mTvSubmitPrice;
    /**
     * 支付
     */
    private TextView mTvPay;

    @Override
    protected void onRefreshData() {}

    @Override
    protected void onLoadMoreData() {}

    @Override
    protected int initFragmentView() {
        return R.layout.pay_fragment_pay_type;
    }

    @Override
    protected void bindFragmentView(View fragmentView) {
        initView(fragmentView);
    }

    public void initView(View view) {
        mTvOrder = (TextView) view.findViewById(R.id.tv_order);
        mLayLinearOrder = (LinearLayout) view.findViewById(R.id.lay_linear_order);
        mCombo = (TextView) view.findViewById(R.id.combo);
        mLayLinearCombo = (LinearLayout) view.findViewById(R.id.lay_linear_combo);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mLayLinearName = (LinearLayout) view.findViewById(R.id.lay_linear_name);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mLayLinearPhone = (LinearLayout) view.findViewById(R.id.lay_linear_phone);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mLayLinearPrice = (LinearLayout) view.findViewById(R.id.lay_linear_price);
        mRbCarpay = (RadioButton) view.findViewById(R.id.rb_carpay);
        mLineCarpay = (RelativeLayout) view.findViewById(R.id.line_carpay);
        mRbUnionpay = (RadioButton) view.findViewById(R.id.rb_unionpay);
        mLineUnionpay = (RelativeLayout) view.findViewById(R.id.line_unionpay);
        mRbAlipay = (RadioButton) view.findViewById(R.id.rb_alipay);
        mLineAlipay = (RelativeLayout) view.findViewById(R.id.line_alipay);
        mRbWeixinpay = (RadioButton) view.findViewById(R.id.rb_weixinpay);
        mImgRight = (ImageView) view.findViewById(R.id.img_right);
        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mLayReCoupon = (RelativeLayout) view.findViewById(R.id.lay_re_coupon);
        mLayReCoupon.setOnClickListener(this);
        mPriceTitle = (TextView) view.findViewById(R.id.price_title);
        mPriceUnit = (TextView) view.findViewById(R.id.price_unit);
        mTvSubmitPrice = (TextView) view.findViewById(R.id.tv_submit_price);
        mTvPay = (TextView) view.findViewById(R.id.tv_pay);
        mTvPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lay_re_coupon) {
        } else if (v.getId() == R.id.tv_pay) {
        }
    }

    @Override
    protected void onFirstDataVisible() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IPayTypeUi) {
            IPayTypeUi iPayTypeUi = (IPayTypeUi) activity;
        }
    }

    public static PayTypeFragment newInstance() {
        return null;
    }
}
