package com.zantong.mobilecttx.chongzhi.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.PullableBaseFragment;
import com.zantong.mobilecttx.chongzhi.activity.RechargeAgreementActivity;
import com.zantong.mobilecttx.chongzhi.adapter.OilPriceAdapter;
import com.zantong.mobilecttx.chongzhi.bean.RechargeBean;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponBean;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserForPayActivity;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.contract.IRechargeAtyContract;
import com.zantong.mobilecttx.order.activity.CouponDetailActivity;
import com.zantong.mobilecttx.order.activity.CouponListActivity;
import com.zantong.mobilecttx.order.bean.CouponFragmentBean;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.UiHelpers;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;


/**
 * 加油充值页面业务
 */

public class RechargeFragment extends PullableBaseFragment
        implements IRechargeAtyContract.IRechargeAtyView, View.OnClickListener {

    private IRechargeAtyContract.IRechargeAtyPresenter mPresenter;

    /**
     * 中石化
     */
    private TextView mTvProvider;
    private LinearLayout mLayProvider;
    /**
     * 请输入19位卡号
     */
    private EditText mEditCardnum;
    private XRecyclerView mXRecyclerView;
    /**
     * 持畅通卡支付
     */
    private TextView mTvPayStyle;
    private ImageView mImgAlipayBoult;
    private RelativeLayout mRayPaystyleLayout;
    /**
     * 持畅通卡支付
     */
    private TextView mTvPayCoupon;
    private TextView mTvPayCouponDate;
    private ImageView mImgCouponState;
    private RelativeLayout mRayDiscountCoupon;
    private TextView mTvDesc;
    /**
     * 我已阅读用户充值协议
     */
    private TextView mTvAgreement;
    private TextView mTvAmount;
    /**
     * 充值
     */
    private TextView mTvCommit;
    private LinearLayout mLyBottom;
    /**
     * 默认折扣 价格提交用
     */
    private String DEFAULT_DISCOUNT_1 = "0.993";
    private String DEFAULT_DISCOUNT_2 = "1.00";
    private String CURRENT_DISCOUNT = "0.00";
    private OilPriceAdapter mAdapter;
    private List<RechargeBean> priceList;//价格
    /**
     * 供应商类型
     */
    private int mProviderType = 1;
    private RechargeDTO mRechargeDTO = new RechargeDTO();
    /**
     * 是否选择使用优惠券
     */
    private boolean isChoice = true;
    /**
     * 优惠券 弹出框布局
     */
    private List<RechargeCouponBean> couponList;
    /**
     * 支付方式  0畅通
     */
    private int mPayType = 0;
    /**
     * 优惠卷id
     */
    private Integer mCouponId;
    /**
     * 优惠卷 type  优惠券类型：1 无；2 折扣；3 代金券
     */
    private int mCouponType = 2;

    public static RechargeFragment newInstance() {
        return new RechargeFragment();
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.home_chongzhi_activity;
    }

    @Override
    protected void initFragmentView(View view) {
        mTvProvider = (TextView) view.findViewById(R.id.tv_provider);
        mLayProvider = (LinearLayout) view.findViewById(R.id.lay_provider);
        mLayProvider.setOnClickListener(this);
        mEditCardnum = (EditText) view.findViewById(R.id.edit_cardnum);
        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_list);
        mTvPayStyle = (TextView) view.findViewById(R.id.tv_pay_style);
        mImgAlipayBoult = (ImageView) view.findViewById(R.id.img_alipay_boult);
        mRayPaystyleLayout = (RelativeLayout) view.findViewById(R.id.ray_paystyle_layout);
        mRayPaystyleLayout.setOnClickListener(this);
        mTvPayCoupon = (TextView) view.findViewById(R.id.tv_pay_coupon);
        mTvPayCouponDate = (TextView) view.findViewById(R.id.tv_pay_coupon_date);
        mImgCouponState = (ImageView) view.findViewById(R.id.img_coupon_state);
        mRayDiscountCoupon = (RelativeLayout) view.findViewById(R.id.ray_discount_coupon);
        mRayDiscountCoupon.setOnClickListener(this);
        mTvDesc = (TextView) view.findViewById(R.id.tv_desc);
        mTvAgreement = (TextView) view.findViewById(R.id.tv_agreement);
        mTvAgreement.setOnClickListener(this);
        mTvAmount = (TextView) view.findViewById(R.id.tv_amount);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
        mLyBottom = (LinearLayout) view.findViewById(R.id.ly_bottom);

        mTvDesc.setText(getClickableSpan());
        mTvDesc.setMovementMethod(LinkMovementMethod.getInstance());

        //TODO 保存的卡号 显示
        mEditCardnum.setText(SPUtils.getInstance().getOilCard());
    }

    /**
     * 说明文字
     */
    private SpannableString getClickableSpan() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act.getInstance().gotoIntent(getActivity(), ProblemFeedbackActivity.class);
            }
        };

        SpannableString spanableInfo = new SpannableString(
                "1. 充值成功后，在加油前把加油卡交给油站工作人员协助圈存；\n"
                        + "2. 副卡、增值税票卡不支持在线充值；\n"
                        + "3. 22：00--03：00进行系统维护，充值后将延迟到账;\n"
                        + "4. 本服务为全国加油卡代充服务，故暂不支持开具发票;\n"
                        + "5. 支付成功后将于30分钟内到账，超过24小时未到账请与我们「联系」"
        );
        //可以为多部分设置超链接
        spanableInfo.setSpan(
                new Clickable(listener),
                spanableInfo.length() - 4, spanableInfo.length(),
                Spanned.SPAN_MARK_MARK);
        return spanableInfo;
    }

    private class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        Clickable(View.OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view);
        }
    }

    @Override
    public void setPresenter(IRechargeAtyContract.IRechargeAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onLoadMoreData() {
        loadingData();
    }

    @Override
    protected void onRefreshData() {
        loadingData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mPresenter.unSubscribe();
        mRechargeDTO = null;
        if (priceList != null) priceList.clear();
        if (couponList != null) couponList.clear();
    }

    /**
     * 可下拉刷新
     *
     * @return true
     */
    @Override
    protected boolean isRefresh() {
        return false;
    }

    /**
     * 不可加载更多
     *
     * @return false
     */
    @Override
    protected boolean isLoadMore() {
        return false;
    }


    @Override
    protected void loadingData() {
        GridLayoutManager manager = new GridLayoutManager(ContextUtils.getContext(), 3);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.noMoreLoadings();

        mAdapter = new OilPriceAdapter(mRechargeDTO);
        mXRecyclerView.setAdapter(mAdapter);
//优惠劵
        if (mPresenter != null) mPresenter.getCouponByType();

//价格刷新
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (data != null && data instanceof RechargeBean && mAdapter != null) {
                    RechargeBean bean = (RechargeBean) data;
                    //充值模板号
                    mRechargeDTO.setRechargeTemplate(bean.getTemplate());
                    //加油卡面值
                    mRechargeDTO.setProdMoney(bean.getAmount());
                    //充值折扣
                    mRechargeDTO.setDiscount(bean.getDiscount());
                    displayPriceValue(bean);

                    for (RechargeBean rechargeBean : mAdapter.getAll()) {
                        rechargeBean.setCheckd(rechargeBean.getTemplate().equals(bean.getTemplate()));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 价格显示 各种状态判断
     */
    private void displayPriceValue(RechargeBean bean) {
        if (mCouponType == 2) {
            double value = Double.parseDouble(TextUtils.isEmpty(bean.getDiscount()) ? "1" : bean.getDiscount());
            double price = Double.parseDouble(bean.getAmount());
            String priceValue = StringUtils.getPriceDouble(price * value);
            mTvAmount.setText(priceValue);
        } else if (mCouponType == 3 && isChoice && mPayType == 0) {//抵扣
            double value = Double.parseDouble(TextUtils.isEmpty(bean.getDiscount()) ? "0" : bean.getDiscount()) * 100.00;
            double price = Double.parseDouble(bean.getAmount());
            double submitPrice = (price - value) <= 0 ? 0 : (price - value);
            String priceValue = String.valueOf(submitPrice);

            double doubleValue = Double.parseDouble(priceValue);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String format = decimalFormat.format(doubleValue);
            mTvAmount.setText(format);
        } else if (mCouponType == 3) {
            double value = Double.parseDouble(TextUtils.isEmpty(bean.getDiscount()) ? "1" : bean.getDiscount());
            double price = Double.parseDouble(bean.getAmount());
            String priceValue = StringUtils.getPriceDouble(price * value);
            mTvAmount.setText(priceValue);
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_provider://提供商
                DialogUtils.createSelectDialog(this.getActivity(),
                        "提供商",
                        initProviderList(),
                        new DialogUtils.DialogOnClickBack() {
                            @Override
                            public void onRechargeProviderClick(View view, Object data) {
                                initProvider(data);
                            }
                        });
                break;
            case R.id.ray_paystyle_layout://支付方式
                DialogUtils.createSelectDialog(this.getActivity(),
                        "支付方式",
                        initPayStyles(),
                        new DialogUtils.DialogOnClickBack() {
                            @Override
                            public void onRechargeProviderClick(View view, Object data) {
                                initPayStyle(data);
                            }
                        });
                break;
            case R.id.ray_discount_coupon://优惠劵
                if (initDiscountCoupon() == null || initDiscountCoupon().size() < 1) {
                    if (mPresenter != null) mPresenter.getCouponByType();
                } else {
                    Intent intent = new Intent(getActivity(), CouponListActivity.class);
                    Bundle bundle = new Bundle();
                    List<RechargeCouponBean> list = initDiscountCoupon();
                    ArrayList<RechargeCouponBean> arrayList = new ArrayList<>();
                    arrayList.addAll(list);

                    bundle.putParcelableArrayList(GlobalConstant.putExtra.recharge_coupon_extra, arrayList);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, GlobalConstant.requestCode.recharge_coupon_list);
                }
                break;
            case R.id.tv_agreement://充值协议
                Act.getInstance().gotoIntent(getActivity(), RechargeAgreementActivity.class);
                break;
            case R.id.tv_commit://提交
                submitFormValidation();
                break;
        }
    }

    /**
     * 页面回掉
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == GlobalConstant.requestCode.recharge_coupon_list
                && resultCode == GlobalConstant.resultCode.recharge_coupon_choice) {
            Bundle bundle = data.getExtras();
            RechargeCouponBean couponBean = bundle.getParcelable(
                    GlobalConstant.putExtra.recharge_coupon_bean_extra);
            couponActivityResult(couponBean);
        } else if (data != null && requestCode == GlobalConstant.requestCode.recharge_coupon_list
                && resultCode == GlobalConstant.resultCode.recharge_coupon_unchoice) {

            couponActivityResult(null);
        }
    }

    /**
     * 选择优惠后显示
     */
    private void couponActivityResult(RechargeCouponBean couponBean) {
        if (couponBean != null && couponList != null && couponList.size() > 0) {
            for (RechargeCouponBean bean : couponList) {
                if (bean.getId() == couponBean.getId()) {
                    bean.setChoice(true);
                    isChoice = bean.isChoice();
                    displayCouponByBean(bean);
                } else {
                    bean.setChoice(false);
                }
            }
        } else {
            isChoice = false;
            displayCouponState("不使用优惠劵");
        }
        priceStateList(mProviderType);
    }

    /**
     * @deprecated 旧
     */
    protected void oldChoiceCoupon() {
        DialogUtils.createCouponSelectDialog(this.getActivity(),
                "优惠劵",
                initDiscountCoupon(),
                new DialogUtils.DialogOnClickBack() {
                    @Override
                    public void onRechargeProviderClick(View view, Object data) {
                        couponDialogClickBack(data);
                    }
                },
                new DialogUtils.ActivityOnClick() {
                    @Override
                    public void onActivityCouponClick(View view) {
                        openCouponActy();
                    }
                });
    }

    /**
     * 去吧 优惠详情页面
     */
    private void openCouponActy() {
        //选择的优惠劵
        if (couponList != null && couponList.size() > 0) {
            RechargeCouponBean couponBean = null;
            for (RechargeCouponBean bean : couponList) {
                if (bean.isChoice()) {
                    couponBean = bean;
                    break;
                }
            }
            if (couponBean == null) {
                ToastUtils.toastShort("请选择优惠劵");
                return;
            }
            CouponFragmentBean couponFragmentBean = null;
            try {
                couponFragmentBean = new CouponFragmentBean();
                couponFragmentBean.setCouponId(String.valueOf(couponBean.getId()));
                couponFragmentBean.setCouponName(couponBean.getCouponName());
                couponFragmentBean.setCouponContent(couponBean.getCouponContent());
                couponFragmentBean.setCouponCode(String.valueOf(couponBean.getCouponCode()));
                couponFragmentBean.setCouponImage(couponBean.getCouponImage());
                couponFragmentBean.setCouponStatus(String.valueOf(couponBean.getCouponType()));
                couponFragmentBean.setCouponUse(couponBean.getCouponUse());
                couponFragmentBean.setCouponValidityEnd(couponBean.getCouponValidityEnd());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(getActivity(), CouponDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("CouponFragmentBean", couponFragmentBean);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /**
     * 提交用表单验证
     */
    private void submitFormValidation() {
        String card = mEditCardnum.getText().toString().trim();
        if (TextUtils.isEmpty(card)) {
            ToastUtils.toastShort("请填写正确的卡号");
        } else if (mProviderType == 1 && card.length() != 19) {
            ToastUtils.toastShort("请填写正确的19位卡号");
        } else if (mProviderType == 2 && card.length() != 16) {
            ToastUtils.toastShort("请填写正确的16位卡号");
        } else {
            if (mPresenter != null) mPresenter.addOilCreateOrder();
        }
    }

    /**
     * 价格状态
     * 1、优惠劵时改变
     * 2、供应商时改变
     * 3、支付方式时改变
     */
    private void priceStateList(int providerType) {
        if (priceList == null) priceList = new ArrayList<>();
        if (!priceList.isEmpty()) priceList.clear();
        if (mAdapter != null && !mAdapter.getAll().isEmpty()) mAdapter.removeAll();
        if (providerType == 1) {//中石化
            //不选择优惠劵时，优惠劵为0时，
            String value = isChoice && !CURRENT_DISCOUNT.equals("0.00") && mPayType == 0 ? CURRENT_DISCOUNT : DEFAULT_DISCOUNT_1;
            priceList.add(new RechargeBean("100", "10001", value, true, isChoice && mPayType == 0 ? mCouponType : 2));
            priceList.add(new RechargeBean("200", "10002", value, false, isChoice && mPayType == 0 ? mCouponType : 2));
            priceList.add(new RechargeBean("500", "10003", value, false, isChoice && mPayType == 0 ? mCouponType : 2));
            priceList.add(new RechargeBean("1000", "10004", value, false, isChoice && mPayType == 0 ? mCouponType : 2));
            mAdapter.append(priceList);
        } else if (providerType == 2) {//中石化
            String value = isChoice && !CURRENT_DISCOUNT.equals("0.00") && mPayType == 0 ? CURRENT_DISCOUNT : DEFAULT_DISCOUNT_2;
            priceList.add(new RechargeBean("100", "100082", value, true, isChoice && mPayType == 0 ? mCouponType : 2));
            priceList.add(new RechargeBean("200", "100083", value, false, isChoice && mPayType == 0 ? mCouponType : 2));
            priceList.add(new RechargeBean("500", "100084", value, false, isChoice && mPayType == 0 ? mCouponType : 2));
            mAdapter.append(priceList);
        }
        RechargeBean bean = priceList.get(0);
        //充值模板号
        mRechargeDTO.setRechargeTemplate(bean.getTemplate());
        //加油卡面值
        mRechargeDTO.setProdMoney(bean.getAmount());
        //充值折扣
        mRechargeDTO.setDiscount(bean.getDiscount());

        displayPriceValue(priceList.get(0));
    }

    /**
     * 供应商选择
     * mProviderType =1、 2
     */
    private ArrayList<CommonTwoLevelMenuBean> initProviderList() {
        ArrayList<CommonTwoLevelMenuBean> list = new ArrayList<>();
        list.add(new CommonTwoLevelMenuBean(1, "中石化", R.mipmap.icon_zhongshihua));
        list.add(new CommonTwoLevelMenuBean(2, "中石油", R.mipmap.icon_zhongshiyou));
        return list;
    }

    private void initProvider(Object data) {
        if (data != null && data instanceof CommonTwoLevelMenuBean) {
            CommonTwoLevelMenuBean bean = (CommonTwoLevelMenuBean) data;
            int id = bean.getId();
            mProviderType = id;
            if (id == 1) {
                initEditViewByProvider(bean.getContext(), 19);
            } else if (id == 2) {
                initEditViewByProvider(bean.getContext(), 16);
            } else {
                initEditViewByProvider(bean.getContext(), 19);
            }
            priceStateList(mProviderType);
        }
    }

    private void initEditViewByProvider(String context, int num) {
        mTvProvider.setText(context);
        mEditCardnum.setHint("请输入" + num + "位卡号");
        mEditCardnum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
    }

    /**
     * 支付方式
     */
    private ArrayList<CommonTwoLevelMenuBean> initPayStyles() {
        ArrayList<CommonTwoLevelMenuBean> list = new ArrayList<>();
        list.add(new CommonTwoLevelMenuBean(1, "畅通卡支付", R.mipmap.icon_changtongka));
        list.add(new CommonTwoLevelMenuBean(2, "其它银行卡支付", R.mipmap.icon_othercard));
        return list;
    }

    private void initPayStyle(Object data) {
        if (data != null && data instanceof CommonTwoLevelMenuBean) {
            CommonTwoLevelMenuBean bean = (CommonTwoLevelMenuBean) data;
            mTvPayStyle.setText(bean.getContext());
            mPayType = bean.getId() == 1 ? 0 : 1;//支付卡

            priceStateList(mProviderType);
            //优惠劵控件隐藏
            mRayDiscountCoupon.setVisibility(mPayType == 0 ? View.VISIBLE : View.GONE);

            if (bean.getId() == 1) {
                initTvPayStyleDrawable(bean);
            } else if (bean.getId() == 2) {
                UiHelpers.setTextViewIcon(getContext().getApplicationContext(),
                        mTvPayStyle,
                        bean.getImgId(),
                        -1,
                        -1,
                        UiHelpers.DRAWABLE_LEFT);
            } else {
                initTvPayStyleDrawable(bean);
            }
        }
    }

    private void initTvPayStyleDrawable(CommonTwoLevelMenuBean bean) {
        Drawable drawableLeft = getResources().getDrawable(bean.getImgId());
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        Drawable drawableRight = getResources().getDrawable(R.mipmap.icon_recommand);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        mTvPayStyle.setCompoundDrawables(drawableLeft, null, drawableRight, null);
    }

    /**
     * 优惠卷
     */
    private List<RechargeCouponBean> initDiscountCoupon() {
        return couponList;
    }

    /**
     * 点击优惠卷 回显示
     */
    private void couponDialogClickBack(Object data) {
        if (data != null && data instanceof CommonTwoLevelMenuBean) {
            CommonTwoLevelMenuBean bean = (CommonTwoLevelMenuBean) data;
            if (couponList != null && couponList.size() > 0) {
                for (RechargeCouponBean couponBean : couponList) {
                    if (couponBean.getCouponContent().equals(bean.getContext())) {
                        isChoice = !(bean.getId() == -1);
                        couponBean.setChoice(isChoice);
                        displayCouponByBean(couponBean);
                    } else {
                        couponBean.setChoice(false);
                    }
                }
            }
            priceStateList(mProviderType);
        }
    }

    /**
     * 加载提示框
     */
    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    /**
     * 获取优惠劵失败
     */
    @Override
    public void onCouponByTypeError(String message) {
        displayCouponState("获取失败,点击加载");

        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    /**
     * 优惠劵错误状态
     */
    private void displayCouponState(String msg) {
        mCouponType = 2;
        isChoice = msg.equals("无优惠劵") || msg.equals("获取失败,点击加载");
        mTvPayCoupon.setText(msg);
        mTvPayCouponDate.setVisibility(View.GONE);
        mImgCouponState.setImageResource(R.mipmap.icon_nocoupon);
    }

    /**
     * 网络获取优惠卷 信息
     *
     * @param result 折扣值
     */
    @Override
    public void onCouponByTypeSucceed(RechargeCouponResult result) {
        if (result.getData() != null) {

            List<RechargeCouponBean> resultData = result.getData();
            if (couponList == null) couponList = new ArrayList<>();
            if (!couponList.isEmpty()) couponList.clear();
            couponList.addAll(resultData);

            if (resultData.size() > 0) {
                RechargeCouponBean bean = resultData.get(0);
                if (bean == null) return;
                bean.setChoice(true);//默认手动选择第一条优惠劵
                displayCouponByBean(bean);
            } else {
                displayCouponState("无优惠劵");
            }
        } else {
            displayCouponState("无优惠劵");
        }

        priceStateList(mProviderType);
    }

    /**
     * 选择优惠券 设置当前优惠券
     */
    @SuppressLint("SetTextI18n")
    private void displayCouponByBean(RechargeCouponBean bean) {
        double value = (bean.getCouponValue() / 100.00d);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        CURRENT_DISCOUNT = decimalFormat.format(value);
        mCouponType = bean.getCouponType();

        if (isChoice) {
            mCouponId = bean.getId();
            mTvPayCoupon.setText(bean.getCouponContent());
            mImgCouponState.setImageResource(R.mipmap.icon_coupon);
            mTvPayCouponDate.setVisibility(View.VISIBLE);
            mTvPayCouponDate.setText(bean.getCouponValidityEnd() + "到期");
        } else {
            displayCouponState("不使用优惠劵");
        }
    }

    @Override
    public void addOilCreateOrderError(String msg) {
        ToastUtils.toastShort(msg);
        dismissLoadingDialog();
    }

    /**
     * 创建订单成功
     */
    @Override
    public void addOilCreateOrderSucceed(final RechargeResult result) {
        ToastUtils.toastShort(result.getResponseDesc());

        DialogUtils.createRechargeDialog(getActivity(),
                result.getData().getOrderId(),
                getRechargeMoney(),
                String.valueOf(mPayType),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPayOrderByCoupon(result);
                    }
                });
    }

    private void onPayOrderByCoupon(RechargeResult result) {
        String moneyString = getRechargeMoney();

        double money = Double.parseDouble(moneyString) * 100;
        int intMoney = (int) money;
        String stringMoney = String.valueOf(intMoney);

        if (mPresenter != null) {
            mPresenter.onPayOrderByCoupon(
                    result.getData().getOrderId(),
                    stringMoney,
                    String.valueOf(mPayType));
        }
    }

    @Override
    public RechargeDTO initRechargeDTO() {
        mRechargeDTO.setMethodType("rechargeOrder");
        String card = mEditCardnum.getText().toString().trim();
        mRechargeDTO.setOilCardNum(card);//充值的卡号
        mRechargeDTO.setOilType(mProviderType);//加油卡类型 （1:中石化、2:中石油；默认
        mRechargeDTO.setPayType(mPayType);//0：畅通卡；1：其他卡
        mRechargeDTO.setRechargeMoney(getRechargeMoney());//充值金额
        if (isChoice) mRechargeDTO.setUserCouponId(mCouponId);
        return mRechargeDTO;
    }

    /**
     * 54.充值接口 成功回调
     */
    @Override
    public void onPayOrderByCouponSucceed(PayOrderResult result) {
        PublicData.getInstance().webviewTitle = "支付";
        PublicData.getInstance().webviewUrl = result.getData();
        Act.getInstance().gotoIntentLogin(getActivity(), BrowserForPayActivity.class);
        //TODO 保存卡号
        SPUtils.getInstance().setOilCard(mRechargeDTO.getOilCardNum());
        //TODO 确保优惠劵的使用状态
        getActivity().finish();
    }

    @Override
    public void onPayOrderByCouponError(String msg) {
        dismissLoadingDialog();
        ToastUtils.showShort(getContext().getApplicationContext(), msg);
    }

    /**
     * 获取要充值的金额
     */
    public String getRechargeMoney() {
        return mTvAmount.getText().toString().trim();
    }

}
