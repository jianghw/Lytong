package com.tzly.ctcyh.pay.pay_type_v;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.pay.BuildConfig;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.bean.response.CouponBean;
import com.tzly.ctcyh.pay.bean.response.PayTypeBean;
import com.tzly.ctcyh.pay.bean.response.PayTypeResponse;
import com.tzly.ctcyh.pay.bean.response.PayTypesBean;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.pay_type_p.IPayTypeContract;
import com.tzly.ctcyh.pay.pay_type_p.PayTypePresenter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.tzly.ctcyh.router.util.FormatUtils;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 选择支付方式
 */
public class PayTypeFragment extends JxBaseRefreshFragment
        implements View.OnClickListener, IPayTypeContract.IPayTypeView {

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
    /**
     * p
     */
    private IPayTypeContract.IPayTypePresenter mPresenter;
    private RadioGroup mRadioGroup;
    /**
     * 1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练 6 年检，7 保养，
     * 8 海外驾驶培训，9 换电瓶，10 一元购, 11 电影券
     */
    private int mCouponType;
    /**
     * 支付类型 1、2、3、4
     */
    private int mPayType;
    /**
     * 当前
     */
    private int mCouponBeanId;

    /**
     * 刷新数据
     */
    @Override
    protected void onRefreshData() {
        if (mPresenter != null) mPresenter.getOrderInfo();
    }

    @Override
    protected void onLoadMoreData() {}

    @Override
    protected int initFragmentView() {
        return R.layout.pay_fragment_pay_type;
    }

    @Override
    protected void bindFragmentView(View fragmentView) {
        initView(fragmentView);

        PayTypePresenter mPresenter = new PayTypePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
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

        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_pay);
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

        radioButtonToPay();
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lay_re_coupon) {//优惠劵
            Bundle bundle = new Bundle();
            bundle.putString(PayGlobal.putExtra.coupon_list_type, String.valueOf(mCouponType));
            bundle.putString(PayGlobal.Host.coupon_list_host, PayGlobal.Host.coupon_list_host);

            UiRouter.getInstance().openUriForResult(getActivity(),
                    PayGlobal.Scheme.pay_scheme + "://" + PayGlobal.Host.coupon_list_host,
                    bundle, PayGlobal.requestCode.coupon_list_choice);
        } else if (v.getId() == R.id.tv_pay) {
            if (mPayType == 1) {
                gotoCarPay();
            } else if (mPayType == 2) {
                gotoUnionPay();
            } else if (mPayType == 3) {
                gotoAliPay();
            } else if (mPayType == 4) {
            }
        }
    }

    private void gotoCarPay() {
        int priceInteger = getSubmitPrice();

        if (mPresenter != null)
            mPresenter.getBankPayHtml(String.valueOf(priceInteger), mCouponBeanId);
    }

    /**
     * 提交价格
     */
    private int getSubmitPrice() {
        String price = mTvSubmitPrice.getText().toString();
        return (int) (Float.valueOf(price) * 100);
    }

    /**
     * 银行卡支付
     */
    private void gotoUnionPay() {

    }

    /**
     * 支付宝
     */
    private void gotoAliPay() {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, "支付宝支付");
        String url = BuildConfig.DEBUG
                ? "http://dev.liyingtong.com/" : "http://biz.liyingtong.com/";
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("aliPay/aliPayHtml");
        sb.append("?orderId=" + getExtraOrderId());
        sb.append("&amount=" + getSubmitPrice());
        if (mCouponBeanId != 0) sb.append("&couponUserId=" + mCouponBeanId);
        bundle.putString(PayGlobal.putExtra.web_url_extra, sb.toString());
        bundle.putString(PayGlobal.putExtra.web_orderId_extra, getExtraOrderId());
        bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, mPayType);

        UiRouter.getInstance().openUriForResult(getActivity(),
                PayGlobal.Scheme.pay_scheme + "://" + PayGlobal.Host.html_5_host,
                bundle, PayGlobal.requestCode.pay_type_price);
    }

    /**
     * 事件监听
     */
    public void radioButtonToPay() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mTvPay.setEnabled(true);

                if (checkedId == R.id.rb_carpay) {
                    mPayType = 1;
                } else if (checkedId == R.id.rb_unionpay) {
                    mPayType = 2;
                } else if (checkedId == R.id.rb_alipay) {
                    mPayType = 3;
                } else if (checkedId == R.id.rb_weixinpay) {
                    mPayType = 4;
                }
            }
        });
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getOrderInfo();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IPayTypeUi) {
            IPayTypeUi iPayTypeUi = (IPayTypeUi) activity;
        }
    }

    public static PayTypeFragment newInstance(String extraOrder) {
        PayTypeFragment f = new PayTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.pay_type_order, extraOrder);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void setPresenter(IPayTypeContract.IPayTypePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public String getExtraOrderId() {
        return getArguments().getString(PayGlobal.putExtra.pay_type_order);
    }

    @Override
    public void getOrderInfoError(String message) {
        toastShore(message);
        showActivityError();
    }

    /**
     * 订单信息
     */
    @Override
    public void getOrderInfoSucceed(PayTypeResponse response) {
        showActivityContent();

        PayTypeBean payTypeBean = response.getData();
        if (payTypeBean == null) return;
        mCouponType = payTypeBean.getBusiness();

        mTvOrder.setText(FormatUtils.textForNull(getExtraOrderId()));

        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(payTypeBean.getName())) sb.append(payTypeBean.getName());
        if (!TextUtils.isEmpty(payTypeBean.getDescription())) {
            sb.append("/");
            sb.append(payTypeBean.getDescription());
        }
        mCombo.setText(FormatUtils.textForNull(sb.toString()));
        mTvPrice.setText(String.valueOf(payTypeBean.getPrice()));
        //显示价格
        mTvSubmitPrice.setText(String.valueOf(payTypeBean.getPrice()));

        List<PayTypesBean> typesBeanList = payTypeBean.getPayTypes();
        for (PayTypesBean bean : typesBeanList) {
            if (bean.getPayId() == 1) {
                mRbCarpay.setVisibility(View.VISIBLE);
                mLineCarpay.setVisibility(View.VISIBLE);
            } else if (bean.getPayId() == 2) {
                mRbUnionpay.setVisibility(View.VISIBLE);
                mLineUnionpay.setVisibility(View.VISIBLE);
            } else if (bean.getPayId() == 3) {
                mRbAlipay.setVisibility(View.VISIBLE);
                mLineAlipay.setVisibility(View.VISIBLE);
            } else if (bean.getPayId() == 4) {
                mRbWeixinpay.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 回调功能
     * <p>
     * 参数名	类型	说明
     * id	int	用户优惠券id
     * couponValidity_end	string	优惠券截止日
     * couponImage	string	优惠券图片
     * couponName	string	优惠券名称
     * couponType	int	优惠券类型：1 无；2 折扣；3 代金券
     * couponUse	string	优惠券使用说明
     * couponCode	string	优惠券编码
     * couponContent	string	优惠券内容
     * couponValue	int	优惠券的值：如果类型是折扣则为折扣值，如果是代金券则为金额
     * couponLimit	int	优惠券使用限制条件，比如满100方可使用，为0表示不限制
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //不使用优惠劵
        if (requestCode == PayGlobal.requestCode.coupon_list_choice &&
                resultCode == PayGlobal.resultCode.coupon_unused) {
            mTvSubmitPrice.setText(mTvPrice.getText().toString());

            mCouponBeanId = 0;
        } else if (requestCode == PayGlobal.requestCode.coupon_list_choice &&
                resultCode == PayGlobal.resultCode.coupon_used) {
            couponResult(data);
        } else if (requestCode == PayGlobal.requestCode.pay_type_price &&
                resultCode == PayGlobal.resultCode.web_pay_succeed) {
            toastShore("支付成功");
            //支付成功
            //        Intent intent = new Intent();
            //        if (mOrderListBean.getType() == 3) {
            //            intent.putExtra(JxGlobal.putExtra.fahrschule_position_extra, 2);
            //            Act.getInstance().gotoLoginByIntent(this, FahrschuleActivity.class, intent);
            //        } else if (mOrderListBean.getType() == 4) {
            //            intent.putExtra(JxGlobal.putExtra.fahrschule_position_extra, 3);
            //            Act.getInstance().gotoLoginByIntent(this, SubjectActivity.class, intent);
            //        } else if (mOrderListBean.getType() == 5) {
            //            intent.putExtra(JxGlobal.putExtra.fahrschule_position_extra, 2);
            //            Act.getInstance().gotoLoginByIntent(this, SparringActivity.class, intent);
            //        }
        } else if (requestCode == PayGlobal.requestCode.pay_type_price &&
                resultCode == PayGlobal.resultCode.web_pay_error) {
            toastShore("支付失败");
            //支付失败

            //前往 订单详情页面
            //        String orderId = data.getStringExtra(JxGlobal.putExtra.web_order_id_extra);
            //        String targetType = mOrderListBean.getTargetType();
            //        if (targetType.equals("0")) {//前往 订单详情页面
            //            Intent intent = new Intent(this, mOrderListBean.getType() == 6
            //                    ? AnnualDetailActivity.class : OrderDetailActivity.class);
            //            intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
            //            startActivity(intent);
        }
    }

    /**
     * 优惠巻回调
     */
    protected void couponResult(Intent data) {
        if (data != null) {
            CouponBean bean = data.getExtras()
                    .getParcelable(PayGlobal.putExtra.coupon_list_bean);

            String price = mTvPrice.getText().toString();
            float priceFloat = Float.valueOf(price);

            if (bean != null) {
                mCouponBeanId = bean.getId();
                mTvCoupon.setText(bean.getCouponUse());
                int couponValue = bean.getCouponValue();
                int couponLimit = bean.getCouponLimit();
                float priceValue = priceFloat;

                if (couponLimit != 0) {
                    if (priceFloat >= couponLimit) {
                        priceValue = bean.getCouponType() == 2
                                ? priceFloat * couponValue :
                                bean.getCouponType() == 3 ? priceFloat - couponValue
                                        : priceFloat;
                        priceValue = priceValue <= 0 ? 0 : priceValue;
                    } else {
                        mCouponBeanId = 0;
                        mTvCoupon.setText("商品价格未满足" + couponLimit + "元,不可使用优惠劵");
                    }
                } else {//不限制
                    priceValue = bean.getCouponType() == 2
                            ? priceFloat * couponValue :
                            bean.getCouponType() == 3 ? priceFloat - couponValue
                                    : priceFloat;
                    priceValue = priceValue <= 0 ? 0 : priceValue;
                }
                mTvSubmitPrice.setText(FormatUtils.submitPrice(priceValue));
            }
        }
    }

    /**
     * 畅通卡支付功能
     */
    @Override
    public void getBankPayHtmlError(String message) {
        toastShore(message);
    }

    @Override
    public void getBankPayHtmlSucceed(PayUrlResponse response) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, "畅通卡支付");
        bundle.putString(PayGlobal.putExtra.web_url_extra, response.getData());
        bundle.putString(PayGlobal.putExtra.web_orderId_extra, getExtraOrderId());
        bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, mPayType);

        UiRouter.getInstance().openUriForResult(getActivity(),
                PayGlobal.Scheme.pay_scheme + "://" + PayGlobal.Host.html_5_host,
                bundle, PayGlobal.requestCode.pay_type_price);
    }
}
