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
import com.zantong.mobile.contract.fahrschule.ISparringOrderContract;
import com.zantong.mobile.eventbus.SparringOrderEvent;
import com.zantong.mobile.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobile.presenter.fahrschule.SparringOrderPresenter;
import com.zantong.mobile.login_v.OldLoginActivity;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ToastUtils;

/**
 * 驾校陪练 确认页面
 */
public class SparringOrderFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISparringOrderContract.ISparringOrderView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView mTvOrderNum;

    private TextView mTvLocale;
    private TextView mTvAddress;
    private TextView mTvMotorcycleType;
    private TextView mTvTime;
    private TextView mTvUser;
    private TextView mTvPhone;
    private TextView mTvLicense;
    private TextView mTvRemark;

    /**
     * 订单金额
     */
    private TextView mTvPrice;

    /**
     * 提交订单
     */
    private TextView mTvCommit;
    /**
     * mPresenter
     */
    private ISparringOrderContract.ISparringOrderPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static SparringOrderFragment newInstance() {
        return new SparringOrderFragment();
    }

    public static SparringOrderFragment newInstance(String param1, String param2) {
        SparringOrderFragment fragment = new SparringOrderFragment();
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
        return R.layout.fragment_sparring_order;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        SparringOrderPresenter mPresenter = new SparringOrderPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(ISparringOrderContract.ISparringOrderPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void DestroyViewAndThing() {
        EventBus.getDefault().removeStickyEvent(SparringOrderEvent.class);
        EventBus.getDefault().unregister(this);

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {

        mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        mTvLocale = (TextView) view.findViewById(R.id.tv_locale);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvMotorcycleType = (TextView) view.findViewById(R.id.tv_motorcycle_type);

        mTvTime = (TextView) view.findViewById(R.id.tv_time);
        mTvUser = (TextView) view.findViewById(R.id.tv_user);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mTvLicense = (TextView) view.findViewById(R.id.tv_license);
        mTvRemark = (TextView) view.findViewById(R.id.tv_remark);

        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(SparringOrderEvent event) {
        if (event != null) initData(event);
    }

    private void initData(SparringOrderEvent event) {
        mTvOrderNum.setText(event.getOrderId());
        CreateOrderDTO bean = event.getGoodsBean();
        if (bean == null) return;
        mTvLocale.setText(bean.getServiceArea());
        mTvAddress.setText(bean.getServiceAddress());
        mTvTime.setText(event.getTextTime());
        mTvMotorcycleType.setText(event.getCarType());
        mTvUser.setText(bean.getUserName());
        mTvPhone.setText(bean.getPhone());
        mTvLicense.setText(bean.getDriveNum());
        mTvRemark.setText(bean.getRemark());
        mTvPrice.setText(bean.getPrice());
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
        String moneyString = mTvPrice.getText().toString();

        double money = Double.parseDouble(moneyString);
        int intMoney = (int) money * 100;
        String stringMoney = String.valueOf(intMoney);
        if (mPresenter != null) mPresenter.getBankPayHtml(
                mTvOrderNum.getText().toString().trim(),
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
