package com.zantong.mobilecttx.fahrschule.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectCommitContract;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.eventbus.SubjectCommitEvent;
import com.zantong.mobilecttx.eventbus.SubjectOrderEvent;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderBean;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.bean.SubjectGoodsBean;
import com.zantong.mobilecttx.presenter.fahrschule.SubjectCommitPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private SubjectGoodsBean goodsBean;


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
    }

    @Override
    protected void DestroyViewAndThing() {
        EventBus.getDefault().removeStickyEvent(SubjectCommitEvent.class);
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) mPresenter.unSubscribe();

        goodsBean = null;
    }

    private void initView(View view) {
        mTvCourseTitle = (TextView) view.findViewById(R.id.tv_course_title);
        mTvCourseTime = (TextView) view.findViewById(R.id.tv_course_time);
        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);

        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mLayCoupon = (RelativeLayout) view.findViewById(R.id.lay_coupon);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(SubjectCommitEvent event) {
        if (event != null) initData(event);
    }

    private void initData(SubjectCommitEvent event) {
        goodsBean = event.getSubjectGoodsBean();
        mTvCourseTitle.setText(goodsBean.getName());
        mTvCourseTime.setText(goodsBean.getDataValue());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                dataFormValidation();
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
        if (TextUtils.isEmpty(getEditPhone()) || !RegexUtils.isMobileExact(getEditPhone())) {
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

    @Override
    public void createOrderError(String s) {
        dismissLoadingDialog();
        ToastUtils.toastShort(s);
    }

    @Override
    public void createOrderSucceed(CreateOrderResult result) {
        CreateOrderBean resultData = result.getData();
        String orderId = resultData.getOrderId();

        if (mSwitcherListener != null) mSwitcherListener.setCurPosition(2);
        EventBus.getDefault().postSticky(
                new SubjectOrderEvent(orderId, goodsBean, getEditName(), getEditPhone()));
    }

    @Override
    public String getGoodsId() {
        return String.valueOf(goodsBean.getGoodsId());
    }

    @Override
    public String getPriceValue() {
        return String.valueOf(goodsBean.getPrice());
    }

}
