package com.zantong.mobilecttx.fahrschule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponBean;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectCommitContract;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.eventbus.SubjectCommitEvent;
import com.zantong.mobilecttx.eventbus.SubjectOrderEvent;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderBean;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import cn.qqtheme.framework.contract.bean.SubjectGoodsBean;
import com.zantong.mobilecttx.order.activity.CouponListActivity;
import com.zantong.mobilecttx.presenter.fahrschule.SubjectCommitPresenter;
import com.zantong.mobilecttx.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.RegexUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.ViewUtils;

/**
 * 科目强化提交订单
 */
public class SubjectCommitFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISubjectCommitContract.ISubjectCommitView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    /**
     * mPresenter
     */
    private ISubjectCommitContract.ISubjectCommitPresenter mPresenter;
    private ISubjectSwitcherListener mSwitcherListener;

    private TextView mTvCourseTitle;
    private TextView mTvCourseTime;
    /**
     * 请输入姓名
     */
    private EditText mEditName;
    /**
     * 请输入手机号码
     */
    private EditText mEditPhone;
    /**
     * 提交订单
     */
    private TextView mTvCommit;
    /**
     * 优惠卷
     */
    private TextView mTvCoupon;
    private RelativeLayout mLayCoupon;
    private TextView mTvRemark;

    private SubjectGoodsBean goodsBean;
    /**
     * 优惠券 弹出框布局
     */
    private List<RechargeCouponBean> mCouponBeanList = new ArrayList<>();
    /**
     * 是否点击获取优惠
     */
    private boolean isChoiceCoupon = true;
    /**
     * 优惠券id
     */
    private int mCouponId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static SubjectCommitFragment newInstance() {
        return new SubjectCommitFragment();
    }

    public static SubjectCommitFragment newInstance(String param1, String param2) {
        SubjectCommitFragment fragment = new SubjectCommitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected void onRefreshData() {
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_subject_commit;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        SubjectCommitPresenter mPresenter = new SubjectCommitPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);

        ViewUtils.editTextInputSpace(mEditName);
        ViewUtils.editTextInputSpace(mEditPhone);

        if (BuildConfig.DEBUG) {
            mEditName.setText("小姐哦啊金粉世家");
            mEditPhone.setText("15252525569");
        }
    }

    @Override
    public void setPresenter(ISubjectCommitContract.ISubjectCommitPresenter presenter) {
        mPresenter = presenter;
    }

    public void setSwitcherListener(ISubjectSwitcherListener switcherListener) {
        mSwitcherListener = switcherListener;
    }

    @Override
    protected void onFirstDataVisible() {
        EventBus.getDefault().register(this);

        //优惠劵
        if (mPresenter != null) mPresenter.getCouponByType();
    }

    @Override
    protected void DestroyViewAndThing() {
        EventBus.getDefault().removeStickyEvent(SubjectCommitEvent.class);
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) mPresenter.unSubscribe();

        if (!mCouponBeanList.isEmpty()) mCouponBeanList.clear();
        goodsBean = null;
    }

    private void initView(View view) {
        mTvCourseTitle = (TextView) view.findViewById(R.id.tv_course_title);
        mTvCourseTime = (TextView) view.findViewById(R.id.tv_course_time);
        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);

        mLayCoupon = (RelativeLayout) view.findViewById(R.id.lay_coupon);
        mLayCoupon.setOnClickListener(this);
        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
    }

    /**
     * 数值传递
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(SubjectCommitEvent event) {
        if (event != null) initData(event);
    }

    private void initData(SubjectCommitEvent event) {
        goodsBean = event.getSubjectGoodsBean();
        mTvCourseTitle.setText(goodsBean.getGoods().getName());

        String timeFormat = getResources().getString(R.string.tv_subject_time);
        List<String> details = goodsBean.getDetails();
        String textTime = "数据出错";
        if (details.size() > 1) {
            textTime = String.format(timeFormat, details.get(0), details.get(1));
        }
        mTvCourseTime.setText(textTime);

        mTvRemark.setText(event.getRemarkBean().getRemark2());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                dataFormValidation();
                break;
            case R.id.lay_coupon://优惠价
                if (mCouponBeanList == null || mCouponBeanList.size() < 1) {
                    if (mPresenter != null) mPresenter.getCouponByType();
                } else {
                    Intent intent = new Intent(getActivity(), CouponListActivity.class);
                    Bundle bundle = new Bundle();

                    ArrayList<RechargeCouponBean> arrayList = new ArrayList<>();
                    arrayList.addAll(mCouponBeanList);
                    bundle.putParcelableArrayList(GlobalConstant.putExtra.recharge_coupon_extra, arrayList);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, GlobalConstant.requestCode.recharge_coupon_list);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 数据验证
     */
    private void dataFormValidation() {
        if (TextUtils.isEmpty(getEditName())) {
            ToastUtils.toastShort("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(getEditPhone()) || !RegexUtils.isMobileSimple(getEditPhone())) {
            ToastUtils.toastShort("请填写正确手机号");
            return;
        }
        if (mPresenter != null) mPresenter.createOrder();
    }

    public String getEditName() {
        return mEditName.getText().toString().trim();
    }

    public String getEditPhone() {
        return mEditPhone.getText().toString().trim();
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    /**
     * 57.获取指定类型优惠券
     */
    @Override
    public void couponByTypeError(String message) {
        displayCouponState("获取失败,点击加载");
        serverError(message);
    }

    @Override
    public void couponByTypeSucceed(RechargeCouponResult result) {
        if (result.getData() != null && result.getData().size() > 0) {
            List<RechargeCouponBean> resultData = result.getData();
            if (!mCouponBeanList.isEmpty()) mCouponBeanList.clear();
            mCouponBeanList.addAll(resultData);

            RechargeCouponBean bean = resultData.get(0);
            if (bean == null) return;
            bean.setChoice(true);//默认手动选择第一条优惠劵

            displayCouponByBean(bean);
        } else {
            displayCouponState("无优惠劵");
        }
    }

    @Override
    public String getCouponId() {
        return String.valueOf(mCouponId);
    }

    /**
     * 是否使用优惠劵
     */
    @Override
    public boolean getUseCoupon() {
        return isChoiceCoupon;
    }

    private void displayCouponByBean(RechargeCouponBean bean) {
        mCouponId = bean.getId();

        StringBuilder sb = new StringBuilder();
        sb.append(bean.getCouponContent());
        sb.append("\n");
        sb.append(bean.getCouponValidityEnd() + "到期");

        displayCouponState(sb.toString());
    }

    private void displayCouponState(String msg) {
        isChoiceCoupon = msg.contains("到期");
        setTvCoupon(msg);
    }

    public void setTvCoupon(String tvCoupon) {
        mTvCoupon.setText(tvCoupon);
    }

    @Override
    public void createOrderError(String message) {
        serverError(message);
    }

    protected void serverError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    /**
     * 创建订单成功
     */
    @Override
    public void createOrderSucceed(CreateOrderResult result) {
        CreateOrderBean resultData = result.getData();
        String orderId = resultData.getOrderId();

        if (mSwitcherListener != null) mSwitcherListener.setCurPosition(2);

        EventBus.getDefault().postSticky(
                new SubjectOrderEvent(
                        orderId, goodsBean, getEditName(), getEditPhone(), getPriceValue()));
    }

    @Override
    public String getGoodsId() {
        return String.valueOf(goodsBean.getGoods().getGoodsId());
    }

    @Override
    public String getPriceValue() {
        int price = goodsBean.getGoods().getPrice();
        String finalPrice = String.valueOf(price);
        if (isChoiceCoupon) {
            for (RechargeCouponBean bean : mCouponBeanList) {
                if (bean.isChoice()) {
                    double discount = (bean.getCouponValue() / 100.00d);
                    int couponType = bean.getCouponType();
                    finalPrice = displayPriceValue(discount, couponType, price);
                    break;
                }
            }
        }
        return finalPrice;
    }

    /**
     * 优惠 价格处理
     */
    private String displayPriceValue(double discount, int couponType, int price) {
        if (couponType == 2) {
            return StringUtils.getPriceDouble(price * discount);
        } else if (couponType == 3) {
            double value = discount * 100.00d;
            double submitPrice = (price - value) <= 0 ? 0 : (price - value);
            return StringUtils.getPriceDouble(submitPrice);
        }
        return StringUtils.getPriceDouble(price);
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
        if (couponBean != null && mCouponBeanList != null && mCouponBeanList.size() > 0) {
            for (RechargeCouponBean bean : mCouponBeanList) {
                if (bean.getId() == couponBean.getId()) {
                    bean.setChoice(true);
                    displayCouponByBean(bean);
                } else {
                    bean.setChoice(false);
                }
            }
        } else {
            displayCouponState("不使用优惠劵");
        }
    }

}
