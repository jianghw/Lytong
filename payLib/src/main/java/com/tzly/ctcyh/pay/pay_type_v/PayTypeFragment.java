package com.tzly.ctcyh.pay.pay_type_v;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.response.CouponBean;
import com.tzly.ctcyh.pay.response.CouponResponse;
import com.tzly.ctcyh.pay.response.PayTypeBean;
import com.tzly.ctcyh.pay.response.PayTypeResponse;
import com.tzly.ctcyh.pay.response.PayTypesBean;
import com.tzly.ctcyh.pay.response.PayUrlResponse;
import com.tzly.ctcyh.pay.response.PayWeixinResponse;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.pay_type_p.IPayTypeContract;
import com.tzly.ctcyh.pay.pay_type_p.PayTypePresenter;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.FormatUtils;
import com.tzly.ctcyh.router.util.Utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 选择支付方式
 */
public class PayTypeFragment extends RefreshFragment
        implements View.OnClickListener, IPayTypeContract.IPayTypeView {

    private TextView mTvOrder;
    private LinearLayout mLayLinearOrder;
    private TextView mCombo;
    private LinearLayout mLayLinearCombo;
    private TextView mTvName;
    private LinearLayout mLayLinearName;
    private TextView mTvPhone;
    private LinearLayout mLayLinearPhone;
    private LinearLayout mLayLinearAddress;
    private LinearLayout mLayLinearArea;
    private TextView mTvArea;
    private TextView mTvAddress;
    private TextView mTvPrice;
    private LinearLayout mLayLinearPrice;
    /**
     * 畅通卡支付
     */
    private RadioButton mRbCarpay;
    private TextView mTvCarpay;
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
     * 支付模块
     */
    private IPayTypeContract.IPayTypePresenter mPresenter;
    private RadioGroup mRadioGroup;
    /**
     * 1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练 6 年检，7 保养，
     * 8 海外驾驶培训，9 换电瓶，10 一元购, 11 电影券 13,14,15
     */
    private int mCouponType;
    /**
     * 支付类型 1、2、3、4
     */
    private int mPayType;
    /**
     * 当前
     */
    private int mCouponBeanId = -1;
    /**
     * 通讯接口
     */
    private IPayTypeUi iPayTypeUi;

    public static PayTypeFragment newInstance(String extraOrder) {
        PayTypeFragment f = new PayTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.pay_type_order, extraOrder);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IPayTypeUi) {
            iPayTypeUi = (IPayTypeUi) activity;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected int fragmentView() {
        return R.layout.pay_fragment_pay_type;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        PayTypePresenter mPresenter = new PayTypePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    public void initView(View view) {
        mTvOrder = (TextView) view.findViewById(R.id.tv_order);
        mLayLinearOrder = (LinearLayout) view.findViewById(R.id.lay_linear_order);
        mCombo = (TextView) view.findViewById(R.id.combo);
        mLayLinearCombo = (LinearLayout) view.findViewById(R.id.lay_linear_combo);

        mLayLinearName = (LinearLayout) view.findViewById(R.id.lay_linear_name);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mLayLinearPhone = (LinearLayout) view.findViewById(R.id.lay_linear_phone);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mLayLinearArea = (LinearLayout) view.findViewById(R.id.lay_linear_area);
        mTvArea = (TextView) view.findViewById(R.id.tv_area);
        mLayLinearAddress = (LinearLayout) view.findViewById(R.id.lay_linear_address);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);

        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mLayLinearPrice = (LinearLayout) view.findViewById(R.id.lay_linear_price);

        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_pay);
        mRbCarpay = (RadioButton) view.findViewById(R.id.rb_carpay);
        mLineCarpay = (RelativeLayout) view.findViewById(R.id.line_carpay);
        mTvCarpay = (TextView) view.findViewById(R.id.tv_carpay);
        mTvCarpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayRouter.gotoWebHtmlActivity(getActivity(),
                        "申办工行卡",
                        "http://icbccard.una-campaign.com/?cid=283");
            }
        });

        mRbUnionpay = (RadioButton) view.findViewById(R.id.rb_unionpay);
        mLineUnionpay = (RelativeLayout) view.findViewById(R.id.line_unionpay);
        mRbAlipay = (RadioButton) view.findViewById(R.id.rb_alipay);
        mLineAlipay = (RelativeLayout) view.findViewById(R.id.line_alipay);
        mRbWeixinpay = (RadioButton) view.findViewById(R.id.rb_weixinpay);
        mImgRight = (ImageView) view.findViewById(R.id.img_right);
        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mLayReCoupon = (RelativeLayout) view.findViewById(R.id.lay_re_coupon);
        mLayReCoupon.setOnClickListener(this);

        radioButtonToPay();
    }

    /**
     * 事件监听
     */
    public void radioButtonToPay() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_carpay) {
                    mPayType = 1;
                } else if (checkedId == R.id.rb_unionpay) {
                    mPayType = 2;
                } else if (checkedId == R.id.rb_alipay) {
                    mPayType = 3;
                } else if (checkedId == R.id.rb_weixinpay) {
                    mPayType = 4;
                }
                float original = getOriginalPrice();
                //恢复不使用优惠劵
                setSubmitPrice(original);

                mCouponBeanId = -1;
                //获取优惠
                if (iPayTypeUi != null) iPayTypeUi.setPayEnable(true);

                if (mPresenter != null && mLayReCoupon.isEnabled()) mPresenter.getCouponByType();
            }
        });
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lay_re_coupon) {//优惠劵
            if (mPayType == 0) {
                toastShort("请先选择支付方式");
            } else {
                PayRouter.gotoCouponListActivity(getActivity(), mCouponType, mPayType);
            }
        }
    }

    /**
     * 支付事件
     */
    public void submitPrice() {
        if (mPayType == 1) {
            gotoCarPay();
        } else if (mPayType == 2) {
            gotoUnionPay();
        } else if (mPayType == 3) {
            gotoAliPay();
        } else if (mPayType == 4) {
            gotoWeixinPay();
        }
    }

    /**
     * 工行卡支付
     */
    private void gotoCarPay() {
        if (mPresenter != null) {
            int priceInteger = getSubmitPrice();
            mPresenter.getBankPayHtml(
                    getExtraOrderId(),
                    String.valueOf(priceInteger),
                    mCouponBeanId);
        }
    }

    /**
     * 银行卡支付
     */
    private void gotoUnionPay() {
    }

    /**
     * 微信支付
     */
    private void gotoWeixinPay() {
        if (mPresenter != null) {
            int priceInteger = getSubmitPrice();
            mPresenter.weChatPay(
                    getExtraOrderId(),
                    String.valueOf(priceInteger));
        }
    }

    /**
     * 支付宝
     */
    private void gotoAliPay() {
        //single_top
        PayRouter.gotoAliHtmlActivity(getContext(),
                "支付宝支付", getExtraOrderId(), mPayType, getSubmitPrice(), mCouponBeanId, null);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getOrderInfo();
    }

    @Override
    public void setPresenter(IPayTypeContract.IPayTypePresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 优惠券id
     */
    @Override
    public int getCouponUserId() {
        return mCouponBeanId;
    }

    @Override
    public String getExtraOrderId() {
        return getArguments().getString(PayGlobal.putExtra.pay_type_order);
    }

    /**
     * 数据显示
     */
    @Override
    protected void responseData(Object response) {
        if (!(response instanceof PayTypeResponse))
            responseError();
        else {
            PayTypeResponse payTypeResponse = (PayTypeResponse) response;
            PayTypeBean payTypeBean = payTypeResponse.getData();
            if (payTypeBean == null) return;

            mCouponType = payTypeBean.getBusiness();
            mTvOrder.setText(FormatUtils.textForNull(getExtraOrderId()));
            mCombo.setText(FormatUtils.textForNull(payTypeBean.getDescription()));

            String name = payTypeBean.getName();
            mTvName.setText(name);
            mLayLinearName.setVisibility(TextUtils.isEmpty(name) ? View.GONE : View.VISIBLE);
            String phone = payTypeBean.getPhone();
            mTvPhone.setText(phone);
            mLayLinearPhone.setVisibility(TextUtils.isEmpty(phone) ? View.GONE : View.VISIBLE);
            String sheng = payTypeBean.getSheng();
            mTvArea.setText(sheng + "/" + FormatUtils.textForNull(payTypeBean.getShi()) + "/" + FormatUtils.textForNull(payTypeBean.getXian()));
            mLayLinearArea.setVisibility(TextUtils.isEmpty(sheng) ? View.GONE : View.VISIBLE);
            String address = payTypeBean.getAddressDetail();
            mTvAddress.setText(address);
            mLayLinearAddress.setVisibility(TextUtils.isEmpty(address) ? View.GONE : View.VISIBLE);

            //原价格
            setOriginalPrice(payTypeBean.getPrice());
            //显示价格
            setSubmitPrice(payTypeBean.getPrice());

            List<PayTypesBean> typesBeanList = payTypeBean.getPayTypes();
            for (PayTypesBean bean : typesBeanList) {
                if (bean.getPayId() == 1) {
                    mRbCarpay.setVisibility(View.VISIBLE);
                    mTvCarpay.setVisibility(View.VISIBLE);
                    mLineCarpay.setVisibility(View.VISIBLE);
                } else if (bean.getPayId() == 2) {
                    mRbUnionpay.setVisibility(View.VISIBLE);
                    mLineUnionpay.setVisibility(View.VISIBLE);
                } else if (bean.getPayId() == 3) {
                    mRbAlipay.setVisibility(View.VISIBLE);
                    if (mCouponType == 13 || mCouponType == 14 || mCouponType == 15) {
                        mRbAlipay.setText(spannableTitle("支付宝支付\n(加收1%平台手续费)", 5));
                    }
                    mLineAlipay.setVisibility(View.VISIBLE);
                } else if (bean.getPayId() == 4) {
                    mRbWeixinpay.setVisibility(View.VISIBLE);
                    if (mCouponType == 13 || mCouponType == 14 || mCouponType == 15) {
                        mRbWeixinpay.setText(spannableTitle("微信支付\n(加收1%平台手续费)", 4));
                    }
                }
            }

            //标记是否可点优惠劵
            mLayReCoupon.setEnabled(payTypeBean.getCouponUserId() <= 0);
            if (payTypeBean.getCouponUserId() > 0) mTvCoupon.setText("此订单已用优惠劵,可直接支付");

            //默认支付方式
            if (payTypeBean.getCouponUserId() <= 0) {
                if (mRbCarpay != null && mRbCarpay.getVisibility() == View.VISIBLE) {
                    mRbCarpay.setChecked(true);
                } else if (mRbWeixinpay != null && mRbWeixinpay.getVisibility() == View.VISIBLE) {
                    mRbWeixinpay.setChecked(true);
                } else if (mRbAlipay != null && mRbAlipay.getVisibility() == View.VISIBLE) {
                    mRbAlipay.setChecked(true);
                }
            }
        }
    }

    public SpannableString spannableTitle(String title, int start) {
        //创建一个 SpannableString对象
        SpannableString ali = new SpannableString(title);
        ali.setSpan(new RelativeSizeSpan(0.6f),
                start, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ali.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.res_color_red_f3)),
                start, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ali;
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

        if (requestCode == PayGlobal.requestCode.coupon_list_choice &&
                resultCode == PayGlobal.resultCode.coupon_unused) {
            //不使用优惠劵
            setSubmitPrice(getOriginalPrice());
            mCouponBeanId = 0;
            setCouponText("未使用优惠券");
        } else if (requestCode == PayGlobal.requestCode.coupon_list_choice &&
                resultCode == PayGlobal.resultCode.coupon_used) {
            //使用优惠劵
            couponResult(data);
        } else if (requestCode == PayGlobal.requestCode.pay_html_price &&
                resultCode == PayGlobal.resultCode.web_pay_succeed) {
            toastShort("支付完成");
            //前往 完成页面
            String orderId = getExtraOrderId();
            PayRouter.gotoOrderSucceedActivity(getActivity(), orderId, mCouponType);
        } else if (requestCode == PayGlobal.requestCode.pay_html_price &&
                resultCode == PayGlobal.resultCode.web_pay_error) {
            toastShort("未完成支付");
            PayRouter.gotoMainActivity(getActivity(), 1);
            //前往 订单详情页面
            //            String orderId = getExtraOrderId();
            //            PayRouter.gotoOrderDetailActivity(getActivity(), orderId, mCouponType);
        }
    }

    /**
     * 优惠巻回调
     */
    protected void couponResult(Intent data) {
        if (data == null || data.getExtras() == null) return;
        CouponBean bean = data.getExtras().getParcelable(PayGlobal.putExtra.coupon_list_bean);
        if (bean == null) return;

        mCouponBeanId = bean.getCouponUserId();
        setCouponText("已选择:" + bean.getCouponUse());
        int couponValue = bean.getCouponValue();
        int couponLimit = bean.getCouponLimit();

        float originalPrice = getOriginalPrice();
        float priceValue = originalPrice;

        if (couponLimit != 0) {//限制
            if (originalPrice >= couponLimit) {
                priceValue = getPriceValue(bean, couponValue, originalPrice);
            } else {
                mCouponBeanId = 0;
                setCouponText("商品价格未满足" + couponLimit + "元,不可使用优惠劵");
            }
        } else {//不限制
            priceValue = getPriceValue(bean, couponValue, originalPrice);
        }
        setSubmitPrice(priceValue);
    }

    /**
     * 价格处理
     */
    private float getPriceValue(CouponBean bean, int couponValue, float originalPrice) {
        float priceValue;
        priceValue = bean.getCouponType() == 2
                ? floatByDouble(originalPrice * (couponValue / 100.00f)) :
                bean.getCouponType() == 3 ? originalPrice - couponValue
                        : originalPrice;
        priceValue = priceValue <= 0 ? 0 : priceValue;
        return priceValue;
    }

    private float floatByDouble(float value) {
        BigDecimal bd = new BigDecimal(value);
        BigDecimal decimal = bd.setScale(2, BigDecimal.ROUND_UP);
        return decimal.floatValue();
    }

    /**
     * 畅通卡支付功能
     */
    @Override
    public void getBankPayHtmlError(String message) {
        toastShort(message);
    }

    @Override
    public void getBankPayHtmlSucceed(PayUrlResponse response) {
        //直接打开singletop
        PayRouter.gotoWebHtmlActivity(getContext(),
                "银行支付", response.getData(), getExtraOrderId(), mPayType, null);
    }

    protected void closeActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) activity.finish();
    }

    @Override
    public void weChatPayError(String message) {
        toastShort(message);
    }

    @Override
    public void weChatPaySucceed(PayWeixinResponse response) {
        //直接打开singletop
        PayRouter.gotoWebHtmlActivity(getActivity(),
                "微信支付", response.getData().getMweburl(), getExtraOrderId(), mPayType, null);
    }

    /**
     * 获取原价格
     */
    protected float getOriginalPrice() {
        String price = mTvPrice.getText().toString();
        return Float.valueOf(price);
    }

    protected void setOriginalPrice(float price) {
        mTvPrice.setText(FormatUtils.submitPrice(price));
    }

    /**
     * 提交价格
     */
    private int getSubmitPrice() {
        String price = iPayTypeUi.getSubmitPrice();
        return (int) (Float.valueOf(price) * 100);
    }

    /**
     * 只有在加油时 加费用
     */
    protected void setSubmitPrice(float price) {
        //可点击时扣费
        if (mLayReCoupon != null && (mCouponType == 13 || mCouponType == 14 || mCouponType == 15)
                && mLayReCoupon.isEnabled() && mPayType > 1) {

            BigDecimal servicePrice = new BigDecimal(getOriginalPrice())
                    .multiply(new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP))
                    .setScale(2, BigDecimal.ROUND_UP);
            price = price + servicePrice.floatValue();
        }
        if (iPayTypeUi != null) iPayTypeUi.setSubmitPrice(FormatUtils.submitPrice(price));
    }

    /**
     * 优惠劵模块
     */
    @Override
    public String getExtraType() {
        return String.valueOf(mCouponType);
    }

    @Override
    public int getPayType() {
        return mPayType;
    }

    protected void setCouponText(String text) {
        mTvCoupon.setText(text);
    }

    /**
     * 优惠---------------------------------------
     */
    @Override
    public void couponByTypeError(String message) {
        setCouponText(0 + "张可用优惠劵");
    }

    /**
     * 可使用优惠券
     */
    @Override
    public void couponByTypeSucceed(CouponResponse response) {
        if (mCouponBeanId == 0) {
            setCouponText("未使用优惠券");
            return;
        }

        List<CouponBean> list = response.getData();
        int userCount = 0;
        float priceFloat = getOriginalPrice();
        for (CouponBean bean : list) {
            int couponLimit = bean.getCouponLimit();
            if (couponLimit == 0 || priceFloat >= couponLimit) {
                userCount++;
            }
        }
        setCouponText(userCount + "张可用优惠劵");
    }
}
