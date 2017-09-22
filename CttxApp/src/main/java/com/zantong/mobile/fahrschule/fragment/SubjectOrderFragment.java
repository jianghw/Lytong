package com.zantong.mobile.fahrschule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobile.R;
import com.zantong.mobile.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobile.common.Injection;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.browser.PayBrowserActivity;
import com.zantong.mobile.contract.fahrschule.ISubjectOrderContract;
import com.zantong.mobile.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobile.eventbus.SubjectOrderEvent;
import com.tzly.annual.base.bean.response.SubjectGoodsBean;
import com.zantong.mobile.presenter.fahrschule.SubjectOrderPresenter;
import com.zantong.mobile.login_v.OldLoginActivity;
import com.zantong.mobile.utils.StringUtils;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ToastUtils;

/**
 * 科目强化订单确认页面
 */
public class SubjectOrderFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISubjectOrderContract.ISubjectOrderView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView mTvOrderNum;

    private TextView mTvCourseName;
    private TextView mTvUser;
    private TextView mTvPhone;

    /**
     * 订单金额
     */
    private TextView mTvPriceTitle;
    /**
     * 订单金额
     */
    private TextView mTvPrice;
    /**
     * 提交订单
     */
    private TextView mTvCommit;

    private ISubjectOrderContract.ISubjectOrderPresenter mPresenter;
    private ISubjectSwitcherListener mSwitcherListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static SubjectOrderFragment newInstance() {
        return new SubjectOrderFragment();
    }

    public static SubjectOrderFragment newInstance(String param1, String param2) {
        SubjectOrderFragment fragment = new SubjectOrderFragment();
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
        return R.layout.fragment_subject_order;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        SubjectOrderPresenter mPresenter = new SubjectOrderPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(ISubjectOrderContract.ISubjectOrderPresenter presenter) {
        mPresenter = presenter;
    }

    public void setSwitcherListener(ISubjectSwitcherListener switcherListener) {
        mSwitcherListener = switcherListener;
    }

    @Override
    protected void onFirstDataVisible() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void DestroyViewAndThing() {
        EventBus.getDefault().removeStickyEvent(SubjectOrderEvent.class);
        EventBus.getDefault().unregister(this);

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        mTvCourseName = (TextView) view.findViewById(R.id.tv_course_name);
        mTvUser = (TextView) view.findViewById(R.id.tv_user);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);

        mTvPriceTitle = (TextView) view.findViewById(R.id.tv_price_title);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(SubjectOrderEvent event) {
        if (event != null) initData(event);
    }

    /**
     * 优惠卷 type  优惠券类型：1 无；2 折扣；3 代金券
     */
    private void initData(SubjectOrderEvent event) {
        mTvOrderNum.setText(event.getOrderId());
        SubjectGoodsBean goodsBean = event.getGoodsBean();

        if (goodsBean != null) {
            mTvCourseName.setText(goodsBean.getGoods().getName());
            mTvUser.setText(event.getEditName());
            mTvPhone.setText(event.getPhone());
        }

        mTvPrice.setText(event.getPrice());
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_commit:
                dataFormValidation();
                break;
        }
    }

    /**
     * 数据验证
     */
    private void dataFormValidation() {

        String moneyString = mTvPrice.getText().toString();

        double money = Double.parseDouble(moneyString);
        int intMoney = (int) money * 100;
        String stringMoney = String.valueOf(intMoney);
        if (mPresenter != null) mPresenter.getBankPayHtml(
                mTvOrderNum.getText().toString(),
                stringMoney);
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
     * 支付页面
     */
    @Override
    public void bankPayHtmlError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void bankPayHtmlSucceed(PayOrderResult result) {
        if (!PublicData.getInstance().loginFlag && !TextUtils.isEmpty(PublicData.getInstance().userID)) {
            Intent intent = new Intent(getActivity(), OldLoginActivity.class);
            getActivity().startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), PayBrowserActivity.class);
            intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付");
            intent.putExtra(JxGlobal.putExtra.web_url_extra, result.getData());
            intent.putExtra(JxGlobal.putExtra.web_order_id_extra, mTvOrderNum.getText().toString());
            getActivity().startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
        }
    }

}